package com.enation.app.shop.core.model.support;

import com.enation.app.shop.core.model.Comments;
import java.util.List;

public class CommentDTO
{
  private Comments comments;
  private List<Comments> list;

  public Comments getComments()
  {
    return this.comments;
  }

  public void setComments(Comments comments) {
    this.comments = comments;
  }

  public List<Comments> getList() {
    return this.list;
  }

  public void setList(List<Comments> list) {
    this.list = list;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.support.CommentDTO
 * JD-Core Version:    0.6.1
 */