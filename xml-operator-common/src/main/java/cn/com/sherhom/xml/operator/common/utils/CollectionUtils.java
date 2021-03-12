package cn.com.sherhom.xml.operator.common.utils;



import java.util.Collection;
import java.util.Map;

/**
 * @author Sherhom
 * @date 2020/9/2 20:12
 */
public class CollectionUtils {
    public static boolean isEmpty(  Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(  Map<?, ?> map) {
        return map == null || map.isEmpty();
    }


}
