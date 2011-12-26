package ua.cn.stu.auction2011.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.Calendar;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.BeforeClass;
import org.junit.Test;

import ua.cn.stu.auction2011.ImplService.UserServiceImpl;
import ua.cn.stu.auction2011.domain.Lott;
import ua.cn.stu.auction2011.domain.Message;
import ua.cn.stu.auction2011.domain.Role;

import ua.cn.stu.auction2011.domain.Users;
import ua.cn.stu.auction2011.exceptions.GeneralServiceException;
import ua.cn.stu.auction2011.services.IGenericService;
import ua.cn.stu.auction2011.services.ILottService;
import ua.cn.stu.auction2011.services.ILottinfoService;
import ua.cn.stu.auction2011.services.IMessageServices;
import ua.cn.stu.auction2011.services.IUserService;

public class TestMessageService  {
	//@EJB
	private static UserServiceImpl usservice;
	//@EJB
	//(mappedName="java:global/auction2011/AddressServiceImpl")
	private ILottService lotserv;
	private IMessageServices messerv; 
	
	@BeforeClass
	public static void setUp() throws NamingException{
		Context ct1x = new InitialContext();
		usservice = (UserServiceImpl) ct1x.lookup("java:global/auction2011/UserSRV");
	}
	
	protected void getFirstEntity() throws IllegalArgumentException, GeneralServiceException {
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
		
		Lott l=new Lott();
		l.setName("mers1");
		l.setDatepay(Calendar.getInstance());
		l.setRealprice(new Long(121));
		l.setType("cars");
		l.setPrice(new Long(121));
		lotserv.setLott(l, usservice.getUserByLogin(log).getId());
		
		Message ms=new Message();
		ms.setAuthorname("vetal");
		ms.setData("as");
		ms.setDate(Calendar.getInstance());
		ms.setRead(true);
		ms.setThem("bla");
		messerv.sendMessage(ms, lotserv.getLott(log, null, null, null, 0).get(0).getId(), usservice.getUserByLogin(log).getId(), usservice.getUserByLogin(log).getId());
		
	}

	
	@Test
	public void TestDelMessageByUser()
			throws IllegalArgumentException, GeneralServiceException {
		assertEquals(true,messerv.delMessageByUser(Long.valueOf(1)) );

	}

	@Test
	public void TestdelMessageByLott()
			throws IllegalArgumentException, GeneralServiceException {
		assertEquals(true,messerv.delMessageByLott(Long.valueOf(1)) );

	}

	@Test
	public void TestsendMessage()
			throws IllegalArgumentException, GeneralServiceException {
			getFirstEntity();
	}

	@Test
	public void TestgetMessage() throws IllegalArgumentException,
			GeneralServiceException {
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.YEAR,2010);
		assertNotNull(messerv.getMessage(null, null, cl, null, null,null,true) );
		assertNotNull(messerv.getMessage(null, null, null, "vetal", null,null,true));
	}

	@Test
	public void TestreaddMessage() throws IllegalArgumentException,
			GeneralServiceException {
		assertEquals(true,messerv.readdMessage(Long.valueOf(1)));
	}
	
}
