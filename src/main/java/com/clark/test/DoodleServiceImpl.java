package com.clark.test;

import com.clark.core.annotation.Service;

/**
 * @Author: ClarkRao
 * @Date: 2019/2/24 13:29
 * @Description:
 */
@Service
public class DoodleServiceImpl implements DoodleService {
    @Override
    public String helloWord() {
        return "hello word";
    }
}
