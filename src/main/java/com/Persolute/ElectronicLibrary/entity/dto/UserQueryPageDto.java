package com.Persolute.ElectronicLibrary.entity.dto;

import com.Persolute.ElectronicLibrary.entity.po.User;
import lombok.Data;

/**
 * @author Persolute
 * @version 1.0
 * @description User QueryPage Dto
 * @email 1538520381@qq.com
 * @date 2025/02/11 12:03
 */
@Data
public class UserQueryPageDto extends User {
    // 页码
    private Integer page;

    // 页大小
    private Integer pageSize;
}
