package com.enation.app.shop.core.model;

import com.enation.app.base.core.model.Member;

public class MemberDTO extends Member
{
  private String lv_name;

  public String getLv_name()
  {
    return this.lv_name;
  }

  public void setLv_name(String lv_name) {
    this.lv_name = lv_name;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.MemberDTO
 * JD-Core Version:    0.6.1
 */