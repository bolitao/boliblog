package xyz.bolitao.boliblog.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author bolitao
 */
@Data
public class AccountProfile implements Serializable {
    private Long id;

    private String username;

    private String avatar;

    private String email;

    private Date created;

}
