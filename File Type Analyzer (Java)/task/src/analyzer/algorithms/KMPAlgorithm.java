package analyzer.algorithms;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class KMPAlgorithm implements Algorithm{
    @Override
    public boolean matchBinaryDataToTemplate(String fileName, String temp) {
        try{
            var lines = Files.readAllLines(Paths.get(fileName));
            char[] chars = lines.get(0).toCharArray();
            int[] pi = createOffsetArray(chars);

            return findTemp(chars, temp.toCharArray(), pi);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private static int[] createOffsetArray(final char[] chars){
        int j = 0;
        int i = 1;
        int[] pi = new int[chars.length];
        pi[0] = 0;

        while (i < chars.length){
            if(chars[j] == chars[i]){
                pi[i] = j + 1;
                ++j;
                ++i;
            }
            else if(j == 0){
                pi[i] = 0;
                ++i;
            }
            else {
                j = pi[j - 1];
            }
        }

        return pi;
    }

    private static boolean findTemp(final char[] lineChars, final char[] tempChars, final int[] pi){
        int i = 0;
        int j = 0;

        while (i < lineChars.length){
            if(lineChars[i] == tempChars[j]){
                ++i;
                ++j;
                if(j == tempChars.length){
                    return true;
                }
            }
            else if(j > 0){
                j = pi[j - 1];
            }
            else {
                ++i;
            }
        }

        return false;
    }
}
