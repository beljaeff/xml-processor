package com.github.beljaeff.xmlprocessor.command;

import java.util.Set;

abstract class PrintSimple extends Command {

    abstract Set<String> getMarkers();

    abstract String getMessage();

    @Override
    public boolean isSuitable() {
        return commandLineArgs != null && commandLineArgs.length == 1 && getMarkers().contains(commandLineArgs[0]);
    }

    @Override
    public void execute() {
        System.out.println(getMessage());
    }
}
