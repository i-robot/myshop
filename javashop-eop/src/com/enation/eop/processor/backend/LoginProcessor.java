package com.enation.eop.processor.backend;

import com.enation.app.base.core.service.auth.IAdminUserManager;
import com.enation.eop.processor.Processor;
import com.enation.eop.processor.core.EopException;
import com.enation.eop.processor.core.Response;
import com.enation.eop.processor.core.StringResponse;
import com.enation.framework.context.spring.SpringContextHolder;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.context.webcontext.WebSessionContext;
import com.enation.framework.util.EncryptionUtil;
import com.enation.framework.util.HttpUtil;
import com.enation.framework.util.StringUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class LoginProcessor
  implements Processor
{
  protected final Logger logger = Logger.getLogger(getClass());

  public Response process(int mode, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
  {
    String type = httpRequest.getParameter("type");
    if ((type == null) || ("".equals(type))) {
      return userLogin(httpResponse, httpRequest);
    }
    return sysLogin(httpResponse, httpRequest);
  }

  public Response sysLogin(HttpServletResponse httpResponse, HttpServletRequest httpRequest)
  {
    Response response = new StringResponse();

    String endata = httpRequest.getParameter("s");
    if (endata == null) { response.setContent("非法数据"); return response;
    }
    endata = EncryptionUtil.authCode(endata, "DECODE");
    String[] ar = StringUtils.split(endata, ",");

    String username = ar[0];
    String password = ar[1];
    Long dateline = Long.valueOf(ar[2]);
    try
    {
      IAdminUserManager userManager = (IAdminUserManager)SpringContextHolder.getBean("adminUserManager");
      int result = userManager.loginBySys(username, password);
      if (result == 1)
        response.setContent("<script>location.href='main.jsp'</script>正在转向后台...");
      else {
        response.setContent("{'result':1,'message':'登录失败：用户名或密码错误'}");
      }
      return response;
    }
    catch (EopException exception)
    {
      response.setContent("{'result':1,'message':'" + exception.getMessage() + "'}");
    }return response;
  }

  public Response userLogin(HttpServletResponse httpResponse, HttpServletRequest httpRequest)
  {
    String username = httpRequest.getParameter("username");
    String password = httpRequest.getParameter("password");
    String valid_code = httpRequest.getParameter("valid_code");
    try
    {
      if (valid_code == null) throw new EopException("验证码输入错误");
      WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();
      Object realCode = sessonContext.getAttribute("valid_codeadmin");

      if (!valid_code.equals(realCode)) {
        throw new EopException("验证码输入错误");
      }

      IAdminUserManager userManager = (IAdminUserManager)SpringContextHolder.getBean("adminUserManager");
      userManager.login(username, password);

      StringBuffer json = new StringBuffer();
      json.append("{'result':0}");

      Response response = new StringResponse();
      response.setContent(json.toString());

      String remember_login_name = httpRequest.getParameter("remember_login_name");
      if (!StringUtil.isEmpty(remember_login_name))
        HttpUtil.addCookie(httpResponse, "loginname", username, 31536000);
      else {
        HttpUtil.addCookie(httpResponse, "loginname", "", 0);
      }

      return response;
    }
    catch (RuntimeException exception) {
      exception.printStackTrace();
      this.logger.error(exception.getMessage(), exception.fillInStackTrace());
      Response response = new StringResponse();
      response.setContent("{'result':1,'message':'" + exception.getMessage() + "'}");
      return response;
    }
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.processor.backend.LoginProcessor
 * JD-Core Version:    0.6.1
 */