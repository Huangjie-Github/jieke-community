package cn.jiekemaike.jiekecommunity.interceptor;

import org.springframework.stereotype.Component;
import javax.servlet.*;
import java.io.IOException;

public class AllFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("Utf-8");
        servletResponse.setContentType("text/html;charset=UTF-8");
        System.out.println("拦截器成功");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
