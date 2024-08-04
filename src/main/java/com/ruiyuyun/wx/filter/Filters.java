package com.ruiyuyun.wx.filter;

import com.ruiyuyun.wx.response.Result;
import com.ruiyuyun.wx.util.JwtUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "Filters", urlPatterns = "/diary/*") //过滤器名称,过滤的url
public class Filters implements Filter {
    @Override //初始化,只调用一次
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Filters init");
        Filter.super.init(filterConfig);
    }

    @Override//拦截到请求后调用,调用多次
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        System.out.println("拦截到请求");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getRequestURI();

/*        if (isSwaggerRequest(request)) {
            // 如果是 Swagger 请求，则直接放行
            filterChain.doFilter(request, response);
        }*/

        if (url.contains("login")||url.contains("register")) {
            System.out.println("登录/注册请求放行");
            filterChain.doFilter(request, response);
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
