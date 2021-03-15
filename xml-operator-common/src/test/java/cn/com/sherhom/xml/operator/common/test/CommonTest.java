package cn.com.sherhom.xml.operator.common.test;

import cn.com.sherhom.xml.operator.common.entity.DomOperator;
import cn.com.sherhom.xml.operator.common.utils.DocumentUtil;
import cn.com.sherhom.xml.operator.common.utils.FileUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.*;
import org.dom4j.tree.DefaultElement;
import org.junit.jupiter.api.Test;

/**
 * @author Sherhom
 * @date 2021/3/12 11:27
 */
@Slf4j
public class CommonTest {
    @Test
    public void documentUtilTest() throws Exception {
        String basePath = "G:\\HZY\\coding\\Java\\IdeaCoding\\xml-operator\\data\\";
        String readFile = "config.xml";
        String targetFile = "config_target.xml";
        String filePath = basePath + readFile;
        String targetPath = basePath + targetFile;
        String xmlStr = FileUtil.readFile(filePath);
        JSONObject jsonObject = DocumentUtil.xml2Json(xmlStr);
        System.out.println(jsonObject.toJSONString());
        DocumentUtil.writeToXML(DocumentHelper.parseText(xmlStr), targetPath);
    }

    @Test
    public void docTest() throws DocumentException {
        String basePath = "G:\\HZY\\coding\\Java\\IdeaCoding\\xml-operator\\data\\";
        String readFile = "config.xml";
        String targetFile = "config_target.xml";
        String filePath = basePath + readFile;
        String targetPath = basePath + targetFile;
        String xmlStr = FileUtil.readFile(filePath);
        Document doc = DocumentHelper.parseText(xmlStr);
        Element element = (Element) doc.selectSingleNode("/yandex/openSSL/server");
//        Element element= (Element)doc.selectSingleNode("/server");
        element.elements();
        Element e01 = new DefaultElement("asd");
        System.out.println(element);
        System.out.println(doc);
        String domText = "<interserver_http_port>9009</interserver_http_port>";
        Document document = DocumentHelper.parseText(domText);
        System.out.println(document);
    }

    @Test
    public void docTest02() throws DocumentException {
        String domText = "<interserver_http_port>9009</interserver_http_port>";
        Document document = DocumentHelper.parseText(domText);
        System.out.println(document);
    }

    @Test
    public void addAndSaveAndBakTest() {
        String basePath = "G:\\HZY\\coding\\Java\\IdeaCoding\\xml-operator\\data\\";
        String readFile = "config.xml";
        String xPath = "/yandex/openSSL/server";
        String domText2Add = " <logger>\n" +
                "        <!-- Possible levels: https://github.com/pocoproject/poco/blob/poco-1.9.4-release/Foundation/include/Poco/Logger.h#L105 -->\n" +
                "        <level id=\"hzy01\">trace</level>\n" +
                "        <log>/var/log/clickhouse-server/clickhouse-server.log</log>\n" +
                "        <errorlog>/var/log/clickhouse-server/clickhouse-server.err02.log</errorlog>\n" +
                "</logger>";
        String filePath = basePath + readFile;
        DomOperator domOperator = new DomOperator(filePath);
        domOperator.addOrModifyDomText(xPath, domText2Add);
        DocumentUtil.writeAndBak(domOperator.getDocument(), filePath);
    }

    @Test
    public void rmTest() {
        String basePath = "G:\\HZY\\coding\\Java\\IdeaCoding\\xml-operator\\data\\";
        String readFile = "config.xml";
        String xPath = "/yandex/openSSL/server/logger";
        String filePath = basePath + readFile;
        DomOperator domOperator = new DomOperator(filePath);
        domOperator.remove(xPath);
        domOperator.flushAndBak();

    }
    @Test
    public void parseTest() throws DocumentException {
        String domText2Add = " <logger>" +
                "        <!-- Possible levels: https://github.com/pocoproject/poco/blob/poco-1.9.4-release/Foundation/include/Poco/Logger.h#L105 -->\n" +
                "        <level id=\"hzy01\">trace</level>" +
                "        <log>/var/log/clickhouse-server/clickhouse-server.log</log>" +
                "        <errorlog>/var/log/clickhouse-server/clickhouse-server.err02.log</errorlog>" +
                "</logger>";
        Document element=DocumentHelper.parseText(domText2Add);

    }
}
