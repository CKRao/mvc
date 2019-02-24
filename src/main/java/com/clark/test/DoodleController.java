package com.clark.test;

import com.clark.core.annotation.Controller;
import com.clark.ioc.Autowired;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: ClarkRao
 * @Date: 2019/2/24 13:29
 * @Description:
 */
@Controller
@Slf4j
public class DoodleController {
    @Autowired
    private DoodleService doodleService;

    public void hello() {
        log.info(doodleService.helloWord());
    }

    public void helloForAspect() {
        log.info("Hello Aspectj");
    }

    public void testForAspect() {
        log.info("Test Aspectj");
    }
}
