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
import javax.faces.context.FacesContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;


import ua.cn.stu.auction2011.domain.LotInfo;
import ua.cn.stu.auction2011.domain.Lott;
import ua.cn.stu.auction2011.domain.Users;
import ua.cn.stu.auction2011.exceptions.GeneralServiceException;
import ua.cn.stu.auction2011.services.ILottService;
import ua.cn.stu.auction2011.services.ILottinfoService;
import ua.cn.stu.auction2011.services.IUserService;

@ManagedBean(name="lotservb")
@SessionScoped
public class LotServiceBean extends BeanSuper {

	private RenderLists rendlist;
	/** */
	private static final long serialVersionUID = 1L;
	
	private Lott reqlott;
	
    private UploadedFile uploadedFile;
    private String fileName;
	private List<LotInfo> reqlottinfo;
	private boolean dofnd=true; 
	private String addinguser=null;
	private String fndUser=null;
	private Calendar date=null;
	private String type=null;
	private String name=null;
	private int price=0;
	private Long lottoshow=null;
	private List<LotInfo> lotdetail=null;
	private boolean showlotdetail=false;
	/** Collection of Lott */
	private List<Lott> lots;
	/** New Lott */
	protected Lott newLott= new Lott(); 

	@EJB
	private ILottService lotService;
	@EJB
	private IUserService userService;
	@EJB
	private ILottinfoService liService;
	
	
	
