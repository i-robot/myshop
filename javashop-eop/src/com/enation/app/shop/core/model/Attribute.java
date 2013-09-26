package com.enation.app.shop.core.model;

import com.enation.framework.util.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attribute
{
  private String name;
  private String options;
  private int type;
  private String value;
  private List valueList;
  private int[] nums = null;
  private int hidden;
  private int required;
  private String datatype;
  private String unit;
  private Map[] maps;

  public Attribute()
  {
    this.valueList = new ArrayList();
    this.hidden = 0;
  }

  public void addValue(String _value) {
    this.valueList.add(_value);
  }

  public String getName()
  {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public int getType() {
    return this.type;
  }
  public void setType(int type) {
    this.type = type;
  }
  public String getValue() {
    return this.value;
  }
  public void setValue(String value) {
    this.value = value;
  }

  public String getValStr()
  {
    if (this.type >= 3)
    {
      if ((this.type >= 3) && (this.type <= 5) && 
        (this.value != null) && (!this.value.equals("")) && (!this.value.equals("null"))) {
        int index1 = StringUtil.toInt(this.value, false);
        if (getOptionAr().length > index1) {
          return getOptionAr()[StringUtil.toInt(this.value, false)];
        }
      }
    }

    return this.value;
  }

  public String getOptions() {
    return this.options;
  }
  public void setOptions(String options) {
    this.options = options;
  }

  public String[] getOptionAr()
  {
    if ((this.options == null) || (this.options.equals(""))) {
      return new String[0];
    }

    String[] ar_options = this.options.split(",");

    return ar_options;
  }

  public Map[] getOptionMap()
  {
    String[] optionAr = getOptionAr();

    if (this.maps == null) {
      this.maps = new Map[optionAr.length];

      for (int i = 0; i < optionAr.length; i++) {
        Map m = new HashMap(4);
        m.put("name", optionAr[i]);
        m.put("num", Integer.valueOf(getNums()[i]));
        m.put("url", "");
        m.put("selected", Integer.valueOf(0));
        this.maps[i] = m;
      }
    }
    return this.maps;
  }

  public List getValueList()
  {
    return this.valueList;
  }
  public void setValueList(List valueList) {
    this.valueList = valueList;
  }

  public int[] getNums()
  {
    if (this.nums == null) this.nums = new int[getOptionAr().length];

    return this.nums;
  }

  public void setNums(int[] nums) {
    this.nums = nums;
  }

  public int getHidden()
  {
    return this.hidden;
  }

  public void setHidden(int hidden) {
    this.hidden = hidden;
  }

  public int getRequired() {
    return this.required;
  }

  public void setRequired(int required) {
    this.required = required;
  }

  public String getDatatype() {
    return this.datatype;
  }

  public void setDatatype(String datatype) {
    this.datatype = datatype;
  }

  public String getUnit() {
    return this.unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public static void main(String[] args)
  {
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.Attribute
 * JD-Core Version:    0.6.1
 */