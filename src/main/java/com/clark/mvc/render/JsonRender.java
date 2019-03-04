package com.clark.mvc.render;

import com.alibaba.fastjson.JSON;
import com.clark.mvc.RequestHandlerChain;
import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;

import static com.clark.mvc.handler.PreRequestHandler.CHARACTER;

/**
 * @Author: ClarkRao
 * @Date: 2019/3/4 23:20
 * @Description: 渲染json
 */
@Slf4j
public class JsonRender implements Render {

    public static final String CONTENT_TYPE_JSON = "application/json";

    private Object jsonData;

    public JsonRender(Object jsonData) {
        this.jsonData = jsonData;
    }

    @Override
    public void render(RequestHandlerChain handlerChain) throws Exception {
        //设置响应头
        handlerChain.getResponse().setContentType(CONTENT_TYPE_JSON);
        handlerChain.getResponse().setCharacterEncoding(CHARACTER);
        //向响应中写入数据
        try (PrintWriter writer = handlerChain.getResponse().getWriter()) {
            writer.write(JSON.toJSONString(jsonData));
            writer.flush();
        }
    }
}
