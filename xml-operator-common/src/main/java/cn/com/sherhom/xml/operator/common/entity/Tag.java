package cn.com.sherhom.xml.operator.common.entity;

import cn.com.sherhom.xml.operator.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

import java.util.List;

/**
 * @author Sherhom
 * @date 2021/3/12 15:38
 */
@Data
public class Tag {
    private String name;
    private List<Pair<String, String>> attributes;
    private Object content;

    public Element toElement() {
        if (content == null)
            return null;
        Element element = new DefaultElement(this.name);
        List<Pair<String, String>> attributes = this.attributes;
        if (!CollectionUtils.isEmpty(attributes)) {
            for (Pair<String, String> attribute : attributes) {
                if (StringUtils.isNotBlank(attribute.getValue())) {
                    element.addAttribute(attribute.getKey(), attribute.getValue());
                }
            }
        }
        Object content=this.content;
        if(content instanceof String){
            element.setText((String)content);
        }
        else if(content instanceof Tag){
            Element element1=((Tag) content).toElement();
            if(element1!=null)
                element.add(element1);
        }
        else if(content instanceof JSONObject){
            Tag childTag=((JSONObject)content).toJavaObject(Tag.class);
            if(childTag!=null){
                Element childElement=childTag.toElement();
                if(childElement!=null)
                    element.add(childElement);
            }
        }
        else{
            return null;
        }

        return element;
    }
}
