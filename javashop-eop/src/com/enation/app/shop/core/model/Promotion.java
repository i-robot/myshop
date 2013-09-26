package com.enation.app.shop.core.model;

import com.enation.framework.database.PrimaryKeyField;
import java.io.Serializable;

public class Promotion
  implements Serializable
{
  private int pmt_id;
  private String pmts_id;
  private int pmta_id;
  private Long pmt_time_begin;
  private Long pmt_time_end;
  private Double order_money_from;
  private Double order_money_to;
  private int seq;
  private int pmt_type;
  private int pmt_belong;
  private int pmt_bond_type;
  private String pmt_describe;
  private String pmt_solution;
  private int pmt_ifcoupon;
  private Long pmt_update_time;
  private String pmt_basic_type;
  private String disabled;
  private String pmt_ifsale;
  private int pmt_distype;

  @PrimaryKeyField
  public int getPmt_id()
  {
    return this.pmt_id;
  }

  public void setPmt_id(int pmtId) {
    this.pmt_id = pmtId;
  }

  public String getPmts_id() {
    return this.pmts_id;
  }

  public void setPmts_id(String pmtsId) {
    this.pmts_id = pmtsId;
  }

  public int getPmta_id() {
    return this.pmta_id;
  }

  public void setPmta_id(int pmtaId) {
    this.pmta_id = pmtaId;
  }

  public Long getPmt_time_begin() {
    return this.pmt_time_begin;
  }

  public void setPmt_time_begin(Long pmtTimeBegin) {
    this.pmt_time_begin = pmtTimeBegin;
  }

  public Long getPmt_time_end() {
    return this.pmt_time_end;
  }

  public void setPmt_time_end(Long pmtTimeEnd) {
    this.pmt_time_end = pmtTimeEnd;
  }

  public Double getOrder_money_from() {
    return this.order_money_from;
  }

  public void setOrder_money_from(Double orderMoneyFrom) {
    this.order_money_from = orderMoneyFrom;
  }

  public Double getOrder_money_to() {
    return this.order_money_to;
  }

  public void setOrder_money_to(Double orderMoneyTo) {
    this.order_money_to = orderMoneyTo;
  }

  public int getSeq() {
    return this.seq;
  }

  public void setSeq(int seq) {
    this.seq = seq;
  }

  public int getPmt_type() {
    return this.pmt_type;
  }

  public void setPmt_type(int pmtType) {
    this.pmt_type = pmtType;
  }

  public int getPmt_belong() {
    return this.pmt_belong;
  }

  public void setPmt_belong(int pmtBelong) {
    this.pmt_belong = pmtBelong;
  }

  public int getPmt_bond_type() {
    return this.pmt_bond_type;
  }

  public void setPmt_bond_type(int pmtBondType) {
    this.pmt_bond_type = pmtBondType;
  }

  public String getPmt_describe() {
    return this.pmt_describe;
  }

  public void setPmt_describe(String pmtDescribe) {
    this.pmt_describe = pmtDescribe;
  }

  public String getPmt_solution() {
    return this.pmt_solution;
  }

  public void setPmt_solution(String pmtSolution) {
    this.pmt_solution = pmtSolution;
  }

  public int getPmt_ifcoupon() {
    return this.pmt_ifcoupon;
  }

  public void setPmt_ifcoupon(int pmtIfcoupon) {
    this.pmt_ifcoupon = pmtIfcoupon;
  }

  public Long getPmt_update_time() {
    return this.pmt_update_time;
  }

  public void setPmt_update_time(Long pmtUpdateTime) {
    this.pmt_update_time = pmtUpdateTime;
  }

  public String getPmt_basic_type() {
    return this.pmt_basic_type;
  }

  public void setPmt_basic_type(String pmtBasicType) {
    this.pmt_basic_type = pmtBasicType;
  }

  public String getDisabled() {
    return this.disabled;
  }

  public void setDisabled(String disabled) {
    this.disabled = disabled;
  }

  public String getPmt_ifsale() {
    return this.pmt_ifsale;
  }

  public void setPmt_ifsale(String pmtIfsale) {
    this.pmt_ifsale = pmtIfsale;
  }

  public int getPmt_distype() {
    return this.pmt_distype;
  }

  public void setPmt_distype(int pmtDistype) {
    this.pmt_distype = pmtDistype;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.Promotion
 * JD-Core Version:    0.6.1
 */