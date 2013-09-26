package com.enation.eop.sdk.utils;

import java.io.PrintStream;

public class HtmlUtil
{
  public static String appendTo(String html, String nodeName, String content)
  {
    return html.replaceAll("</" + nodeName + ">", content + "</" + nodeName + ">");
  }

  public static String insertTo(String html, String nodeName, String content)
  {
    return html.replaceAll("<" + nodeName + ">", "<" + nodeName + ">" + content);
  }

  public static void main(String[] args) {
    String html = "<html><head>adfbb</head><body>adfadsf</body></html>";
    html = insertTo(html, "head", "abc");
    System.out.println(html);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.sdk.utils.HtmlUtil
 * JD-Core Version:    0.6.1
 */