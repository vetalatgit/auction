package ua.cn.stu.auction2011.ImplService;

import ua.cn.stu.auction2011.services.IAddresService;
import ua.cn.stu.auction2011.services.IUserService;
import ua.cn.stu.auction2011.domain.Address;
import ua.cn.stu.auction2011.domain.Users;
import ua.cn.stu.auction2011.exceptions.GeneralServiceException;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

@Stateless(name="AddressService")
@Remote
public class AddressServiceImpl extends GenericServiceImpl<Address> implements
		IAddresService {
	
	@EJB
	IUserService usrserv;
	
	public AddressServiceImpl() {
		super(Address.class);
		
	}

	@Override
	protected void beforeEntityAddUpdate(Address entity)
			throws GeneralServiceException {

		
	}

	@Override
	protected void beforeEntityDelete(Address entity)
			throws GeneralServiceException {

		
	}

	@Override
	public Address getAddressByUser(Long userId)
			throws IllegalArgumentException, GeneralServiceException {
		return executeQuery("Address.getAddressByUser", true, true, usrserv.getEntityById(userId));
	}
	
	public Address setAddressToUser(Address adr,Long userId)
			throws IllegalArgumentException, GeneralServiceException{
		if(usrserv.getEntityById(userId)==null){
		return null;
		}else{
		adr.setUser(usrserv.getEntityById(userId));
		save(adr);
		return adr;
		}
	}

}
