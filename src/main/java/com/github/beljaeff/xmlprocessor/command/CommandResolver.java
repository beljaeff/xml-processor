package com.github.beljaeff.xmlprocessor.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CommandResolver {

    private final Map<Class<? extends Command>, Command> allCommands;

    public Command resolveCommand() {
        if (CollectionUtils.isEmpty(allCommands)) {
            throw new RuntimeException("There are no command resolvers!");
        }

        if (!allCommands.containsKey(PrintHelp.class)) {
            throw new RuntimeException("There is no help command resolver!");
        }

        return allCommands
                .values()
                .stream()
                .filter(Command::isSuitable)
                .findFirst()
                .orElse(allCommands.get(PrintHelp.class));
    }
}
