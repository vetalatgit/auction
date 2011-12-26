package ua.cn.stu.auction2011.web.beans;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import ua.cn.stu.auction2011.domain.Address;
import ua.cn.stu.auction2011.domain.UserSf;
import ua.cn.stu.auction2011.exceptions.GeneralServiceException;
import ua.cn.stu.auction2011.services.IAddresService;
import ua.cn.stu.auction2011.services.IMessageServices;
import ua.cn.stu.auction2011.services.IUserService;

/**
 * Bean for viewing user page
 * 
 */
@ManagedBean(name="uspageb")
@SessionScoped
public class UsersPageBean  extends BeanSuper{
		/**
		 * default serial version UID
		 */
		private static final long serialVersionUID = 1L;
		
		/** User service to access users */
		@EJB
		private IUserService userService;
		
		@EJB
		private IMessageServices mesService;
		
		@EJB
		private IAddresService adService;
		/**
		 * User that is viewed right now
		 */
		private UserSf user;	
		/**
		 * @return the user
		 */
		public UserSf getUser() {
			return user;
		}
		
		public int getMessageCount() {
			try {
				return mesService.getMessage(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser(), null, null, null, null,false,true).size();
			} catch (IllegalArgumentException e) {
				return 0;
			} catch (GeneralServiceException e) {
				return 0;
			}
		}
		/**
		 * @param Usaddr
		 *           
		 */
		public Address getAddress() {
			Address adr= null;
			try {
				adr=adService.getAddressByUser(user.getId());
				if( adr == null) return adService.setAddressToUser(new Address(), user.getId());
				return adr;
			} catch (IllegalArgumentException e) {
				return null;
			} catch (GeneralServiceException e) {
				return null;
			}
		}
		
		/**
		 * @param user
		 *            the user to set
		 */
		public void setUser(UserSf user) {
			this.user = user;
		}
		/**
		 * Loading info about selected user
		 */
		@PostConstruct
		public void postConstruct() {
			user = new UserSf();
			loadUser();
		}

		public String logOut() throws IOException{
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			FacesContext.getCurrentInstance().getExternalContext().redirect("/auction2011/");
			return "/pages/hello";
		}
		

		/**
		 * Loading info about selected user
		 */
		
		private void loadUser() {
			String userl = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
			if (userl==null)throw new IllegalArgumentException("user can`t be null");
			else {
				try {
					user = userService.getUserByLogin(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
			

		






	