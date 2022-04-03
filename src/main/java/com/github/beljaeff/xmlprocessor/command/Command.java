package com.github.beljaeff.xmlprocessor.command;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class Command {

    @Getter
    @Setter(onMethod_ = @Autowired)
    String[] commandLineArgs;

    public abstract boolean isSuitable();

    public abstract void execute();
}
