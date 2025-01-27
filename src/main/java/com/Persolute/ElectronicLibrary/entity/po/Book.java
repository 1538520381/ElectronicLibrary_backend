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
 * @description 书本
 * @email 1538520381@qq.com
 * @date 2025/01/27 11:40
 */
@TableName("Book")
@Data
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    // 主键
    private Long id;

    // 书名
    private String name;

    // 封面路径名
    private String coverPathName;

    // 原文件路径名
    private String originalFilePathName;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    // 删除标识
    private Boolean isDeleted;
}
