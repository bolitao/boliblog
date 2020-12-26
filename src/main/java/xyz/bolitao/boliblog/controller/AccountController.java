package xyz.bolitao.boliblog.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.bolitao.boliblog.entity.dbentity.MUser;
import xyz.bolitao.boliblog.entity.dto.LoginDto;
import xyz.bolitao.boliblog.service.UserService;
import xyz.bolitao.boliblog.util.JwtUtil;
import xyz.bolitao.boliblog.util.Result;

import javax.servlet.http.HttpServletResponse;

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
    public ResponseEntity<Result> login(@Validated @RequestBody LoginDto loginDto,
                                        HttpServletResponse response) {
        MUser user = userService.getOne(Wrappers.lambdaQuery(MUser.class).eq(MUser::getUsername,
                loginDto.getUsername()));
        Assert.notNull(user, "用户不存在");
        if (!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
            return ResponseEntity.ok(new Result<>("账号或密码有误"));
        }
        String jwt = jwtUtil.generateToken(user.getId());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-headers", "Authorization");
        return ResponseEntity.ok(new Result<>(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .map()));
    }

    @RequiresAuthentication
    @PostMapping(value = "/logout")
    @ApiOperation(value = "logout")
    public ResponseEntity logout() {
        SecurityUtils.getSubject().logout();
        return ResponseEntity.ok(new Result<>("登出成功"));
    }
}
