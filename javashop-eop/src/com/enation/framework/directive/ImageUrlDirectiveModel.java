package com.enation.framework.directive;

import com.enation.eop.resource.model.EopSite;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.eop.sdk.utils.UploadUtil;
import com.enation.framework.util.StringUtil;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class ImageUrlDirectiveModel
  implements TemplateDirectiveModel
{
  public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
    throws TemplateException, IOException
  {
    String pic = params.get("pic").toString();
    String postfix = null;
    if (params.get("postfix") != null) {
      postfix = params.get("postfix").toString();
    }
    pic = getImageUrl(pic, postfix);
    env.getOut().write(pic);
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
 * Qualified Name:     com.enation.framework.directive.ImageUrlDirectiveModel
 * JD-Core Version:    0.6.1
 */