package analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileTemplateDB {
    private static final Pattern PATTERN = Pattern.compile("(.*?);\"(.*?)\";\"(.*?)\"");
    private final List<FileTemplate> db;

    public FileTemplateDB(String path){
        db = createDB(path);
    }

    private static List<FileTemplate> createDB(String path){
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));

            return lines.stream()
                    .map(PATTERN::matcher)
                    .filter(Matcher::matches)
                    .map(matcher -> new FileTemplate(
                            Integer.parseInt(matcher.group(1)),
                            matcher.group(2),
                            matcher.group(3))
                    )
                    .sorted((p1, p2) -> p2.priority() - p1.priority())
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<FileTemplate> getDb(){
        return db;
    }
}
