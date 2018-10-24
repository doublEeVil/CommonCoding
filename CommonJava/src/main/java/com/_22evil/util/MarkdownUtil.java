package com._22evil.util;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
/**
 * markdown 处理
 */
public class MarkdownUtil {

    /**
     * markdown 转 html(一般而言还是浏览器js 转码比较好，不需要这个也让服务端去处理)
     * @param input
     * @return
     */
    public static final String md2Html(String input) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(input);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String output = renderer.render(document);
        return output;
    }
}
