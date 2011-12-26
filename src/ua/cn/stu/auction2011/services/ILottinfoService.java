package ua.cn.stu.auction2011.services;

import java.util.List;

import ua.cn.stu.auction2011.exceptions.GeneralServiceException;
import ua.cn.stu.auction2011.domain.LotInfo;
import javax.ejb.Remote;

@Remote
public interface ILottinfoService extends IGenericService<LotInfo> {

	public void delLottInfosByLott(Long lottId)
			throws IllegalArgumentException, GeneralServiceException;
	
	public List<LotInfo> getLottInfoByLott(Long lottId)
			throws IllegalArgumentException, GeneralServiceException;
	
	public LotInfo setLottInfo(LotInfo lot, Long lottId)
			throws IllegalArgumentException, GeneralServiceException;
	
}