	public List<Lott> getFindLots() {
		this.lots.clear();
		List<Lott> fndl = null;
		if(!dofnd){ lots=lotService.getEntites(rendlist.getFirstEntity(), rendlist.getBatchSize());
		}else{
		try {
			fndl = lotService.getLott(fndUser, date, type, name, price);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (GeneralServiceException e) {
				e.printStackTrace();
			}
		rendlist.setCount(fndl.size());
		for (int i=rendlist.getFirstEntity();(i<rendlist.getBatchSize())&&(i<fndl.size());i++){
				this.lots.add(fndl.get(i));
			}
		}
		return this.lots;
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

	public List<LotInfo> getLotdetail() {
		return lotdetail;
	}

	public void setLotdetail(List<LotInfo> lotDetail) {
		this.lotdetail = lotDetail;
	}
	
	public Lott getReqlott() {
		return reqlott;
	}

	public void setReqlott(Lott reqlott) {
		this.reqlott = reqlott;
	}

	public List<LotInfo> getReqlottinfo() {
		return reqlottinfo;
	}

	public void setReqlottinfo(List<LotInfo> reqlottinfo) {
		this.reqlottinfo = reqlottinfo;
	}

	/**
	 * @return the lots
	 */
	public List<Lott> getLots() {
	getFindLots();
	if ((rendlist.getRenderList()==null)||rendlist.getScrolPage()!=2){rendlist.setRenderList(new HashMap<Long, Boolean>());
	for (Lott l: lots)rendlist.getRenderList().put(l.getId(),false);}
	return this.lots;
	}
	
	/**
	 * @param users
	 *            the users to set
	 */
	public void setLots(List<Lott> lots) {
		this.lots = lots;
	}

	/**
	 * @return the newLot
	 */
	public Lott getNewLott() {
		return newLott;
	}
	
	public String doFindLot(){
		dofnd=true;
		rendlist=new RenderLists();
		return "/pages/admin/listLots";
	}
	
	public String doFindLotUser(){
		dofnd=true;
		rendlist=new RenderLists();
		fndUser=FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
		return "/pages/admin/userLott";
	}
	
	public String reqLot(){
		 Map<String,String> params = 
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		try {
			reqlott=lotService.getEntityById(new Long(params.get("p_lotid")));
			reqlottinfo=liService.getLottInfoByLott(reqlott.getId());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (GeneralServiceException e) {
			e.printStackTrace();
		}
		return "/pages/Lott";
	}

	public String getLotDetails(Long idlot)
	{
		try {
			lotdetail=liService.getLottInfoByLott(idlot);
			if (lotdetail.isEmpty())return "No_photo.jpg";
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (GeneralServiceException e) {
			e.printStackTrace();
		}
		return lotdetail.get(0).getImg();
		
	}
	
	public void delLottImages(Long id){
		try {
			liService.delLottInfosByLott(id);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (GeneralServiceException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> getReqImgs()
	{
		List<String> images = new ArrayList<String>();
		if (!reqlottinfo.isEmpty())	for (LotInfo lif : reqlottinfo) images.add(lif.getImg());	
	else images.add("No_photo.jpg");
	return images;
	}
	
		/**
	 * @param newUser
	 *            the newUsersto set
	 */
	public void setNewLott(Lott newLott) {
		this.newLott= newLott;
	}
	
	public String showLotDetail(Long idlot){
		try {
			lottoshow=idlot;
			newLott=lotService.getEntityById(idlot);
			lotdetail=liService.getLottInfoByLott(idlot);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (GeneralServiceException e) {
			e.printStackTrace();
		}
		showlotdetail=true;
		return "/pages/admin/listLots";
	}
	
	public boolean isShowlotdetail() {
		return showlotdetail;
	}

	public void setShowlotdetail(boolean showlotdetail) {
		this.showlotdetail = showlotdetail;
	}

	/**
	 * @return list of LotTypes
	 */
	public List<String> getTypes() {
		 List<String> types = new ArrayList<String>();
		try {
			types = lotService.getAllTypeOfLotts();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (GeneralServiceException e) {
			e.printStackTrace();
		}
		return types;
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
	
	public String getUsrByLot(Long idlot){
		return lotService.getEntityById(idlot).getUser().getLogin();
	}

	/**
	 * Method that will be called, after bean was initialized
	 */
	@PostConstruct
	public void postConstruct() {
		addinguser=null;
		fndUser=null;
		date=null;
		type=null;
		name=null;
		price=0;
		dofnd=false;
		showlotdetail=false;
		lots=new ArrayList<Lott>();
		getUsLogin();
		newLott= new Lott();
		rendlist=new RenderLists();
		log.debug("Loading 5 users"); //$NON-NLS-1$
		newLott.setDatepay(Calendar.getInstance());
		rendlist.setCount(getCount());
		getLots();
	}
	
	public String editLott(){
		int i=0;
		try {

			for (i=0;i<lots.size();i++){
				if(rendlist.getRenderList().get(lots.get(i).getId()).equals(true) ){
					lotService.setLott(lots.get(i),userService.getUserByLogin(addinguser).getId());
					rendlist.chRendComp(lots.get(i).getId());
				}
			}
		} catch (GeneralServiceException e) {
			//	addGlobalErrorMessage(e.getLocalizedMessage(), e);
		}
		rendlist.setScrolPage(0);
		return "/pages/admin/listLots";
	}
	
	public int getCount() {      
        int count = (int)lotService.getAllEntitiesCount();
        rendlist.setCount(count);
        return count;        
    }
	
	public List<Lott> getSortlot(){
		List<Long> llotid=new ArrayList<Long>();
		List<Lott> llot=new ArrayList<Lott>();
	/*	FacesMessage msg = new FacesMessage("asas");
		FacesContext.getCurrentInstance().addMessage(null, msg);*/
		try {
			llotid=lotService.getSortLott();
			for (Long l: llotid){
				llot.add(lotService.getEntityById(l));
			}
			return llot;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			 llot.addAll(lotService.getEntites(0,5));
		} catch (GeneralServiceException e) {
			e.printStackTrace();
			llot.addAll(lotService.getEntites(0,5));
		}
		return llot;
	}

	public boolean checkUserRg(String login){
		try {
			return userService.getUserByLogin(login).getRating()>lotService.getLott(login, null, null, null, 0).size();
		} catch (IllegalArgumentException e) {
			return false;
		} catch (GeneralServiceException e) {
			return false;
		}
	}
	
	/**
	 * Action that is performed, when adding new user.
	 * 
	 * @return <code>null</code> in all cases, so there will be no navigation to
	 *         other pages
	 */
	public void doAddLott() {
		if(!FacesContext.getCurrentInstance().getExternalContext().isUserInRole("admin") && 
				!checkUserRg(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser())){
			return ;
		}
		try {
			newLott=lotService.setLott(newLott,userService.getUserByLogin(addinguser).getId());
			
		} catch (GeneralServiceException e) {
			return;
		}
		//addGlobalINFOMessage(Messages.getString("admin.User") + newUser.getLogin() //$NON-NLS-1$
		//		+ Messages.getString("admin.SuccessAdd"), null); //$NON-NLS-1$
		rendlist.setCount(getCount());
		newLott= new Lott();
		newLott.setDatepay(Calendar.getInstance());
	}
	
	/**
	 * Deleting all lot who in find request.
	 * 
	 * @return <code>null</code> in all cases, so there will be no navigation to
	 *         other pages
	 */
	public void removeAllFindes(){
		try {
			for (int i=0;i<lots.size();i++){
				lotService.delEntity(lots.get(i).getId());
			}
			
		//	addGlobalINFOMessage(Messages.getString("admin.SuccessDelete"), null); //$NON-NLS-1$

		} catch (GeneralServiceException e) {
		//	addGlobalErrorMessage(e.getLocalizedMessage(), e);
		}
		rendlist.setRenderList(null);
		rendlist.setCount(0);
	}	
	
	public void findAll(){
		dofnd=false;
	}
	/**
	 * Action that is performed, when deleting lot.
	 * <p>
	 * Getting the lot id delete from row it was clicked.
	 * </p>
	 * 
	 * @return <code>null</code> in all cases, so there will be no navigation to
	 *         other pages
	 */	 
	public String dellott(Long id) {
		if (id != null) {

			try {
				lotService.delEntity(id);
			//	addGlobalINFOMessage(Messages.getString("admin.SuccessDelete"), null); //$NON-NLS-1$

			} catch (GeneralServiceException e) {
			//	addGlobalErrorMessage(e.getLocalizedMessage(), e);
			}
		} else {
			//addGlobalErrorMessage(Messages.getString("admin.ErrorGetUserFromTableForDelete"), null);
		}
		rendlist.setCount(getCount());
		return "/pages/admin/listLots";
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

	public String getType() {
		if(type==null)return "";else
		return type;
	}

	public void setType(String type) {
		if(type=="")this.type=null;else
		this.type = type;
	}

	public String getName() {
		if(name==null)return "";else
		return name;
	}
	
	public void setName(String name) {
		if(name=="")this.name=null;else
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * save info about lot
	 */
	public void saveInfo(){
	  try {
		lotService.save(newLott);
	} catch (IllegalArgumentException e) {
		e.printStackTrace();
	} catch (GeneralServiceException e) {
		e.printStackTrace();
	}
	}
    public void submit() {
       // Prepare filename prefix and suffix for an unique filename in upload folder.
        String prefix = FilenameUtils.getBaseName(uploadedFile.getName());
        String suffix = FilenameUtils.getExtension(uploadedFile.getName());
        // Prepare file and outputstream.
        File file = null;
        OutputStream output = null;
        try {
            // Create file with unique name in upload folder and write to it.
            file = File.createTempFile(prefix + "_", "." + suffix, new File("/home/vetal/workspace/auction2011/WebContent/resources/"));
            output = new FileOutputStream(file);
            IOUtils.copy(uploadedFile.getInputStream(), output);
            fileName = file.getName();
            LotInfo lisa=new LotInfo();
            lisa.setImg(fileName);
            try {
				liService.setLottInfo(lisa,lottoshow );
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (GeneralServiceException e) {
				e.printStackTrace();
			}
            FacesContext.getCurrentInstance().addMessage("uploadForm", new FacesMessage(
                FacesMessage.SEVERITY_INFO, "Файл загружен!", null));
            	
        } catch (IOException e) {
            // Cleanup.
            if (file != null) file.delete();

            // Show error message.
            FacesContext.getCurrentInstance().addMessage("uploadForm", new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Ошибка загрузки файла!", null));
        } catch (IllegalArgumentException e) {
			e.printStackTrace();
		}  finally {
            IOUtils.closeQuietly(output);
        }
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

	/**
	 * @return the lottoshow
	 */
	public Long getLottoshow() {
		return lottoshow;
	}

	/**
	 * @param lottoshow the lottoshow to set
	 */
	public void setLottoshow(Long lottoshow) {
		this.lottoshow = lottoshow;
	}


}