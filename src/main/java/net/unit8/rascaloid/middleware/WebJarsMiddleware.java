package net.unit8.rascaloid.middleware;

import enkan.MiddlewareChain;
import enkan.collection.OptionMap;
import enkan.data.HttpRequest;
import enkan.data.HttpResponse;
import enkan.middleware.AbstractWebMiddleware;
import enkan.util.CodecUtils;
import enkan.util.HttpRequestUtils;
import enkan.util.HttpResponseUtils;
import org.webjars.WebJarAssetLocator;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WebJarsMiddleware<NRES> extends AbstractWebMiddleware<HttpRequest, NRES> {
    private static final Set<String> TARGET_METHODS = new HashSet<>(Arrays.asList("GET", "HEAD"));
    private static final Pattern WEBJARS_PATTERN = Pattern.compile("META-INF/resources/webjars/([^/]+)/([^/]+)/(.*)");
    private final Map<String, String> assetMap;

    private String prefix = "/webjars";

    public WebJarsMiddleware() {
        assetMap = new WebJarAssetLocator().listAssets().stream()
            .map(p -> new AssetMapEntry(assetPath(p), p))
            .filter(e -> Objects.nonNull(e.getKey()))
            .collect(Collectors.toMap(AssetMapEntry::getKey, AssetMapEntry::getValue));
    }

    private String assetPath(String resource) {
        Matcher m = WEBJARS_PATTERN.matcher(resource);
        if (m.matches()) {
            return prefix + "/" + m.group(1) + "/" + m.group(3);
        }
        return null;
    }

    private String requestPath(HttpRequest request) {
        return CodecUtils.urlDecode(HttpRequestUtils.pathInfo(request));
    }

    private HttpResponse assetResponse(String path) {
        return HttpResponseUtils.resourceResponse(path, OptionMap.of());
    }
    @Override
    public HttpResponse handle(HttpRequest request, MiddlewareChain<HttpRequest, NRES, ?, ?> chain) {
        if (TARGET_METHODS.contains(request.getRequestMethod().toUpperCase(Locale.ENGLISH))) {
            String path = requestPath(request);
            if (assetMap.containsKey(path)) {
                return assetResponse(assetMap.get(path));
            }
        }
        return castToHttpResponse(chain.next(request));
    }

    private static class AssetMapEntry implements Map.Entry<String, String> {
        private String key;
        private String value;

        public AssetMapEntry(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String setValue(String value) {
            this.value = value;
            return value;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
