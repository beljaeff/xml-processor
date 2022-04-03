package com.github.beljaeff.xmlprocessor.command;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Component;

import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Scope(scopeName = "prototype")
public class Validate extends Command {

    private final Marshaller marshaller;

    private final Unmarshaller unmarshaller;

    @Override
    public boolean isSuitable() {
        return commandLineArgs != null && commandLineArgs.length > 0 &&
               !"-h".equals(commandLineArgs[0]) && !"--help".equals(commandLineArgs[0]) &&
               !"-v".equals(commandLineArgs[0]) && !"--version".equals(commandLineArgs[0]);
    }

    @Override
    public void execute() {
        for (String filePath : commandLineArgs) {
            processFile(Paths.get(filePath));
        }
    }

    private void processFile(Path path) {
        if (Files.isSymbolicLink(path)) {
            System.out.println("Can not read from symbolic link: " + path);
            return;
        }

        if (!Files.isReadable(path)) {
            System.out.println("Can not read: " + path);
            return;
        }

        if (Files.isDirectory(path)) {
            processDirectory(path);
            return;
        }

        if (!Files.isRegularFile(path)) {
            System.out.println("Unexpected file: " + path);
            return;
        }

        try {
            try(FileInputStream fileInputStream = new FileInputStream(path.toFile())) {
                Object entity = unmarshaller.unmarshal(new StreamSource(fileInputStream));
                DOMResult domResult = new DOMResult();
                marshaller.marshal(entity, domResult);
                System.out.println(domResult);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Validation error: " + path);
            return;
        }
        System.out.println("Validation success: " + path);
    }

    private void processDirectory(Path path) {
        try {
            try (Stream<Path> walk = Files.walk(path, 1)) {
                walk.forEach(this::processFile);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error processing directory: " + path);
        }
    }
}
