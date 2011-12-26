package ua.cn.stu.auction2011.domain;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

//import javax.enterprise.context.RequestScoped;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import ua.cn.stu.auction2011.domain.Users;
import ua.cn.stu.auction2011.exceptions.GeneralServiceException;
import ua.cn.stu.auction2011.exceptions.LDAPException;
import ua.cn.stu.auction2011.ImplService.LottServiceImpl;
import ua.cn.stu.auction2011.ImplService.UserServiceImpl;
import ua.cn.stu.auction2011.services.IAddresService;
import ua.cn.stu.auction2011.services.ILottService;
import ua.cn.stu.auction2011.services.ILottinfoService;
import ua.cn.stu.auction2011.services.IMessageServices;
import ua.cn.stu.auction2011.services.IRadeService;
import ua.cn.stu.auction2011.services.IUserService;


@ManagedBean(name="userBean")
@SessionScoped
public class UserBean {
	
	
	@EJB//(mappedName="UserSRV")
	private IMessageServices messervice;
	
	@EJB//(mappedName="UserSRV")
	private IRadeService radesrv;
	
	@EJB//(mappedName="UserSRV")
	private IAddresService adresrv;
	
	@EJB//(mappedName="UserSRV")
	private ILottService lotesrv;
	
	@EJB//(mappedName="UserSRV")
	private ILottinfoService lotiesrv;
	
	@EJB//(mappedName="UserSRV")
	private IUserService usservice;
	
	private String usr;
	private Integer num;
	
	public void setNum(Integer num){
		this.num = num;
	}
	
	public Integer getNum(){
		return num;
	}
	
	public UserBean(){}
	
	public String getUsr() {
		return usr;
	}
	public void setUsr(String usr){
		this.usr=usr;
	}
	/*HttpServletRequest request = (HttpServletRequest) FacesContext
			.getCurrentInstance().getExternalContext().getRequest();
		try {
			request.login(this.login, this.password);
			Principal p = request.getUserPrincipal();*/
		
	public String select() throws IllegalArgumentException, GeneralServiceException, NamingException{
		
		
		//String login="vetal";
		
		StringBuffer str=new StringBuffer();
		
		//List<Lott> userList = (List<Lott>)lotesrv.getLott(null,Calendar.getInstance(), null, null, 0);
	//	Query  q = Rade.getBestLots
		
	/*	for (Lott user : userList) {
			str.append(user.getName());
			str.append("  and  ");			
		}	*/	
		
		//str.append(" "+usservice.getStr());
		return str.toString();
	    
        /*String userName = "CN=Username Surname";  
        String oldPassword = "passwrod";  
        String newPassword = "password";  
        String searchBase = "dc=auction,dc=com";  
        
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://localhost:389");
		env.put(Context.SECURITY_CREDENTIALS, "159357");
		env.put(Context.SECURITY_PRINCIPAL,"cn=Manager,dc=auction,dc=com");
        env.put(Context.SECURITY_AUTHENTICATION,"Simple");   
        
		DirContext ctx = null;
		try {
			ctx = new InitialDirContext(env);
			System.out.println("connection created!!!!!");
		} catch (Exception e) {
			  System.out.println(" "+ctx.getNameInNamespace()); 
		}
		return ctx.getNameInNamespace();  
		 /*ModificationItem[] mods = new ModificationItem[2];
		 String oldQuotedPassword = "\"" + oldPassword + "\"";  
         byte[] oldUnicodePassword = oldQuotedPassword.getBytes("UTF-16LE");  
         String newQuotedPassword = "\"" + newPassword + "\"";  
         byte[] newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");  
       
         mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, new BasicAttribute("unicodePwd", oldUnicodePassword));  
         mods[1] = new ModificationItem(DirContext.ADD_ATTRIBUTE, new BasicAttribute("unicodePwd", newUnicodePassword));  

         // Perform the update  
         ctx.modifyAttributes(userName + searchBase, mods);  
         System.out.println(userName + " " + mods);  
       
         System.out.println("Reset Password for: " + userName);    
         ctx.close();  */
		   
	
	}
	
	public void action() throws IllegalArgumentException, GeneralServiceException{
		
		     
		
		/*Users user = new Users();
		user.setEmail("lyag1@mail.ru");
		user.setName(usr);
		Long jo=new Long(12);
		user.setIdentnumb(jo);
		//user.setId(jo);
		user.setLogin(usr);
		user.setPassword("sdf");
		user.setPhone("123");
		user.setRating(new Long(12));
		user.setRole(Role.ADMIN);
		usservice.regUser(user);*/
		/*Users us = usservice.getEntityById(new Long(1));
		us.setName("loua.cn.stu.auction2011oooon!");
		usservice.regUser(us);
		*/
		/*
		List <Lott> lts=lotesrv.getLott(new Long(1),null, null, null, 0);
		for(int i=0;i<lts.size();i++){
			Users us = usservice.getEntityById(lts.get(i).getUser().getId());
			us.setId(null);
			us.setLogin("null");
			usservice.regUser(us);
		}
		*/
		//lotesrv.delEntity(new Long(2));
	//	usservice.delEntity(new Long(1));
		//adresrv.delEntity(new Long(102));
		//usservice.delEntity(new Long(51));
		
		/*Address a=new Address();
		a.setAddr("ul.lisova.d2");
		a.setCity("snovyanka");
		a.setCode("123123");
		a.setCountry("Ukrain");*/
		//adresrv.setAddressToUser(a, usservice.getUserByLogin(usr).getId());
		
		/////////
		/*List <Rade> rd=radesrv.getRads(null, new Long(2),null, null, "");
		for(int i=0;i<rd.size();i++){
			radesrv.doRade(rd.get(i), rd.get(i).getUser().getId(), null);
		}
		///////// 
		 */
		/*List <Rade> rd=radesrv.getRads(null,new Long(2),null, null, "");
		radesrv.delEntity(rd.get(0).getId());*/
		/*rd.setIsdone(true);
		rd.setPrice(new Long(1233));
		rd.setTimerate(Calendar.getInstance());*/
		//radesrv.doRade(rd,  new Long(51), new Long(2));
	/*
		Lott l=new Lott();
		l.setName("dog");
		l.setDatepay(Calendar.getInstance());//Calendar.getInstance()
		l.setRealprice(new Long(121));
		l.setType("cars");
		l.setPrice(new Long(123));
		//lotesrv.save(l);
		lotesrv.setLott(l, new Long(1));
		*/
	//	lotesrv.delEntity(new Long(702));
		
/*	Users user = new Users();
	user.setEmail("lyag1@mail.ru");
	user.setName(usr);
	Long jo=new Long(12);
	user.setIdentnumb(jo);
	//user.setId(jo);
	user.setLogin(usr);
	user.setPassword("sdf");
	user.setPhone("123");
	user.setRating(new Long(12));
	user.setRole(Role.ADMIN);
	usservice.save(user);
	*/
	/*LotInfo li=new LotInfo();
	li.setImg("e:/vetal/img1.jpg");
	li.setText("Super puper test");
	lotiesrv.setLottInfo(li, new Long(752));*/
	
	
	
	
/*	
Message ms=new Message();
	ms.setData("as");
	ms.setDate(Calendar.getInstance());
	ms.setRead(true);
	ms.setThem("bla");
	messervice.sendMessage(ms, null, new Long(1), new Long(1));
*/
	}
	
}

