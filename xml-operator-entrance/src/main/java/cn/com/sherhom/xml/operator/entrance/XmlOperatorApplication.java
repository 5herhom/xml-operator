package cn.com.sherhom.xml.operator.entrance;

import cn.com.sherhom.xml.operator.common.entity.DomOperator;
import cn.com.sherhom.xml.operator.entrance.contansts.OperateEnum;

import static cn.com.sherhom.xml.operator.entrance.contansts.OperateEnum.*;

/**
 * @author Sherhom
 * @date 2021/3/15 15:17
 */
public class XmlOperatorApplication {
    public static void main(String[] args) {
        if(args.length<3){
            printHelp();
            return;
        }
        String operate=args[0];
        if(!OperateEnum.isInOperate(operate)){
            System.out.println("Arg:"+operate+" is not support.Please entrance:put/rm.");
            printHelp();
            return;
        }
        String filePath=args[1];
        String xPath=args[2];
        if(ADD_OR_MODIFY.is(operate)&&args.length<4){
            System.out.println("Miss domText to add/modify.");
            printHelp();
            return;
        }
        DomOperator domOperator=new DomOperator(filePath);
        boolean res=false;
        if(REMOVE.is(operate)){
            res=domOperator.remove(xPath);
        }
        else if(ADD_OR_MODIFY.is(operate)){
            String domText=args[3];
            printArgs(args);
            res=domOperator.addOrModifyDomText(xPath,domText);
        }
        else{

        }
        if(res)
            domOperator.flushAndBak();
        System.out.println("Result:"+res);
    }
    public static void printHelp(){
        String helps="add/modify: put {filePath} {xPath} {domText}\n" +
                "filePath: is the path of document to add/modify.\n" +
                "xPath : is the parent node of the tag you want to add/modify.\n"+
                "domText : is the text of document you want to add/modify.\n" +
                "\n" +
                "remove: rm {filePath} {xPath}\n" +
                "filePath: is the path of document to remove.\n" +
                "xPath: the nodes match the xPath will be removed.";

    }
    public static void printArgs(String[] args){
        StringBuilder msg=new StringBuilder("Args:[");
        for(String arg:args){
            msg.append("(").append(arg).append("),");
        }
        msg.deleteCharAt(msg.length()-1);
        msg.append("]");
        System.out.println(msg);
    }

}
