package com.clark.mvc.render;

import com.clark.ClarkMVC;
import com.clark.mvc.RequestHandlerChain;
import com.clark.mvc.bean.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: ClarkRao
 * @Date: 2019/3/4 23:26
 * @Description: 渲染页面
 */
public class ViewRender implements Render {
    private ModelAndView mv;

    public ViewRender(Object mv) {
        if (mv instanceof ModelAndView) {
            this.mv = ((ModelAndView) mv);
        } else if (mv instanceof String) {
            this.mv = new ModelAndView();
            this.mv.setView((String) mv);
        }else {
            throw new RuntimeException("返回类型不合法");
        }
    }

    @Override
    public void render(RequestHandlerChain handlerChain) throws Exception {
        HttpServletRequest req = handlerChain.getRequest();
        HttpServletResponse resp = handlerChain.getResponse();
        String path = mv.getView();
        Map<String, Object> model = mv.getModel();
        model.forEach(req::setAttribute);
        req.getRequestDispatcher(ClarkMVC.getConfiguration().getViewPath() + path).forward(req, resp);

    }
}
