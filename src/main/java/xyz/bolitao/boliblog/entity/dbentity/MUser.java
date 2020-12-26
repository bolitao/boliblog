package xyz.bolitao.boliblog.entity.dbentity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@ApiModel(value = "xyz-bolitao-boliblog-entity-dbentity-MUser")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "m_user")
public class MUser {
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "id")
    private Long id;

    @TableField(value = "username")
    @ApiModelProperty(value = "username")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @TableField(value = "avatar")
    @ApiModelProperty(value = "avatar")
    private String avatar;

    @TableField(value = "email")
    @ApiModelProperty(value = "email")
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式有误")
    private String email;

    @TableField(value = "`password`")
    @ApiModelProperty(value = "password")
    private String password;

    @TableField(value = "`status`")
    @ApiModelProperty(value = "status")
    private Integer status;

    @TableField(value = "created")
    @ApiModelProperty(value = "create date")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;

    @TableField(value = "last_login")
    @ApiModelProperty(value = "last login date")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLogin;

    public static final String COL_ID = "id";

    public static final String COL_USERNAME = "username";

    public static final String COL_AVATAR = "avatar";

    public static final String COL_EMAIL = "email";

    public static final String COL_PASSWORD = "password";

    public static final String COL_STATUS = "status";

    public static final String COL_CREATED = "created";

    public static final String COL_LAST_LOGIN = "last_login";
}