package com.enation.eop.sdk.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{
  public static Date toDate(String date, String pattern)
  {
    if (("" + date).equals("")) {
      return null;
    }
    if (pattern == null) {
      pattern = "yyyy-MM-dd";
    }
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    Date newDate = new Date();
    try {
      newDate = sdf.parse(date);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return newDate;
  }

  public static String toString(Date date, String pattern)
  {
    if (date == null) {
      return "";
    }
    if (pattern == null) {
      pattern = "yyyy-MM-dd";
    }
    String dateString = "";
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    try {
      dateString = sdf.format(date);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return dateString;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.sdk.utils.DateUtil
 * JD-Core Version:    0.6.1
 */