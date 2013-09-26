package com.enation.framework.taglib;

import com.enation.framework.util.DateUtil;
import java.util.Date;
import javax.servlet.jsp.JspException;

public class DateFormatTaglib extends EnationTagSupport
{
  private Long time;
  private String times;
  private String pattern;

  public int doEndTag()
    throws JspException
  {
    this.time = (this.times == null ? this.time : Long.valueOf(this.times));
    if (this.time.longValue() > 0L) {
      if (this.time.toString().length() == 10) {
        this.time = Long.valueOf(this.time.longValue() * 1000L);
      }
      Date date = new Date(this.time.longValue());
      String str = DateUtil.toString(date, this.pattern);
      print(str);
    } else {
      print("");
    }
    return 1;
  }

  public int doStartTag() throws JspException
  {
    return 6;
  }

  public Long getTime() {
    return this.time;
  }

  public void setTime(Long time) {
    this.time = time;
  }

  public String getPattern() {
    return this.pattern;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public String getTimes() {
    return this.times;
  }

  public void setTimes(String times) {
    this.times = times;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.taglib.DateFormatTaglib
 * JD-Core Version:    0.6.1
 */