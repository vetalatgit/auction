package ua.cn.stu.auction2011.domain;

import java.util.Calendar;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries( {
	@NamedQuery(name = "Message.getAllThems", query = "SELECT DISTINCT x.them FROM Message x WHERE x.id = ?1"),
	@NamedQuery(name = "Message.readMessage",query = "UPDATE Message x SET x.read = true WHERE x.id = ?1"),
	@NamedQuery(name = "Message.deleteAllByUser", query = "DELETE FROM Message x WHERE x.user = ?1"),
    @NamedQuery(name = "Message.deleteAllByLott", query = "DELETE FROM Message x WHERE x.lott = ?1"),
	@NamedQuery(name = "Message.getAllMess", query = "SELECT x FROM Message x order by x.date") })
public class Message extends DomainObject {
	/**
	 * 
	 */
	@NotEmpty
	@Lob
	private String data;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar date;
	@NotEmpty
	private String authorname;
	@NotEmpty
	private String them;

	private boolean read;
	
	public boolean isRead() {
		return read;
	}
	public void setRead(boolean read) {
		this.read = read;
	}
	public String getThem() {
		return them;
	}
	public void setThem(String them) {
		this.them = them;
	}
	
	private Users user;
	
	public String getAuthorname() {
		return authorname;
	}
	public void setAuthorname(String authorname) {
		this.authorname = authorname;
	}
	
	private Lott lott;
	
	@ManyToOne(optional=false)
	@JoinColumn(name = "USER_ID", insertable = false, updatable=false)
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	
	@ManyToOne(optional=false)
	@JoinColumn(name="LOTT_ID", insertable = false, updatable=false)
	public Lott getLott() {
		return lott;
	}
	public void setLott(Lott lott) {
		this.lott = lott;
	}
	
	private static final long serialVersionUID = 1L;
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}

}
