package com.enation.app.base.core.model;

import com.enation.framework.database.PrimaryKeyField;
import java.io.Serializable;

public class Message
  implements Serializable
{
  private Integer msg_id;
  private Integer for_id;
  private String msg_from;
  private Integer from_id;
  private Integer from_type;
  private Integer to_id;
  private String msg_to;
  private Integer to_type;
  private String unread;
  private String folder;
  private String email;
  private String tel;
  private String subject;
  private String message;
  private Integer rel_order;
  private Long date_line;
  private String is_sec;
  private String del_status;
  private String disabled;
  private String msg_ip;
  private String msg_type;

  public String getMsg_from()
  {
    return this.msg_from;
  }

  public void setMsg_from(String msgFrom) {
    this.msg_from = msgFrom;
  }

  public String getMsg_to() {
    return this.msg_to;
  }

  public void setMsg_to(String msgTo) {
    this.msg_to = msgTo;
  }

  public String getUnread() {
    return this.unread;
  }

  public void setUnread(String unread) {
    this.unread = unread;
  }

  public String getFolder() {
    return this.folder;
  }

  public void setFolder(String folder) {
    this.folder = folder;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTel() {
    return this.tel;
  }

  public void setTel(String tel) {
    this.tel = tel;
  }

  public String getSubject() {
    return this.subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @PrimaryKeyField
  public Integer getMsg_id() {
    return this.msg_id;
  }

  public void setMsg_id(Integer msg_id) {
    this.msg_id = msg_id;
  }

  public Integer getFor_id() {
    return this.for_id;
  }

  public void setFor_id(Integer for_id) {
    this.for_id = for_id;
  }

  public Integer getFrom_id() {
    return this.from_id;
  }

  public void setFrom_id(Integer from_id) {
    this.from_id = from_id;
  }

  public Integer getFrom_type() {
    return this.from_type;
  }

  public void setFrom_type(Integer from_type) {
    this.from_type = from_type;
  }

  public Integer getTo_id() {
    return this.to_id;
  }

  public void setTo_id(Integer to_id) {
    this.to_id = to_id;
  }

  public Integer getTo_type() {
    return this.to_type;
  }

  public void setTo_type(Integer to_type) {
    this.to_type = to_type;
  }

  public Integer getRel_order() {
    return this.rel_order;
  }

  public void setRel_order(Integer rel_order) {
    this.rel_order = rel_order;
  }

  public Long getDate_line() {
    return this.date_line;
  }

  public void setDate_line(Long dateLine) {
    this.date_line = dateLine;
  }

  public String getIs_sec() {
    return this.is_sec;
  }

  public void setIs_sec(String isSec) {
    this.is_sec = isSec;
  }

  public String getDel_status() {
    return this.del_status;
  }

  public void setDel_status(String delStatus) {
    this.del_status = delStatus;
  }

  public String getDisabled() {
    return this.disabled;
  }

  public void setDisabled(String disabled) {
    this.disabled = disabled;
  }

  public String getMsg_ip() {
    return this.msg_ip;
  }

  public void setMsg_ip(String msgIp) {
    this.msg_ip = msgIp;
  }

  public String getMsg_type() {
    return this.msg_type;
  }

  public void setMsg_type(String msgType) {
    this.msg_type = msgType;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.app.base.core.model.Message
 * JD-Core Version:    0.6.1
 */