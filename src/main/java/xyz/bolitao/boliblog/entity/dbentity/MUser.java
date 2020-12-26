package xyz.bolitao.boliblog.entity.dbentity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="xyz-bolitao-boliblog-entity-dbentity-MUser")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "m_user")
public class MUser {
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="")
    private Long id;

    @TableField(value = "username")
    @ApiModelProperty(value="")
    private String username;

    @TableField(value = "avatar")
    @ApiModelProperty(value="")
    private String avatar;

    @TableField(value = "email")
    @ApiModelProperty(value="")
    private String email;

    @TableField(value = "`password`")
    @ApiModelProperty(value="")
    private String password;

    @TableField(value = "`status`")
    @ApiModelProperty(value="")
    private Integer status;

    @TableField(value = "created")
    @ApiModelProperty(value="")
    private Date created;

    @TableField(value = "last_login")
    @ApiModelProperty(value="")
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