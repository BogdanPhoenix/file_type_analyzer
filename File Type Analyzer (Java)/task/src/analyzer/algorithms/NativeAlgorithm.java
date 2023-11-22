package analyzer.algorithms;

import analyzer.FileTemplateDB;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class NativeAlgorithm implements Algorithm{
    @Override
    public boolean matchBinaryDataToTemplate(String fileName, String temp) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lines.stream()
                .anyMatch(line -> line.contains(temp));
    }
}
