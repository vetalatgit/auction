package ua.cn.stu.auction2011.ImplService;

import ua.cn.stu.auction2011.domain.LotInfo;
import ua.cn.stu.auction2011.domain.Lott;
import ua.cn.stu.auction2011.domain.Message;
import ua.cn.stu.auction2011.exceptions.GeneralServiceException;
import ua.cn.stu.auction2011.services.ILottService;
import ua.cn.stu.auction2011.services.ILottinfoService;
import ua.cn.stu.auction2011.services.IUserService;

import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

@Stateless
@Remote
public class LottServiceImpl extends GenericServiceImpl<Lott> implements
		ILottService {

	@EJB
	IUserService usrserv;
	
	@EJB
	ILottinfoService lotinfoserv;
	
	public LottServiceImpl() {
		super(Lott.class);
	}
	
	public LottServiceImpl(Class<Lott> persistentClass) {
		super(persistentClass);
	}

	@Override
	protected void beforeEntityAddUpdate(Lott entity)
			throws GeneralServiceException {
	}
	
	public List<Long> getSortLott()
			throws IllegalArgumentException, GeneralServiceException{
		return executeQuery("select DISTINCT(x.id) from Lott x, Rade y where x.id = y.lott.id group " +
				"by x.id order by COUNT(x.id) DESC;", false, false);
	}

	@Override
	protected void beforeEntityDelete(Lott entity)
			throws GeneralServiceException {
		executeUpdateQuery("Rade.deleteAllByLott", true, entity);
		executeUpdateQuery("Message.deleteAllByLott", true, entity);
		executeUpdateQuery("LotInfo.deleteByLott", true, entity);
	}

	@Override
	public List<Lott> getLott(String userlogn, Calendar date, String type,
			String name, int Price) throws IllegalArgumentException,
			GeneralServiceException {
		List<Lott> lotlst=getAllEntites();
		for(int i=0; i<lotlst.size();){
			if((userlogn!=null)&&(!lotlst.get(i).getUser().getLogin().contains(userlogn))){lotlst.remove(i);continue;}
			if(Price>lotlst.get(i).getPrice()){lotlst.remove(i);continue;}
			if((type!=null)&&(!(lotlst.get(i).getType().contains(type)))){lotlst.remove(i);continue;}
			if((name!=null)&&(!lotlst.get(i).getName().contains(name))){lotlst.remove(i);continue;}
			if((date!=null)&&(date.getTimeInMillis()>lotlst.get(i).getDatepay().getTimeInMillis())){lotlst.remove(i);continue;}
			i++;
		}
		return lotlst;
	}

	@Override
	public List<String> getAllTypeOfLotts() throws IllegalArgumentException,
			GeneralServiceException {
		return executeQuery("Lott.getAllTypes", true, false);
	}

	@Override
	public Lott setLott(Lott lot, Long idUser)
			throws IllegalArgumentException, GeneralServiceException {
		
		if(usrserv.getEntityById(idUser)==null){
			throw new IllegalArgumentException("user can`t be null");
		}else{
			lot.setUser(usrserv.getEntityById(idUser));
			return save(lot);
		}}

}
