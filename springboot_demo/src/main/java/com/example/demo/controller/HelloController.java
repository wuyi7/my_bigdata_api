package com.example.demo.controller;

import com.example.demo.bean.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: WuYi at 2023-12-06 17:35
 * @Description: 接受请求 处理请求 给出相应结果
 * @Version:1.0
 */
@Controller
@RestController//为所有方法默认添加ResponseBody
public class HelloController {

    @ResponseBody//将方法的返回值作为响应体回复给请求发送方，页面能收到这个信息
    @RequestMapping(value = "/myhello")
    public Object handle(String message) {
        System.out.println("收到请求 处理完毕");
        return new Employee(1, "Tom");
        //非字面量对象会被转化为json
    }

    //如果要传参 只需要在方法中什么同名参数即可
    @RequestMapping(value = "/myhello2")
    public Object handle2(String name, Integer age) {
        System.out.println("收到请求 处理完毕" + age + name);
        return new Employee(age, name);
        //非字面量对象会被转化为json
    }

    //如果要传入非字面量对象需要传入json格式，并且在参数处加入RequestBody注解
    //注意对象必须要有无参构造
    //只能用Postman工具发，浏览器只能发get不能post
    @RequestMapping(value = "/sendJsonParam")
    public Object handle3(@RequestBody Employee employee) {
        System.out.println("收到请求 处理完毕" + employee.getName());
        return employee;
        //非字面量对象会被转化为json
    }

    //可以直接用map传json 都不需要创建bean了
    @RequestMapping(value = "/sendJsonParam2")
    public Object handle4(@RequestBody Map<String,String> employee) {
        System.out.println("收到请求 处理完毕" + employee);
        return employee;
        //非字面量对象会被转化为json
    }
}
