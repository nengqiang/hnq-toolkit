package com.hnq.toolkit.parse;

import com.google.common.collect.Maps;
import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.util.*;

/**
 * @author henengqiang
 * @date 2019/04/04
 */
public class XmlReader {

    private static Document doc = null;

    public XmlReader(String xmlStr) {
        try {
            StringReader read = new StringReader(xmlStr);
            InputSource source = new InputSource(read);

            SAXReader reader = new SAXReader();
            doc = reader.read(source);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public XmlReader(File file) {
        try {
            SAXReader reader = new SAXReader();
            doc = reader.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public XmlReader(InputStream in) {
        try {
            SAXReader reader = new SAXReader();
            doc = reader.read(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * xml String to {@link JSON} String
     */
    public static String xmlToJson(String xmlStr) {
        XMLSerializer xmlSerializer = new XMLSerializer();
        JSON json = xmlSerializer.read(xmlStr);
        return json.toString();
    }

    /**
     * {@link Document} to {@link JSON} String
     */
    public static String xmlToJson(Document doc) {
        return xmlToJson(doc.toString());
    }

    /**
     * jsonStr to xmlStr
     */
    public static String jsonToXmlStr(String jsonStr) {
        XMLSerializer xmlSerializer = new XMLSerializer();
        return xmlSerializer.write(JSONSerializer.toJSON(jsonStr));
    }

    public String getRootTagName() {
        return doc.getRootElement().getName();
    }

    public List getAttributes(String xpath) {
        List attrs;
        xpath = formatXPath(xpath);
        try {
            if (doc == null) {
                return null;
            }
            String path = getXPath(xpath);
            Element node = (Element) doc.selectSingleNode(path);
            attrs = node.attributes();
        } catch (Exception e) {
            return null;
        }
        return attrs;
    }

    public String getAttribute(String xpath, String attrName) {
        String rslt = null;
        try {
            if (doc == null) {
                return null;
            }
            Element node = (Element) doc.selectSingleNode(getXPath(xpath));
            rslt = node.attributeValue(attrName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rslt;
    }

    public String getAttribute(String xpath) {
        Properties prop = this.getPathAndName(xpath);
        if (prop == null) {
            return null;
        }
        return this.getAttribute(prop.getProperty("path"), prop.getProperty("name"));
    }

    public String getText(String xpath) {
        try {
            if (doc == null) {
                return null;
            }
            Element node = (Element) doc.selectSingleNode(getXPath(xpath));
            return node.getText();
        } catch (Exception e) {
            return null;
        }
    }

    public Map getNode(String xpath) {
        return toMap(xpath);
    }

    public Map getNode(Element node) {
        return domToMap(node);
    }

    public List getNodes(String xpath) {
        return doc.selectNodes(getXPath(xpath));
    }

    public Map getRootNode() {
        return domToMap(doc);
    }

    public Map getRootNode2() {
        return domToMap2(doc);
    }

    public Document getDocument() {
        return doc;
    }

    private Map toMap(String xpath) {
        try {
            if (doc == null) {
                return null;
            }
            Element node = (Element) doc.selectSingleNode(getXPath(xpath));
            return domToMap(node);
        } catch (Exception e) {
            return null;
        }
    }

    private Map<String, Object> domToMap(Document doc) {
        Map<String, Object> map = Maps.newHashMap();
        if (doc == null) {
            return map;
        }
        Element root = doc.getRootElement();
        for (Iterator iterator = root.elementIterator(); iterator.hasNext(); ) {
            Element e = (Element) iterator.next();
            List list = e.elements();
            if (list.size() > 0) {
                map.put(e.getName(), domToMap(e));
            } else {
                map.put(e.getName(), e.getText());
            }
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    private Map domToMap(Element e) {
        Map map = Maps.newHashMap();
        List list = e.elements();
        List attrs = e.attributes();
        if (list.size() > 0) {
            for (Object o : list) {
                Element iter = (Element) o;
                List mapList = new ArrayList();
                if (iter.elements().size() > 0) {
                    Map m = domToMap(iter);
                    if (map.get(iter.getName()) != null) {
                        Object obj = map.get(iter.getName());
                        if (!"java.util.ArrayList".equals(obj.getClass().getName())) {
                            mapList = new ArrayList();
                            mapList.add(obj);
                            mapList.add(m);
                        }
                        if ("java.util.ArrayList".equals(obj.getClass().getName())) {
                            mapList = (List) obj;
                            mapList.add(m);
                        }
                        map.put(iter.getName(), mapList);
                    } else {
                        map.put(iter.getName(), m);
                    }
                } else {
                    if (map.get(iter.getName()) != null) {
                        Object obj = map.get(iter.getName());
                        if (!"java.util.ArrayList".equals(obj.getClass().getName())) {
                            mapList = new ArrayList();
                            mapList.add(obj);
                            mapList.add(iter.getText());
                        }
                        if ("java.util.ArrayList".equals(obj.getClass().getName())) {
                            mapList = (List) obj;
                            mapList.add(iter.getText());
                        }
                        map.put(iter.getName(), mapList);
                    } else {
                        map.put(iter.getName(), iter.getText());
                    }
                }
            }
        } else {
            map.put(e.getName(), e.getText());
        }
        for (int i = 0; attrs != null && i < attrs.size(); i++) {
            Attribute attr = (Attribute) attrs.get(i);
            map.put(attr.getName(), attr.getData());
        }
        return map;
    }

    private Map toMap2(String xpath) {
        try {
            if (doc == null) {
                return null;
            }
            Element node = (Element) doc.selectSingleNode(getXPath(xpath));
            return domToMap2(node);
        } catch (Exception e) {
            return null;
        }
    }

    private Map<String, Object> domToMap2(Document doc) {
        Map<String, Object> map = Maps.newHashMap();
        if (doc == null) {
            return map;
        }
        Element root = doc.getRootElement();
        for (Iterator iterator = root.elementIterator(); iterator.hasNext(); ) {
            Element e = (Element) iterator.next();
            map.put(e.getName(), domToMap2(e));
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    private Map domToMap2(Element e) {
        Map map = Maps.newHashMap();
        List list = e.elements();
        List attrs = e.attributes();
        System.out.println(attrs.size());
        if (list.size() > 0) {

            for (Object o : list) {
                Element iter = (Element) o;

                List mapList = new ArrayList();

                Map m = domToMap2(iter);

                if (map.get(iter.getName()) != null) {
                    Object obj = map.get(iter.getName());
                    if (!"java.util.ArrayList".equals(obj.getClass().getName())) {
                        mapList = new ArrayList();
                        mapList.add(obj);
                        mapList.add(m);
                    }
                    if ("java.util.ArrayList".equals(obj.getClass().getName())) {
                        mapList = (List) obj;
                        mapList.add(m);
                    }
                    map.put(iter.getName(), mapList);
                } else {
                    map.put(iter.getName(), m);
                }
            }
        } else {
            map.put("text", e.getText());
        }
        if (attrs.size() > 0) {
            Map atts = Maps.newHashMap();
            for (Object attr1 : attrs) {
                Attribute attr = (Attribute) attr1;
                atts.put(attr.getName(), attr.getValue());
            }
            map.put("attributes", atts);
        }
        return map;
    }


    private String formatXPath(String xpath) {
        xpath = xpath.replace('\\', '/');
        if ("/".equals(xpath)) {
            return xpath;
        }

        if (xpath.endsWith("/")) {
            xpath = xpath.substring(0, xpath.length() - 1);
        }
        return xpath;
    }

    private Properties getPathAndName(String xpath) {
        Properties pro = new Properties();
        xpath = formatXPath(xpath);
        if (xpath.length() <= 0) {
            return null;
        }
        if (!xpath.contains("/")) {
            pro.setProperty("path", "/");
            pro.setProperty("name", xpath);
        } else {
            pro.setProperty("path", xpath.substring(0, xpath.lastIndexOf("/")));
            pro.setProperty("name", xpath.substring(xpath.lastIndexOf("/") + 1));
        }
        return pro;
    }

    private String getXPath(String xpath) {
        xpath = formatXPath(xpath);
        if ("/".equals(xpath)) {
            xpath = "/" + getRootTagName();
            return xpath;
        }

        if (xpath.startsWith("/")) {
            return xpath;
        }

        xpath = "/" + getRootTagName() + "/" + xpath;
        return xpath;

    }

}
