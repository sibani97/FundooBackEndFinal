package com.bridgelabz.fundoonotes.notes.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.bridgelabz.fundoonotes.labels.model.Labels;
import com.bridgelabz.fundoonotes.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Notes implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long noteId;
	private String title;
	private String description;
	private boolean isPin;
	private boolean isTrash;
	private boolean archive;
	private LocalDate createDate;
	private LocalDate lastUpdateDate;
	private String colour;
	private String reminder;

	private long uid;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getReminder() {
		return reminder;
	}

	public void setReminder(String reminder) {
		this.reminder = reminder;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	//
//	@JoinColumn(name="mynote_id")
//	@ManyToMany(cascade=CascadeType.ALL)
//	User user;
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<User> collaborateUser;

	@ManyToOne()
//	@ManyToOne
	@JsonIgnore
	User user;

	@ManyToMany(mappedBy = "notes", cascade = CascadeType.ALL)
	Set<Labels> label;

	public Set<Labels> getLabel() {
		return label;
	}

	public void setLabel(Set<Labels> label) {
		this.label = label;
	}

	public Long getNoteId() {
		return noteId;
	}

	public void setNoteId(Long noteId) {
		this.noteId = noteId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPin() {
		return isPin;
	}

	public Notes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setPin(boolean isPin) {
		this.isPin = isPin;
	}

	public boolean isTrash() {
		return isTrash;
	}

	public void setTrash(boolean isTrash) {
		this.isTrash = isTrash;
	}

	public boolean isArchive() {
		return archive;
	}

	public void setArchive(boolean archive) {
		this.archive = archive;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public LocalDate getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(LocalDate lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	// need
//	@Override
//	public String toString() {
//		return "Notes [noteId=" + noteId + ", title=" + title + ", description=" + description + ", isPin=" + isPin
//				+ ", isTrash=" + isTrash + ", archive=" + archive + ", createDate=" + createDate + ", lastUpdateDate="
//				+ lastUpdateDate + ", user=" + user + ", label=" + label + ", colour=" + colour + "]";
//	}

	public Set<User> getCollaborateUser() {
		return collaborateUser;
	}

	@Override
	public String toString() {
		return "Notes [noteId=" + noteId + ", title=" + title + ", description=" + description + ", isPin=" + isPin
				+ ", isTrash=" + isTrash + ", archive=" + archive + ", createDate=" + createDate + ", lastUpdateDate="
				+ lastUpdateDate + ", colour=" + colour + ", user=" + user + ", label=" + label + ", reminder="
				+ reminder + ", userId=" + uid + "]";
	}

	public void setCollaborateUser(Set<User> collaborateUser) {
		this.collaborateUser = collaborateUser;
	}

}
