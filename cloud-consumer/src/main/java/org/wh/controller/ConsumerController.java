package org.wh.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("consumer")
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("data")
    @HystrixCommand(fallbackMethod = "getDataFail")
    public String getData(){
        String url = "http://cloud-consul-producer/test/data";
        String remoteData = restTemplate.getForEntity(url,String.class).getBody();
        return "服务端获取内容为："+remoteData;
    }

    public String getDataFail(){
        return "从生产者端获取数据失败，现在返回默认处理内容";
    }

    @GetMapping("producerList")
    public Map<String,Object> getProducerList(){
        List<String> serviceList = discoveryClient.getServices();
        List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances("cloud-consul-producer");
        Map<String,Object> map = new HashMap<>();
        map.put("serviceList",serviceList);
        map.put("producerServiceInstanceList",serviceInstanceList);
        return map;
    }
}
