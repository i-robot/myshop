package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.MemberComment;
import com.enation.framework.database.Page;

public abstract interface IMemberCommentManager
{
  public abstract void add(MemberComment paramMemberComment);

  public abstract Page getGoodsComments(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public abstract int getGoodsGrade(int paramInt);

  public abstract Page getAllComments(int paramInt1, int paramInt2, int paramInt3);

  public abstract Page getCommentsByStatus(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public abstract MemberComment get(int paramInt);

  public abstract void update(MemberComment paramMemberComment);

  public abstract int getGoodsCommentsCount(int paramInt);

  public abstract void delete(int paramInt);

  public abstract Page getMemberComments(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public abstract int getMemberCommentTotal(int paramInt1, int paramInt2);
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.IMemberCommentManager
 * JD-Core Version:    0.6.1
 */