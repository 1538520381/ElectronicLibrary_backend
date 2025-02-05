package com.Persolute.ElectronicLibrary.entity.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Persolute
 * @version 1.0
 * @description 用户
 * @email 1538520381@qq.com
 * @date 2025/02/05 14:59
 */
@TableName("User")
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    // 主键
    private Long id;

    // 账号
    private String account;

    // 密码
    private String password;

    // 类型（0：管理员；1：普通用户）
    private Integer type;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    // 删除标识
    private Boolean isDeleted;
}
