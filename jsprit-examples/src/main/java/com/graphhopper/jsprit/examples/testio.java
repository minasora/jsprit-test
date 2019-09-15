package com.graphhopper.jsprit.examples;

import java.io.*;

public class testio {
    public static void main(String[] args)
    {
        try{
            BufferedReader in = new BufferedReader(new FileReader("D:\\java learn\\jsprit-master\\result2.txt"));
            if(in.ready()){
                System.out.println("OK!");
            }
            BufferedWriter out = new BufferedWriter(new FileWriter("result3.txt"));
            String str;
            while((str=in.readLine())!=null)
            {
                System.out.println(str.contains("D:\\java learn\\jsprit-master\\input\\"));

                str = str.replace("]"," ");
                str = str.replace(("["),",");
                str = str.replace(("运行时间:"),"");
                String[] sourceStrArray = str.split(",");
                for (int i = 0; i < sourceStrArray.length; i++) {
                    out.write(sourceStrArray[i]);
                    out.newLine();

                }


            }
            in.close();
            out.close();
        }
        catch (IOException e){}
    }
}
