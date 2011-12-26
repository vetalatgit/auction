package ua.cn.stu.auction2011.service;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.BeforeClass;
import org.junit.Test;

import ua.cn.stu.auction2011.ImplService.UserServiceImpl;
import ua.cn.stu.auction2011.domain.Address;
import ua.cn.stu.auction2011.domain.Role;
import ua.cn.stu.auction2011.domain.Users;
import ua.cn.stu.auction2011.domain.UserSf;
import ua.cn.stu.auction2011.exceptions.GeneralServiceException;
import ua.cn.stu.auction2011.services.IAddresService;
import ua.cn.stu.auction2011.services.IGenericService;
import ua.cn.stu.auction2011.services.IUserService;

public class TestUserService{

	private static Context ctx;
	
	//@EJB
	private static UserServiceImpl usservice;
	//@EJB
	//(mappedName="java:global/auction2011/AddressServiceImpl")
	private IAddresService adresrv;

	@BeforeClass
	public static void setUp() throws NamingException{
		Context ct1x = new InitialContext();
		usservice = (UserServiceImpl) ct1x.lookup("java:global/auction2011/UserSRV");
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
		user.setRating(new Long(0));
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
	
		String log = "asd" + Calendar.getInstance().getTimeInMillis();
		user.setLogin(log);
		user.setPassword("sdf");
		user.setPhone("123");
		user.setRating(new Long(1));
		user.setRole(Role.USER);
		usservice.regUser(user);

		Address a = new Address();
		a.setAddr("ul.lisova.d2");
		a.setCity("snovyanka");
		a.setCode("123123");
		a.setCountry("Ukrain");
		adresrv.setAddressToUser(a, usservice.getUserByLogin(log).getId());
	}

	
	@Test
	public void testGetByRole() throws IllegalArgumentException, GeneralServiceException {
		List<UserSf> res=usservice.getUsersByRole(Role.ADMIN);
		assertNotNull(res);
		assertEquals(1, res.size());
	    assertEquals(usservice.getEntityById(new Long(1)), res.get(0));
		
		res=usservice.getUsersByRole(Role.USER);
    	assertNotNull(res);
		assertEquals(1, res.size());
		assertEquals(usservice.getEntityById(new Long(3)), res.get(0));

	}
	
	@Test
	public void testGetUserByLogin() throws IllegalArgumentException, GeneralServiceException{
		UserSf res =usservice.getUserByLogin(usservice.getEntityById(new Long(1)).getLogin());
		assertNotNull(res);
		assertEquals(usservice.getEntityById(new Long(1)), res);
		res =	usservice.getUserByLogin(usservice.getEntityById(new Long(51)).getLogin());
		assertNotNull(res);
		assertEquals(usservice.getEntityById(new Long(51)), res);
		res =	usservice.getUserByLogin(usservice.getEntityById(new Long(1)).getLogin());
		assertNotNull(res);
	}
	
	@Test
	public void testIncrRating() throws IllegalArgumentException, GeneralServiceException {
		usservice.getEntityById(new Long(1)).incrRating();
		assertEquals(usservice.getEntityById(new Long(1)).getRating(), usservice.getEntityById(new Long(51)).getRating());
		usservice.decrUserRating(new Long(51));
		assertEquals(new Long(1), usservice.getEntityById(new Long(1)).getRating());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIncrRatingllegalArgument() throws GeneralServiceException{
		usservice.incrUserRating(null);	
	}
	

	@Test
	public void deleteUser() throws GeneralServiceException{
		usservice.delEntity(new Long(51));	
	}
	
	
}
	


	


