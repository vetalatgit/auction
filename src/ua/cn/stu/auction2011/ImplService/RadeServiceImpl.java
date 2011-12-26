package ua.cn.stu.auction2011.ImplService;

import ua.cn.stu.auction2011.domain.Message;
import ua.cn.stu.auction2011.domain.Rade;
import ua.cn.stu.auction2011.domain.Users;
import ua.cn.stu.auction2011.exceptions.GeneralServiceException;
import ua.cn.stu.auction2011.services.ILottService;
import ua.cn.stu.auction2011.services.IRadeService;
import ua.cn.stu.auction2011.services.IUserService;

import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Stateless
@Remote
public class RadeServiceImpl extends GenericServiceImpl<Rade> implements
		IRadeService {

	@EJB
	IUserService usserv;
	
	@EJB
	ILottService lotserv;
	
	public RadeServiceImpl() {
		super(Rade.class);
	}
	
	public RadeServiceImpl(Class<Rade> persistentClass) {
		super(persistentClass);
	}

	@Override
	protected void beforeEntityAddUpdate(Rade entity)
			throws GeneralServiceException {	
	}
	
	public int getSpendMoney(Long userId) throws IllegalArgumentException, GeneralServiceException{
		return executeQuery("Rade.getSpendMoney", true, true, usserv.getEntityById(userId));
	}

	@Override
	protected void beforeEntityDelete(Rade entity)
			throws GeneralServiceException {
	}

	@Override
	public boolean delRadeByUser(Long userId) throws IllegalArgumentException,
			GeneralServiceException {
	executeUpdateQuery("Rade.deleteAllByUser", true, usserv.getEntityById(userId));
	return true;
	}

	@Override
	public boolean delRadeByLott(Long lottId) throws IllegalArgumentException,
			GeneralServiceException {
	executeUpdateQuery("Rade.deleteAllByLott", true, lottId);
	return true;
	}

	@Override
	public boolean doneRade(Long radeId) throws IllegalArgumentException,
			GeneralServiceException {
		executeUpdateQuery("Rade.doneRade", true, radeId);
		return true;
	}

	@Override
	public boolean doRade(Rade rade, Long idUser, Long idLot )
			throws IllegalArgumentException, GeneralServiceException {
		if((idUser==null) || (idLot==null)){
			throw new IllegalArgumentException("Author or lott is null");
		}else{
			rade.setLott(lotserv.getEntityById(idLot));			
			rade.setUser(usserv.getEntityById(idUser));
			save(rade);
		return true;
		}
	}

	/*@Override
	public List<Rade> getRadeByAuth(Calendar date, Long userId, Long price,
			String sale) throws IllegalArgumentException,
			GeneralServiceException {
		if (userId == null){
			throw new IllegalArgumentException("user id is null");
		}else{
		List<Rade> rdlist=executeQuery("Rade.getRadeByUser", true, false, userId);
		for(int i=1; i<rdlist.size();){
			if((sale!="")&&(new Boolean(sale)!=rdlist.get(i).isDone())){rdlist.remove(i);break;}
			if((price!=null)&&(price>rdlist.get(i).getPrice())){rdlist.remove(i);break;}
			if((date!=null)&&(date.getTimeInMillis()>rdlist.get(i).getTimerate().getTimeInMillis())){rdlist.remove(i);break;}
			i++;
		}
		return rdlist;
		}
	}*/

	@Override
	public List<Rade> getRads(Calendar date, String lottname, String userLogn, Long price,
			String sale) throws IllegalArgumentException,
			GeneralServiceException {
		List<Rade> rdlist=getAllEntites();
		for(int i=0; i<rdlist.size();){
			if((userLogn!=null)&&(!rdlist.get(i).getUser().getLogin().contains(userLogn))){rdlist.remove(i);continue;}
			if((lottname!=null)&&(!rdlist.get(i).getLott().getName().contains(lottname))){rdlist.remove(i);continue;}
			if((sale!="")&&(new Boolean(sale)!=rdlist.get(i).getIsdone())){rdlist.remove(i);continue;}
			if((price!=null)&&(price>rdlist.get(i).getPrice())){rdlist.remove(i);continue;}
			if((date!=null)&&(date.getTimeInMillis()>rdlist.get(i).getTimerate().getTimeInMillis())){rdlist.remove(i);continue;}
			i++;
		}
		return rdlist;
		}
	 
	@Override
	public List<Rade> getRadsByLU(Long lottId, Long userId) throws IllegalArgumentException,
			GeneralServiceException {
		List<Rade> rdlist=getAllEntites();
		for(int i=0; i<rdlist.size();){
			if((userId!=null)&&(!userId.equals(rdlist.get(i).getUser().getId()))){rdlist.remove(i);continue;}
			if((lottId!=null)&&(!lottId.equals(rdlist.get(i).getLott().getId()))){rdlist.remove(i);continue;}
			i++;
		}
		return rdlist;
		}

}
