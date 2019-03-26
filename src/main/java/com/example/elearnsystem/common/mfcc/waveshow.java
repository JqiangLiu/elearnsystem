package com.example.elearnsystem.common.mfcc;


import com.example.elearnsystem.common.dtw.DynamicTimeWrapping2D;

import java.io.IOException;


public class waveshow {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		 String filename = "d://a191.wav";  
//         Vad vad = new Vad(filename);
	  //      WaveFileReader reader = new WaveFileReader(filename); 
	 //       int max = 0;
	//        System.out.println("²ÉÑùÂÊ"+reader.getSampleRate());//11025
	         //   System.out.println(max);//55296
//	            try{
//	            	FileWriter fileWriter=new FileWriter("d:\\javaresult.txt");
//	            for(int i=0;i<vad.FrmNum;i++){
//	            	for(int j=0;j<vad.FRM_LEN;j++){
//	            	double a = vad.audFrame[i].fltFrame[j];
//	             fileWriter.write(String.valueOf(a)+" ");
//	            	}
//	            
//	            }fileWriter.flush();
//	            fileWriter.close();
//	            }catch(Exception e){
//	            	
//	            }
//         System.out.println(vad.WavStart); 
//	           System.out.println(vad.WavEnd); 
		MFCC mfcc = new MFCC();
		double[][] result1 = mfcc.getMfcc("e:\\test1.wav");
        double[][] result2 = mfcc.getMfcc("e:\\test2.wav");
        DynamicTimeWrapping2D dtw = new DynamicTimeWrapping2D(result1,result2);
        double distance = dtw.calDistance();
        System.out.println(distance);
//		File file = new File("e:\\wavTxt.txt");
//            FileWriter out  = new FileWriter(file);
//            for (int i = 0; i<result.length; i++){
//                for (int j = 0; j<result[i].length; j++){
//                    out.write(result[i][j]+"\t");
//                }
//                out.write("\r\n");
//            }
//            out.close();
	        }
	    }  
	


