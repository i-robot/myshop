package com.enation.eop.resource;

import com.enation.eop.resource.model.Border;
import java.util.List;

public abstract interface IBorderManager
{
  public abstract void add(Border paramBorder);

  public abstract void update(Border paramBorder);

  public abstract void delete(Integer paramInteger);

  public abstract List<Border> list();

  public abstract void clean();
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.eop.resource.IBorderManager
 * JD-Core Version:    0.6.1
 */