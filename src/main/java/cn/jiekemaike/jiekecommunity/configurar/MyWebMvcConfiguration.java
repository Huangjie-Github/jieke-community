package cn.jiekemaike.jiekecommunity.configurar;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfiguration {

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        WebMvcConfigurer webMvcConfigurer = new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("index.html");
                registry.addViewController("/index").setViewName("index.html");
                registry.addViewController("/index.html").setViewName("index.html");
            }
        };
        return webMvcConfigurer;
    };
}
