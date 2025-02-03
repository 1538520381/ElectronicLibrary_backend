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
 * @description 类别
 * @email 1538520381@qq.com
 * @date 2025/01/27 13:48
 */
@TableName("Category")
@Data
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    // 主键
    private Long id;

    // 名称
    private String name;

    // 顺序
    private Integer sort;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    // 删除标识
    private Boolean isDeleted;
}
