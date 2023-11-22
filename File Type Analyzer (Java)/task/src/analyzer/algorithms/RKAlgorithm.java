package analyzer.algorithms;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RKAlgorithm implements Algorithm{
    private static final long Q = 1_000_000_000 + 15;
    private static final int B = 34;

    @Override
    public boolean matchBinaryDataToTemplate(String fileName, String temp) {
        try{
            var lines = Files.readAllLines(Paths.get(fileName));

            return search(lines.get(0), temp);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private boolean search(String text, String pattern){
        if (text.length() < pattern.length()) {
            return false;
        }

        int patternLen = pattern.length();
        int textLen = text.length();
        long patternHash = hash(pattern, patternLen);
        long textHash = hash(text, patternLen);
        long multiplier = getMultiplier(patternLen);

        for (int i = 0; i <= textLen - patternLen; i++) {
            if (patternHash == textHash && checkSubstring(text, i, pattern)) {
                return true;
            }

            if (i < textLen - patternLen) {
                textHash = (B * (textHash - text.charAt(i) * multiplier) + text.charAt(i + patternLen)) % Q;
                if (textHash < 0) {
                    textHash = (textHash + Q);
                }
            }
        }

        return false;
    }

    private long hash(String text, int M){
        long result = 0;

        for(int i = 0; i < M; ++i){
            result = (B * result + text.charAt(i)) % Q;
        }

        return result;
    }

    private long getMultiplier(int length){
        long multiplier = 1;

        for (int i = 0; i < length - 1; i++) {
            multiplier = (multiplier * B) % Q;
        }

        return multiplier;
    }

    private boolean checkSubstring(String text, int indexText, String pattern){
        String buffer = text.substring(indexText, indexText + pattern.length());
        return buffer.equals(pattern);
    }
}
