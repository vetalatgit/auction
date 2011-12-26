package ua.cn.stu.auction2011.service;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.List;
//import javax.enterprise.context.RequestScoped;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import junit.framework.TestCase;

import org.junit.BeforeClass;
import org.junit.Test;

import ua.cn.stu.auction2011.domain.Address;
import ua.cn.stu.auction2011.domain.Role;
import ua.cn.stu.auction2011.domain.Users;
import ua.cn.stu.auction2011.exceptions.GeneralServiceException;
import ua.cn.stu.auction2011.ImplService.AddressServiceImpl;
import ua.cn.stu.auction2011.ImplService.LottServiceImpl;
import ua.cn.stu.auction2011.ImplService.UserServiceImpl;
import ua.cn.stu.auction2011.services.IAddresService;
import ua.cn.stu.auction2011.services.ILottService;
import ua.cn.stu.auction2011.services.ILottinfoService;
import ua.cn.stu.auction2011.services.IMessageServices;
import ua.cn.stu.auction2011.services.IRadeService;
import ua.cn.stu.auction2011.services.IUserService;

public class TestAddressService {
	private static Context ctx;
	
	//@EJB
	private static UserServiceImpl usservice;
	//@EJB
	//(mappedName="java:global/auction2011/AddressServiceImpl")
	private static IAddresService adresrv;

	@BeforeClass
	public static void setUp() throws NamingException{
		Context ct1x = new InitialContext();
		//usservice = (UserServiceImpl) ct1x.lookup("java:global/auction2011/UserSRV");
		adresrv = (IAddresService) ct1x.lookup("java:global/auction2011/AddressService");
	}
	
	protected void getFirstEntity() throws IllegalArgumentException,
			GeneralServiceException {
		Users user = new Users();
		user.setEmail("lyag1@mail.ru");
		user.setName("asd");
		Long jo = new Long(12);
		user.setIdentnumb(jo);
		// user.setId(jo);
		String log = "as1d" + Calendar.getInstance().getTimeInMillis();
		user.setLogin(log);
		user.setPassword("sdf");
		user.setPhone("123");
		user.setRating(new Long(12));
		user.setRole(Role.ADMIN);
		usservice.regUser(user);

		Address a = new Address();
		a.setAddr("ul.lisova.d2");
		a.setCity("snovyanka");
		a.setCode("123123");
		a.setCountry("Ukrain");
		adresrv.setAddressToUser(a, usservice.getUserByLogin(log).getId());

	}

	protected void getSecondEntity() throws IllegalArgumentException,
			GeneralServiceException {
		Users user = new Users();
		user.setEmail("lyag1@mail.ru");
		user.setName("asd");
		Long jo = new Long(12);
		user.setIdentnumb(jo);
		// user.setId(jo);
		String log = "asd" + Calendar.getInstance().getTimeInMillis();
		user.setLogin(log);
		user.setPassword("sdf");
		user.setPhone("123");
		user.setRating(new Long(12));
		user.setRole(Role.ADMIN);
		usservice.regUser(user);

		Address a = new Address();
		a.setAddr("ul.lisova.d2");
		a.setCity("snovyanka");
		a.setCode("123123");
		a.setCountry("Ukrain");
		adresrv.setAddressToUser(a, usservice.getUserByLogin(log).getId());
	}

	/*
	 * public TestAddressService() throws IllegalArgumentException,
	 * GeneralServiceException{ getFirstEntity(); getSecondEntity(); }
	 */

	@Test
	public void testGetAddressByUser() throws IllegalArgumentException,
			GeneralServiceException, NamingException {
		assertNotNull(adresrv.getAddressByUser(usservice.getEntityById(
				new Long(1)).getId()));
	}

	@Test
	public void testDellAddAdress() throws IllegalArgumentException,
			GeneralServiceException, NamingException {
		adresrv.delEntity(adresrv.getAddressByUser(usservice.getEntityById(
				new Long(1)).getId()));
		
		Address a = new Address();
		a.setAddr("ul.lisova.d2");
		a.setCity("snovyanka");
		a.setCode("123123");
		a.setCountry("Ukrain");
		assertNotNull(adresrv.setAddressToUser(a, new Long(1)));
		
	}
	
		
}
