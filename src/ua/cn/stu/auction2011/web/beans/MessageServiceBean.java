package ua.cn.stu.auction2011.web.beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.validator.ValidatorException;

import ua.cn.stu.auction2011.domain.Message;
import ua.cn.stu.auction2011.domain.Users;
import ua.cn.stu.auction2011.exceptions.GeneralServiceException;
import ua.cn.stu.auction2011.services.IMessageServices;
import ua.cn.stu.auction2011.services.IUserService;

@ManagedBean(name="messervb")
@SessionScoped
public class MessageServiceBean extends BeanSuper {

	private RenderLists rendlist;
	private Long idAuthor;
	/** */
	private RenderLists dellist=null;
	private static final long serialVersionUID = 1L;
	private Long reqUser=null;
	private String addinguser=null;
	private Long addinglott=null;
	private String finduserlogin=null;
	private String fndUser=null;
	private String fndLott=null;
	private Boolean isread=null;
	private Calendar date=null;
	private String theme=null;
	private String author=null;
	private boolean finds=false;
	private Message shmes=null;
	private boolean showdetail=false;
	/** Collection of message */
	private List<Message> messages;
	/** New message */
	protected Message newMessage= new Message(); 

	@EJB
	private IMessageServices mesService;
	@EJB
	private IUserService userService;
	
