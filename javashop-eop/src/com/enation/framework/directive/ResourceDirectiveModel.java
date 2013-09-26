package com.enation.framework.directive;

import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.eop.sdk.utils.UploadUtil;
import com.enation.framework.resource.Resource;
import com.enation.framework.resource.impl.ResourceBuilder;
import com.enation.framework.util.StringUtil;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class ResourceDirectiveModel extends AbstractResourceDirectiveModel
  implements TemplateDirectiveModel
{
  public void execute(Environment env, Map params, TemplateModel[] arg2, TemplateDirectiveBody arg3)
    throws TemplateException, IOException
  {
    String type = getValue(params, "type");

    if (StringUtil.isEmpty(type)) type = "javascript";

    if (type.equals("script")) type = "javascript";

    Resource resource = createResource(params);
    resource.setType(type);

    if ("javascript".equals(type)) {
      ResourceBuilder.putScript(resource);
    }

    if ("css".equals(type)) {
      ResourceBuilder.putCss(resource);
    }

    if ("image".equals(type)) {
      String src = params.get("src").toString();
      String postfix = getValue(params, "postfix");
      String imageurl = getImageUrl(src, postfix);
      env.getOut().write(imageurl);
    }
  }

  private String getImageUrl(String pic, String postfix)
  {
    if (StringUtil.isEmpty(pic))
      pic = EopSetting.DEFAULT_IMG_URL;
    if (pic.toUpperCase().startsWith("HTTP"))
      return pic;
    if (pic.startsWith("fs:")) {
      pic = UploadUtil.replacePath(pic);
    }
    else {
      EopContext ectx = EopContext.getContext();

      if (!pic.startsWith("/")) {
        pic = "/" + pic;
      }

      if (("2".equals(EopSetting.RESOURCEMODE)) || (EopSetting.DEVELOPMENT_MODEL))
      {
        EopSite site = ectx.getCurrentSite();

        pic = ectx.getResDomain() + "/themes/" + site.getThemepath() + pic;
      }
      else
      {
        pic = ectx.getResDomain() + pic;
      }

    }

    if (!StringUtil.isEmpty(postfix)) {
      return UploadUtil.getThumbPath(pic, postfix);
    }
    return pic;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.directive.ResourceDirectiveModel
 * JD-Core Version:    0.6.1
 */