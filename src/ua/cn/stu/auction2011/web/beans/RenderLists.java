package ua.cn.stu.auction2011.web.beans;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

public class RenderLists implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<Long, Boolean> renderList=null;
	
	private boolean renn;
	private boolean renp;
	private int firstEntity;
	private int lastEntity;
	private int batchSize;
	private int count=0;
	/** Page for the data scroller */
	private int scrolpage = 1;
	
	private boolean rend =false;
	
	public void init(){
		batchSize=5;
		firstEntity=0;
		lastEntity=firstEntity+batchSize;	
	}

	public RenderLists(){
		init();
	}
	
	public void setScrolPage(int scrolpage){
		this.scrolpage=scrolpage;
	}

	public int getScrolPage() {
		return scrolpage;
	}

	public int getLastEntity() {
		return lastEntity;
	}

	public void setLastEntity(int lastEntity) {
		this.lastEntity = lastEntity;
	}

	public boolean getRend() {
		return rend;
	}

	public void setRend(boolean rend) {
		this.rend = rend;
	}
	
	public boolean isRendComp(Long idEntity){
		return renderList.get(idEntity);
		
	}
	public Map<Long, Boolean> getRenderList() {
		return renderList;
	}

	public void setRenderList(Map<Long, Boolean> renderList) {
		this.renderList = renderList;
	}

	public void chRendComp(Long idEntity){
		for (Entry<Long, Boolean> entry : renderList.entrySet())
			if(entry.getKey().equals(idEntity)){entry.setValue(!entry.getValue());
			break;}
		scrolpage=2;
     }
		
	public int getFirstEntity() {
		return firstEntity;
	}

	public void setFirstEntity(int firstEntity) {
		this.firstEntity = firstEntity;
	}

	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}
	
	public void next() {
	   if (firstEntity + batchSize < getCount()) {
	    	firstEntity += batchSize;
	    	lastEntity=firstEntity+batchSize;
	       }
    } 
	  
	    public void prev() {
		  firstEntity -= batchSize;
	        if (firstEntity < 0) {
	        	firstEntity = 0;
	        }
	        lastEntity=firstEntity+batchSize;
	    }  
	  
	public boolean isRenn() {
		renn= (firstEntity + batchSize < getCount());
		return renn;
	}

	public void setRenn(boolean renn) {
		this.renn = renn;
	}

	public boolean isRenp() {
		renp=(firstEntity >= batchSize);
		return renp;
	}

	public void setRenp(boolean renp) {
		this.renp = renp;
	}
	
	public void setCount(int count){
		this.count=count;
	}
	
	public int getCount(){
		return count;
	}
	

}
