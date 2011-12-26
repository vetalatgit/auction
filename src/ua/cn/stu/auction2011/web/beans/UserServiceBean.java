package ua.cn.stu.auction2011.web.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import ua.cn.stu.auction2011.domain.Role;
import ua.cn.stu.auction2011.domain.Address;
import ua.cn.stu.auction2011.domain.UserSf;
import ua.cn.stu.auction2011.domain.Users;
import ua.cn.stu.auction2011.exceptions.GeneralServiceException;
import ua.cn.stu.auction2011.services.IUserService;
import ua.cn.stu.auction2011.services.IAddresService;

@ManagedBean(name="usersb")
@SessionScoped
public class UserServiceBean extends BeanSuper {
	
	private RenderLists rendlist;
	
		/** */
	private UserSf requser;
	private Address reqaddr;
	private static final long serialVersionUID = 1L;
	private String findlogin;
	private String findmail;
	private boolean showadrusr;
	private Address usadress;
	private String rpassword;
	private Role findrole;
	/** Page for the data scroller */
	private int userPage = 1;

	/** Collection of users */
	private List<UserSf> users;
	
	/** New Usersentity */
	protected Users newUser= new Users(); 

	@EJB
	private IUserService userService;
	@EJB
	private IAddresService adService;
	/**
	 * @return the usersPage
	 */
	public int getUserPage() {
		return userPage;
	}

	public Address getUsadress() {
		return usadress;
	}

	public void setUsadress(Address usadress) {
		this.usadress = usadress;
	}

	public String getRpassword() {
		return rpassword;
	}

	public void setRpassword(String rpassword) {
		if(!newUser.getPassword().equals(rpassword)){FacesMessage messageText = new FacesMessage("Пароли не совпадают, введите правильный пароль!");
				FacesContext.getCurrentInstance().addMessage(null, messageText);
				return;
			}
		this.rpassword = rpassword;
	}

