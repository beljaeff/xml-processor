package com.github.beljaeff.xmlprocessor.command;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Scope(scopeName = "prototype")
public class PrintVersion extends PrintSimple {

    @Value("${application.version}")
    private String version;

    @Getter
    private final Set<String> markers = new HashSet<String>() {{ add("-v"); add("--version"); }};

    public String getMessage() {
        return "XML processor version " + version;
    }
}
