package cn.com.sherhom.xml.operator.entrance.contansts;

public enum OperateEnum {
    ADD_OR_MODIFY("put"),
    REMOVE("rm");

    public String getArg() {
        return arg;
    }

    String arg;
    OperateEnum(String arg){
        this.arg=arg;
    }
    public static boolean isInOperate(String arg){
        for(OperateEnum e:OperateEnum.values()){
            if(e.arg.equals(arg)){
                return true;
            }
        }
        return false;
    }
    public boolean is(Object o){
        if(o==null)
            return false;
        if(o instanceof OperateEnum){
            return o==this;
        }
        if(o instanceof String){
            return o.equals(this.arg);
        }
        return false;
    }
}
