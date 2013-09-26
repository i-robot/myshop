package com.enation.framework.context.webcontext.impl;

import com.enation.framework.context.webcontext.WebSessionContext;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Hashtable;
import java.util.Set;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WebSessionContextImpl
  implements WebSessionContext, Externalizable
{
  private HttpSession session;
  private final Log logger = LogFactory.getLog(getClass());
  private Hashtable attributes;

  public HttpSession getSession()
  {
    return this.session;
  }

  public void setSession(HttpSession session)
  {
    this.session = session;
    this.attributes = ((Hashtable)this.session.getAttribute("EOPSessionKey"));

    if (this.attributes == null) {
      this.attributes = new Hashtable();
      onSaveSessionAttribute();
    }
  }

  public void invalidateSession()
  {
    this.session.invalidate();
  }

  private void onSaveSessionAttribute()
  {
    this.session.setAttribute("EOPSessionKey", this.attributes);
  }

  public void setAttribute(String name, Object value)
  {
    if (this.attributes != null)
    {
      this.attributes.put(name, value);
      onSaveSessionAttribute();
    }
  }

  public Object getAttribute(String name)
  {
    if (this.attributes != null)
      return this.attributes.get(name);
    return null;
  }

  public Set getAttributeNames()
  {
    return this.attributes.keySet();
  }

  public void removeAttribute(String name)
  {
    this.attributes.remove(name);
    onSaveSessionAttribute();
  }

  public void readExternal(ObjectInput input)
    throws IOException, ClassNotFoundException
  {
    this.attributes = ((Hashtable)input.readObject());
  }

  public void writeExternal(ObjectOutput output) throws IOException {
    output.writeObject(this.attributes);
  }

  public void destory() {
    this.attributes = null;
    this.session = null;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.context.webcontext.impl.WebSessionContextImpl
 * JD-Core Version:    0.6.1
 */