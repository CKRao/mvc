package com.clark;

import com.clark.aop.Aop;
import com.clark.config.Configuration;
import com.clark.core.BeanContainer;
import com.clark.ioc.Ioc;
import com.clark.mvc.server.Server;
import com.clark.mvc.server.TomcatServer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: ClarkRao
 * @Date: 2019/3/3 20:56
 * @Description: ClarkMVC Starter
 */

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClarkMVC {

    /**
     * 全局配置
     */
    @Getter
    private static Configuration configuration = Configuration.builder().build();

    /**
     * 默认服务器
     */
    @Getter
    private static Server server;

    /**
     * 启动
     * @param bootClass
     */
    public static void run(Class<?> bootClass) {
        run(Configuration.builder().bootClass(bootClass).build());
    }

    /**
     * 启动
     * @param configuration
     */
    public static void run(Configuration configuration) {
        new ClarkMVC().start(configuration);
    }
    /**
     * 启动
     */
    public static void run(Class<?> bootClass, int port) {
        run(Configuration.builder().bootClass(bootClass).serverPort(port).build());
    }

    /**
     * 初始化
     * @param configuration
     */
    private void start(Configuration configuration) {
        try {
            ClarkMVC.configuration = configuration;
            String basePackage = configuration.getBootClass().getPackage().getName();
            BeanContainer.getInstance().loadBeans(basePackage);

            //Aop必须在Ioc之前执行
            new Aop().doAop();
            new Ioc().doIoc();

            server = new TomcatServer(configuration);
            server.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
