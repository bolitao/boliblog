package xyz.bolitao.boliblog.security;

import cn.hutool.json.JSONUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import xyz.bolitao.boliblog.util.JwtUtil;
import xyz.bolitao.boliblog.util.Result;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author bolitao
 */
@Component
@Slf4j
public class JwtFilter extends AuthenticatingFilter {
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("Authorization");
        if (jwt == null || "".equals(jwt.trim())) {
            return null;
        }
        return new JwtToken(jwt);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setContentType("application/json; charset=utf-8");
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String jwt = request.getHeader("Authorization");
        // header 不带 token 时直接放行，交给 shiro 角色相关注解处理
        if (jwt == null || jwt.trim().length() == 0) {
            return true;
        } else {
            // 校验 jwt
            Claims claim = jwtUtil.getClaimByToken(jwt);

            if (claim == null || jwtUtil.isTokenExpired(claim.getExpiration())) {
//                throw new ExpiredCredentialsException("token已失效，请重新登录");
                // TODO: 下面的异常未被拦截，成为 ResponseEntity 形式
                response.getWriter().println(JSONUtil.toJsonStr(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Result<>("token 失效"))));
            }

            // 登录
            return executeLogin(servletRequest, servletResponse);
        }
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
                                     ServletResponse response) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setContentType("application/json; charset=utf-8");

        Throwable throwable = e.getCause() == null ? e : e.getCause();
        try {
            // TODO: 直接抛出异常
            httpServletResponse.getWriter().println(JSONUtil.toJsonStr(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result<Throwable>(throwable))));
        } catch (IOException ioException) {
            log.error("登陆出错", ioException);
        }
        return false;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control" +
                "-Request-Headers"));
        // 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(org.springframework.http.HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
