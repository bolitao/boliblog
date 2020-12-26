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

import javax.validation.constraints.NotBlank;
import java.util.Date;

@ApiModel(value = "xyz-bolitao-boliblog-entity-dbentity-MBlog")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "m_blog")
public class MBlog {
    /**
     * TODO: redis generated key
     * id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "id")
    private Long id;

    @TableField(value = "user_id")
    @ApiModelProperty(value = "user_id")
    private Long userId;

    @TableField(value = "title")
    @ApiModelProperty(value = "title")
    @NotBlank(message = "标题不能为空")
    private String title;

    @TableField(value = "description")
    @ApiModelProperty(value = "description")
    @NotBlank(message = "描述不能为空")
    private String description;

    @TableField(value = "content")
    @ApiModelProperty(value = "content")
    @NotBlank(message = "内容不能为空")
    private String content;

    @TableField(value = "created")
    @ApiModelProperty(value = "created")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;

    @TableField(value = "`status`")
    @ApiModelProperty(value = "status")
    private Byte status;

    public static final String COL_ID = "id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_TITLE = "title";

    public static final String COL_DESCRIPTION = "description";

    public static final String COL_CONTENT = "content";

    public static final String COL_CREATED = "created";

    public static final String COL_STATUS = "status";
}