package analyzer;

import analyzer.algorithms.TypeAlgorithm;

import java.io.File;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        String pathDirectory = args[0];
        FileTemplateDB db = new FileTemplateDB(args[1]);

        createThread(pathDirectory, db);
    }

    private static void createThread(final String pathDirectory, final FileTemplateDB db){
        File directory = new File(pathDirectory);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = Objects.requireNonNull(directory.listFiles());

            for(File file : files){
                Runnable runnable = createRun(file.getPath(), db);
                Thread thread = new Thread(runnable);
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static Runnable createRun(final String filePath, final FileTemplateDB db){
        return () -> {
            TypeAnalyzer analyzer = new TypeAnalyzer(TypeAlgorithm.RK, filePath, db);
            String type = analyzer.getFileType();
            System.out.printf("%s: %s%n", filePath, type);
        };
    }
}
