package cn.com.sherhom.xml.operator.common.entity;

import cn.com.sherhom.xml.operator.common.exceptions.SherhomException;
import cn.com.sherhom.xml.operator.common.utils.DocumentUtil;
import cn.com.sherhom.xml.operator.common.utils.FileUtil;
import cn.com.sherhom.xml.operator.common.utils.LogUtil;
import org.dom4j.*;

import java.util.List;

/**
 * @author Sherhom
 * @date 2021/3/12 15:42
 */
public class DomOperator {
//    public static final String PATH_SEPARATOR="\\.";
    private Document document;
    private String filePath;
    public DomOperator(Document document){
        this.document=document;
        this.filePath=null;
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
        this.filePath=documentPath;
    }
    public boolean addOrModifyDomText(String xPath, String domText){
        Element element2Add;
        try {
            Document document=DocumentHelper.parseText(domText);
            element2Add=document.getRootElement();
        } catch (DocumentException e) {
            LogUtil.printStackTrace(e);
            return false;
        }
        return addOrModify(xPath,element2Add);
    }
    public boolean addOrModify(String xPath, Tag tag){
        Element element2Add=tag.toElement();
        return addOrModify(xPath,element2Add);
    }
    public boolean addOrModify(String xPath, Element element){
        Document document=this.document;
        Element target = (Element) document.selectSingleNode(xPath);
        if(target==null||element==null)
            return false;
        Element oldElement=target.element(element.getName());
        if(oldElement!=null){
            target.remove(oldElement);
        }
        target.add(element);
        return true;
    }
    public boolean remove(String xPath){
        Document document=this.document;
        List<Node> element2Rm =  document.selectNodes(xPath);
        for (Node node2Rm:
                element2Rm) {
            node2Rm.detach();
        }
        return true;
    }
    public void flushAndBak(String filePath){
        if(filePath==null)
            throw new SherhomException("Path cannot be null.");
        DocumentUtil.writeAndBak(this.document, filePath);
    }
    public void flushAndBak(){
        String filePath=this.filePath;
        flushAndBak(filePath);
    }
}
