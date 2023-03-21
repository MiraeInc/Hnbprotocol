package com.gxenSoft.config;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

/**
 * 설정참고 : https://kim-jong-hyun.tistory.com/17
 */
@Configuration
public class PropertyConfig {

    @Profile({"local"})
    @Bean(name = "systemProperty")
    public PropertiesFactoryBean propertiesFactoryBean_Local() {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("property/application-local.properties"));

        return propertiesFactoryBean;
    }

    @Profile("live")
    @Bean(name = "systemProperty")
    public PropertiesFactoryBean propertiesFactoryBean_Live() {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("property/application-live.properties"));

        return propertiesFactoryBean;
    }

}
