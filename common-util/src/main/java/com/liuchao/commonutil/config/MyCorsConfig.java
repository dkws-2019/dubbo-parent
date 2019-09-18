package com.liuchao.commonutil.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class MyCorsConfig implements WebMvcConfigurer {
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        //验证信息权限
        corsConfiguration.setAllowCredentials(true);
        //域名访问权限
        corsConfiguration.addAllowedOrigin("*");
        //请求头访问权限
        corsConfiguration.addAllowedHeader("*");
        //get post权限
        corsConfiguration.addAllowedMethod("*");
        //所有url都可以访问
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

}
