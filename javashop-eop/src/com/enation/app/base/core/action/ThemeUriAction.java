package com.enation.app.base.core.action;

import com.enation.eop.resource.IThemeUriManager;
import com.enation.eop.resource.model.ThemeUri;
import com.enation.framework.action.WWAction;
import java.util.ArrayList;
import java.util.List;

public class ThemeUriAction extends WWAction
{
  private IThemeUriManager themeUriManager;
  private List uriList;
  private ThemeUri themeUri;
  private int id;
  private int[] ids;
  private String[] uri;
  private String[] path;
  private String[] pagename;
  private int[] point;
  private int[] httpcache;

  public String list()
  {
    this.uriList = this.themeUriManager.list();
    return "list";
  }

  public String add()
  {
    return "input";
  }
  public String edit() {
    this.themeUri = this.themeUriManager.get(Integer.valueOf(this.id));
    return "input";
  }

  public String saveAdd() {
    try {
      this.themeUriManager.add(this.themeUri);
      this.json = "{result:1}";
    } catch (RuntimeException e) {
      this.json = ("{result:0,message:'" + e.getMessage() + "'}");
    }
    return "json_message";
  }

  public String saveEdit()
  {
    try {
      this.themeUriManager.edit(this.themeUri);
      this.json = "{result:1}";
    } catch (RuntimeException e) {
      this.json = ("{result:0,message:'" + e.getMessage() + "'}");
    }
    return "json_message";
  }

  public String batchEdit()
  {
    try {
      List uriList = new ArrayList();
      if (this.uri != null) {
        int i = 0; for (int len = this.uri.length; i < len; i++) {
          ThemeUri themeUri = new ThemeUri();
          themeUri.setUri(this.uri[i]);
          themeUri.setId(Integer.valueOf(this.ids[i]));
          themeUri.setPath(this.path[i]);
          themeUri.setPagename(this.pagename[i]);
          themeUri.setPoint(this.point[i]);
          themeUri.setHttpcache(this.httpcache[i]);
          uriList.add(themeUri);
        }
      }
      this.themeUriManager.edit(uriList);
      this.json = "{result:1}";
    } catch (RuntimeException e) {
      this.json = ("{result:0,message:'" + e.getMessage() + "'}");
    }
    return "json_message";
  }

  public String delete()
  {
    try {
      this.themeUriManager.delete(this.id);
      this.json = "{result:1}";
    } catch (RuntimeException e) {
      this.json = ("{result:0,message:'" + e.getMessage() + "'}");
    }

    return "json_message";
  }

  public IThemeUriManager getThemeUriManager() {
    return this.themeUriManager;
  }

  public void setThemeUriManager(IThemeUriManager themeUriManager) {
    this.themeUriManager = themeUriManager;
  }

  public List getUriList() {
    return this.uriList;
  }

  public void setUriList(List uriList) {
    this.uriList = uriList;
  }

  public ThemeUri getThemeUri() {
    return this.themeUri;
  }

  public void setThemeUri(ThemeUri themeUri) {
    this.themeUri = themeUri;
  }

  public String[] getUri() {
    return this.uri;
  }

  public void setUri(String[] uri) {
    this.uri = uri;
  }

  public String[] getPath() {
    return this.path;
  }

  public void setPath(String[] path) {
    this.path = path;
  }

  public String[] getPagename() {
    return this.pagename;
  }

  public void setPagename(String[] pagename) {
    this.pagename = pagename;
  }

  public int[] getPoint() {
    return this.point;
  }

  public void setPoint(int[] point) {
    this.point = point;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int[] getIds() {
    return this.ids;
  }

  public void setIds(int[] ids) {
    this.ids = ids;
  }

  public int[] getHttpcache()
  {
    return this.httpcache;
  }

  public void setHttpcache(int[] httpcache)
  {
    this.httpcache = httpcache;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.action.ThemeUriAction
 * JD-Core Version:    0.6.1
 */