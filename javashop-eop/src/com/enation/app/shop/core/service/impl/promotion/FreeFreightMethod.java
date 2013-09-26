package com.enation.app.shop.core.service.impl.promotion;

import com.enation.app.shop.core.service.promotion.IPromotionMethod;
import com.enation.app.shop.core.service.promotion.IReduceFreightBehavior;
import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import javax.servlet.http.HttpServletRequest;

public class FreeFreightMethod
  implements IPromotionMethod, IReduceFreightBehavior
{
  public String getInputHtml(Integer pmtid, String solution)
  {
    FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
    freeMarkerPaser.setClz(getClass());
    freeMarkerPaser.putData("free", solution);
    return freeMarkerPaser.proessPageContent();
  }

  public String getName()
  {
    return "free";
  }

  public String onPromotionSave(Integer pmtid)
  {
    HttpServletRequest request = ThreadContextHolder.getHttpRequest();
    String free = request.getParameter("free");
    return free == null ? "" : free;
  }

  public Double reducedPrice(Double freight)
  {
    return Double.valueOf(0.0D);
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.promotion.FreeFreightMethod
 * JD-Core Version:    0.6.1
 */