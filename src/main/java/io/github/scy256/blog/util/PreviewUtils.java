package io.github.scy256.blog.util;

public class PreviewUtils {

    public static final String getPreview(String content) {
        String contentWithoutHtmlTags =
                content.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
        return shorten(contentWithoutHtmlTags);
    }

    private static final String shorten(String text) {
        if(100 < text.length())
            return text.substring(0, 100);
        return text;
    }

}
