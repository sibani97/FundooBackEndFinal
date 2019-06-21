
package com.bridgelabz.fundoonotes.user.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.bridgelabz.fundoonotes.labels.model.Labels;
import com.bridgelabz.fundoonotes.notes.model.Notes;
import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "userId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;

	@Column(name="username")
	private String userName;
	@Column(name="email")
	private String emailId;
	
	public Set<Notes> getCollaboratedNote() {
		return collaboratedNote;
	}

	public void setCollaboratedNote(Set<Notes> collaboratedNote) {
		this.collaboratedNote = collaboratedNote;
	}

	@Column(name="password")
	private String password;

	@Column(name = "registeredDate")
	private LocalDate registeredDate;
	
	
	@Column(name = "isVerified")
	boolean isVarified;
	public String profilepic;
	
	@JsonIgnore
	@ManyToMany(cascade=CascadeType.ALL)
	private Set<Notes> collaboratedNote;
	
	public String getProfilepic() {
		return profilepic;
	}



	public void setProfilepic(String profilepic) {
		this.profilepic = profilepic;
	}

	@Column(name = "updatedDate")
	private LocalDate updatedDate;
	@Column(name = "mobileNumber")
	@Pattern(regexp = "[0-9]{10}" , message = "enter your mobile number")
	@NotEmpty(message = "please provide your mobile number")
	private String mobileNumber;

	@JoinColumn(name="userId") 
	@OneToMany()
//	@OneToMany
	List<Notes> notes;
	
	
	@OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="userId")
	List<Labels> lable;
	 
	



	

	public List<Notes> getNotes() {
		return notes;
	}



	public List<Labels> getLable() {
		return lable;
	}



	public void setLable(List<Labels> lable) {
		this.lable = lable;
	}



	public void setNotes(List<Notes> notes) {
		this.notes = notes;
	}



	public User() {
		super();
		
	}

	
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}




	public String getEmailId() {
		return emailId;
	}



	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", emailId=" + emailId + ", password=" + password
				+ ", registeredDate=" + registeredDate + ", isVarified=" + isVarified + ", profilepic=" + profilepic
				+ ", updatedDate=" + updatedDate + ", mobileNumber=" + mobileNumber + "]";
	}



	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}



	



//	@Override
//	public String toString() {
//		return "User [userId=" + userId + ", userName=" + userName + ", emailId=" + emailId + ", password=" + password
//				+ ", registeredDate=" + registeredDate + ", isVarified=" + isVarified + ", profilepic=" + profilepic
//				+ ", collaboratedNote=" + collaboratedNote + ", updatedDate=" + updatedDate + ", mobileNumber="
//				+ mobileNumber + ", notes=" + notes + ", lable=" + lable + "]";
//	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(LocalDate registeredDate) {
		this.registeredDate = registeredDate;
	}

	public LocalDate getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public boolean isVarified() {
		return isVarified;
	}

	public void setVarified(boolean isVarified) {
		this.isVarified = isVarified;
	}

	
}
