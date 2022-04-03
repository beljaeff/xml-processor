package com.github.beljaeff.xmlprocessor.configuration;

import com.github.beljaeff.xmlprocessor.command.Command;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("com.github.beljaeff.xmlprocessor.command")
public class ApplicationConfiguration {

    public static final String COMMAND_LINE_SOURCE_NAME = "commandLine";

    @Bean
    public String[] commandLineArgs(Environment environment) {
        return environment.getRequiredProperty(COMMAND_LINE_SOURCE_NAME, String[].class);
    }

    @Bean
    @Scope("prototype")
    public Map<Class<? extends Command>, Command> allCommands(ApplicationContext context) {
        Map<Class<? extends Command>, Command> commands = new HashMap<>();
        for (String beanName : context.getBeanNamesForType(Command.class)) {
            Command command = context.getBean(beanName, Command.class);
            commands.put(command.getClass(), context.getBean(beanName, Command.class));
        }
        return commands;
    }

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() throws Exception {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("com.github.beljaeff.xmlprocessor.model");
        /*marshaller.setContextPaths(
                "com.github.beljaeff.xmlprocessor.model.common.message",
                "com.github.beljaeff.xmlprocessor.model.common.request",
                "com.github.beljaeff.xmlprocessor.model.person"
        );*/
        marshaller.setSchemas(
//                new ClassPathResource("xsd/schema/types/common/message.xsd"),
//                new ClassPathResource("xsd/schema/types/common/primitive.xsd"),
//                new ClassPathResource("xsd/schema/types/common/request.xsd"),
//                new ClassPathResource("xsd/schema/types/person/basket-item.xsd"),
//                new ClassPathResource("xsd/schema/types/person/person.xsd"),
                new ClassPathResource("xsd/schema/validation/address-request.xsd"),
                new ClassPathResource("xsd/schema/validation/basket-request.xsd")
        );
        marshaller.setMarshallerProperties(Collections.singletonMap(JAXB_FORMATTED_OUTPUT, true));
        //marshaller.afterPropertiesSet();
        return marshaller;
    }
}
