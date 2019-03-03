package com.clark.mvc;

import com.clark.util.ValidateUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: ClarkRao
 * @Date: 2019/2/24 21:51
 * @Description:  DispatcherServlet 所有http请求都由此Servlet转发
 */
@Slf4j
public class DispatcherServlet extends HttpServlet {
    private ControllerHandler controllerHandler = new ControllerHandler();

    private ResultRender resultRender = new ResultRender();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置请求编码方式
        req.setCharacterEncoding("UTF-8");
        //获取请求方法和请求路径
        String requestMethod = req.getMethod();
        String requestPath = req.getPathInfo();
        log.info("[ClarkMVCConfig] {} {}", requestMethod, requestPath);
        if (requestPath.endsWith("/")) {
            requestPath = requestPath.substring(0, requestPath.length() - 1);
        }

        ControllerInfo controllerInfo = controllerHandler.getController(requestMethod, requestPath);
        log.info("{}", controllerInfo);
        if (ValidateUtil.isEmpty(controllerInfo)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        resultRender.invokeController(req, resp, controllerInfo);
    }
}
