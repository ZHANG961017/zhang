package bwie.com.newsinfo.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by 13435 on 2017/9/13.
 */

public class Stream {

    public static String getHttpData(InputStream is){

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;

            while((len = is.read(buffer))!=-1){
                baos.write(buffer,0,len);
            }
            baos.flush();
            baos.close();
            is.close();
            return baos.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
