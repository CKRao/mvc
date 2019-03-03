package com.clark.mvc.server;

/**
 * @Author: ClarkRao
 * @Date: 2019/3/3 20:19
 * @Description: 服务器 interface
 */
public interface Server {

    /**
     * 启动服务器
     * @throws Exception
     */
    void startServer() throws Exception;


    /**
     * 停止服务器
     * @throws Exception
     */
    void stopServer() throws Exception;
}
