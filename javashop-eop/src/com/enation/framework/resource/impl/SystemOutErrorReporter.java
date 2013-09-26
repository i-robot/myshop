package com.enation.framework.resource.impl;

import java.io.PrintStream;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

class SystemOutErrorReporter
  implements ErrorReporter
{
  private String format(String arg0, String arg1, int arg2, String arg3, int arg4)
  {
    return String.format("%s%s at line %d, column %d:\n%s", new Object[] { arg0, ":" + arg1, Integer.valueOf(arg2), Integer.valueOf(arg4), arg3 });
  }

  public void warning(String arg0, String arg1, int arg2, String arg3, int arg4)
  {
    System.out.println("WARNING: " + format(arg0, arg1, arg2, arg3, arg4));
  }

  public void error(String arg0, String arg1, int arg2, String arg3, int arg4)
  {
    System.out.println("ERROR: " + format(arg0, arg1, arg2, arg3, arg4));
  }

  public EvaluatorException runtimeError(String arg0, String arg1, int arg2, String arg3, int arg4)
  {
    System.out.println("RUNTIME ERROR: " + format(arg0, arg1, arg2, arg3, arg4));
    return new EvaluatorException(arg0);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.resource.impl.SystemOutErrorReporter
 * JD-Core Version:    0.6.1
 */