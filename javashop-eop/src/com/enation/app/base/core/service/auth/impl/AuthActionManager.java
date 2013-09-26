package com.enation.app.base.core.service.auth.impl;

import com.enation.app.base.core.model.AuthAction;
import com.enation.app.base.core.service.auth.IAuthActionManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.util.StringUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class AuthActionManager extends BaseSupport<AuthAction>
  implements IAuthActionManager
{
  @Transactional(propagation=Propagation.REQUIRED)
  public int add(AuthAction act)
  {
    this.baseDaoSupport.insert("auth_action", act);
    return this.baseDaoSupport.getLastId("auth_action");
  }

  @Transactional(propagation=Propagation.REQUIRED)
  public void delete(int actid)
  {
    this.baseDaoSupport.execute("delete from role_auth where authid=?", new Object[] { Integer.valueOf(actid) });

    this.baseDaoSupport.execute("delete from auth_action where actid=?", new Object[] { Integer.valueOf(actid) });
  }

  public void edit(AuthAction act)
  {
    this.baseDaoSupport.update("auth_action", act, "actid=" + act.getActid());
  }

  public List<AuthAction> list()
  {
    return this.baseDaoSupport.queryForList("select * from auth_action", AuthAction.class, new Object[0]);
  }

  public AuthAction get(int authid)
  {
    List list = this.baseDaoSupport.queryForList("select * from auth_action where actid=?", AuthAction.class, new Object[] { Integer.valueOf(authid) });
    AuthAction result = null;
    if (list.size() > 0)
      result = (AuthAction)list.get(0);
    return result;
  }

  public void addMenu(int actid, Integer[] menuidAr)
  {
    if (menuidAr == null) return;

    AuthAction authAction = get(actid);
    String menuStr = authAction.getObjvalue();
    if (StringUtil.isEmpty(menuStr)) {
      menuStr = StringUtil.arrayToString(menuidAr, ",");
      authAction.setObjvalue(menuStr);
    } else {
      String[] oldMenuAr = StringUtils.split(menuStr, ",");
      oldMenuAr = merge(menuidAr, oldMenuAr);
      menuStr = StringUtil.arrayToString(oldMenuAr, ",");
      authAction.setObjvalue(menuStr);
    }

    edit(authAction);
  }

  public void deleteMenu(int actid, Integer[] menuidAr)
  {
    if (menuidAr == null) return;
    AuthAction authAction = get(actid);
    String menuStr = authAction.getObjvalue();
    if (StringUtil.isEmpty(menuStr)) {
      return;
    }

    String[] oldMenuAr = StringUtils.split(menuStr, ","); menuStr.split(",");
    oldMenuAr = delete(menuidAr, oldMenuAr);
    menuStr = StringUtil.arrayToString(oldMenuAr, ",");
    authAction.setObjvalue(menuStr);
    edit(authAction);
  }

  private static String[] merge(Integer[] ar1, String[] ar2)
  {
    List newList = new ArrayList();
    for (String num : ar2) {
      newList.add(num);
    }

    boolean flag = false;
    for (Integer num1 : ar1) {
      flag = false;

      for (String num2 : ar2) {
        if (num1.intValue() == Integer.valueOf(num2).intValue()) {
          flag = true;
          break;
        }
      }

      if (!flag) {
        newList.add(String.valueOf(num1));
      }

    }

    return (String[])newList.toArray(new String[newList.size()]);
  }

  public static String[] delete(Integer[] ar1, String[] ar2)
  {
    List newList = new ArrayList();
    boolean flag = false;
    for (String num2 : ar2) {
      flag = false;
      for (Integer num1 : ar1) {
        if (num1.intValue() == Integer.valueOf(num2).intValue()) {
          flag = true;
          break;
        }
      }

      if (!flag) {
        newList.add(num2);
      }
    }

    return (String[])newList.toArray(new String[newList.size()]);
  }

  public static void main(String[] args) {
    Integer[] ar1 = { Integer.valueOf(15), Integer.valueOf(29), Integer.valueOf(23) };
    String[] ar2 = { "1", "2", "7", "0", "23", "4" };

    String[] newar = delete(ar1, ar2);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.service.auth.impl.AuthActionManager
 * JD-Core Version:    0.6.1
 */