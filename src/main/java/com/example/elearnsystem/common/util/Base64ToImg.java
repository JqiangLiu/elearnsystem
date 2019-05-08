package com.example.elearnsystem.common.util;

import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class Base64ToImg {
    public static String base64ToImg(String imgStr,String imgFilePath)
    {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return imgFilePath;
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
