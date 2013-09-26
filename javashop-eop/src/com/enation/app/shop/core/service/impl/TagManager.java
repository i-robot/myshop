package com.enation.app.shop.core.service.impl;

import com.enation.app.shop.core.model.Tag;
import com.enation.app.shop.core.service.ITagManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import com.enation.framework.util.StringUtil;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class TagManager extends BaseSupport<Tag>
  implements ITagManager
{
  private IDaoSupport daoSupport;
  private JdbcTemplate jdbcTemplate;

  public boolean checkname(String name, Integer tagid)
  {
    if (name != null) name = name.trim();
    if (tagid == null) tagid = Integer.valueOf(0);
    String sql = "select count(0) from tags where tag_name=? and tag_id!=?";
    int count = this.baseDaoSupport.queryForInt(sql, new Object[] { name, tagid });
    if (count > 0) {
      return true;
    }
    return false;
  }

  public void add(Tag tag)
  {
    tag.setRel_count(Integer.valueOf(0));
    this.baseDaoSupport.insert("tags", tag);
  }

  public boolean checkJoinGoods(Integer[] tagids)
  {
    if (tagids == null) return false;
    String ids = StringUtil.implode(",", tagids);
    String sql = "select count(0)  from tag_rel where tag_id in(" + ids + ")";
    int count = this.baseDaoSupport.queryForInt(sql, new Object[0]);
    if (count > 0) {
      return true;
    }
    return false;
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void delete(Integer[] tagids) {
    String ids = StringUtil.implode(",", tagids);
    if ((ids == null) || (ids.equals(""))) return;

    this.baseDaoSupport.execute("delete from tags where tag_id in(" + ids + ")", new Object[0]);
    this.daoSupport.execute("delete from " + getTableName("tag_rel") + " where tag_id in(" + ids + ")", new Object[0]);
  }

  public Page list(int pageNo, int pageSize)
  {
    return this.baseDaoSupport.queryForPage("select * from tags order by tag_id", pageNo, pageSize, new Object[0]);
  }

  public void update(Tag tag)
  {
    this.baseDaoSupport.update("tags", tag, "tag_id=" + tag.getTag_id());
  }

  public IDaoSupport<Tag> getDaoSupport()
  {
    return this.daoSupport;
  }

  public void setDaoSupport(IDaoSupport<Tag> daoSupport) {
    this.daoSupport = daoSupport;
  }

  public Tag getById(Integer tagid)
  {
    String sql = "select * from tags where tag_id=?";
    Tag tag = (Tag)this.baseDaoSupport.queryForObject(sql, Tag.class, new Object[] { tagid });
    return tag;
  }

  public List<Tag> list()
  {
    String sql = "select * from tags";
    return this.baseDaoSupport.queryForList(sql, Tag.class, new Object[0]);
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void saveRels(Integer relid, Integer[] tagids)
  {
    String sql = "delete from " + getTableName("tag_rel") + " where rel_id=?";
    this.daoSupport.execute(sql, new Object[] { relid });
    if (tagids != null)
    {
      sql = "insert into " + getTableName("tag_rel") + "(tag_id,rel_id)values(?,?)";
      for (Integer tagid : tagids)
        this.daoSupport.execute(sql, new Object[] { tagid, relid });
    }
  }

  public List<Integer> list(Integer relid)
  {
    String sql = "select tag_id from " + getTableName("tag_rel") + " where rel_id=" + relid;
    List tagids = this.jdbcTemplate.queryForList(sql, Integer.class);
    return tagids;
  }

  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.TagManager
 * JD-Core Version:    0.6.1
 */