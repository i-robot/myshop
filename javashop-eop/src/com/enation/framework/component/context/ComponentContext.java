package com.enation.framework.component.context;

import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.component.ComponentView;
import com.enation.framework.component.IComponent;
import com.enation.framework.component.PluginView;
import com.enation.framework.component.WidgetView;
import com.enation.framework.util.FileUtil;
import com.enation.framework.util.StringUtil;
import com.enation.framework.util.XMLUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ComponentContext
{
  private static List<ComponentView> componentList = new ArrayList();
  private static Map<String, Boolean> siteComponentState = new HashMap();

  public static void siteComponentStart(int userid, int siteid)
  {
    siteComponentState.put(userid + "_" + siteid, Boolean.TRUE);
  }

  public static boolean getSiteComponentState(int userid, int siteid)
  {
    Boolean state = (Boolean)siteComponentState.get(userid + "_" + siteid);
    return state == null ? false : state.booleanValue();
  }

  public static void registerComponent(ComponentView componentView)
  {
    try
    {
      loadComponent(componentView);
      componentList.add(componentView);
    }
    catch (SAXException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    catch (ParserConfigurationException e) {
      e.printStackTrace();
    }
  }

  public static List<ComponentView> getComponents()
  {
    return componentList;
  }

  private static void loadComponent(ComponentView componentView)
    throws SAXException, IOException, ParserConfigurationException
  {
    IComponent component = componentView.getComponent();

    String path = component.getClass().getPackage().getName();
    path = path.replace('.', '/') + "/component.xml";

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.parse(FileUtil.getResourceAsStream(path));
    Element componentEl = (Element)doc.getFirstChild();

    String needVersion = componentEl.getAttribute("javashop_version");
    String currentVersion = EopSetting.VERSION;

    componentView.setName(componentEl.getAttribute("name"));
    componentView.setAuthor(componentEl.getAttribute("author"));
    componentView.setVersion(componentEl.getAttribute("version"));
    componentView.setJavashop_version(needVersion);
    componentView.setDescription(componentEl.getAttribute("description"));

    if (!versionLargerThen(currentVersion, needVersion))
    {
      componentView.setInstall_state(2);
      componentView.setError_message("当前的Javashop版本无法安装此组件，需要的Javashop版本[" + needVersion + "] ，当前版本[" + currentVersion + "]");
    }

    Element pluginsEl = XMLUtil.getChildByTagName(componentEl, "plugins");
    Element widgetsEl = XMLUtil.getChildByTagName(componentEl, "widgets");

    if (pluginsEl != null)
    {
      NodeList pluginNodeList = pluginsEl.getElementsByTagName("plugin");

      if (pluginNodeList != null)
      {
        int length = pluginNodeList.getLength();
        for (int i = 0; i < length; i++) {
          Element pluginEl = (Element)pluginNodeList.item(i);
          String name = pluginEl.getAttribute("name");
          String pluginBeanid = pluginEl.getAttribute("id");

          PluginView pluginView = new PluginView();
          pluginView.setId(pluginBeanid);
          pluginView.setName(name);

          NodeList bundleList = pluginEl.getElementsByTagName("bundle");
          if (bundleList != null) {
            int bundleLength = bundleList.getLength();
            for (int j = 0; j < bundleLength; j++) {
              Element bundleEl = (Element)bundleList.item(j);
              String beanid = bundleEl.getAttribute("id");
              pluginView.addBundle(beanid);
            }

          }

          componentView.addPlugin(pluginView);
        }

      }

    }

    if (widgetsEl != null)
    {
      NodeList widgetNodeList = widgetsEl.getElementsByTagName("widget");
      if (widgetNodeList != null) {
        int length = widgetNodeList.getLength();
        for (int i = 0; i < length; i++) {
          Element widgetEl = (Element)widgetNodeList.item(i);
          String name = widgetEl.getAttribute("name");
          String id = widgetEl.getAttribute("id");
          WidgetView widgetView = new WidgetView();
          widgetView.setName(name);
          widgetView.setId(id);
          componentView.addWidget(widgetView);
        }
      }
    }
  }

  private static boolean versionLargerThen(String ver1, String ver2)
  {
    if (StringUtil.isEmpty(ver1)) throw new IllegalArgumentException("ver1版本不能为空");
    if (StringUtil.isEmpty(ver2)) throw new IllegalArgumentException("ver2版本不能为空");
    if (ver1.length() != ver2.length()) throw new IllegalArgumentException("ver2与ver2版本号格式不相同");
    if (ver1.length() != 5) throw new IllegalArgumentException("版本号格式不正确，应为如：2.1.0");

    String[] ver1a = ver1.split("\\.");
    Integer ver1i = Integer.valueOf(Integer.valueOf(ver1a[0]).intValue() * 1000000 + Integer.valueOf(ver1a[1]).intValue() * 1000 + Integer.valueOf(ver1a[2]).intValue());
    String[] ver2a = ver2.split("\\.");
    Integer ver2i = Integer.valueOf(Integer.valueOf(ver2a[0]).intValue() * 1000000 + Integer.valueOf(ver2a[1]).intValue() * 1000 + Integer.valueOf(ver2a[2]).intValue());

    return ver1i.intValue() >= ver2i.intValue();
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.component.context.ComponentContext
 * JD-Core Version:    0.6.1
 */