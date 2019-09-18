package com.gooalgene.wutbiolab.config;

import com.alibaba.fastjson.JSON;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.response.common.ResultCode;
import com.gooalgene.wutbiolab.security.TokenAuthenticationFilter;
import com.gooalgene.wutbiolab.util.CacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.NullSecurityContextRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 2019/7/3 10:34
 *
 * @author XL
 * @version 1.0
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**/*.html");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http.securityContext().securityContextRepository(new NullSecurityContextRepository());
        //放开swagger
	    http.authorizeRequests().antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/configuration/ui").permitAll()
                .antMatchers("/configuration/security").permitAll()
        ;

        http.authorizeRequests().antMatchers("/api/**").permitAll();
        http.authorizeRequests().antMatchers("/user/login").permitAll();
        http.authorizeRequests().antMatchers("/user/logout").permitAll();
        http.authorizeRequests().antMatchers("/**").permitAll();
		http.csrf().disable();
		http.formLogin();

        http.addFilterBefore(new TokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		http.exceptionHandling().accessDeniedHandler((req, resp, ex) -> {
			resp.setCharacterEncoding("utf-8");
			resp.setContentType("application/json");
            CommonResponse commonResponse= ResponseUtil.error(ResultCode.ACCESS_ERROR);
            String json = JSON.toJSONString(commonResponse);
            resp.getWriter().print(json);
		});
		http.exceptionHandling().authenticationEntryPoint((req, resp, ex) -> {
			resp.setCharacterEncoding("utf-8");
			resp.setContentType("application/json");
            CommonResponse commonResponse= ResponseUtil.error(ResultCode.AUTH_ERROR);
            String json = JSON.toJSONString(commonResponse);
			resp.getWriter().print(json);
		});

		http.formLogin().loginProcessingUrl("/user/login").successHandler((request,response, authentication)->{
            Object[] objects = authentication.getAuthorities().toArray();
            StringBuilder stringBuilder=new StringBuilder();
            for (int i=0;i<objects.length;i++){
                if(i==objects.length-1){
                    stringBuilder.append(objects[i].toString());
                }else {
                    stringBuilder.append(objects[i].toString().concat(","));
                }
            }
            String roles = stringBuilder.toString();
            String jti= UUID.randomUUID().toString();
            Map<String,String> map=new HashMap<>();
            map.put("username",authentication.getName());
//            map.put("userId",authentication.getName());
            map.put("authorities",roles);
            map.put("token",jti);
            CacheUtil.setToken(jti,map);

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
		    response.getWriter().print(JSON.toJSONString(ResponseUtil.success(map)));
        }).failureHandler((request,response, e)->{
            log.info("登录失败，异常信息：{}", e.getMessage());
            String errorMessgae = "";
            if (e instanceof BadCredentialsException) {
                errorMessgae = "密码错误";
            } else if (e instanceof AuthenticationServiceException) {
                errorMessgae = "账号不存在";
            } else if (e instanceof AccountExpiredException) {
                errorMessgae = "账户异地登录";
            } else if (e instanceof AuthenticationCredentialsNotFoundException) {
                errorMessgae = "账户已过期";
            } else if (e instanceof DisabledException) {
                errorMessgae = "账户被禁用";
            } else if (e instanceof LockedException) {
                errorMessgae = "账户未审核";
            } else {
                errorMessgae = "未知错误";
            }
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            response.getWriter().print(JSON.toJSONString(ResponseUtil.error(errorMessgae)));
        });

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    public static void main(String[] args) {
        String encode = new BCryptPasswordEncoder().encode("123");
        System.out.println(encode);
    }


    @Bean
    public CacheManager cacheManager(){
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        ArrayList<CaffeineCache> caches = new ArrayList<>();
        /*CaffeineCache dbCache = new CaffeineCache(CommonConstants.DB_CONFIG_CACHE,
                Caffeine.newBuilder().recordStats()
                        .maximumSize(1000)
                        .build());*/
        CaffeineCache tokenCache = new CaffeineCache(CommonConstants.TOKEN_CACHE,
                Caffeine.newBuilder().recordStats()
//                        .expireAfterAccess(7200, TimeUnit.SECONDS)
                        .maximumSize(2000)
                        .build());
//        caches.add(dbCache);
        caches.add(tokenCache);
        cacheManager.setCaches(caches);
        return cacheManager;
    }
}
