package cn.jiekemaike.jiekecommunity.configurar;

import cn.jiekemaike.jiekecommunity.interceptor.AllFilter;
import cn.jiekemaike.jiekecommunity.interceptor.UserLoginInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;

@Configuration
public class MyWebMvcConfiguration implements WebMvcConfigurer {

    @Bean
    public UserLoginInterceptor userLoginInterceptor(){
        return new UserLoginInterceptor();
    }
    @Bean
    public HttpMessageConverter<String> httpMessageConverter(){
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }
    @Bean
    public FilterRegistrationBean allFilter(){
        FilterRegistrationBean<AllFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new AllFilter());
        filterFilterRegistrationBean.addUrlPatterns("/*");
        filterFilterRegistrationBean.setName("allFilter");
        filterFilterRegistrationBean.setOrder(1);
        return filterFilterRegistrationBean;
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index.html");
        registry.addViewController("/index").setViewName("index.html");
        registry.addViewController("/index.html").setViewName("index.html");
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginInterceptor()).addPathPatterns("/profile/**","/publish/**");
    }

}
