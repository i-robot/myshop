package com.enation.app.base.core.service.solution;

import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.context.webcontext.WebSessionContext;
import java.util.ArrayList;
import java.util.List;

public class InstallUtil
{
  public static void putMessaage(String msg)
  {
    if (ThreadContextHolder.getSessionContext() != null) {
      List msgList = (List)ThreadContextHolder.getSessionContext().getAttribute("installMsg");
      if (msgList == null) {
        msgList = new ArrayList();
      }
      msgList.add(msg);
      ThreadContextHolder.getSessionContext().setAttribute("installMsg", msgList);
    }
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.solution.InstallUtil
 * JD-Core Version:    0.6.1
 */