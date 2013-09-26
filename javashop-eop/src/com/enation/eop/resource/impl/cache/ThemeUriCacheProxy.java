package com.enation.eop.resource.impl.cache;

import com.enation.eop.processor.core.UrlNotFoundException;
import com.enation.eop.resource.IThemeUriManager;
import com.enation.eop.resource.model.EopSite;
import com.enation.eop.resource.model.ThemeUri;
import com.enation.eop.sdk.context.EopContext;
import com.enation.framework.cache.AbstractCacheProxy;
import com.enation.framework.cache.ICache;
import java.io.PrintStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThemeUriCacheProxy extends AbstractCacheProxy<List<ThemeUri>>
  implements IThemeUriManager
{
  private IThemeUriManager themeUriManager;
  public static final String LIST_KEY_PREFIX = "theme_uri_list_";

  public void clean()
  {
    this.themeUriManager.clean();
  }
  public void add(ThemeUri uri) {
    this.themeUriManager.add(uri);
    cleanCache();
  }

  public void edit(List<ThemeUri> uriList) {
    this.themeUriManager.edit(uriList);
    cleanCache();
  }

  public void edit(ThemeUri themeUri) {
    this.themeUriManager.edit(themeUri);
    cleanCache();
  }

  public ThemeUriCacheProxy(IThemeUriManager themeUriManager)
  {
    super("themeUriCache");
    this.themeUriManager = themeUriManager;
  }

  private void cleanCache() {
    EopSite site = EopContext.getContext().getCurrentSite();
    Integer userid = site.getUserid();
    Integer siteid = site.getId();
    this.cache.remove("theme_uri_list_" + userid + "_" + siteid);
  }

  public List<ThemeUri> list() {
    EopSite site = EopContext.getContext().getCurrentSite();
    Integer userid = site.getUserid();
    Integer siteid = site.getId();
    List uriList = (List)this.cache.get("theme_uri_list_" + userid + "_" + siteid);

    if (uriList == null)
    {
      uriList = this.themeUriManager.list();

      this.cache.put("theme_uri_list_" + userid + "_" + siteid, uriList);
    }

    return uriList;
  }

  public ThemeUri getPath(String uri)
  {
    if (uri.equals("/")) {
      uri = "/index.html";
    }

    List<ThemeUri> uriList = list();
    for (ThemeUri themeUri : uriList) {
      Matcher m = themeUri.getPattern().matcher(uri);

      if (m.find())
      {
        return themeUri;
      }

    }

    throw new UrlNotFoundException();
  }

  public static void main(String[] args)
  {
    Pattern p = Pattern.compile("/goods-(\\d+).html", 34);
    Matcher m = p.matcher("/goods-1.html");
    if (m.find()) {
      System.out.println("found...");
      String s = m.replaceAll("/goods.jsp");
      System.out.println(s);
    } else {
      System.out.println("not found...");
    }
  }

  public void delete(int id)
  {
    this.themeUriManager.delete(id);
    cleanCache();
  }

  public ThemeUri get(Integer id) {
    return this.themeUriManager.get(id);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.impl.cache.ThemeUriCacheProxy
 * JD-Core Version:    0.6.1
 */