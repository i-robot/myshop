package com.enation.framework.jms;

import com.enation.app.base.core.model.Smtp;
import com.enation.app.base.core.service.ISmtpManager;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.eop.sdk.utils.FreeMarkerUtil;
import com.sun.xml.messaging.saaj.util.ByteOutputStream;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailConsumer
{
  private JavaMailSender mailSender;
  private ISmtpManager smtpManager;

  public void sendEmail(EmailModel emailModel)
  {
    try
    {
      EopContext.setContext(emailModel.getEopContext());

      Smtp smtp = this.smtpManager.getCurrentSmtp();
      JavaMailSenderImpl javaMailSender = (JavaMailSenderImpl)this.mailSender;
      javaMailSender.setHost(smtp.getHost());
      javaMailSender.setUsername(smtp.getUsername());
      javaMailSender.setPassword(smtp.getPassword());

      MimeMessage message = this.mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

      helper.setSubject(emailModel.getTitle());
      helper.setTo(emailModel.getTo());
      helper.setFrom(smtp.getMail_from());

      Configuration cfg = FreeMarkerUtil.getCfg();

      String pageFolder = EopSetting.EOP_PATH + EopContext.getContext().getContextPath() + "/themes/";
      cfg.setDirectoryForTemplateLoading(new File(pageFolder));

      Template temp = cfg.getTemplate(emailModel.getTemplate());
      ByteOutputStream stream = new ByteOutputStream();

      Writer out = new OutputStreamWriter(stream);
      temp.process(emailModel.getData(), out);

      out.flush();
      String html = stream.toString();
      helper.setText(html, true);
      javaMailSender.send(message);
      this.smtpManager.sendOneMail(smtp);
      EopContext.remove();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public JavaMailSender getMailSender() {
    return this.mailSender;
  }

  public void setMailSender(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public ISmtpManager getSmtpManager() {
    return this.smtpManager;
  }

  public void setSmtpManager(ISmtpManager smtpManager) {
    this.smtpManager = smtpManager;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.jms.EmailConsumer
 * JD-Core Version:    0.6.1
 */