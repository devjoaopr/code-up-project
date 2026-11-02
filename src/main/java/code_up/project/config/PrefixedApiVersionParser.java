package code_up.project.config;

import org.springframework.web.accept.ApiVersionParser;
import org.springframework.web.accept.SemanticApiVersionParser;

public class PrefixedApiVersionParser implements ApiVersionParser {
    private final SemanticApiVersionParser delegate = new SemanticApiVersionParser();

    @Override
    public Comparable parseVersion(String version) {
        if (version.startsWith("v") || version.startsWith("V")) {
            version = version.substring(1);
        }
        return delegate.parseVersion(version);
    }
}