	public List<Message> getFindMessage(boolean iscoments) {
		this.messages.clear();
		rendlist.setRenderList(null);
		List<Message> fndmesgs=null;
		List<Message> fndmesgsto=null;
		try {
			if(!finds){
				if(iscoments){
					fndmesgs= mesService.getMessage(null, fndLott, null, author, null, null,false);					
				}else{
					fndmesgs= mesService.getMessage(null, null, null, FacesContext.getCurrentInstance().getExternalContext().getRemoteUser(), null, null,true);
					fndmesgsto=mesService.getMessage(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser(), null, null,null, null, null,true);
					for(Message m: fndmesgsto)if(!fndmesgs.contains(m))fndmesgs.add(m);
				}
			}else{	
				fndmesgs = mesService.getMessage(finduserlogin, fndLott, date, author, theme, isread,true);
				rendlist.setCount(messages.size());
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (GeneralServiceException e) {
			e.printStackTrace();
		}
		for (int i=rendlist.getFirstEntity();(i<rendlist.getBatchSize())&&(i<fndmesgs.size());i++){
				this.messages.add(fndmesgs.get(i));
		}
		/*fndUser=null;
		date=null;
		type=null;
		name=null;
		price=0;*/
		return this.messages;
	}
	
	public String getFinduserlogin() {
		return finduserlogin;
	}

	public void setFinduserlogin(String finduserlogin) throws IllegalArgumentException, GeneralServiceException {
		if(finduserlogin=="all")fndUser=null;else fndUser=finduserlogin; 
		this.finduserlogin = finduserlogin;
	}

	public Long getAddinglott() {
		return addinglott;
	}

	public void setAddinglott(Long addinglott) {
		this.addinglott = addinglott;
	}

	public String getFndLott() {
		return fndLott;
	}

	public void setFndLott(String fndLott) {
		this.fndLott = fndLott;
	}

	public String getAddinguser() {
		return addinguser;
	}

	public void setAddinguser(String addinguser) {
		this.addinguser = addinguser;
	}
	
	public RenderLists getRendlist() {
		return rendlist;
	}

	public RenderLists getDellist() {
		return dellist;
	}

	public void setDellist(RenderLists dellist) {
		this.dellist = dellist;
	}

	public void setRendlist(RenderLists rendlist) {
		this.rendlist = rendlist;
	}

	public Message getShmes() {
		return shmes;
	}
	
	public List<Message> getComents(String auth, String lott) {
		author=auth;
		fndLott=lott;
		getFindMessage(true);
		if ((rendlist.getRenderList()==null)||rendlist.getScrolPage()!=2){rendlist.setRenderList(new HashMap<Long, Boolean>());
		for (Message l: messages)rendlist.getRenderList().put(l.getId(),false);}
		//setDelList();
		return this.messages;
	}

	/**
	 * @return the messages
	 */
	public List<Message> getMessages() {
	getFindMessage(false);
	if ((rendlist.getRenderList()==null)||rendlist.getScrolPage()!=2){rendlist.setRenderList(new HashMap<Long, Boolean>());
	for (Message l: messages)rendlist.getRenderList().put(l.getId(),false);}
	setDelList();
	return this.messages;
	}
	
	/**
	 * @param users
	 *            the users to set
	 */
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	/**
	 * @return the newMessage
	 */
	public Message getNewMessage() {
		return newMessage;
	}

		/**
	 * @param newUser
	 *            the newUsersto set
	 */
	public void setNewMessage(Message newMessage) {
		this.newMessage= newMessage;
	}
	
	public void showMessageDetail(Long idmessage){
		try {
			shmes=mesService.getEntityById(idmessage);
			mesService.readdMessage(idmessage);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (GeneralServiceException e) {
			e.printStackTrace();
		}
		
		showdetail=true;
	}
	
	public boolean isShowdetail() {
		return showdetail;
	}

	public void setShowdetail(boolean showdetail) {
		this.showdetail = showdetail;
	}

	/**
	 * @return list of Message
	 */
	public Map<String,String> getThems(Long usid) {
		 Map<String,String> types = new HashMap<String,String>();
		List<String> typs=null;
		try {
			typs = mesService.getAllThems(usid);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (GeneralServiceException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < typs.size(); i++)
		types.put(typs.get(i),typs.get(i));
		return types;
	}
	
	public Long getReqUser() {
		return reqUser;
	}
	
	public Map<String,String> getUsLogin(){
		Map<String,String> usLogin = new HashMap<String,String>();
		List<Users> users=null;
		users = userService.getAllEntites();
		usLogin.put("all","all");
		for (int i = 0; i < users.size(); i++)
		usLogin.put(users.get(i).getLogin(),users.get(i).getLogin());
		return usLogin;
	}
	
	public String getUsrByMessage(Long idmessage){
		return mesService.getEntityById(idmessage).getUser().getLogin();
	}
	
	public void setReqUser(Long reqUser) {
		this.reqUser = reqUser;
	}
	/**
	 * Method that will be called, after bean was initialized
	 */
	@PostConstruct
	public void postConstruct() {
		reqUser=null;
		addinguser=null;
		fndUser=null;
		date=null;
		theme=null;
		author=null;
		showdetail=false;
		messages=new ArrayList<Message>();
		dellist=new RenderLists();
		getUsLogin();
		newMessage= new Message();
		rendlist=new RenderLists();
		log.debug("Loading 5 users"); //$NON-NLS-1$
		newMessage.setDate(Calendar.getInstance());
		rendlist.setCount(getCount());
		getMessages();
	}
	
	public void editMessage(Long lottId,Long userId){
		int i=0;
		try {

			for (i=0;i<messages.size();i++){
				if(rendlist.getRenderList().get(messages.get(i).getId()).equals(true) ){
					mesService.sendMessage(messages.get(i), lottId, userId, idAuthor);
					rendlist.chRendComp(messages.get(i).getId());
				}
			}
		} catch (GeneralServiceException e) {
			//	addGlobalErrorMessage(e.getLocalizedMessage(), e);
		}
		rendlist.setScrolPage(0);
		
	}
	
	public int getCount() {      
        int count = (int)mesService.getAllEntitiesCount();
        rendlist.setCount(count);
        return count;        
    }

	
	/**
	 * Action that is performed, when adding new user.
	 * 
	 * @return <code>null</code> in all cases, so there will be no navigation to
	 *         other pages
	 */
	public void doAddMessage() {
		try {
		
			newMessage=mesService.sendMessage(newMessage,addinglott,userService.getUserByLogin(addinguser).getId(),
			userService.getUserByLogin(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()).getId());
		} catch (GeneralServiceException e) {
			return;
		}
		//addGlobalINFOMessage(Messages.getString("admin.User") + newUser.getLogin() //$NON-NLS-1$
		//		+ Messages.getString("admin.SuccessAdd"), null); //$NON-NLS-1$
		rendlist.setCount(getCount());
		newMessage= new Message();
		newMessage.setDate(Calendar.getInstance());
		
	}
	
	public void doSendComent(Long idlot) {
		try {
			newMessage.setThem(idlot.toString());
			newMessage.setRead(false);
			newMessage.setDate(Calendar.getInstance());
			newMessage=mesService.sendMessage(newMessage,idlot,null,
			userService.getUserByLogin(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()).getId());
		} catch (GeneralServiceException e) {
			return ;
		}
		//addGlobalINFOMessage(Messages.getString("admin.User") + newUser.getLogin() //$NON-NLS-1$
		//		+ Messages.getString("admin.SuccessAdd"), null); //$NON-NLS-1$
		rendlist.setCount(getCount());
		newMessage= new Message();
		newMessage.setDate(Calendar.getInstance());
	}
	
	public void doSendMessage() {
		try {
			if (userService.getUserByLogin(addinguser) == null) return;
			newMessage.setRead(false);
			newMessage.setDate(Calendar.getInstance());
			newMessage=mesService.sendMessage(newMessage,null,userService.getUserByLogin(addinguser).getId(),
			userService.getUserByLogin(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()).getId());
		} catch (GeneralServiceException e) {
			return;
		}
		//addGlobalINFOMessage(Messages.getString("admin.User") + newUser.getLogin() //$NON-NLS-1$
		//		+ Messages.getString("admin.SuccessAdd"), null); //$NON-NLS-1$
		rendlist.setCount(getCount());
		newMessage= new Message();
		newMessage.setDate(Calendar.getInstance());
	}
	/**
	 *  
	 * @param event
	 */
	public void userNotNullVal(AjaxBehaviorEvent event){
		
		FacesMessage messageText = new FacesMessage("User "+addinguser+" does not exist!");
		
		if (userService.getUserByLogin(addinguser) == null) FacesContext.getCurrentInstance().addMessage(null, messageText);
		 
}
	
	public void userSetValidator(FacesContext context, UIComponent component, Object value)
	        throws ValidatorException {
		FacesMessage messageText = new FacesMessage("User "+value+" does not exist!");
		try {
				if (userService.getUserByLogin(value.toString()) == null) 
				{  
					FacesContext.getCurrentInstance().addMessage(null, messageText);
					return;
				}
			} catch (Exception e) {
				return;
			} 
}
	
	public void setDelList(){
	dellist.setRenderList(new HashMap<Long, Boolean>());
	for (Message l: messages)dellist.getRenderList().put(l.getId(),false);
	}
	
	public void chengDelList(Long id){
		rendlist.chRendComp(id); 
	}
	
	public String executeDelete(){
		for (int i=0;i<messages.size();i++){
			if(rendlist.getRenderList().get(messages.get(i).getId()).equals(true) ){
				dellist.getRenderList().remove(messages.get(i).getId());
				delMessage(messages.get(i).getId());
			}
		}
		return "/pages/user/userMess";
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
	 
	public void delMessage(Long id) {
		if (id != null) {

			try {
				mesService.delEntity(id);
			//	addGlobalINFOMessage(Messages.getString("admin.SuccessDelete"), null); //$NON-NLS-1$

			} catch (GeneralServiceException e) {
			//	addGlobalErrorMessage(e.getLocalizedMessage(), e);
			}
		} else {
			//addGlobalErrorMessage(Messages.getString("admin.ErrorGetUserFromTableForDelete"), null);
		}
		rendlist.setCount(getCount());
	} 
	
	public String getFndUser() {
		return fndUser;
	}

	public void setFndUser(String fndUser) {
		this.fndUser = fndUser;
	}

	public Calendar getDate() {
		if(date==null)return Calendar.getInstance();else
		return date;
	}

	public void setDate(Calendar date) {
		if(date==null)date=Calendar.getInstance();else
		this.date = date;
	}

	public String getTheme() {
		if(theme==null)return "";else
		return theme;
	}

	public void setTheme(String theme) {
		if(theme=="")this.theme=null;else
		this.theme = theme;
	}

	public String getAuthor() {
		if(author==null)return "";else
		return author;
	}

	public void setAuthor(String author) {
		if(author=="")this.author=null;else
		this.author = author;
	}

	
	}