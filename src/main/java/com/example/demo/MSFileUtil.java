package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MSFileUtil {
   public static String streamToString(InputStream stream) {
       StringBuffer collector = new StringBuffer();

       try {
          BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
          String str;

          while ( (str = reader.readLine()) != null) {
             collector.append(str + "\n");
          }

          stream.close();
       } catch (IOException e) {
           e.printStackTrace();
           return "";
       }

      return collector.toString();
   }
}
