package com.enation.app.base.core.service.impl.cache;

import com.enation.app.base.core.model.Smtp;
import com.enation.app.base.core.service.ISmtpManager;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.cache.AbstractCacheProxy;
import com.enation.framework.cache.ICache;
import com.enation.framework.util.DateUtil;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

public class SmtpCacheProxy extends AbstractCacheProxy<List<Smtp>>
  implements ISmtpManager
{
  private static final String cacheName = "smtp_cache";
  private ISmtpManager smtpManager;

  public SmtpCacheProxy(ISmtpManager _smtpManager)
  {
    super("smtp_cache");
    this.smtpManager = _smtpManager;
  }

  private String getKey()
  {
    if ("2".equals(EopSetting.RUNMODE)) {
      EopSite site = EopContext.getContext().getCurrentSite();
      return "smtp_cache_" + site.getUserid() + "_" + site.getId();
    }
    return "smtp_cache";
  }

  private void cleanCache()
  {
    String mainkey = getKey();
    this.cache.remove(mainkey);
  }

  private void put(List<Smtp> smtpList)
  {
    String mainkey = getKey();
    this.cache.put(mainkey, smtpList);
  }

  private List<Smtp> get() {
    String mainkey = getKey();
    return (List)this.cache.get(mainkey);
  }

  public void add(Smtp smtp)
  {
    this.smtpManager.add(smtp);
    cleanCache();
  }

  public void edit(Smtp smtp)
  {
    this.smtpManager.edit(smtp);
    cleanCache();
  }

  public void delete(Integer[] idAr)
  {
    this.smtpManager.delete(idAr);
    cleanCache();
  }

  public List<Smtp> list()
  {
    List smtpList = get();
    if (smtpList == null) {
      smtpList = this.smtpManager.list();
      put(smtpList);
    }
    return smtpList;
  }

  public void sendOneMail(Smtp currSmtp)
  {
    currSmtp.setLast_send_time(DateUtil.getDatelineLong());
    currSmtp.setSend_count(currSmtp.getSend_count() + 1);

    this.smtpManager.sendOneMail(currSmtp);
  }

  public Smtp get(int id)
  {
    return this.smtpManager.get(id);
  }

  public Smtp getCurrentSmtp()
  {
    List<Smtp> smtpList = list();
    if ((smtpList == null) || (smtpList == null)) throw new RuntimeException("没有可用的smtp服务器");

    Smtp currentSmtp = null;

    for (Smtp smtp : smtpList) {
      if (checkCount(smtp)) {
        currentSmtp = smtp;
        break;
      }

    }

    if (currentSmtp == null) {
      this.logger.error("未寻找可用smtp");
      throw new RuntimeException("未找到可用smtp，都已达到最大发信数 ");
    }

    if (this.logger.isDebugEnabled()) {
      this.logger.debug("找到smtp->host[" + currentSmtp.getHost() + "],username[" + currentSmtp.getUsername() + "]");
    }

    return currentSmtp;
  }

  private boolean checkCount(Smtp smtp)
  {
    long last_send_time = smtp.getLast_send_time();

    if (!DateUtil.toString(new Date(last_send_time * 1000L), "yyyy-MM-dd").equals(DateUtil.toString(new Date(), "yyyy-MM-dd"))) {
      smtp.setSend_count(0);

      if (this.logger.isDebugEnabled()) {
        this.logger.debug("host[" + smtp.getHost() + "]不是今天,此smtp发信数置为0");
      }
    }

    return smtp.getSend_count() < smtp.getMax_count();
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.impl.cache.SmtpCacheProxy
 * JD-Core Version:    0.6.1
 */