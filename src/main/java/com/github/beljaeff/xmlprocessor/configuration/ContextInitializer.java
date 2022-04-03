package com.github.beljaeff.xmlprocessor.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.lang.Nullable;

import static com.github.beljaeff.xmlprocessor.configuration.ApplicationConfiguration.COMMAND_LINE_SOURCE_NAME;

@RequiredArgsConstructor
public class ContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private final String[] commandLineArgs;

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        context
                .getEnvironment()
                .getPropertySources()
                .addFirst(new CommandLinePropertySource(commandLineArgs));
    }

    private static class CommandLinePropertySource extends EnumerablePropertySource<String[]> {

        private final String propertyName;

        public CommandLinePropertySource(String[] source) {
            this(COMMAND_LINE_SOURCE_NAME, source);
        }

        public CommandLinePropertySource(String propertySourceName, String[] source) {
            this(propertySourceName, COMMAND_LINE_SOURCE_NAME, source);
        }

        public CommandLinePropertySource(String propertySourceName, String propertyName, String[] source) {
            super(propertySourceName, source);
            this.propertyName = propertyName;
        }

        @Override
        public final boolean containsProperty(String name) {
            return propertyName.equals(name);
        }

        @Override
        public String[] getPropertyNames() {
            return new String[] { propertyName };
        }

        @Override
        @Nullable
        public final String[] getProperty(String name) {
            if (!propertyName.equals(name)) {
                return null;
            }
            return getSource();
        }
    }
}
