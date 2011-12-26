package ua.cn.stu.auction2011.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotEmpty;
@Entity
@NamedQueries( {
@NamedQuery(name = "LotInfo.deleteByLott", query = "DELETE FROM LotInfo x WHERE x.lott = ?1"),
@NamedQuery(name = "LotInfo.getInfoByLott", query = "SELECT x FROM LotInfo x WHERE x.lott = ?1")}) 
public class LotInfo extends DomainObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotEmpty	
	private String img;
	
	private Lott lott;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "LOTT_ID")//, insertable = false, updatable=false)
	/**
	 * @return the lott
	 */
	public Lott getLott() {
		return lott;
	}
	/**
	 * @param lott the lott to set
	 */
	public void setLott(Lott lott) {
		this.lott = lott;
	}
	
	
	/**
	 * @return the img
	 */
	public String getImg() {
		return img;
	}
	/**
	 * @param img the img to set
	 */
	public void setImg(String img) {
		this.img = img;
	}
	
	/**
	 * construct lotinfo
	 */
	public LotInfo(){
		this.img="No_photo.jpg";

	}

}
