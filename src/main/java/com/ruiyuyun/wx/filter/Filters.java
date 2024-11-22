package com.ruiyuyun.wx.filter;

import com.ruiyuyun.wx.response.Result;
import com.ruiyuyun.wx.util.JwtUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "Filters", urlPatterns = "/diary/*") //过滤器名称,过滤的url
@Order(1) //过滤器顺序,数字越小优先级越高
/*@Component //声明为Spring组件*/   //不可加入component注解，否则拦截所有请求
public class Filters implements Filter {
    @Override //初始化,只调用一次
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Filters init");
        Filter.super.init(filterConfig);
    }

    @Override//拦截到请求后调用,调用多次
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("拦截到请求");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        /*response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT,OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type,X-CAF-Authorization-Token,sessionToken,X-TOKEN");*/
        // 允许跨域的域名，*:代表所有域名
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 允许跨域请求的方法
        response.setHeader("Access-Control-Allow-Methods",  "POST, PUT, GET, OPTIONS, DELETE");
        // 本次许可的有效时间，单位秒，过期之前的ajax请求就无需再次进行预检啦
        // 默认是1800s,此处设置1h
        response.setHeader("Access-Control-Max-Age", "3600");
        // 允许的响应头
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, client_id, uuid, Authorization,token");
        // 支持HTTP 1.1.
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        // 支持HTTP 1.0. response.setHeader("Expires", "0");
        response.setHeader("Pragma", "no-cache");
        // 编码
        response.setCharacterEncoding("UTF-8");
        // 放行

        String url = request.getRequestURI();

/*        if (isSwaggerRequest(request)) {
            // 如果是 Swagger 请求，则直接放行
            filterChain.doFilter(request, response);
        }*/

        if (url.contains("login")||url.contains("register")/*||url.contains("swagger")*/) {
            System.out.println("登录/注册请求放行");
            filterChain.doFilter(request, response);
            return;
        }

        if("OPTIONS".equals(request.getMethod())){
            filterChain.doFilter(request,response);
            return;
        }

        String token = request.getHeader("token");//获取请求头中的token
        if (token == null) {
            System.out.println("token为空,拦截请求");
            response.sendRedirect("/diary/login");
            String json = Result.error("token为空,请重新登录").toJsonString();
            response.getWriter().write(json);
            return ;
        }
        try {
            JwtUtils.decodeUser(token);//解析token,获取用户信息
        } catch (Exception e) {//解析失败
            System.out.println("token解析失败,拦截请求");
            e.printStackTrace();
            response.sendRedirect("/diary/login");
            String json = Result.error("token解析失败,请重新登录").toJsonString();
            response.getWriter().write(json);
            return ;
        }
        System.out.println("token解析成功,放行");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override//销毁,只调用一次
    public void destroy() {
        System.out.println("Filters destroy");
        Filter.super.destroy();
    }

    private boolean isSwaggerRequest(HttpServletRequest request) {
        String requestPath = request.getRequestURI();
        // 假设Swagger UI的路径是/swagger-ui.html，Swagger JSON文档的路径是/v3/api-docs
        // 注意：这些路径可能因你的Swagger配置而异
        return requestPath.endsWith("/swagger-ui.html") ||
                requestPath.startsWith("/v3/api-docs") ||
                requestPath.startsWith("/swagger-resources") ||
                requestPath.startsWith("/webjars/");
    }
}
