package com.js.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.security.crypto.codec.Base64;

import java.nio.charset.Charset;


public class AuthorizedRequestFilter extends ZuulFilter { //进行授权访问处理

    public String filterType() {
        //在进行zuul过滤的时候可以设置其过滤执行的位置，那么此时有如下几种类型：
        //1：pre:在请求之前执行过滤，如果要进行访问，肯定在请求前设置头信息
        //2：route:在进行路由请求的时候被调用；
        //3：post:在路由之后发送请求信息的时候被调用
        //4：error:出现错误之后进行调用
        return "pre";
    }

    public int filterOrder() { //设置一个优先级
        return 0;
    }

    public boolean shouldFilter() { //该过滤器是否要执行
        return true;
    }

    public Object run() { //表示具体的过滤执行操作
        RequestContext context = RequestContext.getCurrentContext(); //获取当前请求的上下文
        String auth = "jiashun:jiashun"; //认证的原始信息
        byte[] encodedAuth = Base64.encode(auth.getBytes(Charset.forName("US-ASCII"))); //进行一个加密处理
        //在进行授权的时候头信息内容配置的加密信息一定要与"Basic"之间有一个空格
        String authHeader = "Basic " + new String(encodedAuth);
        context.addZuulRequestHeader("Authorization",authHeader);
        return null;
    }
}
