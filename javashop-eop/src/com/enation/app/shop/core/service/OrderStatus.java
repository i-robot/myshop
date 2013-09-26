package com.enation.app.shop.core.service;

import java.util.HashMap;
import java.util.Map;

public abstract class OrderStatus
{
  public static final int ORDER_CHANGED = -7;
  public static final int ORDER_CHANGE_REFUSE = -6;
  public static final int ORDER_RETURN_REFUSE = -5;
  public static final int ORDER_CHANGE_APPLY = -4;
  public static final int ORDER_RETURN_APPLY = -3;
  public static final int ORDER_CANCEL_SHIP = -2;
  public static final int ORDER_CANCEL_PAY = -1;
  public static final int ORDER_NOT_PAY = 0;
  public static final int ORDER_PAY = 1;
  public static final int ORDER_PAY_CONFIRM = 2;
  public static final int ORDER_ALLOCATION = 3;
  public static final int ORDER_ALLOCATION_YES = 4;
  public static final int ORDER_SHIP = 5;
  public static final int ORDER_ROG = 6;
  public static final int ORDER_COMPLETE = 7;
  public static final int ORDER_CANCELLATION = 8;
  public static final int ORDER_CONFIRM = 9;
  public static final int PAY_NO = 0;
  public static final int PAY_YES = 1;
  public static final int PAY_CONFIRM = 2;
  public static final int PAY_CANCEL = 3;
  public static final int PAY_PARTIAL_REFUND = 4;
  public static final int PAY_PARTIAL_PAYED = 5;
  public static final int SHIP_ALLOCATION_NO = 0;
  public static final int SHIP_ALLOCATION_YES = 1;
  public static final int SHIP_NO = 2;
  public static final int SHIP_YES = 3;
  public static final int SHIP_CANCEL = 4;
  public static final int SHIP_PARTIAL_SHIPED = 5;
  public static final int SHIP_PARTIAL_CANCEL = 6;
  public static final int SHIP_PARTIAL_CHANGE = 7;
  public static final int SHIP_CHANED = 8;

  public static Map<String, Object> getOrderStatusMap()
  {
    Map map = new HashMap(17);

    map.put("ORDER_CHANGED", Integer.valueOf(-7));
    map.put("ORDER_CHANGE_REFUSE", Integer.valueOf(-6));
    map.put("ORDER_RETURN_REFUSE", Integer.valueOf(-5));
    map.put("ORDER_CHANGE_APPLY", Integer.valueOf(-4));
    map.put("ORDER_RETURN_APPLY", Integer.valueOf(-3));
    map.put("ORDER_CANCEL_SHIP", Integer.valueOf(-2));
    map.put("ORDER_CANCEL_PAY", Integer.valueOf(-1));
    map.put("ORDER_NOT_PAY", Integer.valueOf(0));
    map.put("ORDER_PAY", Integer.valueOf(1));
    map.put("ORDER_PAY_CONFIRM", Integer.valueOf(2));
    map.put("ORDER_ALLOCATION", Integer.valueOf(3));
    map.put("ORDER_ALLOCATION_YES", Integer.valueOf(4));
    map.put("ORDER_SHIP", Integer.valueOf(5));
    map.put("ORDER_ROG", Integer.valueOf(6));
    map.put("ORDER_COMPLETE", Integer.valueOf(7));
    map.put("ORDER_CANCELLATION", Integer.valueOf(8));
    map.put("ORDER_CONFIRM", Integer.valueOf(9));

    map.put("PAY_NO", Integer.valueOf(0));
    map.put("PAY_YES", Integer.valueOf(1));
    map.put("PAY_CONFIRM", Integer.valueOf(2));
    map.put("PAY_CANCEL", Integer.valueOf(3));
    map.put("PAY_PARTIAL_REFUND", Integer.valueOf(4));
    map.put("PAY_PARTIAL_PAYED", Integer.valueOf(5));

    map.put("SHIP_ALLOCATION_NO", Integer.valueOf(0));
    map.put("SHIP_ALLOCATION_YES", Integer.valueOf(1));
    map.put("SHIP_NO", Integer.valueOf(2));
    map.put("SHIP_YES", Integer.valueOf(3));
    map.put("SHIP_CANCEL", Integer.valueOf(4));
    map.put("SHIP_PARTIAL_SHIPED", Integer.valueOf(5));
    map.put("SHIP_PARTIAL_CANCEL", Integer.valueOf(3));
    map.put("SHIP_PARTIAL_CHANGE", Integer.valueOf(4));
    map.put("SHIP_CHANED", Integer.valueOf(8));

    return map;
  }

  public static String getOrderStatusText(int status)
  {
    String text = "";

    switch (status) {
    case -7:
      text = "已换货";
      break;
    case -6:
      text = "换货被拒绝";
      break;
    case -5:
      text = "退货被拒绝";
      break;
    case -4:
      text = "申请换货";
      break;
    case -3:
      text = "申请退货";
      break;
    case -2:
      text = "退货";
      break;
    case -1:
      text = "退款";
      break;
    case 0:
      text = "等待付款";
      break;
    case 9:
      text = "已确认";
      break;
    case 1:
      text = "已付款待确认";
      break;
    case 2:
      text = "已付款";
      break;
    case 3:
      text = "配货中";
      break;
    case 4:
      text = "配货完成待发货";
      break;
    case 5:
      text = "已发货";
      break;
    case 7:
      text = "已完成";
      break;
    case 6:
      text = "已收货";
      break;
    case 8:
      text = "已取消";
      break;
    default:
      text = "错误状态";
    }

    return text;
  }

  public static String getPayStatusText(int status)
  {
    String text = "";

    switch (status) {
    case 0:
      text = "未付款";
      break;
    case 1:
      text = "已付款待确认";
      break;
    case 2:
      text = "已确认付款";
      break;
    case 3:
      text = "已经退款";
      break;
    case 4:
      text = "部分退款";
      break;
    case 5:
      text = "部分付款";
      break;
    default:
      text = "错误状态";
    }

    return text;
  }

  public static String getShipStatusText(int status)
  {
    String text = "";

    switch (status) {
    case 0:
      text = "未配货";
      break;
    case 1:
      text = "配货中 ";
      break;
    case 2:
      text = "未发货";
      break;
    case 3:
      text = "已发货";
      break;
    case 4:
      text = "已退货";
      break;
    case 5:
      text = "部分发货";
      break;
    case 6:
      text = "部分退货";
      break;
    case 7:
      text = "部分换货";
      break;
    case 8:
      text = " 已换货";
      break;
    default:
      text = "错误状态";
    }

    return text;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.OrderStatus
 * JD-Core Version:    0.6.1
 */