package com.sample.controller;

import com.clark.core.annotation.Controller;
import com.clark.mvc.annotation.RequestMapping;
import com.clark.mvc.annotation.ResponseBody;

/**
 * @Author: ClarkRao
 * @Date: 2019/3/3 21:26
 * @Description:
 */
@Controller
@RequestMapping("/")
public class ClarkController {

    @ResponseBody
    @RequestMapping("/hello")
    public String hello() {
        return "Hello ClarkMVC";
    }
}
