package com.enation.framework.util.ip;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class LogFactory
{
  private static final Logger logger = Logger.getLogger("stdout");

  public static void log(String info, Level level, Throwable ex)
  {
    logger.log(level, info, ex);
  }

  public static Level getLogLevel() {
    return logger.getLevel();
  }

  static
  {
    logger.setLevel(Level.DEBUG);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.util.ip.LogFactory
 * JD-Core Version:    0.6.1
 */