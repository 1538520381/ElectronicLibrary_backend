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

    // 类别主键
    private Long categoryId;

    // 书名
    private String name;

    // 封面文件主键
    private String coverDocumentId;

    // 原文件主键
    private String originalDocumentId;

    // 处理中标识(0：处理成功；1：处理中；2：处理失败)
    private Integer handlingFlag;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    // 删除标识
    private Boolean isDeleted;
}
