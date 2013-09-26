package com.enation.app.shop.core.service.impl.promotion;

import com.enation.app.shop.core.model.Promotion;
import com.enation.app.shop.core.service.promotion.IDiscountBehavior;
import com.enation.app.shop.core.service.promotion.IPromotionMethod;
import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.util.CurrencyUtil;
import javax.servlet.http.HttpServletRequest;

public class DiscountMethod
  implements IPromotionMethod, IDiscountBehavior
{
  public String getInputHtml(Integer pmtid, String solution)
  {
    FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
    freeMarkerPaser.setClz(getClass());
    freeMarkerPaser.putData("discount", solution);
    return freeMarkerPaser.proessPageContent();
  }

  public String getName()
  {
    return "discount";
  }

  public String onPromotionSave(Integer pmtid)
  {
    HttpServletRequest request = ThreadContextHolder.getHttpRequest();
    String discount = request.getParameter("discount");
    return discount == null ? "" : discount;
  }

  public Double discount(Promotion promotion, Double goodsPrice)
  {
    String solution = promotion.getPmt_solution();
    Double discount = Double.valueOf(solution);
    return CurrencyUtil.mul(goodsPrice.doubleValue(), discount.doubleValue());
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.promotion.DiscountMethod
 * JD-Core Version:    0.6.1
 */