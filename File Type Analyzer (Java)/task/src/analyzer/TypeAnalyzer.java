package analyzer;

import analyzer.algorithms.*;

public class TypeAnalyzer {
    private static final String FAIL_FOUND = "Unknown file type";

    private final TypeAlgorithm algorithm;
    private final String filePath;
    private final FileTemplateDB db;

    public TypeAnalyzer(final String filePath, final FileTemplateDB db){
        this(TypeAlgorithm.NAIVE, filePath, db);
    }

    public TypeAnalyzer(final TypeAlgorithm typeAnalyzer, final String filePath, final FileTemplateDB db){
        this.algorithm = typeAnalyzer;
        this.filePath = filePath;
        this.db = db;
    }

    public String getFileType(){
        Algorithm algorithmMatch;

        switch (algorithm){
            case KMP -> algorithmMatch = new KMPAlgorithm();
            case RK -> algorithmMatch = new RKAlgorithm();
            default -> algorithmMatch = new NativeAlgorithm();
        }

        String result = algorithmMatch.matchBinaryDataToTemplateDB(filePath, db);

        return result.isEmpty()
                ? FAIL_FOUND
                : result;
    }
}
