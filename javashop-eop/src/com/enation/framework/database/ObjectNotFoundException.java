package com.enation.framework.database;

public class ObjectNotFoundException extends DBRuntimeException
{
  private static final long serialVersionUID = -3403302876974180460L;

  public ObjectNotFoundException(String message)
  {
    super(message);
  }

  public ObjectNotFoundException(Exception e, String sql) {
    super(e, sql);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.database.ObjectNotFoundException
 * JD-Core Version:    0.6.1
 */