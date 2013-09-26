package com.enation.framework.pager;

import com.enation.framework.pager.impl.AjaxPagerHtmlBuilder;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class AjaxPagerDirectiveModel
  implements TemplateDirectiveModel
{
  public void execute(Environment env, Map params, TemplateModel[] arg2, TemplateDirectiveBody arg3)
    throws TemplateException, IOException
  {
    String pageno = params.get("pageno").toString();
    String pagesize = params.get("pagesize").toString();
    String totalcount = params.get("totalcount").toString();
    int _pageNum = Integer.valueOf(pageno).intValue();
    int _totalCount = Integer.valueOf(totalcount).intValue();
    int _pageSize = Integer.valueOf(pagesize).intValue();
    AjaxPagerHtmlBuilder pageHtmlBuilder = new AjaxPagerHtmlBuilder(_pageNum, _totalCount, _pageSize);
    String html = pageHtmlBuilder.buildPageHtml();
    env.getOut().write(html);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.pager.AjaxPagerDirectiveModel
 * JD-Core Version:    0.6.1
 */