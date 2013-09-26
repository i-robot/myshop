package com.enation.app.shop.core.service.impl;

import com.enation.app.base.core.model.Member;
import com.enation.app.base.core.model.MemberAddress;
import com.enation.app.shop.core.service.IMemberAddressManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.eop.sdk.user.IUserService;
import com.enation.eop.sdk.user.UserServiceFactory;
import com.enation.framework.database.IDaoSupport;
import java.util.List;

public class MemberAddressManager extends BaseSupport<MemberAddress>
  implements IMemberAddressManager
{
  public void addAddress(MemberAddress address)
  {
    IUserService userService = UserServiceFactory.getUserService();
    Member member = userService.getCurrentMember();
    address.setMember_id(member.getMember_id());
    this.baseDaoSupport.insert("member_address", address);
  }

  public void deleteAddress(int addr_id)
  {
    this.baseDaoSupport.execute("delete from member_address where addr_id = ?", new Object[] { Integer.valueOf(addr_id) });
  }

  public MemberAddress getAddress(int addr_id)
  {
    MemberAddress address = (MemberAddress)this.baseDaoSupport.queryForObject("select * from member_address where addr_id = ?", MemberAddress.class, new Object[] { Integer.valueOf(addr_id) });

    return address;
  }

  public List<MemberAddress> listAddress()
  {
    IUserService userService = UserServiceFactory.getUserService();
    Member member = userService.getCurrentMember();
    List list = this.baseDaoSupport.queryForList("select * from member_address where member_id = ?", MemberAddress.class, new Object[] { member.getMember_id() });

    return list;
  }

  public void updateAddress(MemberAddress address)
  {
    this.baseDaoSupport.update("member_address", address, "addr_id=" + address.getAddr_id());
  }

  public void updateAddressDefult()
  {
    IUserService userService = UserServiceFactory.getUserService();
    Member member = userService.getCurrentMember();
    this.baseDaoSupport.execute("update member_address set def_addr = 0 where member_id = ?", new Object[] { member.getMember_id() });
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.service.impl.MemberAddressManager
 * JD-Core Version:    0.6.1
 */