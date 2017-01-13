package com.opm.conf;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.opm")
@EnableTransactionManagement
@EnableAspectJAutoProxy
@PropertySource("classpath:application.properties")
@Import({ SecurityConfig.class })
public class AppConfig extends  WebMvcConfigurerAdapter{

	@Value("${spring.mvc.view.prefix}")
	String PREFIX;
	@Value("${spring.mvc.view.suffix}")
	String SUFFIX;
	private static final int BROWSER_CACHE_CONTROL = 604800;
	
	@Bean
    public  ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass( JstlView.class);
        viewResolver.setPrefix(PREFIX);
        viewResolver.setSuffix(SUFFIX);
        viewResolver.setCache(true);
        viewResolver.setCacheLimit(31556926);
		return viewResolver;
    }
	
 	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
 		
        registry.addResourceHandler("/assets/**")
        	.addResourceLocations("/WEB-INF/assets/")
        	.setCachePeriod(BROWSER_CACHE_CONTROL)
        	.resourceChain(true);        
    }
 	
 	 @Override
     public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
         configurer.enable();
     }
 	
}
