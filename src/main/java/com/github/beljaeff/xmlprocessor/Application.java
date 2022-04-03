package com.github.beljaeff.xmlprocessor;

import com.github.beljaeff.xmlprocessor.command.CommandResolver;
import com.github.beljaeff.xmlprocessor.configuration.ApplicationConfiguration;
import com.github.beljaeff.xmlprocessor.configuration.ContextInitializer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        ContextInitializer contextInitializer = new ContextInitializer(args);
        contextInitializer.initialize(context);
        context.register(ApplicationConfiguration.class);
        context.refresh();
        context.getBean(CommandResolver.class)
               .resolveCommand()
               .execute();
    }
}
