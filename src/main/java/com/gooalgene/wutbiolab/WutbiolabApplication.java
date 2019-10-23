package com.gooalgene.wutbiolab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class WutbiolabApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(WutbiolabApplication.class, args);
        System.out.println("启动成功");
	}

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WutbiolabApplication.class);
    }
}
