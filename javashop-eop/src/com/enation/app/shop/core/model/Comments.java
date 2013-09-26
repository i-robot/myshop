package com.enation.app.shop.core.model;

import com.enation.eop.sdk.utils.UploadUtil;
import com.enation.framework.database.NotDbField;
import com.enation.framework.database.PrimaryKeyField;
import java.util.Date;

public class Comments
{
  private Integer comment_id;
  private Integer for_comment_id;
  private Integer object_id;
  private String object_type;
  private Integer author_id;
  private String author;
  private String levelname;
  private String contact;
  private String mem_read_status;
  private String adm_read_status;
  private Long time;
  private Long lastreply;
  private String reply_name;
  private String title;
  private String acomment;
  private String ip;
  private String display;
  private Integer p_index;
  private String disabled;
  private String commenttype;
  private int grade;
  private String img;
  private int deal_complete;

  public int getDeal_complete()
  {
    return this.deal_complete;
  }

  public void setDeal_complete(int deal_complete) {
    this.deal_complete = deal_complete;
  }

  @NotDbField
  public Date getDate() {
    return new Date(getTime().longValue());
  }

  public String getCommenttype() {
    return this.commenttype;
  }

  public void setCommenttype(String commenttype) {
    this.commenttype = commenttype;
  }

  @PrimaryKeyField
  public Integer getComment_id() {
    return this.comment_id;
  }

  public void setComment_id(Integer commentId) {
    this.comment_id = commentId;
  }

  public Integer getFor_comment_id() {
    return this.for_comment_id;
  }

  public void setFor_comment_id(Integer forCommentId) {
    this.for_comment_id = forCommentId;
  }

  public Integer getObject_id() {
    return this.object_id;
  }

  public void setObject_id(Integer objectId) {
    this.object_id = objectId;
  }

  public String getObject_type() {
    return this.object_type;
  }

  public void setObject_type(String objectType) {
    this.object_type = objectType;
  }

  public Integer getAuthor_id() {
    return this.author_id;
  }

  public void setAuthor_id(Integer authorId) {
    this.author_id = authorId;
  }

  public String getAuthor() {
    return this.author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getLevelname() {
    return this.levelname;
  }

  public void setLevelname(String levelname) {
    this.levelname = levelname;
  }

  public String getContact() {
    return this.contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }

  public String getMem_read_status() {
    return this.mem_read_status;
  }

  public void setMem_read_status(String memReadStatus) {
    this.mem_read_status = memReadStatus;
  }

  public String getAdm_read_status() {
    return this.adm_read_status;
  }

  public void setAdm_read_status(String admReadStatus) {
    this.adm_read_status = admReadStatus;
  }

  public Long getTime() {
    return this.time;
  }

  public void setTime(Long time) {
    this.time = time;
  }

  public Long getLastreply() {
    return this.lastreply;
  }

  public void setLastreply(Long lastreply) {
    this.lastreply = lastreply;
  }

  public String getReply_name() {
    return this.reply_name;
  }

  public void setReply_name(String replyName) {
    this.reply_name = replyName;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAcomment() {
    return this.acomment;
  }

  public void setAcomment(String acomment) {
    this.acomment = acomment;
  }

  public String getIp() {
    return this.ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getDisplay() {
    return this.display;
  }

  public void setDisplay(String display) {
    this.display = display;
  }

  public Integer getP_index() {
    return this.p_index;
  }

  public void setP_index(Integer pIndex) {
    this.p_index = pIndex;
  }

  public String getDisabled() {
    return this.disabled;
  }

  public void setDisabled(String disabled) {
    this.disabled = disabled;
  }

  public int getGrade() {
    return this.grade;
  }

  public void setGrade(int grade) {
    this.grade = grade;
  }

  public String getImg() {
    return this.img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  @NotDbField
  public String getImage() {
    this.img = UploadUtil.replacePath(this.img);
    return this.img;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.Comments
 * JD-Core Version:    0.6.1
 */