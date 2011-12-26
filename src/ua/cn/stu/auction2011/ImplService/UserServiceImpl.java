package ua.cn.stu.auction2011.ImplService;

import javax.ejb.Local;
import javax.ejb.Remote;

import ua.cn.stu.auction2011.config.Messages;
import ua.cn.stu.auction2011.domain.Lott;
import ua.cn.stu.auction2011.domain.Role;
import ua.cn.stu.auction2011.domain.Users;
import ua.cn.stu.auction2011.domain.UserSf;
import ua.cn.stu.auction2011.exceptions.GeneralServiceException;
import ua.cn.stu.auction2011.exceptions.LDAPException;
import ua.cn.stu.auction2011.services.ILottService;
import ua.cn.stu.auction2011.services.IUserService;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;


@Stateless
@Remote
public class UserServiceImpl extends GenericServiceImpl<Users> implements
		IUserService {
	
	private DirContext ctx=null;

	ILottService lotserv;
	
	public UserServiceImpl() {
		super(Users.class);
	}
	
	public UserServiceImpl(Class<Users> persistentClass) {
		super(persistentClass);
	
	}

	@Override
	protected void beforeEntityAddUpdate(Users entity)
			throws GeneralServiceException {
	
		
	}

	@Override
	protected void beforeEntityDelete(Users entity)
			throws GeneralServiceException {
		try {
			executeUpdateQuery("Address.deleteByUser", true, entity);
			executeUpdateQuery("Message.deleteAllByUser", true, entity);
			executeUpdateQuery("Rade.deleteAllByUser", true, entity);
			//lotserv.delAllEntities(ids);
			executeUpdateQuery("Lott.deleteAllByUser", true, entity);
			delUser(entity);
		} catch (LDAPException e) {
			e.printStackTrace();
		}			
	}						
	
	@Override
	public UserSf updateUser(UserSf usr) throws IllegalArgumentException,
			GeneralServiceException {
	 if ( getEntityById(usr.getId())==null)	return null;
	 try {
		 if(!((Users)getEntityById(usr.getId())).getLogin().equals(usr.getLogin())) changeLogin(usr,
				 ((Users)getEntityById(usr.getId())).getLogin(),((Users)getEntityById(usr.getId())).getRole());
		 /*if(!(getUserPasswd(usr.getLogin()).equals(usr.getPassword())))changePassword(usr);			
			*/if(!((Users)getEntityById(usr.getId())).getRole().equals(usr.getRole()))changeRole(usr,
			((Users)getEntityById(usr.getId())).getRole());
		} catch (LDAPException e) {
			e.printStackTrace();
		}
	 	Users usrs = getEntityById(usr.getId());
		usrs.setEmail(usr.getEmail());
		usrs.setIdentnumb(usr.getIdentnumb());
		usrs.setLogin(usr.getLogin());
		usrs.setName(usr.getName());
		usrs.setPhone(usr.getPhone());
		usrs.setRating(usr.getRating());
		usrs.setRole(usr.getRole());
	 save(usrs);
	 return usr;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public Users regUser(Users usr) throws IllegalArgumentException,
			GeneralServiceException {
	 	if (!((List<Users>)executeQuery("Users.getUserByLogin", true, false, usr.getLogin())).isEmpty()){
			return null;
		}
	 	try {
			register(usr);
		} catch (LDAPException e) {
			e.printStackTrace();
		}
		return save(usr);
	}

	@Override
	public void incrUserRating(Long userId) throws IllegalArgumentException,
			GeneralServiceException {
		if (userId == null){
			throw new IllegalArgumentException("user id is null");
		}else{
		executeUpdateQuery("Users.incrRating", true, userId);
	}}

	@Override
	public void setUserRating(Long userId,Long rating) throws IllegalArgumentException,
			GeneralServiceException {
		if (userId == null){
			throw new IllegalArgumentException("user id is null");
		}else{
		
		try{Users usr=getEntityById(userId);
		usr.setRating(rating);
		delEntity(userId);
		save(usr);}catch(Exception e){
		}
	}
		
	}

	@Override
	public List<UserSf> getUsersByRole(Role role)
			throws IllegalArgumentException, GeneralServiceException {
		if (role == null){
			throw new IllegalArgumentException("role isn`t bee null");
		}else{
			ArrayList<UserSf> usrsf=new ArrayList<UserSf>(); 
			List<Users> usrs=executeQuery("Users.getUserByRole", true, false, role);
		for (Users usr : usrs) {
			usrsf.add(usr.getUsersSafe());
		}
			return usrsf;
	}
	}

	@Override
	public UserSf getUserByLogin(String login) {
		UserSf usr=null;
		if (login == null){
			throw new IllegalArgumentException("logi isn`t bee null");
		}else{
			try {
			usr=((Users)executeQuery("Users.getUserByLogin", true, true, login)).getUsersSafe();
			return usr;
			}
			catch (Exception e) {
			return usr;
			}	
		}								 
	}

	@Override
	public UserSf getUserByMail(String mail) throws IllegalArgumentException,
			GeneralServiceException {
		if (mail == null){
			throw new IllegalArgumentException("mail isn`t bee null");
		}else{
		return ((Users)executeQuery("Users.getUserByEmail", true, true, mail)).getUsersSafe();
	}
	}

	@Override
	public void decrUserRating(Long userId) throws IllegalArgumentException,
			GeneralServiceException {
		if (userId == null){
			throw new IllegalArgumentException("user id is null");
		}else{
		executeUpdateQuery("Users.decrRating", true, userId);
	}
	}
	
	/**
	 * @return the ctx
	 * @throws LDAPException 
	 */
	public DirContext getCtx(){
		return ctx;
	}

	/**
	 * @param ctx the ctx to set
	 */
	public void setCtx(DirContext ctx) {
		this.ctx = ctx;
	}
	/**
	 * connects to directory service
	 * connection properties are obtained from container realm
	 * @return DirContext
	 */
	private DirContext connectLDAP()throws LDAPException {
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
			throw new LDAPException(e.getMessage());
		}
		return ctx;
	}
	
	public boolean isUserExists(String login) throws LDAPException {
		if (getCtx()==null)
			setCtx(connectLDAP());
		SearchControls ctls = new SearchControls();		
		String filter = "(&(uid="+login+"))";
		try {

		    NamingEnumeration e = null;
			e = getCtx().search("ou=People,dc=auction,dc=com", filter, ctls);
			if (e.hasMoreElements()) return true;
			
		} catch (NamingException e2) {
			throw new LDAPException(e2.getMessage());
		}
		return false;
	}
	
	public Long getUserPasswd(String login) throws LDAPException {
		if (getCtx()==null)
			setCtx(connectLDAP());
		SearchControls ctls = new SearchControls();		
		String filter = "(&(uid="+login+"))";
		try {
		    NamingEnumeration e = null;
			e = getCtx().search("ou=People,dc=auction,dc=com", filter, ctls);
			if (e.hasMoreElements()){
				  SearchResult searchResult =
               (SearchResult) e.next();
            Attributes attributes =
               searchResult.getAttributes();
            Attribute attr = attributes.get("userPassword");
            System.out.println(attr.get());
            return new Long(attr.get().toString());	
			}
			
		} catch (NamingException e2) {
			throw new LDAPException(e2.getMessage());
		}
		return new Long(0);
	}
	
	public void register(Users entity) throws LDAPException{
		if (getCtx()==null)
			setCtx(connectLDAP());
		if (isUserExists(entity.getLogin()))
			throw new  LDAPException(
					String
							.format(
									Messages
											.getString("UserServiceImpl.ErrorUserWithLoginExists"), entity.getLogin()));
		
		Attributes attrs = new BasicAttributes(true);
		Attribute objclass = new BasicAttribute("objectclass");
		objclass.add("top");		
		objclass.add("inetOrgPerson");
		objclass.add("organizationalPerson");
		objclass.add("person");
		attrs.put(objclass);
		attrs.put(new BasicAttribute("cn", entity.getLogin()));
		attrs.put(new BasicAttribute("sn", entity.getName()));
		attrs.put(new BasicAttribute("userPassword", entity.getPassword()));
			try {
			String path="uid="+entity.getLogin()+",ou=People,dc=auction,dc=com";
			((InitialDirContext)getCtx()).createSubcontext(path, attrs);
			
			ModificationItem[] mods = new ModificationItem[1];
			mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE,
					new BasicAttribute("uniqueMember", path));
			
			if (entity.getRole().equals(Role.ADMIN))
				getCtx().modifyAttributes("cn=admin,ou=Group,dc=auction,dc=com", mods);
			else if (entity.getRole().equals(Role.MODERATOR))
				getCtx().modifyAttributes("cn=moderator,ou=Group,dc=auction,dc=com", mods);
		
				getCtx().modifyAttributes("cn=reguser,ou=Group,dc=auction,dc=com", mods);
		} catch (Exception e) {
			throw new LDAPException(e.getMessage());
		}
	}
		
	public boolean changeLogin(UserSf entity, String oldLogin, Role role) throws LDAPException{
		if (getCtx()==null)
			setCtx(connectLDAP());
		if (entity.getLogin().equals(oldLogin))
			return true;
			String oldUserName="uid="+oldLogin+",ou=People,dc=auction,dc=com";
			String newUserName="uid="+entity.getLogin()+",ou=People,dc=auction,dc=com";
					
			ModificationItem[] mods = new ModificationItem[1];
			mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE,
					new BasicAttribute("uniqueMember", oldUserName));
		try {
			getCtx().modifyAttributes("cn=reguser,ou=Group,dc=auction,dc=com", mods);
			getCtx().rename(oldUserName, newUserName);
			mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE,
					new BasicAttribute("uniqueMember", newUserName));
			getCtx().modifyAttributes("cn=reguser,ou=Group,dc=auction,dc=com", mods);
			
			mods = new ModificationItem[1];
			mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE,
					new BasicAttribute("uniqueMember", oldUserName));
			if (role.equals(Role.ADMIN))getCtx().modifyAttributes("cn=admin,ou=Group,dc=auction,dc=com", mods);
			if (role.equals(Role.MODERATOR))getCtx().modifyAttributes("cn=moderator,ou=Group,dc=auction,dc=com", mods);
		
			if (entity.getRole().equals(Role.ADMIN)){
			mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE,
					new BasicAttribute("uniqueMember", newUserName));
				getCtx().modifyAttributes("cn=admin,ou=Group,dc=auction,dc=com", mods);
			}
			if (entity.getRole().equals(Role.MODERATOR)){
				mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE,
						new BasicAttribute("uniqueMember", newUserName));
					getCtx().modifyAttributes("cn=moderator,ou=Group,dc=auction,dc=com", mods);
			}
		} catch (NamingException e) {
			throw new LDAPException(e.getMessage());
		}
		return true;
	}

	public boolean changeRole(UserSf entity, Role oldRole) throws LDAPException{
		if (getCtx()==null)
			setCtx(connectLDAP());
		if (oldRole==null)
			return false;
		if (entity.getRole().equals(oldRole))
			return true;
			String userName="uid="+entity.getLogin()+",ou=People,dc=auction,dc=com";
			
			ModificationItem[] mods = new ModificationItem[1];
			mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE,
					new BasicAttribute("uniqueMember", userName));
			
		try {
			getCtx().modifyAttributes("cn=reguser,ou=Group,dc=auction,dc=com", mods);
			mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE,
			new BasicAttribute("uniqueMember", userName));
			getCtx().modifyAttributes("cn=reguser,ou=Group,dc=auction,dc=com", mods);
			mods = new ModificationItem[1];
			mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE,
			new BasicAttribute("uniqueMember", userName));
			if (oldRole==Role.ADMIN)getCtx().modifyAttributes("cn=admin,ou=Group,dc=auction,dc=com", mods);
			if (oldRole==Role.MODERATOR)getCtx().modifyAttributes("cn=moderator,ou=Group,dc=auction,dc=com", mods);
			mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE,
			new BasicAttribute("uniqueMember", userName));
			if (entity.getRole().equals(Role.ADMIN))getCtx().modifyAttributes("cn=admin,ou=Group,dc=auction,dc=com", mods);
			if (entity.getRole().equals(Role.MODERATOR))getCtx().modifyAttributes("cn=moderator,ou=Group,dc=auction,dc=com", mods);
		} catch (NamingException e) {
			throw new LDAPException(e.getMessage());
		}
		return true;
	}
	
	public boolean changePassword(Users entity) throws LDAPException, IllegalArgumentException{
		if (getCtx()==null)
			setCtx(connectLDAP());
		ModificationItem[] mods = new ModificationItem[1];
		mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
				new BasicAttribute("userPassword", entity.getPassword()));
		try {
				getCtx().modifyAttributes("uid=" + entity.getLogin()
						+ ",ou=People,dc=auction,dc=com", mods);
		} catch (NamingException e) {
			throw new LDAPException(e.getMessage());
		}
		return true;
	}
	
	public boolean delUser(Users entity) throws LDAPException{
		if (getCtx()==null)
			setCtx(connectLDAP());
		String path="uid="+entity.getLogin()+",ou=People,dc=auction,dc=com";
		ModificationItem[] mods = new ModificationItem[1];
		mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE,
				new BasicAttribute("uniqueMember", path));
		try {
			((InitialDirContext) getCtx()).unbind(path);
			if (entity.getRole().equals(Role.ADMIN))
				getCtx().modifyAttributes("cn=admin,ou=Group,dc=auction,dc=com", mods);
			else if (entity.getRole().equals(Role.MODERATOR))
				getCtx().modifyAttributes("cn=moderator,ou=Group,dc=auction,dc=com", mods);
			getCtx().modifyAttributes("cn=reguser,ou=Group,dc=auction,dc=com", mods);
		} catch (NamingException e) {
			throw new LDAPException(e.getMessage());
		}
		return true;
	}
	
}
