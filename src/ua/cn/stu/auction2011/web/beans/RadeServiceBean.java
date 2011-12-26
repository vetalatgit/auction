package ua.cn.stu.auction2011.web.beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import ua.cn.stu.auction2011.domain.Rade;
import ua.cn.stu.auction2011.domain.Lott;
import ua.cn.stu.auction2011.domain.Role;
import ua.cn.stu.auction2011.domain.UserSf;
import ua.cn.stu.auction2011.domain.Users;
import ua.cn.stu.auction2011.exceptions.GeneralServiceException;
import ua.cn.stu.auction2011.exceptions.LDAPException;
import ua.cn.stu.auction2011.services.ILottService;
import ua.cn.stu.auction2011.services.IRadeService;
import ua.cn.stu.auction2011.services.IUserService;

@ManagedBean(name="radeb")
@SessionScoped
public class RadeServiceBean extends BeanSuper {

	private RenderLists rendlist;
		/** */
	
	private static final long serialVersionUID = 1L;
	private String addinguser=null;
	private String addinglot=null;
	private String fnduser=null;
	private String fndlott=null;
	private boolean dofnd=true;
	private Calendar date=null;
	private String sale=null;
	private String namel=null;
	private Long price=(long) 0;
	private boolean showlotdetail=false;
	/** Collection of Rade */
	private List<Rade> rads=new ArrayList<Rade>();;
	/** New Rade */
	protected Rade newRade= new Rade(); 

	@EJB
	private ILottService lotService;
	@EJB
	private IUserService userService;
	@EJB
	private IRadeService rdService;

	List<UserSf> findusers = new ArrayList<UserSf>();
	
