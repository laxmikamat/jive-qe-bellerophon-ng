package com.aurea.deadcode;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableJms
public class AppConfig {
    @Autowired
    protected Environment env;

    @Bean
    public Queue reposQueue() throws JMSException {
        return connectionFactory()
                .createConnection()
                .createSession(false, Session.AUTO_ACKNOWLEDGE)
                .createQueue("queue.repos");
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        final DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setConcurrency("3-10");
        factory.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        factory.setSessionTransacted(false);
        return factory;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        final String url = env.getRequiredProperty("jms.broker.url");
        return new ActiveMQConnectionFactory(url);
    }

    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("repos")
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.regex("/repos.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Dead Code Analyzer")
                .description("Dead Code Analyzer")
                .contact(new Contact("Przemyslaw Bielicki", null, null))
                .version("1.0")
                .build();
    }
}