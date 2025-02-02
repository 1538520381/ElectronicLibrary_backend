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
 * @description 文件
 * @email 1538520381@qq.com
 * @date 2025/02/02 20:10
 */
@TableName("Document")
@Data
public class Document implements Serializable {
    private static final long serialVersionUID = 1L;

    // 主键
    private Long id;

    // 原文件名
    private String originalDocumentName;

    // 文件路径名
    private String documentPathName;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    // 删除标识
    private Boolean isDeleted;
}
