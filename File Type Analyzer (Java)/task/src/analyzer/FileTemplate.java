package analyzer;

public record FileTemplate(
        int priority,
        String temp,
        String expectedResult
) {
}
