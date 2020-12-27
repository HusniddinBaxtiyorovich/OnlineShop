package uz.personal.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import uz.personal.config.ApplicationContextProvider;

import java.util.Locale;
import java.util.Properties;

@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
@ComponentScan(value = {"uz.personal.controller", "uz.personal.service", "uz.personal.repository"})
@NoRepositoryBean
public class WebConfigurer implements WebMvcConfigurer {

    @Bean
    public ApplicationContextProvider applicationContextProvider() {
        return new ApplicationContextProvider();
    }

//    @Lazy
//    @Bean
//    public ViewResolver viewResolver() {
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setViewClass(JstlView.class);
//        viewResolver.setPrefix("/WEB-INF/views/");
//        viewResolver.setSuffix(".html");
//        return viewResolver;
//    }

    @Bean
    public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath("classpath:/templates/");
        return bean;
    }

    @Lazy
    @Bean(name = "velocityEngine")
    public VelocityEngineFactoryBean velocityEngineFactoryBean() {
        VelocityEngineFactoryBean vefb = new VelocityEngineFactoryBean();
        Properties p = new Properties();
        p.put("velocimacro.library.autoreload", "false");
        p.put("file.resource.loader.cache", "true");
        p.put("file.resource.loader.modificationCheckInterval", "-1");
        p.put("parser.pool.size", "1000");
        p.put("resource.loader", "class");
        p.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        vefb.setVelocityProperties(p);
        return vefb;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

//        registry.addResourceHandler("/js/**")
//                .addResourceLocations("classpath:/src/main/webapp/resources/js/").setCachePeriod(3600).resourceChain(true);
//        registry.addResourceHandler("/css/**")
//                .addResourceLocations("classpath:/src/main/webapp/resources/css/").setCachePeriod(3600).resourceChain(true);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Lazy
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Lazy
    @Bean
    public SessionLocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.forLanguageTag("uz"));
        return localeResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Lazy
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }
}
