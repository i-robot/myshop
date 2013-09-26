package com.enation.app.shop.core.service.impl;

import com.enation.app.shop.core.model.PrintTmpl;
import com.enation.app.shop.core.service.IPrintTmplManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.util.StringUtil;
import java.util.List;

public class PrintTmplManager extends BaseSupport<PrintTmpl>
  implements IPrintTmplManager
{
  public void add(PrintTmpl printTmpl)
  {
    this.baseDaoSupport.insert("print_tmpl", printTmpl);
  }

  public void clean(Integer[] id)
  {
    if ((id == null) || (id.length == 0)) return;
    String ids = StringUtil.arrayToString(id, ",");
    this.baseDaoSupport.execute("delete from print_tmpl where prt_tmpl_id in (" + ids + ")", new Object[0]);
  }

  public void delete(Integer[] id)
  {
    if ((id == null) || (id.length == 0)) return;
    String ids = StringUtil.arrayToString(id, ",");
    this.baseDaoSupport.execute("update print_tmpl set disabled = 'true' where prt_tmpl_id in (" + ids + ")", new Object[0]);
  }

  public void edit(PrintTmpl printTmpl)
  {
    this.baseDaoSupport.update("print_tmpl", printTmpl, "prt_tmpl_id = " + printTmpl.getPrt_tmpl_id());
  }

  public List list()
  {
    return this.baseDaoSupport.queryForList("select * from print_tmpl where disabled = 'false'", PrintTmpl.class, new Object[0]);
  }

  public void revert(Integer[] id)
  {
    if ((id == null) || (id.length == 0)) return;
    String ids = StringUtil.arrayToString(id, ",");
    this.baseDaoSupport.execute("update print_tmpl set disabled = 'false' where prt_tmpl_id in (" + ids + ")", new Object[0]);
  }

  public List trash()
  {
    return this.baseDaoSupport.queryForList("select * from print_tmpl where disabled = 'true'", PrintTmpl.class, new Object[0]);
  }

  public PrintTmpl get(int prt_tmpl_id)
  {
    return (PrintTmpl)this.baseDaoSupport.queryForObject("select * from print_tmpl where prt_tmpl_id = ?", PrintTmpl.class, new Object[] { Integer.valueOf(prt_tmpl_id) });
  }

  public List listCanUse()
  {
    return this.baseDaoSupport.queryForList("select * from print_tmpl where disabled = 'false' and shortcut = 'true'", PrintTmpl.class, new Object[0]);
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.PrintTmplManager
 * JD-Core Version:    0.6.1
 */