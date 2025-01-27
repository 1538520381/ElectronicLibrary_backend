package com.Persolute.ElectronicLibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Persolute
 * @version 1.0
 * @description 启动类
 * @email 1538520381@qq.com
 * @date 2025/01/15 12:02
 */
@SpringBootApplication
@EnableTransactionManagement
public class ElectronicLibraryApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElectronicLibraryApplication.class, args);
    }
}
