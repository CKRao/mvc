package com.clark.mvc;

import lombok.*;

/**
 * @Author: ClarkRao
 * @Date: 2019/2/24 20:50
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PathInfo {
    /**
     * http请求方法
     */
    private String httpMethod;

    /**
     * http请求路径
     */
    private String httpPath;
}
