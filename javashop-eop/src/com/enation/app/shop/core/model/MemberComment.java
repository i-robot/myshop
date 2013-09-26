package com.enation.app.shop.core.model;

import com.enation.framework.database.NotDbField;
import com.enation.framework.database.PrimaryKeyField;

public class MemberComment
{
  private int comment_id;
  private int goods_id;
  private Integer member_id;
  private String content;
  private String img;
  private long dateline;
  private String ip;
  private String reply;
  private long replytime;
  private int status;
  private int type;
  private int replystatus;
  private int grade;
  private String imgPath;

  public MemberComment()
  {
  }

  public MemberComment(int comment_id, int goods_id, Integer member_id, String content, String img, long dateline, String ip, String reply, long replytime, int status, int type, int replystatus, int grade)
  {
    this.comment_id = comment_id;
    this.goods_id = goods_id;
    this.member_id = member_id;
    this.content = content;
    this.img = img;
    this.dateline = dateline;
    this.ip = ip;
    this.reply = reply;
    this.replytime = replytime;
    this.status = status;
    this.type = type;
    this.replystatus = replystatus;
    this.grade = grade;
  }

  @PrimaryKeyField
  public int getComment_id() {
    return this.comment_id;
  }

  public void setComment_id(int comment_id) {
    this.comment_id = comment_id;
  }

  public int getGoods_id() {
    return this.goods_id;
  }

  public void setGoods_id(int goods_id) {
    this.goods_id = goods_id;
  }

  public Integer getMember_id() {
    return this.member_id;
  }

  public void setMember_id(Integer member_id) {
    this.member_id = member_id;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getImg() {
    return this.img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public long getDateline() {
    return this.dateline;
  }

  public void setDateline(long dateline) {
    this.dateline = dateline;
  }

  public String getIp() {
    return this.ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getReply() {
    return this.reply;
  }

  public void setReply(String reply) {
    this.reply = reply;
  }

  public long getReplytime() {
    return this.replytime;
  }

  public void setReplytime(long replytime) {
    this.replytime = replytime;
  }

  public int getStatus() {
    return this.status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int getType() {
    return this.type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getReplystatus() {
    return this.replystatus;
  }

  public void setReplystatus(int replystatus) {
    this.replystatus = replystatus;
  }

  public int getGrade() {
    return this.grade;
  }

  public void setGrade(int grade) {
    this.grade = grade;
  }

  @NotDbField
  public String getImgPath() {
    return this.imgPath;
  }

  public void setImgPath(String imgPath) {
    this.imgPath = imgPath;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.MemberComment
 * JD-Core Version:    0.6.1
 */