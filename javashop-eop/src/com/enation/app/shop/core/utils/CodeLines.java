package com.enation.app.shop.core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class CodeLines
{
  private static long sums = 0L;
  private static String[] suffixs;
  private static String target;
  private static int FLUSH_FLAG = 65536;

  private static StringBuffer statistics = new StringBuffer();

  public static void main(String[] args)
    throws IOException
  {
    args = new String[] { "E:/workspace/Java/eop", "java", "xml", "properties", "html", "jsp", "css", "js" };

    long startTimes = System.currentTimeMillis();
    if (args.length > 1) {
      suffixs = new String[args.length - 1];
    } else {
      System.out.println("As that : targetLocation , fileSuffix , fileSuffix . . .");

      return;
    }

    for (int i = 0; i < args.length; i++) {
      if (i == 0)
        target = args[i];
      else {
        suffixs[(i - 1)] = args[i];
      }
    }

    File targetFile = new File(target);
    if (targetFile.exists()) {
      statistic(targetFile);
      System.out.print(statistics.toString());
      System.out.println("All completement. U write [" + sums + "] lines code. did great job!");
    }
    else {
      System.out.println("File or Dir not exist : " + target);
    }

    System.out.println("Total times " + (System.currentTimeMillis() - startTimes) + " ms");
  }

  private static void statistic(File file)
    throws IOException
  {
    if (file.isDirectory()) {
      File[] files = file.listFiles();
      for (int i = 0; i < files.length; i++) {
        statistic(files[i]);
      }
    }

    if ((file.isFile()) && 
      (isMatchSuffixs(file)))
      sums += countFileTextLines(file);
  }

  private static boolean isMatchSuffixs(File file)
  {
    String fileName = file.getName();
    if (fileName.indexOf(".") != -1) {
      String extName = fileName.substring(fileName.indexOf(".") + 1);
      for (int i = 0; i < suffixs.length; i++) {
        if (suffixs[i].equals(extName)) {
          return true;
        }
      }
    }
    return false;
  }

  private static long countFileTextLines(File file)
    throws IOException
  {
    long result = 0L;
    if (statistics.length() > FLUSH_FLAG) {
      System.out.print(statistics.toString());
      statistics = new StringBuffer();
    }
    statistics.append("Counting in ").append(file.getAbsolutePath());
    BufferedReader br = new BufferedReader(new FileReader(file));
    while (br.readLine() != null)
      result += 1L;
    br.close();
    statistics.append("   -  ").append(result).append("\n");
    return result;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.utils.CodeLines
 * JD-Core Version:    0.6.1
 */