package com.hnq.toolkit.parse;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import com.hnq.toolkit.file.FileUtils;

/**
 * @author henengqiang
 * @date 2019/04/04
 */
class XmlReaderTest {

    @Test
    void loadTest() {
        File file = new File(FileUtils.getResourceFilePath(XmlReaderTest.class, "xml_source.xml"));
        XmlReader xmlReader = new XmlReader(file);
        System.out.println("node: " + xmlReader.getNode("//book[@id='1']"));
        System.out.println("attribute: " + xmlReader.getAttribute("//book[@id='1']"));
        System.out.println("rootNode: " + xmlReader.getRootNode());
        System.out.println("rootTagName: " + xmlReader.getRootTagName());
        System.out.println("rootNode2: " + xmlReader.getRootNode2());
        System.out.println("text: " + xmlReader.getText("//book[@id='1']//name"));
    }

    // TODO: 2019-06-03 henengqiang org.xml.sax.SAXParseException; lineNumber: 1; columnNumber: 1; Content is not allowed in prolog.
//    @Test
//    void xmlToJsonTest() {
//        File xmlFile = new File(FileUtils.getResourceFilePath(XmlReader.class, "xml_source.xml"));
//        XmlReader xmlReader = new XmlReader(xmlFile);
//        String result = XmlReader.xmlToJson(xmlReader.getDocument());
//        System.out.println(result);
//    }

    @Test
    void jsonToXmlStrTest() throws IOException {
        File file = new File(FileUtils.getResourceFilePath(this.getClass(), "json_source.json"));
        String jsonStr = org.apache.commons.io.FileUtils.readFileToString(file, "UTF-8");
        String result = XmlReader.jsonToXmlStr(jsonStr);
        System.out.println(result);
    }
}
