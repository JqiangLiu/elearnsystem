package com.example.elearnsystem.common.util;

import java.io.File;

public class MP3ToWav {
    public static void toWav(String sourcePath,String targetPath) {
//        String sourcePath = "./src/main/resources/static/user_recorder_temporary/"+sourceFile+".mp3";
//        String targetPath = "./src/main/resources/static/user_recorder_temporary/" + sourceFile+".wav";
        String webroot = "e:/javaSoft/ffmpeg-20190312-d227ed5-win64-static/bin";
        Runtime run = null;
        try {
            String path = new File(webroot).getAbsolutePath();
            System.out.println(path);
            run = Runtime.getRuntime();
            Process p = run.exec(path + "/ffmpeg -y -i " + sourcePath + " -acodec pcm_s16le -ac 1 " + targetPath);
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


    public static void toMp3(String sourcePath,String targetPath) {
//        String sourcePath = "./src/main/resources/static/user_recorder_temporary/"+sourceFile+".mp3";
//        String targetPath = "./src/main/resources/static/user_recorder_temporary/" + sourceFile+".wav";
        String webroot = "e:/javaSoft/ffmpeg-20190312-d227ed5-win64-static/bin";
        Runtime run = null;
        try {
            String path = new File(webroot).getAbsolutePath();
            System.out.println(path);
            run = Runtime.getRuntime();
            Process p = run.exec(path + "/ffmpeg -i " + sourcePath + " -f mp3 -acodec libmp3lame -y " + targetPath);
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
