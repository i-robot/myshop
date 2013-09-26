package com.enation.eop.processor.backend;

import com.enation.eop.processor.Processor;
import com.enation.eop.processor.core.Response;
import com.enation.eop.processor.core.StringResponse;
import com.enation.framework.context.spring.SpringContextHolder;
import com.enation.framework.plugin.IAjaxExecuteEnable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AjaxPluginProcessor
  implements Processor
{
  public Response process(int mode, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
  {
    Response response = new StringResponse();
    String beanid = httpRequest.getParameter("beanid");
    Object plugin = SpringContextHolder.getBean(beanid);

    if (plugin == null) {
      response.setContent("error:plugin is null");
      return response;
    }

    if (!(plugin instanceof IAjaxExecuteEnable)) {
      response.setContent("error:plugin is not a IAjaxExecuteEnable");
      return response;
    }

    IAjaxExecuteEnable ajaxPlugin = (IAjaxExecuteEnable)plugin;

    String content = ajaxPlugin.execute();
    response.setContent(content);
    return response;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.backend.AjaxPluginProcessor
 * JD-Core Version:    0.6.1
 */