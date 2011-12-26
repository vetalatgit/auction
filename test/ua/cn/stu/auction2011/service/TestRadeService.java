package ua.cn.stu.auction2011.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ua.cn.stu.auction2011.ImplService.LottServiceImpl;
import ua.cn.stu.auction2011.ImplService.RadeServiceImpl;
import ua.cn.stu.auction2011.ImplService.UserServiceImpl;
import ua.cn.stu.auction2011.domain.Lott;
import ua.cn.stu.auction2011.domain.Message;
import ua.cn.stu.auction2011.domain.Rade;
import ua.cn.stu.auction2011.domain.Role;
import ua.cn.stu.auction2011.domain.Users;
import ua.cn.stu.auction2011.exceptions.GeneralServiceException;
import ua.cn.stu.auction2011.services.IGenericService;
import ua.cn.stu.auction2011.services.ILottService;
import ua.cn.stu.auction2011.services.IMessageServices;
import ua.cn.stu.auction2011.services.IRadeService;

public class TestRadeService {
		private String log;
	
		@EJB
		private static UserServiceImpl usservice;
		@EJB
		//(mappedName="java:global/auction2011/AddressServiceImpl")
		private ILottService lotserv;
		private IRadeService radeserv; 
		
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
		
			log = "asd" + Calendar.getInstance().getTimeInMillis();
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
			
			Rade rd= new Rade();
			rd.setIsdone(true);
			rd.setPrice(new Long(1233));
			rd.setTimerate(Calendar.getInstance());
			radeserv.doRade(rd, usservice.getUserByLogin(log).getId(), lotserv.getLott(log, null, null, null, 0).get(0).getId());
			
		}
		
		
	@Test
	public void TestgetSpendMoney() throws IllegalArgumentException, GeneralServiceException{
		assertEquals(true,radeserv.getSpendMoney(Long.valueOf(401)) );
	}

	@Test
	public void TestdelRadeByUser() throws IllegalArgumentException,
			GeneralServiceException {
		assertEquals(true,radeserv.delRadeByUser(Long.valueOf(601)) );
	}

	@Test
	public void TestdelRadeByLott() throws IllegalArgumentException,
			GeneralServiceException {
		assertEquals(true,radeserv.delRadeByLott(Long.valueOf(103)) );
	}

	@Test
	public void TestdoneRade() throws IllegalArgumentException,
			GeneralServiceException {
		assertEquals(true,radeserv.doneRade(Long.valueOf(401)) );
	}

	@Test
	public void TestdoRade()
			throws IllegalArgumentException, GeneralServiceException {
		getFirstEntity();
	}

	@Test
	public void TestgetRadeByAuth() throws IllegalArgumentException,
			GeneralServiceException {
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.YEAR,2010);
		assertNotNull(radeserv.getRads(null, null, log, null, "") );
		
	}

	@Test
	public void TestgetRadeByLott() throws IllegalArgumentException,
			GeneralServiceException {
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.YEAR,2010);
		assertNotNull(radeserv.getRads(cl, "mers1", null, null, "") );
	
	}


}
