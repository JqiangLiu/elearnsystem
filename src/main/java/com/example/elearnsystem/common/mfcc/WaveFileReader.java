package com.example.elearnsystem.common.mfcc;// RobinTang
// 2012-08-23  

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

@SuppressWarnings("unused")
public class WaveFileReader {  
    private String filename = null;  
    private int[][] data = null;  
  
    private int len = 0;  
      
    private String chunkdescriptor = null;  // 标志块
    static private int lenchunkdescriptor = 4;  
  
    private long chunksize = 0;  //块数据大小
    static private int lenchunksize = 4;  
  
    private String waveflag = null;  // wave标志
    static private int lenwaveflag = 4;  
  
    private String fmtubchunk = null;  // fmt标志
    static private int lenfmtubchunk = 4;  
      
    private long subchunk1size = 0;  // 格式长度
    static private int lensubchunk1size = 4;  
      
    private int audioformat = 0;  // 格式类别，1表示线性PCM编码
    static private int lenaudioformat = 2;  
      
    private int numchannels = 0;  // 通道数，1为单声道，2为双声道
    static private int lennumchannels = 2;  
      
    private long samplerate = 0;  // 采样频率（每秒的样本数）
    static private int lensamplerate = 2;  
      
    private long byterate = 0;  // 每秒字节数，其值为：采样频率*通道数*样本数据位数/8
    static private int lenbyterate = 4;  
      
    private int blockalign = 0;  // DATA数据块的调整数（字节），其值为：通道数*样本数据位数/8
    static private int lenblockling = 2;  
      
    private int bitspersample = 0;  // 样本数据位数，0010H即16，代表一个量化两本占2字节
    static private int lenbitspersample = 2;  
      
    private String datasubchunk = null;   // "data", data数据块标志，固定不变
    static private int lendatasubchunk = 4;  
      
    private long subchunk2size = 0;  // wav文件音频数据所占大小
    static private int lensubchunk2size = 4;

    private String listsubchunk = null; //"LIST",list数据块标志
    static private int lenlistsubchunk = 4;

    private long subchunk3size = 0; // LIST块占用的字节数
    static private int lensubchunk3size = 4;

    private byte[] bytes = null;
      
    private FileInputStream fis = null;  
    private BufferedInputStream bis = null;  
      
    private boolean issuccess = false;  
      
    public WaveFileReader(String filename) {  
          
        this.initReader(filename);  
    }  
      
    // 判断是否创建wav读取器成功
    public boolean isSuccess() {  
        return issuccess;  
    }  
      
    // 获取每个采样的编码长度，8bit或者16bit
    public int getBitPerSample(){  
        return this.bitspersample;  
    }  
      
    // 获取采样率
    public long getSampleRate(){  
        return this.samplerate;  
    }  
      
    // 获取声道个数，1代表单声道 2代表立体声
    public int getNumChannels(){  
        return this.numchannels;  
    }  
      
    // 获取数据长度，也就是一共采样多少个
    public int getDataLen(){  
        return this.len;  
    }  
      
    /// 获取数据
    // 数据是一个二维数组，[n][m]代表第n个声道的第m个采样值
    public int[][] getData(){  
        return this.data;  
    }  
      
    private void initReader(String filename){  
        this.filename = filename;  
  
        try {  
            fis = new FileInputStream(this.filename);  
            bis = new BufferedInputStream(fis);  
  
            this.chunkdescriptor = readString(lenchunkdescriptor);  
            if(!chunkdescriptor.endsWith("RIFF"))  
                throw new IllegalArgumentException("RIFF miss, " + filename + " is not a wave file.");  
              
            this.chunksize = readLong();  
            this.waveflag = readString(lenwaveflag);  
            if(!waveflag.endsWith("WAVE"))  
                throw new IllegalArgumentException("WAVE miss, " + filename + " is not a wave file.");  
              
            this.fmtubchunk = readString(lenfmtubchunk);  
            if(!fmtubchunk.endsWith("fmt "))  
                throw new IllegalArgumentException("fmt miss, " + filename + " is not a wave file.");  
              
            this.subchunk1size = readLong();  // 读取格式长度
            this.audioformat = readInt();  // 读取格式类别
            this.numchannels = readInt();  // 读取通道数
            this.samplerate = readLong();  // 读取采样频率
            this.byterate = readLong();  // 读取每秒字节数，其值为：采样频率*通道数*样本数据位数/8
            this.blockalign = readInt();  // 读取DATA数据块的调整数，其值为：通道数*样本数据位数/8
            this.bitspersample = readInt();  // 读取样本数据位数

            this.listsubchunk = readString(lenlistsubchunk);
            if (!listsubchunk.endsWith("LIST"))
                throw new IllegalArgumentException("LIST miss, " + filename + " is not a wave file.");
            this.subchunk3size = readLong();
            bytes = readBytes((int) this.subchunk3size);

            this.datasubchunk = readString(lendatasubchunk);
            if(!datasubchunk.endsWith("data"))
                throw new IllegalArgumentException("data miss, " + filename + " is not a wave file.");
            this.subchunk2size = readLong();

            this.len = (int)(this.subchunk2size/(this.bitspersample/8)/this.numchannels);  
              
            this.data = new int[this.numchannels][this.len];  
              
            for(int i=0; i<this.len; ++i){  
                for(int n=0; n<this.numchannels; ++n){  
                    if(this.bitspersample == 8){  
                        this.data[n][i] = bis.read();  
                    }  
                    else if(this.bitspersample == 16){  
                        this.data[n][i] = this.readInt();  
                    }  
                }  
            }  
              
            issuccess = true;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        finally{  
            try{  
            if(bis != null)  
                bis.close();  
            if(fis != null)  
                fis.close();  
            }  
            catch(Exception e1){  
                e1.printStackTrace();  
            }  
        }  
    }  
      
    private String readString(int len){  
        byte[] buf = new byte[len];  
        try {  
            if(bis.read(buf)!=len)  
                throw new IOException("no more data!!!");  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return new String(buf);  
    }  
      
    private int readInt(){  
        byte[] buf = new byte[2];  
        int res = 0;  
        try {  
            if(bis.read(buf)!=2)  
                throw new IOException("no more data!!!");  
            res = (buf[0]&0x000000FF) | (((int)buf[1])<<8);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return res;  
    }  
      
    private long readLong(){  
        long res = 0;  
        try {  
            long[] l = new long[4];  
            for(int i=0; i<4; ++i){  
                l[i] = bis.read();  
                if(l[i]==-1){  
                    throw new IOException("no more data!!!");  
                }  
            }  
            res = l[0] | (l[1]<<8) | (l[2]<<16) | (l[3]<<24);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return res;  
    }  
      
    private byte[] readBytes(int len){  
        byte[] buf = new byte[len];  
        try {  
            if(bis.read(buf)!=len)  
                throw new IOException("no more data!!!");  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return buf;  
    }
}  
