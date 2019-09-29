package com.ecjtu.hht.booksmate.ms_oauth.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * sspring boot 2.0 WebMvcConfigurerAdapter过时解决方法:
 * 1:使用WebMvcConfigurer
 * 2:extends WebMvcConfigurationSupport （会导致springboot的自动配置失效）
 */
@Configuration
public class MyWebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("front/search");
       // registry.addViewController("/admin_index_v1.html").setViewName("admin_index_v1");
        //registry.addViewController("/admin_recharge.html").setViewName("admin_recharge");
        registry.addViewController("/500").setViewName("500");
        //registry.addViewController("/dyn_detail.html").setViewName("front/dyn_detail");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    @Bean
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        converter.setObjectMapper(mapper);
        return converter;
    }//添加转换器@Override

    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {
        //将我们定义的时间格式转换器添加到转换器列表中,
        // 这样jackson格式化时候但凡遇到Date类型就会转换成我们定义的格式
        converters.add(jackson2HttpMessageConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        List exurls=new ArrayList();
//        exurls.add("/r/validateEncoding");
//        exurls.add("/oauth/**");
//        exurls.add("/search");
//        exurls.add("/static/**");
//        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**").excludePathPatterns(exurls);
    }
}