	public RenderLists getRendlist() {
		return rendlist;
	}
	public String showAdress(Long idus){
		try {
			usadress=adService.getAddressByUser(idus);
			if(usadress==null){usadress=new Address();usadress=adService.setAddressToUser(usadress,idus);}
			showadrusr=true;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (GeneralServiceException e) {
			e.printStackTrace();
		}
		
		return "/pages/admin/listUsers";
	}
	
	public Address getAddresOfUser(String logn){
		try {
			return adService.getAddressByUser(userService.getUserByLogin(logn).getId());
		} catch (IllegalArgumentException e) {
			return null;
		} catch (GeneralServiceException e) {
			return null;
		}
	}
	
	public String reqUser(){
		 Map<String,String> params = 
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		try {
			requser=userService.getEntityById(new Long(params.get("p_userid"))).getUsersSafe();
			reqaddr=adService.getAddressByUser(requser.getId());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (GeneralServiceException e) {
			e.printStackTrace();
		}
		return "/pages/User";
	}
	
	public String chngUserAdress(){
		if(usadress!=null)
		try {
			adService.setAddressToUser(usadress,usadress.getUser().getId());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (GeneralServiceException e) {
			e.printStackTrace();
		}
		usadress=null;
		return "/pages/admin/listUsers";
		
	}
	/**
	 * @param usersPage
	 *            the usersPage to set
	 */
	public void setUserPage(int userPage) {
		this.userPage = userPage;
	}
	
	public boolean isShowadrusr() {
		return showadrusr;
	}

	public void setShowadrusr(boolean showadrusr) {
		this.showadrusr = showadrusr;
	}	
	/**
	 * @return the users
	 */
	public List<UserSf> getUsers() {
		getRendEntity();
		if ((rendlist.getRenderList()==null)||rendlist.getScrolPage()!=2){rendlist.setRenderList(new HashMap<Long, Boolean>());
		for (UserSf u : users)rendlist.getRenderList().put(u.getId(),false);}
		return this.users;
	}
	
	public List<UserSf> getRendEntity(){
		this.users.clear();
		List<UserSf> findusers = new ArrayList<UserSf>();
		if(findrole==null && findlogin==null && findmail==null){List<Users> us=userService.getEntites(rendlist.getFirstEntity(), rendlist.getBatchSize());
		for (Users usr : us)this.users.add(usr.getUsersSafe());			
		}else{
			try {
				if(findlogin!=null)findusers.add(userService.getUserByLogin(findlogin));
				if(findmail!=null)findusers.add(userService.getUserByMail(findmail));
				if(findrole!=null)findusers = userService.getUsersByRole(findrole);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (GeneralServiceException e) {
				e.printStackTrace();
			}
		
		for (int i=rendlist.getFirstEntity();(i<rendlist.getBatchSize())&&(i<findusers.size());i++){
				this.users.add(findusers.get(i));
			}
		}
		findlogin=null;
		findmail=null;
		findrole=null;
		return findusers; 
	}
	
	/**
	 * @param users
	 *            the users to set
	 */
	public void setUsers(List<UserSf> users) {
		this.users = users;
	}
	  
	/**
	 * @return the newUser
	 */
	public Users getNewUser() {
		return newUser;
	}

	/**
	 * @param newUser
	 *            the newUsersto set
	 */
	public void setNewUser(Users newUser) {
		this.newUser= newUser;
	}

	/**
	 * @return list of Usersroles
	 */
	public Map<String,String> getRoles() {
		 Map<String,String> roles = new HashMap<String,String>();
		Role[] rols = Role.values();
		for (int i = 0; i < rols.length; i++)
		if(rols[i]==Role.MODERATOR){
			roles.put("MODER","MODER");
		}else roles.put(rols[i].toString(),rols[i].toString());
		return roles;
	}
	
	/**
	 * Method that will be called, after bean was initialized
	 */
	@PostConstruct
	public void postConstruct() {
		users = new ArrayList<UserSf>();
		rendlist=new RenderLists();
		findlogin=null;
		findmail=null;
		findrole=null;
		rendlist.setCount(getCount());
		log.debug("Loading 5 users"); //$NON-NLS-1$
		newUser= new Users();
		getUsers();
	}
	
	public String editUser(){
		int i=0;
		try {

			for (i=0;i<users.size();i++){
				if(rendlist.getRenderList().get(users.get(i).getId()).equals(true) ){
					userService.updateUser(users.get(i));
					rendlist.chRendComp(users.get(i).getId());
				}
			}
		} catch (GeneralServiceException e) {
			//	addGlobalErrorMessage(e.getLocalizedMessage(), e);
		}
		rendlist.setScrolPage(0);
		return "/pages/admin/listUsers";
	}
	
	public int getCount() {      
        int count = (int)userService.getAllEntitiesCount();
        rendlist.setCount(count);
        return count;     
    }

	
	/**
	 * Action that is performed, when adding new user.
	 * 
	 * @return <code>null</code> in all cases, so there will be no navigation to
	 *         other pages
	 */
	public String doAddUser() {
		if(!FacesContext.getCurrentInstance().getExternalContext().isUserInRole("admin")){
			newUser.setRole(Role.USER);
			newUser.setRating(new Long(5));}
		try {
			newUser = userService.regUser(newUser);
			usadress=new Address("country", "code", "city", "addr");
			adService.setAddressToUser(usadress, newUser.getId());
			//renderList.put(newUser.getId(), false);
		} catch (GeneralServiceException e) {
		//	addGlobalErrorMessage(e.getLocalizedMessage(), e);
			return null;
		}
		//addGlobalINFOMessage(Messages.getString("admin.User") + newUser.getLogin() //$NON-NLS-1$
		//		+ Messages.getString("admin.SuccessAdd"), null); //$NON-NLS-1$
		rendlist.setCount(getCount());
		newUser= new Users();
		//getUsers();
		return "/pages/admin/listUsers";
	}

	public void userExistVal(AjaxBehaviorEvent event){
		
		FacesMessage messageText = new FacesMessage("Логин "+newUser.getLogin()+" уже используется!");
		
		/*if (userService.getUserByLogin(addinguser) == null) FacesContext.getCurrentInstance().addMessage(null, messageText);
		FacesContext.getCurrentInstance().addMessage("uploadForm", new FacesMessage(
	            FacesMessage.SEVERITY_INFO, "File upload succeed!", null));*/
}
	
	
	

	/**
	 * Action that is performed, when deleting user.
	 * <p>
	 * Getting the Usersto delete from row it was clicked.
	 * </p>
	 * 
	 * @return <code>null</code> in all cases, so there will be no navigation to
	 *         other pages
	 */
	public void incrRating(String login){
		try {
			userService.incrUserRating(userService.getUserByLogin(login).getId());
			userService.decrUserRating(userService.getUserByLogin(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()).getId());	
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (GeneralServiceException e) {
			e.printStackTrace();
		}
	}
	
	public void decrRating(String login){
		try {
			userService.decrUserRating(userService.getUserByLogin(login).getId());
			userService.decrUserRating(userService.getUserByLogin(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()).getId());	
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (GeneralServiceException e) {
			e.printStackTrace();
		}
	}
	
	public String deluser(Long id) {
		if (id != null) {

			try {

				userService.delEntity(id);
				//renderList.remove(id);
				// After successful deletion, updating again the users
				// list
				//getUsers();
				
			//	addGlobalINFOMessage(Messages.getString("admin.SuccessDelete"), null); //$NON-NLS-1$

			} catch (GeneralServiceException e) {
			//	addGlobalErrorMessage(e.getLocalizedMessage(), e);
			}
		} else {
			//addGlobalErrorMessage(Messages.getString("admin.ErrorGetUserFromTableForDelete"), null);
		}
	//	getUsers();	
		rendlist.setCount(getCount());
		return "/pages/admin/listUsers";
	}

	/**
	 * @return the reqaddr
	 */
	public Address getReqaddr() {
		return reqaddr;
	}

	/**
	 * @param reqaddr the reqaddr to set
	 */
	public void setReqaddr(Address reqaddr) {
		this.reqaddr = reqaddr;
	}

	/**
	 * @return the requser
	 */
	public UserSf getRequser() {
		return requser;
	}

	/**
	 * @param requser the requser to set
	 */
	public void setRequser(UserSf requser) {
		this.requser = requser;
	} 
}
