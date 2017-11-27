package niuwei.com.headlinedemo.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by One Dream on 2017/9/10.
 */

public class StreamTools {
    public static String read(InputStream is){

        try {
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            int len;
            byte[] b=new byte[1024];
            while ((len=is.read(b))!=-1){
                baos.write(b,0,len);
            }
            return baos.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
