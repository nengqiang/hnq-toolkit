package com.hnq.toolkit.util.xpath;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.htmlcleaner.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.output.support.AbstractXMLOutputProcessor;
import org.jdom2.output.support.FormatStack;
import org.jdom2.util.NamespaceStack;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.select.QueryParser;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author henengqiang
 * @date 2019/09/18
 */
@Slf4j
public class XpathUtils {

    private XpathUtils() {}

    private static final String TEXT_SIGN = "/text(";

    private static final String SEPARATOR = "\"";

    private static final HtmlCleaner HTML_CLEANER = new HtmlCleaner();

    private static final JDomSerializer JDOM_SERIALIZER =
            new JDomSerializer(HTML_CLEANER.getProperties(), false);

    private static final XMLOutputter XML_OUTPUT =
            new XMLOutputter(Format.getCompactFormat().setEncoding(StandardCharsets.UTF_8.name()), new CustomProcessor());

    public static String getValueByXpath(String select, String content) {
        List<String> result = getXpath(select, content);
        return CollectionUtils.isNotEmpty(result) ? result.get(0) : StringUtils.EMPTY;
    }

    public static List<String> getXpath(String select, String content) {
        List<String> result = new ArrayList<>();
        if (StringUtils.isEmpty(select) || StringUtils.isEmpty(content)) {
            log.warn("empty xpath or content");
            return result;
        }
        for (String selectString : select.split(SEPARATOR)) {
            result.addAll(routeToSelect(selectString, content));
        }
        return result;
    }

    /**
     * check to use jsoup or xpath
     */
    private static List<String> routeToSelect(String xpath, String text) {
        String content = contentPreClean(text);

        String select = xpath;
        String separate = StringUtils.EMPTY;
        boolean extractText = false;
        int i = xpath.indexOf(TEXT_SIGN);
        if (i != -1) {
            extractText = true;
            select = xpath.substring(0, i);

            int fromIndex = i + TEXT_SIGN.length();
            int end = xpath.indexOf(")", fromIndex);
            if (end != -1) {
                separate = xpath.substring(fromIndex, end);
            } else {
                separate = StringUtils.EMPTY;
            }
        }

        List<String> resList;
        if (isValid(select)) {
            log.debug("route to jsoup {}", select);

            if (extractText) {
                List<String> resultStrings = useJsoupSelect(select, content);
                String newContent = String.join("", resultStrings);

                resList = useXpathSelect(".", newContent, true, separate);
            } else {
                resList = useJsoupSelect(select, content);
            }
        } else {
            log.debug("route to xpath {}", select);
            resList = useXpathSelect(select, content, extractText, separate);
        }
        if (CollectionUtils.isEmpty(resList)) {
            log.warn("empty select content! - {}", select);
        }
        return resList;
    }

    private static List<String> useJsoupSelect(String select, String content) {
        List<String> splits = new LinkedList<>();
        try {
            Elements elements = Jsoup.parse(content).select(select);
            if (elements != null) {
                for (org.jsoup.nodes.Element element : elements) {
                    splits.add(element.outerHtml());
                }
            }
        } catch (Exception e) {
            log.error("select use jsoup error! select " + select);
        }
        return splits;
    }

    /**
     * fix htmlcleaner's bug ,support clean dom wrapped with
     * <tr>
     * or
     * <td>
     */
    private static String contentPreClean(String content) {
        content = content.trim();
        if (content.endsWith("</td>") || content.endsWith("</tr>")) {
            content = "<table>" + content + "</table>";
        }
        return content;
    }

    private static List<String> useXpathSelect(String xpath, String content, boolean extractText, String separate) {
        List<String> splits = new LinkedList<>();
        try {
            TagNode node = HTML_CLEANER.clean(content);
            Object[] elements = node.evaluateXPath(xpath);
            if (ArrayUtils.isNotEmpty(elements)) {
                for (Object obj : elements) {
                    if (obj instanceof TagNode) {
                        TagNode resultNode = (TagNode)obj;
                        if (extractText) {
                            String text;
                            if (StringUtils.isEmpty(separate)) {
                                text = resultNode.getText().toString();
                            } else {
                                text = text(resultNode, separate);
                            }
                            splits.add(text.replaceAll("[\n|\r|\t]", ""));
                        } else {
                            Document doc = JDOM_SERIALIZER.createJDom(resultNode);
                            splits.add(XML_OUTPUT.outputString(doc.getRootElement()));
                        }
                    } else if (obj instanceof CharSequence) {
                        splits.add(obj.toString());
                    }
                }
            }
        } catch (Exception e) {
            log.error("select use xpath error! xpath " + xpath);
        }
        return splits;
    }

    private static String text(TagNode tagNode, String separate) {
        List<String> list = collectTexts(tagNode);

        return String.join(separate, list);
    }

    private static List<String> collectTexts(TagNode tagNode) {
        List<String> list = new LinkedList<>();

        for (BaseToken item : tagNode.getAllChildren()) {
            if (item instanceof ContentNode) {
                String text = ((ContentNode)item).getContent();
                if (StringUtils.isNotBlank(text)) {
                    list.add(text);
                }
            } else if (item instanceof TagNode) {
                list.addAll(collectTexts((TagNode)item));
            }
        }

        return list;
    }

    static final class CustomProcessor extends AbstractXMLOutputProcessor {

        @Override
        public void process(final Writer out, final Format format, final Element element) throws IOException {

            FormatStack fStack = new FormatStack(format);
            fStack.setEscapeOutput(false);
            // If this is the root element we could pre-initialize the
            // namespace stack with the namespaces
            printElement(out, fStack, new NamespaceStack(), element);
            out.flush();
        }

    }

    private static boolean isValid(String query) {
        // Check for null
        if (StringUtils.isEmpty(query)) {
            return false;
        }
        boolean flag;
        try {
            QueryParser.parse(query);
            flag = true;
            // these exceptions are thrown if something is not ok
        } catch (Exception e) {
            // ignore
            // If something is not ok, the query is invalid
            flag = false;
        }
        // All ok, query is valid
        return flag;
    }

}
