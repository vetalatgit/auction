package ua.cn.stu.auction2011.services;

import ua.cn.stu.auction2011.exceptions.GeneralServiceException;
import ua.cn.stu.auction2011.domain.Address;
import javax.ejb.Local;
import javax.ejb.Remote;

@Remote
public interface IAddresService extends IGenericService<Address> {

	public Address getAddressByUser(Long userId)
			throws IllegalArgumentException, GeneralServiceException;
	
	public Address setAddressToUser(Address adr,Long userId)
			throws IllegalArgumentException, GeneralServiceException;
	
}
