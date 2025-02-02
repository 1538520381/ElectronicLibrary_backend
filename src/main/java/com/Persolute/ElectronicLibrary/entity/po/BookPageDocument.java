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
 * @description 书本页文件
 * @email 1538520381@qq.com
 * @date 2025/01/27 11:56
 */
@TableName("BookPageDocument")
@Data
public class BookPageDocument implements Serializable {
    private static final long serialVersionUID = 1L;

    // 主键
    private Long id;

    // 书本主键
    private Long bookId;

    // 页码
    private Integer page;

    // 页文件主键
    private String pageDocumentId;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    // 删除标识
    private Boolean isDeleted;
}
