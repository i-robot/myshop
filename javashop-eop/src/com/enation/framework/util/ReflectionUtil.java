package com.enation.framework.util;

import com.enation.framework.database.DynamicField;
import com.enation.framework.database.NotDbField;
import com.enation.framework.database.PrimaryKeyField;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.commons.beanutils.BeanUtils;

public class ReflectionUtil
{
  public static Object invokeMethod(String className, String methodName, Object[] args)
  {
    try
    {
      Class serviceClass = Class.forName(className);
      Object service = serviceClass.newInstance();

      Class[] argsClass = new Class[args.length];
      int i = 0; for (int j = args.length; i < j; i++) {
        argsClass[i] = args[i].getClass();
      }

      Method method = serviceClass.getMethod(methodName, argsClass);
      return method.invoke(service, args);
    }
    catch (SecurityException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    }

    return null;
  }

  public static Object newInstance(String className, Object[] _args)
  {
    try {
      Class[] argsClass = new Class[_args.length];

      int i = 0; for (int j = _args.length; i < j; i++)
      {
        if (_args[i] == null) {
          argsClass[i] = null;
        }
        else
        {
          argsClass[i] = _args[i].getClass();
        }

      }

      Class newoneClass = Class.forName(className);
      Constructor cons = newoneClass.getConstructor(argsClass);

      return cons.newInstance(_args);
    }
    catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }

    return null;
  }

  public static Map po2Map(Object po)
  {
    Map poMap = new HashMap();
    Map map = new HashMap();
    try {
      map = BeanUtils.describe(po);
    } catch (Exception ex) {
    }
    Object[] keyArray = map.keySet().toArray();
    for (int i = 0; i < keyArray.length; i++) {
      String str = keyArray[i].toString();
      if ((str != null) && (!str.equals("class")) && 
        (map.get(str) != null)) {
        poMap.put(str, map.get(str));
      }

    }

    Method[] ms = po.getClass().getMethods();
    for (Method m : ms) {
      String name = m.getName();

      if (((name.startsWith("get")) || (name.startsWith("is"))) && (
        (m.getAnnotation(NotDbField.class) != null) || (m.getAnnotation(PrimaryKeyField.class) != null))) {
        poMap.remove(getFieldName(name));
      }

    }

    if ((po instanceof DynamicField)) {
      DynamicField dynamicField = (DynamicField)po;
      Map fields = dynamicField.getFields();
      poMap.putAll(fields);
    }
    return poMap;
  }

  private static String getFieldName(String methodName)
  {
    methodName = methodName.substring(3);
    methodName = methodName.substring(0, 1).toLowerCase() + methodName.substring(1);
    return methodName;
  }

  public static void main(String[] args) {
    String methodName = "getWidgetList";
    methodName = methodName.substring(3);
    methodName = methodName.substring(0, 1).toLowerCase() + methodName.substring(1);
    System.out.println(methodName);
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.util.ReflectionUtil
 * JD-Core Version:    0.6.1
 */