package com.clark.mvc;

import com.alibaba.fastjson.JSON;
import com.clark.core.BeanContainer;
import com.clark.mvc.annotation.ResponseBody;
import com.clark.mvc.bean.ModelAndView;
import com.clark.util.CastUtil;
import com.clark.util.ValidateUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: ClarkRao
 * @Date: 2019/2/24 21:19
 * @Description: 结果执行器
 */
@Slf4j
public class ResultRender {
    private BeanContainer beanContainer;

    public ResultRender() {
        beanContainer = BeanContainer.getInstance();
    }
    /**
     * 执行Controller的方法
     */
    public void invokeController(HttpServletRequest req, HttpServletResponse resp, ControllerInfo controllerInfo) {
        // 1. 获取HttpServletRequest所有参数
        Map<String, String> requestParam = getRequestParams(req);
        // 2. 实例化调用方法要传入的参数值
        List<Object> methodParams =  instantiateMethodArgs(controllerInfo.getMethodParameter(), requestParam);
        Object controller = beanContainer.getBean(controllerInfo.getControllerClass());
        Method invokeMethod = controllerInfo.getInvokeMethod();
        Object result;
        // 3. 通过反射调用方法
        try {
            if (methodParams.size() == 0) {
                result = invokeMethod.invoke(controller);
            } else {
                result = invokeMethod.invoke(controller, methodParams.toArray());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 4.解析方法的返回值，选择返回页面或者json
        resultResolver(controllerInfo, result, req, resp);
    }

    /**
     * Controller方法执行后返回值解析
     */
    private void resultResolver(ControllerInfo controllerInfo, Object result, HttpServletRequest req, HttpServletResponse resp) {
        if (ValidateUtil.isEmpty(result)) {
            return;
        }
        boolean isJson= controllerInfo.getInvokeMethod().isAnnotationPresent(ResponseBody.class);
        if (isJson) {
            // 设置响应头
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            // 向响应中写入数据
            try (PrintWriter writer = resp.getWriter()) {
                writer.write(JSON.toJSONString(result));
                writer.flush();
            } catch (IOException e) {
                log.error("转发请求失败", e);
                // TODO: 异常统一处理，400等...
            }
        } else {
            String path;
            if (result instanceof ModelAndView) {
                ModelAndView mv = (ModelAndView) result;
                path = mv.getView();
                Map<String, Object> model = mv.getModel();
                if (ValidateUtil.isNotEmpty(model)) {
                    for (Map.Entry<String, Object> entry : model.entrySet()) {
                        req.setAttribute(entry.getKey(),entry.getValue());
                    }
                } else if (result instanceof String) {
                    path = ((String) result);
                } else {
                    throw new RuntimeException("返回类型不合法");
                }
                try {
                    req.getRequestDispatcher("/templates/"+path).forward(req,resp);
                } catch (Exception e) {
                    log.error("转发请求失败", e);
                    // TODO: 异常统一处理，400等...
                }
            }
        }

    }

    /**
     * 实例化方法参数
     * @param methodParams
     * @param requestParams
     */
    private List<Object> instantiateMethodArgs(Map<String, Class<?>> methodParams, Map<String, String> requestParams) {
        return methodParams.keySet().stream().map(paramName -> {
            Class<?> type = methodParams.get(paramName);
            String requestValue = requestParams.get(paramName);
            Object value;
            if (ValidateUtil.isEmpty(requestValue)) {
                value = CastUtil.primitiveNull(type);
            } else {
                value = CastUtil.convert(type, requestValue);
                // TODO: 实现非原生类的参数实例化
            }
            return value;
        }).collect(Collectors.toList());
    }

    private Map<String, String> getRequestParams(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        //GET和POST方法是这样获取请求参数的
        request.getParameterMap().forEach((paramName,paranValues) -> {
            if (ValidateUtil.isNotEmpty(paranValues)) {
                paramMap.put(paramName,paranValues[0]);
            }
        });
        // TODO: Body、Path、Header等方式的请求参数获取
        return paramMap;
    }

}
