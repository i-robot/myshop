package com.enation.framework.context.spring;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

public class SpringContextHolder
  implements ApplicationContextAware
{
  private static ConfigurableApplicationContext applicationContext = null;
  
  public SpringContextHolder(){
  }
  
  public void setApplicationContext(ApplicationContext applicationContext)
  {
	SpringContextHolder.applicationContext = (ConfigurableApplicationContext)applicationContext;
  }

  public static ApplicationContext getApplicationContext()
  {
    checkApplicationContext();
    return SpringContextHolder.applicationContext;
  }

  public static <T> T getBean(String name)
  {
	checkApplicationContext();
    return (T) SpringContextHolder.applicationContext.getBean(name);
  }

  public static <T> T getBean(Class<T> clazz)
  {
	checkApplicationContext();
    return (T) SpringContextHolder.applicationContext.getBeansOfType(clazz);
  }

  private static void checkApplicationContext() {
    if (SpringContextHolder.applicationContext == null)
      throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextUtil");
  }

  public static void loadbean() {
    XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader((BeanDefinitionRegistry)SpringContextHolder.applicationContext.getBeanFactory());
    beanDefinitionReader.setResourceLoader(SpringContextHolder.applicationContext);
    beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(SpringContextHolder.applicationContext));
    try
    {
      beanDefinitionReader.loadBeanDefinitions(SpringContextHolder.applicationContext.getResources("classpath:newspring/newApplicationContext.xml"));
      addBeanPostProcessor();
    } catch (BeansException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void addBeanPostProcessor() { String[] postProcessorNames = SpringContextHolder.applicationContext.getBeanFactory().getBeanNamesForType(BeanPostProcessor.class, true, false);

    for (String postProcessorName : postProcessorNames)
    	SpringContextHolder.applicationContext.getBeanFactory().addBeanPostProcessor((BeanPostProcessor)SpringContextHolder.applicationContext.getBean(postProcessorName));
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.context.spring.SpringContextHolder
 * JD-Core Version:    0.6.1
 */