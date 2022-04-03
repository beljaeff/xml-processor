package com.github.beljaeff.xmlprocessor.command;

import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Scope(scopeName = "prototype")
public class PrintHelp extends PrintSimple {

    @Getter
    private final Set<String> markers = new HashSet<String>() {{ add("-h"); add("--help"); }};

    @Getter
    private final String message = "Usage:\n" +
            " java -jar xml-processor.jar -h\n" +
            " java -jar xml-processor.jar -v\n" +
            " java -jar xml-processor.jar <path_1> [... <path_n>]\n" +
            "\n" +
            " -h,--help     Show this help message\n" +
            " -v,--version  Show program version" +
            "\n" +
            " <path_x>      absolute or relative path to xml file or folder containing xml files for validating\n" +
            "\n" +
            " Example:  java -jar xml-processor.jar /home/user/xml-files-folder ../xml-file.xml";
}
