package com.enation.framework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil
{
  public static String doubleToIntString(Double d)
  {
    int value = d.intValue();
    return String.valueOf(value);
  }

  public static boolean checkFloat(String num, String type)
  {
    String eL = "";
    if (type.equals("0+"))
      eL = "^\\d+(\\.\\d+)?$";
    else if (type.equals("+"))
      eL = "^((\\d+\\.\\d*[1-9]\\d*)|(\\d*[1-9]\\d*\\.\\d+)|(\\d*[1-9]\\d*))$";
    else if (type.equals("-0"))
      eL = "^((-\\d+(\\.\\d+)?)|(0+(\\.0+)?))$";
    else if (type.equals("-"))
      eL = "^(-((\\d+\\.\\d*[1-9]\\d*)|(\\d*[1-9]\\d*\\.\\d+)|(\\d*[1-9]\\d*)))$";
    else
      eL = "^(-?\\d+)(\\.\\d+)?$";
    Pattern p = Pattern.compile(eL);
    Matcher m = p.matcher(num);
    boolean b = m.matches();
    return b;
  }

  public static boolean isInArray(String value, String[] array)
  {
    if (array == null)
      return false;
    for (String v : array) {
      if (v.equals(value))
        return true;
    }
    return false;
  }

  public static boolean isInArray(int value, String[] array)
  {
    if (array == null)
      return false;
    for (String v : array) {
      if (Integer.valueOf(v).intValue() == value)
        return true;
    }
    return false;
  }

  public static String implode(String str, Object[] array)
  {
    if ((str == null) || (array == null)) {
      return "";
    }
    String result = "";
    for (int i = 0; i < array.length; i++) {
      if (i == array.length - 1)
        result = result + array[i].toString();
      else {
        result = result + array[i].toString() + str;
      }
    }
    return result;
  }

  public static String implodeValue(String str, Object[] array) {
    if ((str == null) || (array == null)) {
      return "";
    }
    String result = "";
    for (int i = 0; i < array.length; i++) {
      if (i == array.length - 1)
        result = result + "?";
      else {
        result = result + "?" + str;
      }
    }
    return result;
  }

  public static String md5(String str)
  {
    MessageDigest messageDigest = null;
    try {
      messageDigest = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException ex) {
      ex.printStackTrace();
      return null;
    }
    byte[] resultByte = messageDigest.digest(str.getBytes());
    StringBuffer result = new StringBuffer();
    for (int i = 0; i < resultByte.length; i++) {
      result.append(Integer.toHexString(0xFF & resultByte[i]));
    }
    return result.toString();
  }

  public static boolean validEmail(String sEmail)
  {
    String pattern = "^([a-z0-9A-Z]+[-|\\.|_]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    return sEmail.matches(pattern);
  }

  public static boolean validMaxLen(String str, int length)
  {
    if ((str == null) && (str.equals(""))) {
      return false;
    }
    if (str.length() > length) {
      return false;
    }
    return true;
  }

  public static boolean validMinLen(String str, int length)
  {
    if ((str == null) && (str.equals(""))) {
      return false;
    }
    if (str.length() < length) {
      return false;
    }
    return true;
  }

  public static boolean isEmpty(String str)
  {
    if ((str == null) || ("".equals(str))) {
      return true;
    }
    String pattern = "\\S";
    Pattern p = Pattern.compile(pattern, 34);
    Matcher m = p.matcher(str);
    return !m.find();
  }

  public static boolean equals(String str1, String str2)
  {
    if ((str1 == null) || (str1.equals("")) || (str2 == null) || (str2.equals(""))) {
      return false;
    }
    return str1.equals(str2);
  }

  public static int toInt(String str, boolean checked)
  {
    int value = 0;
    if ((str == null) || (str.equals("")))
      return 0;
    try
    {
      value = Integer.parseInt(str);
    } catch (Exception ex) {
      if (checked) {
        throw new RuntimeException("整型数字格式不正确");
      }
      return 0;
    }

    return value;
  }

  public static int toInt(String str, int defaultValue)
  {
    int value = defaultValue;
    if ((str == null) || (str.equals("")))
      return defaultValue;
    try
    {
      value = Integer.parseInt(str);
    } catch (Exception ex) {
      throw new RuntimeException("整型数字格式不正确");
    }
    return value;
  }

  @Deprecated
  public static int toInt(String str)
  {
    int value = 0;
    if ((str == null) || (str.equals("")))
      return 0;
    try
    {
      value = Integer.parseInt(str);
    } catch (Exception ex) {
      value = 0;
      ex.printStackTrace();
    }
    return value;
  }

  @Deprecated
  public static Double toDouble(String str) {
    Double value = Double.valueOf(0.0D);
    if ((str == null) || (str.equals("")))
      return Double.valueOf(0.0D);
    try
    {
      value = Double.valueOf(str);
    } catch (Exception ex) {
      value = Double.valueOf(0.0D);
    }

    return value;
  }

  public static Double toDouble(String str, boolean checked)
  {
    Double value = Double.valueOf(0.0D);
    if ((str == null) || (str.equals("")))
      return Double.valueOf(0.0D);
    try
    {
      value = Double.valueOf(str);
    } catch (Exception ex) {
      if (checked) {
        throw new RuntimeException("数字格式不正确");
      }
      return Double.valueOf(0.0D);
    }
    return value;
  }

  public static String arrayToString(Object[] array, String split)
  {
    if (array == null) {
      return "";
    }
    String str = "";
    for (int i = 0; i < array.length; i++) {
      if (i != array.length - 1)
        str = str + array[i].toString() + split;
      else {
        str = str + array[i].toString();
      }
    }
    return str;
  }

  public static String listToString(List list, String split)
  {
    if ((list == null) || (list.isEmpty()))
      return "";
    StringBuffer sb = new StringBuffer();
    for (Iterator i$ = list.iterator(); i$.hasNext(); ) { Object obj = i$.next();
      if (sb.length() != 0) {
        sb.append(split);
      }
      sb.append(obj.toString());
    }
    return sb.toString();
  }

  public static String getWebInfPath()
  {
    String filePath = Thread.currentThread().getContextClassLoader().getResource("").toString();

    if (filePath.toLowerCase().indexOf("file:") > -1) {
      filePath = filePath.substring(6, filePath.length());
    }
    if (filePath.toLowerCase().indexOf("classes") > -1) {
      filePath = filePath.replaceAll("/classes", "");
    }
    if (System.getProperty("os.name").toLowerCase().indexOf("window") < 0) {
      filePath = "/" + filePath;
    }
    if (!filePath.endsWith("/"))
      filePath = filePath + "/";
    return filePath;
  }

  public static String getRootPath()
  {
    String filePath = Thread.currentThread().getContextClassLoader().getResource("").toString();

    if (filePath.toLowerCase().indexOf("file:") > -1) {
      filePath = filePath.substring(6, filePath.length());
    }
    if (filePath.toLowerCase().indexOf("classes") > -1) {
      filePath = filePath.replaceAll("/classes", "");
    }
    if (filePath.toLowerCase().indexOf("web-inf") > -1) {
      filePath = filePath.substring(0, filePath.length() - 9);
    }
    if (System.getProperty("os.name").toLowerCase().indexOf("window") < 0) {
      filePath = "/" + filePath;
    }

    if (filePath.endsWith("/")) {
      filePath = filePath.substring(0, filePath.length() - 1);
    }
    return filePath;
  }

  public static String getRootPath(String resource) {
    String filePath = Thread.currentThread().getContextClassLoader().getResource(resource).toString();

    if (filePath.toLowerCase().indexOf("file:") > -1) {
      filePath = filePath.substring(6, filePath.length());
    }

    if (System.getProperty("os.name").toLowerCase().indexOf("window") < 0) {
      filePath = "/" + filePath;
    }
    if (!filePath.endsWith("/")) {
      filePath = filePath + "/";
    }
    return filePath;
  }

  public static int formatPage(String page)
  {
    int iPage = 1;
    if ((page == null) || (page.equals("")))
      return iPage;
    try
    {
      iPage = Integer.parseInt(page);
    } catch (Exception ex) {
      iPage = 1;
    }
    return iPage;
  }

  public static String getFileSize(String fileSize)
  {
    String temp = "";
    DecimalFormat df = new DecimalFormat("0.00");
    double dbFileSize = Double.parseDouble(fileSize);
    if (dbFileSize >= 1024.0D) {
      if (dbFileSize >= 1048576.0D) {
        if (dbFileSize >= 1073741824.0D)
          temp = df.format(dbFileSize / 1024.0D / 1024.0D / 1024.0D) + " GB";
        else
          temp = df.format(dbFileSize / 1024.0D / 1024.0D) + " MB";
      }
      else
        temp = df.format(dbFileSize / 1024.0D) + " KB";
    }
    else {
      temp = df.format(dbFileSize / 1024.0D) + " KB";
    }
    return temp;
  }

  public static String getEntry()
  {
    Random random = new Random(100L);
    Date now = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat(new String("yyyyMMddHHmmssS"));

    return md5(formatter.format(now) + random.nextDouble());
  }

  public static String toUTF8(String str)
  {
    if ((str == null) || (str.equals("")))
      return "";
    try
    {
      return new String(str.getBytes("ISO8859-1"), "UTF-8");
    } catch (Exception ex) {
      ex.printStackTrace();
    }return "";
  }

  public static String to(String str, String charset)
  {
    if ((str == null) || (str.equals("")))
      return "";
    try
    {
      return new String(str.getBytes("ISO8859-1"), charset);
    } catch (Exception ex) {
      ex.printStackTrace();
    }return "";
  }

  public static String getRandStr(int n)
  {
    Random random = new Random();
    String sRand = "";
    for (int i = 0; i < n; i++) {
      String rand = String.valueOf(random.nextInt(10));
      sRand = sRand + rand;
    }
    return sRand;
  }

  public static String getChineseNum(int num)
  {
    String[] chineseNum = { "一", "二", "三", "四", "五", "六", "七", "八", "九", "十" };

    return chineseNum[num];
  }

  public static String replaceEnter(String str) {
    if (str == null)
      return null;
    return str.replaceAll("\r", "").replaceAll("\n", "");
  }

  public static String replaceAll(String source, String target, String content) {
    StringBuffer buffer = new StringBuffer(source);
    int start = buffer.indexOf(target);
    if (start > 0) {
      source = buffer.replace(start, start + target.length(), content).toString();
    }

    return source;
  }

  public static String getTxtWithoutHTMLElement(String element)
  {
    if ((null == element) || ("".equals(element.trim()))) {
      return element;
    }

    Pattern pattern = Pattern.compile("<[^<|^>]*>");
    Matcher matcher = pattern.matcher(element);
    StringBuffer txt = new StringBuffer();
    while (matcher.find()) {
      String group = matcher.group();
      if (group.matches("<[\\s]*>"))
        matcher.appendReplacement(txt, group);
      else {
        matcher.appendReplacement(txt, "");
      }
    }
    matcher.appendTail(txt);
    String temp = txt.toString().replaceAll("\n", "");
    temp = temp.replaceAll(" ", "");
    return temp;
  }

  public static String toTrim(String strtrim)
  {
    if ((null != strtrim) && (!strtrim.equals(""))) {
      return strtrim.trim();
    }
    return "";
  }

  public static String filterDollarStr(String str)
  {
    String sReturn = "";
    if (!toTrim(str).equals("")) {
      if (str.indexOf(36, 0) > -1) {
        while (str.length() > 0) {
          if (str.indexOf(36, 0) > -1) {
            sReturn = sReturn + str.subSequence(0, str.indexOf(36, 0));
            sReturn = sReturn + "\\$";
            str = str.substring(str.indexOf(36, 0) + 1, str.length());
          }
          else {
            sReturn = sReturn + str;
            str = "";
          }
        }
      }

      sReturn = str;
    }

    return sReturn;
  }

  public static String compressHtml(String html) {
    if (html == null) {
      return null;
    }
    html = html.replaceAll("[\\t\\n\\f\\r]", "");
    return html;
  }

  public static String toCurrency(Double d) {
    if (d != null) {
      DecimalFormat df = new DecimalFormat("￥#,###.00");
      return df.format(d);
    }
    return "";
  }

  public static String toString(Integer i) {
    if (i != null) {
      return String.valueOf(i);
    }
    return "";
  }

  public static String toString(Double d) {
    if (null != d) {
      return String.valueOf(d);
    }
    return "";
  }

  public static String getRandom() {
    int[] array = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    Random rand = new Random();
    for (int i = 10; i > 1; i--) {
      int index = rand.nextInt(i);
      int tmp = array[index];
      array[index] = array[(i - 1)];
      array[(i - 1)] = tmp;
    }
    int result = 0;
    for (int i = 0; i < 6; i++) {
      result = result * 10 + array[i];
    }
    return "" + result;
  }

  public static int getMaxLevelCode(int code)
  {
    String codeStr = "" + code;
    StringBuffer str = new StringBuffer();
    boolean flag = true;
    for (int i = codeStr.length() - 1; i >= 0; i--) {
      char c = codeStr.charAt(i);
      if ((c == '0') && (flag)) {
        str.insert(0, '9');
      } else {
        str.insert(0, c);
        flag = false;
      }
    }
    return Integer.valueOf(str.toString()).intValue();
  }

  public static String delSqlComment(String content)
  {
    String pattern = "/\\*(.|[\r\n])*?\\*/";
    Pattern p = Pattern.compile(pattern, 34);
    Matcher m = p.matcher(content);
    if (m.find()) {
      content = m.replaceAll("");
    }
    return content;
  }

  public static String inputStream2String(InputStream is) {
    BufferedReader in = new BufferedReader(new InputStreamReader(is));
    StringBuffer buffer = new StringBuffer();
    String line = "";
    try {
      while ((line = in.readLine()) != null)
        buffer.append(line);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return buffer.toString();
  }

  public static String decode(String keyword) {
    try {
      keyword = URLDecoder.decode(keyword, "UTF-8");
    }
    catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    return keyword;
  }

  public static String doFilter(String regex, String rpstr, String source)
  {
    Pattern p = Pattern.compile(regex, 34);
    Matcher m = p.matcher(source);
    return m.replaceAll(rpstr);
  }

  public static String formatScript(String source)
  {
    source = source.replaceAll("javascript", "&#106avascript");
    source = source.replaceAll("jscript:", "&#106script:");
    source = source.replaceAll("js:", "&#106s:");
    source = source.replaceAll("value", "&#118alue");
    source = source.replaceAll("about:", "about&#58");
    source = source.replaceAll("file:", "file&#58");
    source = source.replaceAll("document.cookie", "documents&#46cookie");
    source = source.replaceAll("vbscript:", "&#118bscript:");
    source = source.replaceAll("vbs:", "&#118bs:");
    source = doFilter("(on(mouse|exit|error|click|key))", "&#111n$2", source);

    return source;
  }

  public static String htmlDecode(String htmlContent)
  {
    htmlContent = formatScript(htmlContent);
    htmlContent = htmlContent.replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n\r", "<br>").replaceAll("\r\n", "<br>").replaceAll("\r", "<br>");

    return htmlContent;
  }

  public static String addPrefix(String table, String prefix)
  {
    String result = "";
    if (table.length() > prefix.length()) {
      if (table.substring(0, prefix.length()).toLowerCase().equals(prefix.toLowerCase()))
      {
        result = table;
      }
      else result = prefix + table; 
    }
    else {
      result = prefix + table;
    }
    return result;
  }

  public static String addSuffix(String table, String suffix) {
    String result = "";
    if (table.length() > suffix.length()) {
      int start = table.length() - suffix.length();
      int end = start + suffix.length();
      if (table.substring(start, end).toLowerCase().equals(suffix.toLowerCase()))
      {
        result = table;
      }
      else result = table + suffix; 
    }
    else { result = table + suffix; }

    return result;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.util.StringUtil
 * JD-Core Version:    0.6.1
 */