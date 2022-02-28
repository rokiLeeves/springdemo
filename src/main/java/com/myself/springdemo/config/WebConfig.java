package com.myself.springdemo.config;


import com.myself.springdemo.bean.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Bean
//    public ViewResolver viewResolver(){
//        InternalResourceViewResolver view=new InternalResourceViewResolver();
//        view.setPrefix("/WEB-INF/views/");
//        view.setSuffix(".html");
//        view.setExposeContextBeansAsAttributes(true);
//        return  view;
//    }
//    @Override
//    public  void configureDefaultServletHandling(DefaultServletHandlerConfigurer df){
//        df.enable();
//    }
@Bean
public HiddenHttpMethodFilter hiddenHttpMethodFilter(){
    HiddenHttpMethodFilter hf=new HiddenHttpMethodFilter();
    hf.setMethodParam("hiddenMethod");
    return  hf;
}

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return  new WebMvcConfigurer() {
            @Override
            public void configurePathMatch(PathMatchConfigurer configurer) {
                WebMvcConfigurer.super.configurePathMatch(configurer);
                UrlPathHelper up=new UrlPathHelper();
                up.setRemoveSemicolonContent(false);
                configurer.setUrlPathHelper(up);
            }

            @Override
            public void addFormatters(FormatterRegistry registry) {
                registry.addConverter(new Converter<String, User>(){
                    @Override
                    public User convert(String source) {
                        User u=new User();
                        u.setName(source);
                        u.setAge(1);
                        return u;
                    }
                });
            }
        };
    }


}
