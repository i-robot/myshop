package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.Comments;
import com.enation.app.shop.core.model.support.CommentDTO;
import com.enation.framework.database.Page;
import java.util.List;
import java.util.Map;

public abstract interface ICommentsManager
{
  public abstract CommentDTO getComments(Integer paramInteger);

  public abstract void addComments(Comments paramComments);

  public abstract void updateComments(Comments paramComments);

  public abstract void deleteComments(String paramString);

  public abstract void cleanComments(String paramString);

  public abstract void revert(String paramString);

  public abstract Page pageComments_Display(int paramInt1, int paramInt2, Integer paramInteger, String paramString);

  public abstract Page pageComments_Display(int paramInt1, int paramInt2, Integer paramInteger, String paramString1, String paramString2);

  public abstract Page pageComments_Display(int paramInt1, int paramInt2);

  public abstract List listComments(int paramInt, String paramString);

  public abstract Page pageComments(int paramInt1, int paramInt2, String paramString);

  public abstract Page pageCommentsTrash(int paramInt1, int paramInt2, String paramString);

  public abstract Map coutObjectGrade(String paramString, Integer paramInteger);

  public abstract Page listAll(int paramInt1, int paramInt2, Integer paramInteger, String paramString);

  public abstract Map coutObejctNum(String paramString, Integer paramInteger);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.ICommentsManager
 * JD-Core Version:    0.6.1
 */