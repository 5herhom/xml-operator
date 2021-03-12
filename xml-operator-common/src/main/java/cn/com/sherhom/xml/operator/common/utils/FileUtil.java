package cn.com.sherhom.xml.operator.common.utils;

import cn.com.sherhom.xml.operator.common.exceptions.SherhomException;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Sherhom
 * @date 2021/3/12 10:55
 */
public class FileUtil {
    public static String readFile(String path){
        FileInputStream fis=null;
        FileChannel fc=null;
        try{
            File file=new File(path);
            fis = new FileInputStream(file);
            fc = fis.getChannel();
            ByteBuffer bb = ByteBuffer.allocate(new Long(file.length()).intValue());
            fc.read(bb);
            bb.flip();
            String str=new String(bb.array(),"UTF8");
            return str;
        }
        catch (Exception e){
            LogUtil.printStackTrace(e);
            throw new SherhomException(e);
        }
        finally {
            IOUtils.closeQuietly(fc);
            IOUtils.closeQuietly(fis);
        }
    }
}
