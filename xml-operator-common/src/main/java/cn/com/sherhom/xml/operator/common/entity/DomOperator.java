package cn.com.sherhom.xml.operator.common.entity;

import cn.com.sherhom.xml.operator.common.exceptions.SherhomException;
import cn.com.sherhom.xml.operator.common.utils.FileUtil;
import cn.com.sherhom.xml.operator.common.utils.LogUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author Sherhom
 * @date 2021/3/12 15:42
 */
public class DomOperator {
//    public static final String PATH_SEPARATOR="\\.";
    private Document document;
    public DomOperator(Document document){
        this.document=document;
    }

    public Document getDocument() {
        return document;
    }

    public DomOperator(String documentPath){
        String domText=FileUtil.readFile(documentPath);
        try {
            this.document=DocumentHelper.parseText(domText);
        } catch (DocumentException e) {
            LogUtil.printStackTrace(e);
            throw new SherhomException(e);
        }
    }
    public boolean addDomText(String xPath, String domText){
        Element element2Add;
        try {
            Document document=DocumentHelper.parseText(domText);
            element2Add=document.getRootElement();
        } catch (DocumentException e) {
            LogUtil.printStackTrace(e);
            return false;
        }
        return add(xPath,element2Add);
    }
    public boolean add(String xPath,Tag tag){
        Element element2Add=tag.toElement();
        return add(xPath,element2Add);
    }
    public boolean add(String xPath,Element element){
        Document document=this.document;
        Element target = (Element) document.selectSingleNode(xPath);
        if(target==null||element==null)
            return false;

        target.add(element);
        return true;
    }
}
