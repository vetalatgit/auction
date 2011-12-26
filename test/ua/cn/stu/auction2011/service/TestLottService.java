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

import ua.cn.stu.auction2011.ImplService.LottServiceImpl;
import ua.cn.stu.auction2011.ImplService.UserServiceImpl;
import ua.cn.stu.auction2011.domain.LotInfo;
import ua.cn.stu.auction2011.domain.Lott;
import ua.cn.stu.auction2011.domain.Role;
import ua.cn.stu.auction2011.domain.Users;
import ua.cn.stu.auction2011.exceptions.GeneralServiceException;
import ua.cn.stu.auction2011.services.IGenericService;
import ua.cn.stu.auction2011.services.ILottService;
import ua.cn.stu.auction2011.services.ILottinfoService;

public class TestLottService {
	
private static Context ctx;
	
	//@EJB
	private static UserServiceImpl usservice;
	//@EJB
	//(mappedName="java:global/auction2011/AddressServiceImpl")
	private ILottService lotserv;
	private ILottinfoService lotiserv;

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
				
		LotInfo li=new LotInfo();
		li.setImg("e:/vetal/img1.jpg");
		li.setText("Super puper test");
		lotiserv.setLottInfo(li, lotserv.getLott(log, null, null, null, 0).get(0).getId());
	}

	protected void getSecondEntity() throws IllegalArgumentException, GeneralServiceException {
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
		l.setName("mers2");
		l.setDatepay(Calendar.getInstance());
		l.setRealprice(new Long(121));
		l.setType("cars");
		l.setPrice(new Long(121));
		lotserv.setLott(l, usservice.getUserByLogin(log).getId());
				
		LotInfo li=new LotInfo();
		li.setImg("e:/vetal/img1.jpg");
		li.setText("Super puper test");
		lotiserv.setLottInfo(li, lotserv.getLott(log, null, null, null, 0).get(0).getId());
	}
	
	@Test
	public void testGetLott() throws IllegalArgumentException,
			GeneralServiceException {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.YEAR, 2001);
		String type="cars";
		String name="me";
		int Price=1;
		List<Lott> lotlst= lotserv.getLott(null, date, type, name, Price);
		assertNotNull(lotlst);
		assertEquals(1,lotlst.size());
		assertEquals(lotlst.get(0),lotserv.getEntityById(new Long(101)));
	}

	@Test
	public void testGetAllTypeOfLotts() throws IllegalArgumentException,
			GeneralServiceException {
		List<String> types=lotserv.getAllTypeOfLotts();
		assertNotNull(types.get(0));
	}

	@Test
	public void testDellAddLott()
			throws IllegalArgumentException, GeneralServiceException {
		Lott lt= lotserv.getEntityById(new Long(401));
		lotserv.delEntity(new Long(401));
		assertEquals(true,lotserv.save(lt) );
	}
	
}
	


	


