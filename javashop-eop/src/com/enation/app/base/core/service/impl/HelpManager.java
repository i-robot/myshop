package com.enation.app.base.core.service.impl;

import com.enation.app.base.core.model.Help;
import com.enation.app.base.core.service.IHelpManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;

public class HelpManager extends BaseSupport<Help>
  implements IHelpManager
{
  public Help get(String helpid)
  {
    String sql = "select * from es_help_1_1 where helpid=?";
    return (Help)this.daoSupport.queryForObject(sql, Help.class, new Object[] { helpid });
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.impl.HelpManager
 * JD-Core Version:    0.6.1
 */