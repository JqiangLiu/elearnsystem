package com.example.elearnsystem.common.util;

import java.io.File;

public class MP3ToWav {
    public static void mp3ToWav(String sourcePath,String targetPath) {
//        String sourcePath = "e:\\cgly170607_2126871Weg.mp3";
        targetPath = "e:\\speakingWav\\" + targetPath;
        String webroot = "e:\\javaSoft\\ffmpeg-20190312-d227ed5-win64-static\\bin";
        Runtime run = null;
        try {
            String path = new File(webroot).getAbsolutePath();
            System.out.println(path);
            run = Runtime.getRuntime();
            Process p = run.exec(path + "\\ffmpeg -y -i " + sourcePath + " -acodec pcm_s16le -ac 1 " + targetPath);
            p.getOutputStream().close();
            p.getInputStream().close();
            p.getErrorStream().close();
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            run.freeMemory();
        }
    }
}
