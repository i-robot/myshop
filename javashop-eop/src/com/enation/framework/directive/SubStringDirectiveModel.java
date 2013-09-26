package com.enation.framework.directive;

import com.enation.framework.util.StringUtil;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class SubStringDirectiveModel
  implements TemplateDirectiveModel
{
  public void execute(Environment env, Map params, TemplateModel[] arg2, TemplateDirectiveBody arg3)
    throws TemplateException, IOException
  {
    String title = params.get("title").toString();
    int length = StringUtil.toInt(params.get("length").toString());
    String dot = params.get("dot").toString();
    String link = "";

    int titleLength = getLength(title);

    if (titleLength > 0) {
      if (length > titleLength)
        link = title;
      else {
        link = getSubString(title, 0, length) + dot;
      }

    }

    env.getOut().write(link);
  }

  private int getLength(String str)
  {
    int count1 = 0;
    if ((str == null) || (str.equals("")))
      return count1;
    char[] temp = new char[str.length()];
    str.getChars(0, str.length(), temp, 0);
    boolean[] bol = new boolean[str.length()];
    for (int i = 0; i < temp.length; i++) {
      bol[i] = false;
      if (temp[i] > 'ÿ') {
        count1++;
        bol[i] = true;
      }
    }
    return count1 + str.length();
  }

  private String getSubString(String str, int pstart, int pend)
  {
    String resu = "";
    int beg = 0;
    int end = 0;
    int count1 = 0;
    char[] temp = new char[str.length()];
    str.getChars(0, str.length(), temp, 0);
    boolean[] bol = new boolean[str.length()];
    for (int i = 0; i < temp.length; i++) {
      bol[i] = false;
      if (temp[i] > 'ÿ') {
        count1++;
        bol[i] = true;
      }
    }

    if (pstart > str.length() + count1) {
      resu = null;
    }
    if (pstart > pend) {
      resu = null;
    }
    if (pstart < 1)
      beg = 0;
    else {
      beg = pstart - 1;
    }
    if (pend > str.length() + count1)
      end = str.length() + count1;
    else {
      end = pend;
    }

    if (resu != null) {
      if (beg == end) {
        int count = 0;
        if (beg == 0) {
          if (bol[0])
            resu = null;
          else
            resu = new String(temp, 0, 1);
        } else {
          int len = beg;
          for (int y = 0; y < len; y++) {
            if (bol[y])
              count++;
            len--;
          }

          if (count == 0) {
            if (temp[beg] > 'ÿ')
              resu = null;
            else
              resu = new String(temp, beg, 1);
          }
          else if (temp[(len + 1)] > 'ÿ')
            resu = null;
          else
            resu = new String(temp, len + 1, 1);
        }
      }
      else {
        int temSt = beg;
        int temEd = end - 1;
        for (int i = 0; i < temSt; i++) {
          if (bol[i])
            temSt--;
        }
        for (int j = 0; j < temEd; j++) {
          if (bol[j])
            temEd--;
        }
        if (bol[temSt])
        {
          int cont = 0;
          for (int i = 0; i <= temSt; i++) {
            cont++;
            if (bol[i])
              cont++;
          }
          if (pstart == cont)
            temSt++;
        }
        if (bol[temEd])
        {
          int cont = 0;
          for (int i = 0; i <= temEd; i++) {
            cont++;
            if (bol[i])
              cont++;
          }
          if (pend < cont)
            temEd--;
        }
        if (temSt == temEd)
          resu = new String(temp, temSt, 1);
        else if (temSt > temEd)
          resu = null;
        else {
          resu = str.substring(temSt, temEd + 1);
        }
      }
    }
    return resu;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.directive.SubStringDirectiveModel
 * JD-Core Version:    0.6.1
 */