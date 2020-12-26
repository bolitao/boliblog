package xyz.bolitao.boliblog.util;

import org.apache.shiro.SecurityUtils;
import xyz.bolitao.boliblog.entity.dto.AccountProfile;

/**
 * @author bolitao
 */
public class ShiroUtil {
    public static AccountProfile getAccountProfile() {
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}
