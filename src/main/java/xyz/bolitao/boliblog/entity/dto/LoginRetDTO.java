package xyz.bolitao.boliblog.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import xyz.bolitao.boliblog.entity.dbentity.MUser;

/**
 * 登录成功返回 DTO
 *
 * @author bolitao
 */
@JsonIgnoreProperties({"password", "created"})
public class LoginRetDTO extends MUser {
}
