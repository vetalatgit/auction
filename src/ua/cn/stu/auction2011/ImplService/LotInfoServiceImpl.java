package ua.cn.stu.auction2011.ImplService;

import java.util.List;

import ua.cn.stu.auction2011.domain.LotInfo;
import ua.cn.stu.auction2011.domain.Lott;
import ua.cn.stu.auction2011.exceptions.GeneralServiceException;
import ua.cn.stu.auction2011.services.ILottService;
import ua.cn.stu.auction2011.services.ILottinfoService;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

@Stateless
@Remote
public class LotInfoServiceImpl extends GenericServiceImpl<LotInfo> implements
ILottinfoService {
	
	@EJB
	ILottService lotserv;
	
	public LotInfoServiceImpl() {
		super(LotInfo.class);
	}

public LotInfoServiceImpl(Class<LotInfo> entityClass) {
		super(entityClass);
		
	}

@Override
protected void beforeEntityAddUpdate(LotInfo entity)
	throws GeneralServiceException {


}

@Override
protected void beforeEntityDelete(LotInfo entity)
	throws GeneralServiceException {


}



@Override
public List<LotInfo> getLottInfoByLott(Long lottId) throws IllegalArgumentException, GeneralServiceException{
	if(lottId==null){
		throw new IllegalArgumentException("lot can`t be null");
	}else
	return executeQuery("LotInfo.getInfoByLott", true, false, lotserv.getEntityById(lottId));
}				

@Override
public LotInfo setLottInfo(LotInfo lot, Long lottId) throws IllegalArgumentException,
	GeneralServiceException {
	if(lotserv.getEntityById(lottId)==null){
		throw new IllegalArgumentException("lot can`t be null");
	}else{
		lot.setLott(lotserv.getEntityById(lottId));
		return save(lot);
	}}

@Override
public void delLottInfosByLott(Long lottId) throws IllegalArgumentException,
		GeneralServiceException {
	if(lottId==null){
		throw new IllegalArgumentException("lot can`t be null");
	}else
	executeUpdateQuery("LotInfo.deleteByLott", true, lotserv.getEntityById(lottId));
}

}
