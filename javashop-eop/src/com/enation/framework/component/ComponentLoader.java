package com.enation.framework.component;

import com.enation.framework.component.context.ComponentContext;
import com.enation.framework.plugin.AutoRegisterPlugin;
import com.enation.framework.plugin.IPluginBundle;
import java.util.List;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class ComponentLoader
  implements BeanPostProcessor
{
  public Object postProcessAfterInitialization(Object bean, String arg1)
    throws BeansException
  {
    return bean;
  }

  public Object postProcessBeforeInitialization(Object bean, String beanName)
    throws BeansException
  {
    AutoRegisterPlugin plugin;
    if ((bean instanceof AutoRegisterPlugin)) {
      plugin = (AutoRegisterPlugin)bean;
      if (plugin.getBundleList() != null)
      {
        List<IPluginBundle> pluginBundelList = plugin.getBundleList();
        for (IPluginBundle bundle : pluginBundelList) {
          bundle.registerPlugin(plugin);
        }

      }

    }

    if ((bean instanceof IComponent))
    {
      IComponent component = (IComponent)bean;
      ComponentView componentView = new ComponentView();
      componentView.setComponent(component);
      componentView.setComponentid(beanName);
      ComponentContext.registerComponent(componentView);
    }

    return bean;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.component.ComponentLoader
 * JD-Core Version:    0.6.1
 */