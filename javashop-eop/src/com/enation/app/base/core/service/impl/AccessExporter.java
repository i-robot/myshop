package com.enation.app.base.core.service.impl;

import com.enation.eop.resource.model.Access;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.util.DateUtil;
import com.enation.framework.util.ExcelUtil;
import com.enation.framework.util.FileUtil;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;

public class AccessExporter
  implements Runnable
{
  private IDaoSupport daoSupport;
  private Integer userid;
  private Integer siteid;

  public void setContext(Integer userid, Integer siteid)
  {
    this.userid = userid;
    this.siteid = siteid;
  }

  public void run()
  {
    String tablename = "es_access";
    if ("2".equals(EopSetting.RUNMODE)) {
      tablename = tablename + "_" + this.userid + "_" + this.siteid;
    }

    ExcelUtil excelUtil = new ExcelUtil();

    InputStream in = FileUtil.getResourceAsStream("com/enation/eop/resource/access.xls");
    excelUtil.openModal(in);

    String[] lastMonth = DateUtil.getLastMonth();
    int start = (int)(DateUtil.toDate(lastMonth[0], "yyyy-MM-dd").getTime() / 1000L);
    int end = (int)(DateUtil.toDate(lastMonth[1], "yyyy-MM-dd").getTime() / 1000L);

    String sql = "select * from " + tablename + " where access_time>=? and access_time<=? order by access_time";
    List<Access> list = this.daoSupport.queryForList(sql, Access.class, new Object[] { Integer.valueOf(start), Integer.valueOf(end) });

    if ((list != null) && (!list.isEmpty()))
    {
      int i = 1;
      for (Access access : list) {
        excelUtil.writeStringToCell(i, 0, access.getIp());
        excelUtil.writeStringToCell(i, 1, access.getArea());
        excelUtil.writeStringToCell(i, 2, access.getPage());
        excelUtil.writeStringToCell(i, 3, "" + access.getStay_time());
        excelUtil.writeStringToCell(i, 4, DateUtil.toString(new Date(access.getAccess_time() * 1000L), "yyyy-MM-dd hh:mm:ss"));
        excelUtil.writeStringToCell(i, 5, "" + access.getPoint());
        i++;
      }
      String target = EopSetting.IMG_SERVER_PATH;

      if ("2".equals(EopSetting.RUNMODE))
        target = target + "/user/" + this.userid + "/" + this.siteid + "/access";
      else {
        target = target + "/access";
      }
      File file = new File(target);
      if (!file.exists()) file.mkdirs();

      excelUtil.writeToFile(target + "/access" + lastMonth[0].replaceAll("-01", "") + ".xls");

      sql = "select sum(point) point  from " + tablename + " where access_time>=? and access_time<=?";
      Long sumpoint = Long.valueOf(this.daoSupport.queryForLong(sql, new Object[] { Integer.valueOf(start), Integer.valueOf(end) }));
      this.daoSupport.execute("update eop_site set sumpoint=sumpoint+?  where id=?", new Object[] { sumpoint, this.siteid });

      sql = "select count(0) c  from " + tablename + " where access_time>=? and access_time<=?";
      Long sumaccess = Long.valueOf(this.daoSupport.queryForLong(sql, new Object[] { Integer.valueOf(start), Integer.valueOf(end) }));
      this.daoSupport.execute("update eop_site set sumaccess=sumaccess+?  where id=?", new Object[] { sumaccess, this.siteid });

      this.daoSupport.execute("delete  from " + tablename + " where access_time>=? and access_time<=?", new Object[] { Integer.valueOf(start), Integer.valueOf(end) });
    }
  }

  public static void main(String[] args) {
    String date = "2010-06-03";
    System.out.println(DateUtil.toDate(date, "yyyy-MM-dd").getTime() / 1000L);
  }

  public Integer getUserid()
  {
    return this.userid;
  }

  public void setUserid(Integer userid) {
    this.userid = userid;
  }

  public Integer getSiteid() {
    return this.siteid;
  }

  public void setSiteid(Integer siteid) {
    this.siteid = siteid;
  }

  public IDaoSupport getDaoSupport() {
    return this.daoSupport;
  }

  public void setDaoSupport(IDaoSupport daoSupport) {
    this.daoSupport = daoSupport;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.impl.AccessExporter
 * JD-Core Version:    0.6.1
 */