package com.enation.framework.util;

import java.io.PrintStream;
import java.math.BigDecimal;

public final class CurrencyUtil
{
  private static final int DEF_DIV_SCALE = 2;

  public static double add(double v1, double v2)
  {
    BigDecimal b1 = new BigDecimal(Double.toString(v1));
    BigDecimal b2 = new BigDecimal(Double.toString(v2));
    return b1.add(b2).doubleValue();
  }

  public static double sub(double v1, double v2)
  {
    BigDecimal b1 = new BigDecimal(Double.toString(v1));
    BigDecimal b2 = new BigDecimal(Double.toString(v2));
    return b1.subtract(b2).doubleValue();
  }

  public static Double mul(double v1, double v2)
  {
    BigDecimal b1 = new BigDecimal(Double.toString(v1));
    BigDecimal b2 = new BigDecimal(Double.toString(v2));
    return Double.valueOf(b1.multiply(b2).doubleValue());
  }

  public static double div(double v1, double v2)
  {
    return div(v1, v2, 2);
  }

  public static double div(double v1, double v2, int scale)
  {
    if (scale < 0) {
      throw new IllegalArgumentException("The scale must be a positive integer or zero");
    }

    BigDecimal b1 = new BigDecimal(Double.toString(v1));
    BigDecimal b2 = new BigDecimal(Double.toString(v2));
    return b1.divide(b2, scale, 4).doubleValue();
  }

  public static double round(double v, int scale)
  {
    if (scale < 0) {
      throw new IllegalArgumentException("The scale must be a positive integer or zero");
    }

    BigDecimal b = new BigDecimal(Double.toString(v));
    BigDecimal one = new BigDecimal("1");
    return b.divide(one, scale, 4).doubleValue();
  }

  public static void main(String[] args)
  {
    System.out.println(sub(2.0D, 10.0D));
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.util.CurrencyUtil
 * JD-Core Version:    0.6.1
 */