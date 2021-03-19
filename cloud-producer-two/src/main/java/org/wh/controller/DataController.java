package org.wh.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class DataController {

    @GetMapping("/data")
    public String getData(){
        return "测试-获取内容-producer-two";
    }
}
