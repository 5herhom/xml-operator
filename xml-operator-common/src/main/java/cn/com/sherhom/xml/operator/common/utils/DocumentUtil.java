package cn.com.sherhom.xml.operator.common.utils;

import cn.com.sherhom.xml.operator.common.exceptions.SherhomException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * @author Sherhom
 * @date 2021/3/12 11:17
 */
public class DocumentUtil {
    public static JSONObject xml2Json(String xmlStr) throws DocumentException {
        Document doc= DocumentHelper.parseText(xmlStr);
        JSONObject json=new JSONObject();
        dom4j2Json(doc.getRootElement(), json);
        return json;
    }
    public static void dom4j2Json(Element element, JSONObject json){
        //如果是属性
        for(Object o:element.attributes()){
            Attribute attr=(Attribute)o;
            if(StringUtils.isNotBlank(attr.getValue())){
                json.put("@"+attr.getName(), attr.getValue());
            }
        }
        List<Element> chdEl=element.elements();
        if(chdEl.isEmpty()&&StringUtils.isNotBlank(element.getText())){//如果没有子元素,只有一个值
            json.put(element.getName(), element.getText());
            return;
        }
        JSONObject chilJson=new JSONObject();
        dom4j2Json0(element,chilJson);
        json.put(element.getName(),chilJson);
    }
    public static void dom4j2Json0(Element element, JSONObject json){
        //如果是属性
        for(Object o:element.attributes()){
            Attribute attr=(Attribute)o;
            if(StringUtils.isNotBlank(attr.getValue())){
                json.put("@"+attr.getName(), attr.getValue());
            }
        }
        List<Element> chdEl=element.elements();
        if(chdEl.isEmpty()&&StringUtils.isNotBlank(element.getText())){//如果没有子元素,只有一个值
            json.put(element.getName(), element.getText());
            return;
        }

        for(Element e:chdEl){//有子元素
            if(!e.elements().isEmpty()){//子元素也有子元素
                JSONObject chdjson=new JSONObject();
                dom4j2Json0(e,chdjson);
                Object o=json.get(e.getName());
                if(o!=null){
                    JSONArray jsona=null;
                    if(o instanceof JSONObject){//如果此元素已存在,则转为jsonArray
                        JSONObject jsono=(JSONObject)o;
                        json.remove(e.getName());
                        jsona=new JSONArray();
                        jsona.add(jsono);
                        jsona.add(chdjson);
                    }
                    if(o instanceof JSONArray){
                        jsona=(JSONArray)o;
                        jsona.add(chdjson);
                    }
                    json.put(e.getName(), jsona);
                }else{
                    if(!chdjson.isEmpty()){
                        json.put(e.getName(), chdjson);
                    }
                }


            }else{//子元素没有子元素
                for(Object o:element.attributes()){
                    Attribute attr=(Attribute)o;
                    if(StringUtils.isNotBlank(attr.getValue())){
                        json.put("@"+attr.getName(), attr.getValue());
                    }
                }
                if(!e.getText().isEmpty()){
                    json.put(e.getName(), e.getText());
                }
            }
        }
    }
    /**
     * 需要一个方法来创建DOM4j的XML解析器并返回一个document对象
     * add by hanwl
     * @throws Exception
     */
    public static Document getDocument(String xmlPath) throws Exception {
        Reader reader = new InputStreamReader(new FileInputStream(new File(xmlPath)),"utf-8");
        SAXReader saxReader = new SAXReader();
        //将XML文件路径传给Document对象并返回其实例dom
        Document dom = saxReader.read(reader);
        return dom;
    }
    public static void writeToXML(Document dom ,String xmlPath) throws IOException {
        writeToXML(dom,xmlPath,dom.getXMLEncoding());
    }
    /**
     * 需要一个方法来将更新后的document对象写入到XML文件中去
     * add by hanwl
     * @throws Exception
     */
    public static void writeToXML(Document dom ,String xmlPath,String codeType) throws IOException {

        //首先创建样式和输出流
        OutputFormat format = new OutputFormat().createPrettyPrint();
        OutputStreamWriter out = null;
        //OutputStream out = new FileOutputStream(xmlPath);
        XMLWriter writer=null;
        //写入之后关闭流
        try{
            if(codeType!=null)
                out = new OutputStreamWriter(new FileOutputStream(xmlPath),codeType);
            else
                out = new OutputStreamWriter(new FileOutputStream(xmlPath));
            writer = new XMLWriter(out,format);
            writer.write(dom);
        }
        catch (IOException e){
            LogUtil.printStackTrace(e);
            throw e;
        }
        finally {
            IOUtils.closeQuietly(out);
            if(writer!=null){
                try {
                    writer.close();
                } catch (IOException e) {
                    LogUtil.printStackTrace(e);
                    throw new SherhomException(e);
                }

            }
        }
    }
    public static boolean writeAndBak(Document dom,String path){
        File file=new File(path);
        try{
            if(file.exists()){
                String newName=file.getName()+"."+DateUtil.date2String(new Date());
                File bakFile=new File(file.getParent(),newName);
                FileUtils.copyFile(file,bakFile);
            }
            writeToXML(dom, path);
        }
        catch (Exception e){
            LogUtil.printStackTrace(e);
            return false;
        }
        finally {

        }
        return true;
    }
}
