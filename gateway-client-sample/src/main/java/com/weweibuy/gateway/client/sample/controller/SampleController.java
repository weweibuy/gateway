package com.weweibuy.gateway.client.sample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author durenhao
 * @date 2019/6/29 22:55
 **/
@RestController
@RequestMapping("/sample")
public class SampleController {


    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }

}
