package com.enation.eop.resource.impl;

import com.enation.eop.resource.IMenuManager;
import com.enation.eop.resource.model.Menu;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class MenuManagerImpl extends BaseSupport<Menu>
  implements IMenuManager
{
  public void clean()
  {
    this.baseDaoSupport.execute("truncate table menu", new Object[0]);
  }
  public List<Menu> getMenuList() {
    return this.baseDaoSupport.queryForList("select * from menu where deleteflag = '0' order by sorder asc", Menu.class, new Object[0]);
  }

  public Integer add(Menu menu)
  {
    if (menu.getTitle() == null) throw new IllegalArgumentException("title argument is null");
    if (menu.getPid() == null) throw new IllegalArgumentException("pid argument is null");
    if (menu.getUrl() == null) throw new IllegalArgumentException("url argument is null");
    if (menu.getSorder() == null) throw new IllegalArgumentException("sorder argument is null");
    menu.setDeleteflag(Integer.valueOf(0));
    this.baseDaoSupport.insert("menu", menu);
    return Integer.valueOf(this.baseDaoSupport.getLastId("menu"));
  }

  public List<Menu> getMenuTree(Integer menuid)
  {
    if (menuid == null) throw new IllegalArgumentException("menuid argument is null");
    List<Menu> menuList = getMenuList();
    List topMenuList = new ArrayList();
    for (Menu menu : menuList) {
      if (menu.getPid().compareTo(menuid) == 0) {
        List children = getChildren(menuList, menu.getId());
        menu.setChildren(children);
        topMenuList.add(menu);
      }
    }
    return topMenuList;
  }

  private List<Menu> getChildren(List<Menu> menuList, Integer parentid)
  {
    List children = new ArrayList();
    for (Menu menu : menuList) {
      if (menu.getPid().compareTo(parentid) == 0) {
        menu.setChildren(getChildren(menuList, menu.getId()));
        children.add(menu);
      }
    }
    return children;
  }

  public Menu get(Integer id)
  {
    if (id == null) throw new IllegalArgumentException("ids argument is null");
    String sql = "select * from menu where id=?";
    return (Menu)this.baseDaoSupport.queryForObject(sql, Menu.class, new Object[] { id });
  }

  public Menu get(String title) {
    String sql = "select * from menu where title=?";
    List menuList = this.baseDaoSupport.queryForList(sql, Menu.class, new Object[] { title });

    if (menuList.isEmpty()) return null;
    return (Menu)menuList.get(0);
  }

  public void edit(Menu menu)
  {
    if (menu.getId() == null) throw new IllegalArgumentException("id argument is null");
    if (menu.getTitle() == null) throw new IllegalArgumentException("title argument is null");
    if (menu.getPid() == null) throw new IllegalArgumentException("pid argument is null");
    if (menu.getUrl() == null) throw new IllegalArgumentException("url argument is null");
    if (menu.getSorder() == null) throw new IllegalArgumentException("sorder argument is null");
    menu.setDeleteflag(Integer.valueOf(0));
    this.baseDaoSupport.update("menu", menu, "id=" + menu.getId());
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void updateSort(Integer[] ids, Integer[] sorts) {
    if (ids == null) throw new IllegalArgumentException("ids argument is null");
    if (sorts == null) throw new IllegalArgumentException("sorts argument is null");
    if (sorts.length != ids.length) throw new IllegalArgumentException("ids's length and sorts's length not same");
    for (int i = 0; i < ids.length; i++) {
      String sql = "update menu set sorder=? where id=?";
      this.baseDaoSupport.execute(sql, new Object[] { sorts[i], ids[i] });
    }
  }

  public void delete(Integer id) throws RuntimeException
  {
    if (id == null) throw new IllegalArgumentException("ids argument is null");
    String sql = "select count(0) from menu where pid=?";
    int count = this.baseDaoSupport.queryForInt(sql, new Object[] { id });
    if (count > 0) throw new RuntimeException("菜单" + id + "存在子类别,不能直接删除，请先删除其子类别。");
    sql = "delete from menu where id=?";
    this.baseDaoSupport.execute(sql, new Object[] { id });
  }

  public void delete(String title)
  {
    String sql = "delete from menu where title=?";
    this.baseDaoSupport.execute(sql, new Object[] { title });
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.impl.MenuManagerImpl
 * JD-Core Version:    0.6.1
 */