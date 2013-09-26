package com.enation.eop.processor.backend;

import com.enation.eop.processor.Processor;
import com.enation.eop.processor.core.Response;
import com.enation.eop.processor.core.StringResponse;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.context.webcontext.WebSessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutProcessor
  implements Processor
{
  public Response process(int mode, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
  {
    WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();
    Response response = new StringResponse();
    sessonContext.removeAttribute("usercontext");
    response.setContent("<script>location.href='index.jsp'</script>");
    return response;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.backend.LogoutProcessor
 * JD-Core Version:    0.6.1
 */