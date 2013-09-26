package com.enation.app.base.core.action;

import com.enation.app.base.core.model.SiteMenu;
import com.enation.app.base.core.service.ISiteMenuManager;
import com.enation.framework.action.WWAction;
import java.util.List;
import java.util.Map;

public class SiteMenuAction extends WWAction
{
  private ISiteMenuManager siteMenuManager;
  private List menuList;
  private Integer[] sortArray;
  private Integer[] menuidArray;
  private Integer menuid;
  private SiteMenu siteMenu;
  private boolean isEdit;

  public String list()
  {
    this.menuList = this.siteMenuManager.list(Integer.valueOf(0));
    return "list";
  }

  public String updateSort() {
    this.siteMenuManager.updateSort(this.menuidArray, this.sortArray);
    return list();
  }

  public String add()
  {
    this.isEdit = false;
    this.menuList = this.siteMenuManager.list(Integer.valueOf(0));
    this.siteMenu = new SiteMenu();
    return "input";
  }

  public String edit() {
    this.isEdit = true;
    this.menuList = this.siteMenuManager.list(Integer.valueOf(0));
    this.siteMenu = this.siteMenuManager.get(this.menuid);
    return "input";
  }

  public String save() {
    if (this.menuid == null) {
      this.siteMenuManager.add(this.siteMenu);
      this.msgs.add("菜单添加成功");
    } else {
      this.siteMenu.setMenuid(this.menuid);
      this.siteMenuManager.edit(this.siteMenu);
      this.msgs.add("菜单修改成功");
    }

    this.urls.put("菜单列表", "siteMenu!list.do");
    return "message";
  }

  public String delete()
  {
    this.siteMenuManager.delete(this.menuid);
    this.msgs.add("菜单删除成功");
    this.urls.put("菜单列表", "siteMenu!list.do");
    return "message";
  }

  public ISiteMenuManager getSiteMenuManager() {
    return this.siteMenuManager;
  }
  public void setSiteMenuManager(ISiteMenuManager siteMenuManager) {
    this.siteMenuManager = siteMenuManager;
  }
  public List getMenuList() {
    return this.menuList;
  }
  public void setMenuList(List menuList) {
    this.menuList = menuList;
  }

  public Integer[] getSortArray() {
    return this.sortArray;
  }

  public void setSortArray(Integer[] sortArray) {
    this.sortArray = sortArray;
  }

  public Integer[] getMenuidArray() {
    return this.menuidArray;
  }

  public void setMenuidArray(Integer[] menuidArray) {
    this.menuidArray = menuidArray;
  }

  public Integer getMenuid() {
    return this.menuid;
  }

  public void setMenuid(Integer menuid) {
    this.menuid = menuid;
  }

  public SiteMenu getSiteMenu() {
    return this.siteMenu;
  }

  public void setSiteMenu(SiteMenu siteMenu) {
    this.siteMenu = siteMenu;
  }

  public boolean getIsEdit() {
    return this.isEdit;
  }

  public void setEdit(boolean isEdit) {
    this.isEdit = isEdit;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.SiteMenuAction
 * JD-Core Version:    0.6.1
 */