	public List<Rade> getFindRads() {
		this.rads.clear();
		List<Rade> findrads = null;
		if(!dofnd){ this.rads=rdService.getEntites(rendlist.getFirstEntity(), rendlist.getBatchSize());
		}else{
		try {
			findrads = rdService.getRads(date, fndlott, fnduser, price, sale);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (GeneralServiceException e) {
				e.printStackTrace();
			}
		rendlist.setCount(findrads.size());
		for (int i=rendlist.getFirstEntity();(i<rendlist.getBatchSize())&&(i<findrads.size());i++){
				this.rads.add(findrads.get(i));
			}
		}
		return findrads;
	}
	
	public String getAddinglot() {
		return addinglot;
	}

	public void findAll(){
		dofnd=false;
	}
	
	public boolean isDofnd() {
		return dofnd;
	}

	public void setDofnd(boolean dofnd) {
		this.dofnd = dofnd;
	}

	public void setAddinglot(String addinglot) {
		this.addinglot = addinglot;
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

	public void setRendlist(RenderLists rendlist) {
		this.rendlist = rendlist;
	}

	/**
	 * @return the rads
	 */
	public List<Rade> getRads() {
	getFindRads();
	if ((rendlist.getRenderList()==null)||rendlist.getScrolPage()!=2){rendlist.setRenderList(new HashMap<Long, Boolean>());
	for (Rade l: rads)rendlist.getRenderList().put(l.getId(),false);}
	return this.rads;
	}
	
	/**
	 * prepare to find
	 *  
	 */
	public String doFindRat(){
		dofnd=true;
		rendlist=new RenderLists();
		return "/pages/admin/listRads";
	}
	
	/**
	 * @param users
	 *            the users to set
	 */
	public void setRads(List<Rade> rads) {
		this.rads = rads;
	}

	/**
	 * @return the newLot
	 */
	public Rade getNewRade() {
		return newRade;
	}

		/**
	 * @param newUser
	 *            the newUsersto set
	 */
	public void setNewRade(Rade newRade) {
		this.newRade= newRade;
	}

	public boolean isShowlotdetail() {
		return showlotdetail;
	}

	public void setShowlotdetail(boolean showlotdetail) {
		this.showlotdetail = showlotdetail;
	}

	/**
	 * @return list of radsales
	 */
	public Map<String,String> getSale() {
		Map<String,String> sales = new HashMap<String,String>();
		sales.put("да","да");
		sales.put("нет","нет");
		sales.put("все","все");
		return sales;
	}
	
	public Map<String,String> getUsLogin(){
		Map<String,String> usLogin = new HashMap<String,String>();
		List<Users> users=null;
		users = userService.getAllEntites();
		for (int i = 0; i < users.size(); i++)
		usLogin.put(users.get(i).getLogin(),users.get(i).getLogin());
		return usLogin;
	}

	public Map<String,String> getLotNames(){
		Map<String,String> lotnam = new HashMap<String,String>();
		List<Lott> lots=null;
		lots = lotService.getAllEntites();
		for (int i = 0; i < lots.size(); i++)
		lotnam.put(lots.get(i).getName(),lots.get(i).getName());
		return lotnam;
	}

	public String getUsrByRade(Long idrade){
		return rdService.getEntityById(idrade).getUser().getLogin();
	}
	
	public String getLotByRade(Long idrade){
		return rdService.getEntityById(idrade).getLott().getName();
	}
	
	/**
	 * Method that will be called, after bean was initialized
	 */
	@PostConstruct
	public void postConstruct() {
		addinguser=null;
		fnduser=null;
		date=null;
		namel=null;
		dofnd=false;
		price=(long) 0;
		showlotdetail=false;
		rads=new ArrayList<Rade>();;
		getUsLogin();
		newRade= new Rade();
		rendlist=new RenderLists();
		log.debug("Loading 5 users"); //$NON-NLS-1$
		newRade.setTimerate(Calendar.getInstance());
		rendlist.setCount(getCount());
		getRads();
	}
	
	public String editRade(){
		int i=0;
		try {
			for (i=0;i<rads.size();i++){
				if(rendlist.getRenderList().get(rads.get(i).getId()).equals(true) ){
					rads.get(i).setUser(userService.getEntityById(userService.getUserByLogin(addinguser).getId()));
					rads.get(i).setLott(lotService.getLott(null, null,null, addinglot,0).get(0));
					rdService.save(rads.get(i));
					rendlist.chRendComp(rads.get(i).getId());
				}
			}
		} catch (GeneralServiceException e) {
			//	addGlobalErrorMessage(e.getLocalizedMessage(), e);
		}
		rendlist.setScrolPage(0);
		return "/pages/admin/listRads";
	}
	
	public int getCount() {      
        int count = (int)rdService.getAllEntitiesCount();
        rendlist.setCount(count);
        return count;        
    }

	
	/**
	 * Action that is performed, when adding new user.
	 * 
	 * @return <code>null</code> in all cases, so there will be no navigation to
	 *         other pages
	 */
	public String doAddRade() {
		try {
			newRade.setUser(userService.getEntityById(userService.getUserByLogin(addinguser).getId()));
			newRade.setLott(lotService.getLott(null, null,null, addinglot,0).get(0));
			rdService.save(newRade);
		} catch (GeneralServiceException e) {
			return null;
		}
		//addGlobalINFOMessage(Messages.getString("admin.User") + newUser.getLogin() //$NON-NLS-1$
		//		+ Messages.getString("admin.SuccessAdd"), null); //$NON-NLS-1$
		rendlist.setCount(getCount());
		newRade= new Rade();
		newRade.setTimerate(Calendar.getInstance());
		return "/pages/admin/listRads";
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
	 
	public String delRade(Long id) {
		if (id != null) {

			try {
				rdService.delEntity(id);
			//	addGlobalINFOMessage(Messages.getString("admin.SuccessDelete"), null); //$NON-NLS-1$

			} catch (GeneralServiceException e) {
			//	addGlobalErrorMessage(e.getLocalizedMessage(), e);
			}
		} else {
			//addGlobalErrorMessage(Messages.getString("admin.ErrorGetUserFromTableForDelete"), null);
		}
		rendlist.setCount(getCount());
		return "/pages/admin/listRads";
	} 

	public Rade getCurentrate(Long idlot){
		try {
			return rdService.getRadsByLU(idlot, null).get(0);
		} catch (IllegalArgumentException e) {
			return null;
		} catch (GeneralServiceException e) {
			return null;
		}
	}
	
	public String getFndUser() {
		if(fnduser==null)return "";else
			return fnduser;
	}

	public void setFndUser(String fnduser) {
		if(fnduser=="")this.fnduser=null;else
		this.fnduser = fnduser;
	}


	public Calendar getDate() {
		if(date==null)return Calendar.getInstance();else
		return date;
	}

	public void setDate(Calendar date) {
		if(date==null)date=Calendar.getInstance();else
		this.date = date;
	}

	public void setSale(String sale) {
		if(sale=="все")this.sale=null;else
			if(sale=="да")this.sale="true";else
				if(sale=="нет")this.sale="false";
	}

	public String getNamel() {
		if(namel==null)return "";else
		return namel;
	}

	public void setNamel(String namel) {
		if(namel=="")this.namel=null;else
		this.namel = namel;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getFndlott() {
		if(fndlott==null)return "";else
		return fndlott;
	}

	public void setFndlott(String fndlott) {
		if(fndlott=="")this.fndlott=null;else
		this.fndlott = fndlott;
	}

}
