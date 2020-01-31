package cn.jiekemaike.jiekecommunity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "cn.jiekemaike.jiekecommunity.mapper")
public class JiekeCommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(JiekeCommunityApplication.class, args);
    }

}
