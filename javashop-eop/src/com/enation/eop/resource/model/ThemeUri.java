package com.enation.eop.resource.model;

import com.enation.framework.database.NotDbField;
import java.io.Serializable;
import java.util.regex.Pattern;

public class ThemeUri extends Resource
  implements Serializable
{
  private static final long serialVersionUID = -3537303897321923063L;
  private Integer themeid;
  private String uri;
  private String path;
  private String pagename;
  private int point;
  private int sitemaptype;
  private String keywords;
  private String description;
  private int httpcache;
  private Pattern pattern;

  public String getPath()
  {
    return this.path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public Integer getThemeid() {
    return this.themeid;
  }

  public void setThemeid(Integer themeid) {
    this.themeid = themeid;
  }

  public String getUri() {
    return this.uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public String getPagename() {
    return this.pagename;
  }

  public void setPagename(String pagename) {
    this.pagename = pagename;
  }

  public int getPoint() {
    return this.point;
  }

  public void setPoint(int point) {
    this.point = point;
  }

  public int getSitemaptype() {
    return this.sitemaptype;
  }

  public void setSitemaptype(int sitemaptype) {
    this.sitemaptype = sitemaptype;
  }

  public String getKeywords() {
    return this.keywords;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getHttpcache() {
    return this.httpcache;
  }

  public void setHttpcache(int httpcache) {
    this.httpcache = httpcache;
  }

  @NotDbField
  public Pattern getPattern() {
    if (this.pattern == null) {
      this.pattern = Pattern.compile("^" + this.uri + "$", 34);
    }
    return this.pattern;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.model.ThemeUri
 * JD-Core Version:    0.6.1
 */