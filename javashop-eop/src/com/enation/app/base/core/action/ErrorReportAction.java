package com.enation.app.base.core.action;

import com.enation.app.base.core.model.ErrorReport;
import com.enation.app.base.core.service.ErrorReportManager;
import com.enation.eop.processor.core.RemoteRequest;
import com.enation.framework.action.WWAction;
import com.enation.framework.util.DateUtil;
import java.util.List;
import org.apache.log4j.Logger;

public class ErrorReportAction extends WWAction
{
  private Integer id;
  private String error;
  private String info;
  private ErrorReportManager errorReportManager;
  private ErrorReport errorReport;

  public String execute()
  {
    try
    {
      ErrorReport report = new ErrorReport();
      report.setDateline(DateUtil.getDatelineLong());
      report.setError(this.error);
      report.setInfo(this.info);
      this.errorReportManager.add(report);
    }
    catch (Exception e) {
      this.logger.error(e.getMessage(), e);
    }
    this.msgs.add("感谢您的提交，我们成功已经收到错误报告。");

    return "message";
  }

  public String proxy()
  {
    RemoteRequest remoteRequest = new RemoteRequest();
    remoteRequest.execute("http://www.enationsoft.com/eop/errorReport.do", getResponse(), getRequest());
    this.json = "感谢您的提交，我们成功已经收到错误报告。";

    return "json_message";
  }

  public String list()
  {
    this.webpage = this.errorReportManager.list(getPage(), getPageSize());
    return "list";
  }

  public String view() {
    this.errorReport = this.errorReportManager.get(this.id);
    return "detail";
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public String getInfo()
  {
    return this.info;
  }

  public void setInfo(String info)
  {
    this.info = info;
  }

  public String getError()
  {
    return this.error;
  }

  public void setError(String error)
  {
    this.error = error;
  }

  public ErrorReportManager getErrorReportManager()
  {
    return this.errorReportManager;
  }

  public void setErrorReportManager(ErrorReportManager errorReportManager)
  {
    this.errorReportManager = errorReportManager;
  }

  public ErrorReport getErrorReport()
  {
    return this.errorReport;
  }

  public void setErrorReport(ErrorReport errorReport)
  {
    this.errorReport = errorReport;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.ErrorReportAction
 * JD-Core Version:    0.6.1
 */