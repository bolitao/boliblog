package xyz.bolitao.boliblog.security;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.bolitao.boliblog.entity.dbentity.MUser;
import xyz.bolitao.boliblog.entity.dto.AccountProfile;
import xyz.bolitao.boliblog.service.UserService;
import xyz.bolitao.boliblog.util.JwtUtil;

/**
 * @author bolitao
 */
@Component
public class AccountRealm extends AuthorizingRealm {
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Autowired
    public AccountRealm(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override

    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) token;
        String userId = jwtUtil.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();
        MUser user = userService.getById(Long.valueOf(userId));
        if (user == null) {
            // 账号不存在
            throw new UnknownAccountException("登陆出错");
        }
        if (user.getStatus() == -1) {
            throw new LockedAccountException("登陆出错");
        }

        AccountProfile accountProfile = new AccountProfile();
        BeanUtils.copyProperties(user, accountProfile);

        return new SimpleAuthenticationInfo(accountProfile, jwtToken.getCredentials(), getName());
    }
}
