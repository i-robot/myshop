package com.enation.framework.jms;

import java.util.HashMap;
import java.util.Map;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.jms.support.converter.MessageConverter;

public class EmailConverter
  implements MessageConverter
{
  public Message toMessage(Object obj, Session session)
    throws JMSException
  {
    if ((obj instanceof EmailModel)) {
      ActiveMQObjectMessage objMsg = (ActiveMQObjectMessage)session.createObjectMessage();
      Map map = new HashMap();
      map.put("EmailModel", (EmailModel)obj);
      objMsg.setObjectProperty("Map", map);
      return objMsg;
    }
    throw new JMSException("Object:[" + obj + "] is not Member");
  }

  public Object fromMessage(Message msg)
    throws JMSException
  {
    if ((msg instanceof ObjectMessage)) {
      return ((Map)((ObjectMessage)msg).getObjectProperty("Map")).get("EmailModel");
    }
    throw new JMSException("Msg:[" + msg + "] is not Map");
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.jms.EmailConverter
 * JD-Core Version:    0.6.1
 */