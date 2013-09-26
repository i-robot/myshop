package com.enation.app.base.taglib;

import com.enation.app.base.component.widget.regions.RegionsSelectWidget;
import com.enation.framework.context.spring.SpringContextHolder;
import com.enation.framework.taglib.EnationTagSupport;
import com.enation.framework.util.StringUtil;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.jsp.JspException;

public class RegionSelectTaglib extends EnationTagSupport
{
  private String province_id;
  private String city_id;
  private String region_id;

  public int doStartTag()
    throws JspException
  {
    RegionsSelectWidget regionsSelect = (RegionsSelectWidget)SpringContextHolder.getBean("regionsSelect");
    Map params = new HashMap();
    String selectHtml = regionsSelect.process(params);
    if ((!StringUtil.isEmpty(this.province_id)) && (!StringUtil.isEmpty(this.city_id)) && (!StringUtil.isEmpty(this.region_id))) {
      selectHtml = selectHtml + "<script>$(function(){ RegionsSelect.load(" + this.province_id + "," + this.city_id + "," + this.region_id + ");  });</script>";
    }
    print(selectHtml);

    return 0;
  }

  public String getProvince_id()
  {
    return this.province_id;
  }

  public void setProvince_id(String province_id)
  {
    this.province_id = province_id;
  }

  public String getCity_id()
  {
    return this.city_id;
  }

  public void setCity_id(String city_id)
  {
    this.city_id = city_id;
  }

  public String getRegion_id()
  {
    return this.region_id;
  }

  public void setRegion_id(String region_id)
  {
    this.region_id = region_id;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.taglib.RegionSelectTaglib
 * JD-Core Version:    0.6.1
 */