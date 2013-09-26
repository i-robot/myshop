package com.enation.framework.jms;

import javax.jms.Queue;
import org.springframework.jms.core.JmsTemplate;

public class EmailProducer
{
  private JmsTemplate template;
  private Queue destination;

  public void setTemplate(JmsTemplate template)
  {
    this.template = template;
  }

  public void setDestination(Queue destination) {
    this.destination = destination;
  }

  public void send(EmailModel emailModel) {
    this.template.convertAndSend(this.destination, emailModel);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.jms.EmailProducer
 * JD-Core Version:    0.6.1
 */