package com.enation.app.base.core.action;

import com.enation.eop.resource.IMenuManager;
import com.enation.eop.resource.model.Menu;
import com.enation.framework.action.WWAction;
import java.util.List;
import org.apache.log4j.Logger;

public class MenuAction extends WWAction
{
  private IMenuManager menuManager;
  private List<Menu> menuList;
  private Menu menu;
  private Integer parentid;
  private Integer id;
  private Integer[] menu_ids;
  private Integer[] menu_sorts;

  public String list()
  {
    this.menuList = this.menuManager.getMenuTree(Integer.valueOf(0));
    return "list";
  }

  public String add() {
    this.menuList = this.menuManager.getMenuTree(Integer.valueOf(0));
    return "add";
  }

  public String saveAdd() {
    try {
      this.menuManager.add(this.menu);
      this.json = "{result:1}";
    } catch (RuntimeException e) {
      this.logger.error(e.getMessage(), e);
      this.json = ("{result:0,message:'" + e.getMessage() + "'}");
    }
    return "json_message";
  }

  public String edit()
  {
    this.menuList = this.menuManager.getMenuTree(Integer.valueOf(0));
    this.menu = this.menuManager.get(this.id);
    return "edit";
  }

  public String saveEdit()
  {
    try {
      this.menuManager.edit(this.menu);
      this.json = "{result:1}";
    } catch (RuntimeException e) {
      this.logger.error(e.getMessage(), e);
      this.json = ("{result:0,message:'" + e.getMessage() + "'}");
    }
    return "json_message";
  }

  public String updateSort()
  {
    try
    {
      this.menuManager.updateSort(this.menu_ids, this.menu_sorts);
      this.json = "{result:1}";
    } catch (RuntimeException e) {
      this.logger.error(e.getMessage(), e);
      this.json = ("{result:0,message:'" + e.getMessage() + "'}");
    }
    return "json_message";
  }

  public String delete() {
    try {
      this.menuManager.delete(this.id);
      this.json = "{result:1}";
    } catch (RuntimeException e) {
      this.logger.error(e.getMessage(), e);
      this.json = ("{result:0,message:'" + e.getMessage() + "'}");
    }
    return "json_message";
  }

  public IMenuManager getMenuManager() {
    return this.menuManager;
  }

  public void setMenuManager(IMenuManager menuManager) {
    this.menuManager = menuManager;
  }

  public List<Menu> getMenuList() {
    return this.menuList;
  }

  public void setMenuList(List<Menu> menuList) {
    this.menuList = menuList;
  }

  public Integer getParentid() {
    return this.parentid;
  }

  public void setParentid(Integer parentid) {
    this.parentid = parentid;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Menu getMenu() {
    return this.menu;
  }

  public void setMenu(Menu menu) {
    this.menu = menu;
  }

  public Integer[] getMenu_ids() {
    return this.menu_ids;
  }

  public void setMenu_ids(Integer[] menuIds) {
    this.menu_ids = menuIds;
  }

  public Integer[] getMenu_sorts() {
    return this.menu_sorts;
  }

  public void setMenu_sorts(Integer[] menuSorts) {
    this.menu_sorts = menuSorts;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.MenuAction
 * JD-Core Version:    0.6.1
 */