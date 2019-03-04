package com.clark.mvc;

import com.clark.mvc.handler.Handler;
import com.clark.mvc.render.DefaultRender;
import com.clark.mvc.render.InternalErrorRender;
import com.clark.mvc.render.Render;
import com.clark.util.ValidateUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;

/**
 * @Author: ClarkRao
 * @Date: 2019/3/4 21:52
 * @Description: http请求链
 */

@Data
@Slf4j
public class RequestHandlerChain {

    /**
     * Handler迭代器
     */
    private Iterator<Handler> handlerIt;

    /**
     * 请求request
     */
    private HttpServletRequest request;

    /**
     * 请求response
     */
    private HttpServletResponse response;

    /**
     * 请求http方法
     */
    private String requestMethod;

    /**
     * 请求http路径
     */
    private String requestPath;

    /**
     * 请求状态码
     */
    private int responseStatus;

    /**
     * 请求结果处理器
     */
    private Render render;

    public RequestHandlerChain(Iterator<Handler> handlerIt, HttpServletRequest request, HttpServletResponse response) {
        this.handlerIt = handlerIt;
        this.request = request;
        this.response = response;
        this.requestMethod = request.getMethod();
        this.requestPath = request.getPathInfo();
        this.responseStatus = HttpServletResponse.SC_OK;
    }

    /**
     * 执行请求链
     */
    public void doHandlerChain() {
        try {
            while (handlerIt.hasNext()) {
                if (!handlerIt.next().handle(this)) {
                    break;
                }
            }
        } catch (Exception e) {
            log.error("doHandlerChain error", e);
            render = new InternalErrorRender();
        }
    }

    /**
     * 执行渲染器
     */
    public void doRender() {
        if (ValidateUtil.isEmpty(render)) {
            render = new DefaultRender();
        }

        try {
            render.render(this);
        } catch (Exception e) {
            log.error("doRender", e);
            throw new RuntimeException(e);
        }
    }

}
