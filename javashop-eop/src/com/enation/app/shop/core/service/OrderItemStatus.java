package com.enation.app.shop.core.service;

public abstract class OrderItemStatus
{
  public static final int NORMAL = 0;
  public static final int APPLY_RETURN = 1;
  public static final int APPLY_CHANGE = 2;
  public static final int REFUSE_RETURN = 3;
  public static final int REFUSE_CHANGE = 4;
  public static final int RETURN_PASSED = 5;
  public static final int CHANGE_PASSED = 6;
  public static final int RETURN_REC = 7;
  public static final int CHANGE_REC = 8;
  public static final int RETURN_END = 9;
  public static final int CHANGE_END = 10;

  public static String getOrderStatusText(int status)
  {
    String text = "";
    switch (status) {
    case 0:
      text = "正常";
      break;
    case 1:
      text = "申请退货";
      break;
    case 2:
      text = "申请换货 ";
      break;
    case 3:
      text = "退货已拒绝";
      break;
    case 4:
      text = "换货已拒绝";
      break;
    case 5:
      text = "退货已通过审核";
      break;
    case 6:
      text = "换货已通过审核";
      break;
    case 7:
      text = "退货已收货";
      break;
    case 8:
      text = "已收货,换货已发出";
      break;
    case 9:
      text = "退货完成";
      break;
    case 10:
      text = "换货完成";
    }

    return text;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.OrderItemStatus
 * JD-Core Version:    0.6.1
 */