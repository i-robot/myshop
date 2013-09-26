package com.enation.app.shop.core.action.backend;

import com.enation.app.shop.core.model.PrintTmpl;
import com.enation.app.shop.core.service.IPrintTmplManager;
import com.enation.framework.action.WWAction;
import java.util.List;
import java.util.Map;

public class PrintTmplAction extends WWAction
{
  private IPrintTmplManager printTmplManager;
  private List list;
  private List trash;
  private List listCanUse;
  private Integer[] id;
  private Integer prt_tmpl_id;
  private PrintTmpl printTmpl;

  public String list()
  {
    this.list = this.printTmplManager.list();
    return "list";
  }

  public String trash() {
    this.trash = this.printTmplManager.trash();
    return "trash";
  }

  public String listCanUse() {
    this.listCanUse = this.printTmplManager.listCanUse();
    return "listCanUse";
  }

  public String add() {
    return "add";
  }

  public String saveAdd() {
    try {
      this.printTmplManager.add(this.printTmpl);
      this.msgs.add("模板添加成功");
    } catch (Exception e) {
      this.msgs.add("模板添加失败");
      e.printStackTrace();
    }
    this.urls.put("打印模板列表", "printTmpl!list.do");
    return "message";
  }

  public String edit() {
    this.printTmpl = this.printTmplManager.get(this.prt_tmpl_id.intValue());
    return "edit";
  }

  public String saveEdit() {
    try {
      this.printTmplManager.edit(this.printTmpl);
      this.msgs.add("模板修改成功");
    } catch (Exception e) {
      this.msgs.add("模板修改失败");
      e.printStackTrace();
    }
    this.urls.put("打印模板列表", "printTmpl!list.do");
    return "message";
  }

  public String delete() {
    try {
      this.printTmplManager.delete(this.id);
      this.json = "{'result':0,'message':'删除成功'}";
    } catch (Exception e) {
      this.json = "{'result':1;'message':'删除失败'}";
      e.printStackTrace();
    }
    return "json_message";
  }

  public String revert() {
    try {
      this.printTmplManager.revert(this.id);
      this.json = "{'result':0,'message':'还原成功'}";
    } catch (Exception e) {
      this.json = "{'result':1;'message':'还原失败'}";
      e.printStackTrace();
    }
    return "json_message";
  }

  public String clean() {
    try {
      this.printTmplManager.clean(this.id);
      this.json = "{'result':0,'message':'清除成功'}";
    } catch (Exception e) {
      this.json = "{'result':1;'message':'清除失败'}";
      e.printStackTrace();
    }
    return "json_message";
  }

  public IPrintTmplManager getPrintTmplManager() {
    return this.printTmplManager;
  }

  public void setPrintTmplManager(IPrintTmplManager printTmplManager) {
    this.printTmplManager = printTmplManager;
  }

  public List getList() {
    return this.list;
  }

  public void setList(List list) {
    this.list = list;
  }

  public List getTrash() {
    return this.trash;
  }

  public void setTrash(List trash) {
    this.trash = trash;
  }

  public List getListCanUse() {
    return this.listCanUse;
  }

  public void setListCanUse(List listCanUse) {
    this.listCanUse = listCanUse;
  }

  public Integer[] getId() {
    return this.id;
  }

  public void setId(Integer[] id) {
    this.id = id;
  }

  public Integer getPrt_tmpl_id() {
    return this.prt_tmpl_id;
  }

  public void setPrt_tmpl_id(Integer prtTmplId) {
    this.prt_tmpl_id = prtTmplId;
  }

  public PrintTmpl getPrintTmpl() {
    return this.printTmpl;
  }

  public void setPrintTmpl(PrintTmpl printTmpl) {
    this.printTmpl = printTmpl;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.action.backend.PrintTmplAction
 * JD-Core Version:    0.6.1
 */