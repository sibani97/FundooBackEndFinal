package com.bridgelabz.fundoonotes.notes.service;

import java.util.List;
import java.util.Set;

import com.bridgelabz.fundoonotes.exception.TokenException;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.notes.dto.NoteDto;
import com.bridgelabz.fundoonotes.notes.model.Notes;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.user.model.User;

public interface NoteService {
	
public Response createNote(NoteDto noteDto,String token);
public Response updateNote(Long noteId,NoteDto noteDto,String token)throws UserException;
public Response trashNote(Long noteId,String token)throws TokenException;
public List<Notes> getAllUserNotes(String token,boolean trash,boolean archive)throws UserException;
public Response isPing(Long noteId,String token)throws TokenException;
public Response archive(Long noteId,String token)throws TokenException;
public List<Notes> getPingNotes(String token);
public List<Notes> getunPinNotes(String token);

public List<Notes> getArchive(String token);
public List<Notes> getUnArchive(String token);
public List<Notes>getTrash(String token);
public List<Notes> getUnTrash(String token);
public Response deleteNotes(Long noteId,String token);
public Response addColaboratorToNote(String token,Long noteId,String emailId);
public Response removeColaboratorToNote(String token,Long noteId,String emailId);
public Set<Notes> getColaborate(String token);
public Set<User> getColaborateNotes(String token,Long noteId);
public Response setColourToNote(String token,Long noteId,String colour);
public Response setReminderToNote(String token,Long noteId,String reminder);
public Response removeReminder(String token,Long noteId);
//public Set<Notes> getReminder(String token,Long noteId);
public String getReminder(String token,Long noteId);
//public Set<User> getReminderToNote(String token,Long noteId);

}
 