package xyz.bolitao.boliblog.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.bolitao.boliblog.entity.dbentity.MUser;
import xyz.bolitao.boliblog.entity.dto.LoginDto;
import xyz.bolitao.boliblog.entity.dto.LoginRetDTO;
import xyz.bolitao.boliblog.exception.entity.BaseException;
import xyz.bolitao.boliblog.service.UserService;
import xyz.bolitao.boliblog.util.JwtUtil;
import xyz.bolitao.boliblog.util.Result;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping(value = "/auth")
@Api(tags = "auth")
public class AccountController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AccountController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/login")
    @ApiOperation(value = "login")
    public ResponseEntity<Result<LoginRetDTO>> login(@Validated @RequestBody LoginDto loginDto,
                                                     HttpServletResponse response) {
        // TODO: 已登录则直接返回
        // TODO: 没调用 shiro 的 login。于是 logout 接口也会提示权限相关的错误。

        MUser user = userService.getOne(Wrappers.lambdaQuery(MUser.class).eq(MUser::getUsername,
                loginDto.getUsername()));
        // 防止穷举用户名，不返回“未找到用户”提示
        // Assert.notNull(user, "账号或密码错误");
        if (user == null) {
            throw new BaseException(HttpStatus.UNAUTHORIZED, "账号或密码错误", "10001");
        }
        if (!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Result<>("1", "账号或密码有误", null));
            throw new BaseException(HttpStatus.UNAUTHORIZED, "账号或密码错误", "10001");
        }
        String jwt = jwtUtil.generateToken(user.getId());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-headers", "Authorization");
        LoginRetDTO loginRetDTO = new LoginRetDTO();
        BeanUtils.copyProperties(user, loginRetDTO);

        // 更新最后登录时间
        MUser updateUser = new MUser(new Date());
        updateUser.setId(user.getId());
        userService.updateById(updateUser);

        return ResponseEntity.ok(new Result<>("1", "登陆成功", loginRetDTO));
    }

    @RequiresAuthentication
    @PostMapping(value = "/logout")
    @ApiOperation(value = "logout")
    public ResponseEntity<Result<String>> logout() {
        SecurityUtils.getSubject().logout();
        return ResponseEntity.ok(new Result<>("1", "登出成功"));
    }
}
