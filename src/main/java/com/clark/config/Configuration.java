package com.clark.config;

import lombok.Builder;
import lombok.Getter;

/**
 * @Author: ClarkRao
 * @Date: 2019/3/3 20:07
 * @Description: 服务器相关配置
 */
@Getter
@Builder
public class Configuration {
    /**
     * 启动类
     */
    private Class<?> bootClass;
    /**
     * 资源目录
     */
    @Builder.Default
    private String resourcePath = "src/main/resources/";

    /**
     * jsp目录
     */
    @Builder.Default
    private String viewPath = "/templates/";

    /**
     * 静态文件目录
     */
    @Builder.Default
    private String assetPath = "/static/";

    /**
     * 端口号
     */
    @Builder.Default
    private int serverPort = 9090;

    /**
     * tomcat docBase目录
     */
    @Builder.Default
    private String docBase = "";

    /**
     * tomcat contextPath目录
     */
    @Builder.Default
    private String contextPath = "";
}
