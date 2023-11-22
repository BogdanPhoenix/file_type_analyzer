package analyzer.algorithms;

import analyzer.FileTemplateDB;

public interface Algorithm {
    boolean matchBinaryDataToTemplate(final String fileName, final String temp);

    default String matchBinaryDataToTemplateDB(String fileName, FileTemplateDB db) {
        for (var pattern : db.getDb()){
            if(matchBinaryDataToTemplate(fileName, pattern.temp())){
                return pattern.expectedResult();
            }
        }

        return "";
    }
}
