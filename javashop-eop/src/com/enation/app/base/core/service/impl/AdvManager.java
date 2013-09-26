package com.enation.app.base.core.service.impl;

import com.enation.app.base.core.model.Adv;
import com.enation.app.base.core.model.AdvMapper;
import com.enation.app.base.core.service.IAdvManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.eop.sdk.utils.UploadUtil;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import com.enation.framework.util.StringUtil;
import java.util.Date;
import java.util.List;

public class AdvManager extends BaseSupport<Adv>
  implements IAdvManager
{
  public void addAdv(Adv adv)
  {
    this.baseDaoSupport.insert("adv", adv);
  }

  public void delAdvs(String ids)
  {
    if ((ids == null) || (ids.equals("")))
      return;
    String sql = "delete from adv where aid in (" + ids + ")";

    this.baseDaoSupport.execute(sql, new Object[0]);
  }

  public Adv getAdvDetail(Long advid)
  {
    Adv adv = (Adv)this.baseDaoSupport.queryForObject("select * from adv where aid = ?", Adv.class, new Object[] { advid });
    String pic = adv.getAtturl();
    if (pic != null) {
      pic = UploadUtil.replacePath(pic);
      adv.setAtturl(pic);
    }
    return adv;
  }

  public Page pageAdv(String order, int page, int pageSize)
  {
    order = order == null ? " aid desc" : order;
    String sql = "select v.*, c.cname   cname from " + getTableName("adv") + " v left join " + getTableName("adcolumn") + " c on c.acid = v.acid";
    sql = sql + " order by " + order;
    Page rpage = this.daoSupport.queryForPage(sql, page, pageSize, new AdvMapper(), new Object[0]);
    return rpage;
  }

  public void updateAdv(Adv adv)
  {
    this.baseDaoSupport.update("adv", adv, "aid = " + adv.getAid());
  }

  public List listAdv(Long acid)
  {
    Long nowtime = Long.valueOf(new Date().getTime());

    List list = this.baseDaoSupport.queryForList("select a.*,'' cname from adv a where acid = ? and begintime<=? and endtime>=? and isclose = 0", new AdvMapper(), new Object[] { acid, nowtime, nowtime });
    return list;
  }

  public Page search(Long acid, String cname, int pageNo, int pageSize, String order)
  {
    StringBuffer term = new StringBuffer();
    StringBuffer sql = new StringBuffer("select v.*, c.cname   cname from " + getTableName("adv") + " v left join " + getTableName("adcolumn") + " c on c.acid = v.acid");

    if (acid != null) {
      term.append(" where  c.acid=" + acid);
    }

    if (!StringUtil.isEmpty(cname)) {
      if (term.length() > 0) {
        term.append(" and ");
      }
      else
      {
        term.append(" where ");
      }

      term.append(" aname like'%" + cname + "%'");
    }
    sql.append(term);

    order = order == null ? " aid desc" : order;
    sql.append(" order by " + order);
    Page page = this.daoSupport.queryForPage(sql.toString(), pageNo, pageSize, new Object[0]);
    return page;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.impl.AdvManager
 * JD-Core Version:    0.6.1
 */