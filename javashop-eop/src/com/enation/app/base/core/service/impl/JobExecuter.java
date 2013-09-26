package com.enation.app.base.core.service.impl;

import com.enation.app.base.core.plugin.job.JobExecutePluginsBundle;
import com.enation.app.base.core.service.IJobExecuter;

public class JobExecuter
  implements IJobExecuter
{
  private JobExecutePluginsBundle jobExecutePluginsBundle;

  public void everyHour()
  {
    this.jobExecutePluginsBundle.everyHourExcecute();
  }

  public void everyDay()
  {
    try {
      this.jobExecutePluginsBundle.everyDayExcecute();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void everyMonth()
  {
    this.jobExecutePluginsBundle.everyMonthExcecute();
  }

  public JobExecutePluginsBundle getJobExecutePluginsBundle() {
    return this.jobExecutePluginsBundle;
  }

  public void setJobExecutePluginsBundle(JobExecutePluginsBundle jobExecutePluginsBundle)
  {
    this.jobExecutePluginsBundle = jobExecutePluginsBundle;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.impl.JobExecuter
 * JD-Core Version:    0.6.1
 */