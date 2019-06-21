package com.bridgelabz.fundoonotes.notes.controller;



import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.elasticsearch.ElasticsearchServiceImpl;
import com.bridgelabz.fundoonotes.notes.dto.NoteDto;
import com.bridgelabz.fundoonotes.notes.model.Notes;
import com.bridgelabz.fundoonotes.notes.service.NoteServiceImpl;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.response.ResponseToken;
import com.bridgelabz.fundoonotes.user.model.User;

import antlr.Token;
@RestController
@RequestMapping(value="/user/note")
@CrossOrigin(allowedHeaders = "*" ,origins = "*")
public class NoteController {
	
	@Autowired(required=true)
	private ElasticsearchServiceImpl elasticImpl;
	
	@Autowired
	NoteServiceImpl noteServiceImpl;
	Logger logger=org.slf4j.LoggerFactory.getLogger(NoteController.class);
	
	@PostMapping(value="/createNote")
	public ResponseEntity<Response> createNote(HttpServletRequest request,@RequestBody NoteDto noteDto,@RequestHeader String token) throws IOException
	{
		logger.info("token---"+token);
	    logger.info((String)request.getAttribute("jwt_token"));
		System.out.println("title is"+noteDto.getTitle().length());
		Response response= noteServiceImpl.createNote(noteDto, token);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
		
		
	}
	@PutMapping(value="/update")
	public ResponseEntity<Response> updateNote(@RequestParam Long noteId,@RequestBody NoteDto noteDto,@RequestHeader String token)
	{
		System.out.println("inside update");
		logger.info("note details"+noteDto.toString());
		logger.info("note id"+noteId.toString());
		logger.info("update");
		Response response=noteServiceImpl.updateNote(noteId, noteDto, token);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
			}
	
	@PutMapping(value="/trash")
	public ResponseEntity<Response>trashNote(@RequestParam Long noteId,@RequestHeader String token)

	{
		logger.info("note noteId"+noteId);
		Response response=noteServiceImpl.trashNote(noteId, token);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
		
			}
	@PutMapping(value="/isPin")
	public ResponseEntity<Response>isPin(@RequestParam Long noteId,@RequestHeader String token)
	{
		logger.info("note noteId"+noteId);
		Response response=noteServiceImpl.isPing(noteId,token);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	@PutMapping(value="/isArchive")
	public ResponseEntity<Response>isArchive(@RequestParam Long noteId,@RequestHeader String token)
	{
		logger.info("note noteId"+noteId);
		Response response=noteServiceImpl.archive(noteId, token);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	@GetMapping(value="/getnote")
	public List<Notes> getNotes(@RequestHeader String token,@RequestParam boolean trash,@RequestParam boolean archive)
	{
		System.out.println("User get note");
		logger.info("get all notes");
		List<Notes> note=noteServiceImpl.getAllUserNotes(token,trash,archive);
		return note;
	}
	@GetMapping(value="/getTrash")
	public List<Notes> getTrash(@RequestHeader String token)
	
	{	logger.info("get all trash notes");
		List<Notes> note=noteServiceImpl.getTrash(token);
		return note;
	}
	
@GetMapping(value="/getUntrash")
public List<Notes> getUnTrash(@RequestHeader String token)
	
	{
		logger.info("get all untrash notes");
		List<Notes> note=noteServiceImpl.getUnTrash(token);
		return note;
	}
	
	
	@GetMapping(value="/getPin")
	public List<Notes> getisPinNotes(@RequestHeader String token)
	{
		logger.info("get all pin notes");
		List<Notes> note=noteServiceImpl.getPingNotes(token);
		return note;
	}
	
	@GetMapping(value="/getUnPin")
	public List<Notes> getisUnPinNotes(@RequestHeader String token)
	{
		logger.info("get all unpin notes");
		List<Notes> note=noteServiceImpl.getunPinNotes(token);
		return note;
	}
	
	@GetMapping(value="/getarchive")
	public List<Notes> getArchiveNotes(@RequestHeader String token)
	{
		logger.info("get all archive notes");
		List<Notes> note=noteServiceImpl.getArchive(token);
		return note;
	}
	
	@GetMapping(value="/getUnarchive")
	public List<Notes> getUnArchiveNotes(@RequestHeader String token)
	{
		logger.info("get all Unarchive notes");
		List<Notes> note=noteServiceImpl.getUnArchive(token);
		return note;
	}
	
	@DeleteMapping(value="/deletenote")
	public ResponseEntity<Response> deleteNote(@RequestParam Long noteId,@RequestHeader String token)
	{
		logger.info("note delete");
		Response response=noteServiceImpl.deleteNotes(noteId, token);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@PostMapping(value="/addColaborator")
	public ResponseEntity<Response> addColaborator(@RequestHeader String token,@RequestParam Long noteId,@RequestParam String emailId)
	{
		logger.info("add colaborated note");
		Response response=noteServiceImpl.addColaboratorToNote(token, noteId, emailId);
	
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@DeleteMapping(value="/deleteColaborator")
	public ResponseEntity<Response> deleteColaborator(@RequestHeader String token,@RequestParam Long noteId,@RequestParam String emailId)
	{
		logger.info("remove colaborator from note");
		Response response=noteServiceImpl.removeColaboratorToNote(token, noteId, emailId);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	
	@GetMapping(value="/getColaborator")
	public Set<Notes> getColaborator(@RequestHeader String token)
	{
		
		logger.info("get all colaboretor notes");
		Set<Notes> note=noteServiceImpl.getColaborate(token);
		return note;
	}
	
	
	@GetMapping(value="/getColaboratorNote")
	public Set<User> getColaboratorNote(@RequestHeader String token,@RequestParam Long noteId )
	{
		
		logger.info("get all colaboretor from notes");
		Set<User> note=noteServiceImpl.getColaborateNotes(token, noteId);
		return note;
	}
	@PostMapping(value="/addColor")
	public ResponseEntity<Response> setColour(@RequestHeader String token,@RequestParam Long noteId,@RequestParam String colour)
	{
	logger.info("colour set");
	Response response=noteServiceImpl.setColourToNote(token, noteId, colour);
	
	return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
@PostMapping(value="/addReminder")	
public  ResponseEntity<Response> setReminder(@RequestHeader String token,@RequestParam Long noteId,@RequestParam String reminder)
{
logger.info("reminder set");
Response response=noteServiceImpl.setReminderToNote(token, noteId, reminder);
return new ResponseEntity<Response>(response,HttpStatus.OK);
}

@DeleteMapping(value="/removeReminder")
public ResponseEntity<Response> removeReminder(@RequestHeader String token,@RequestParam Long noteId)
{
logger.info("reminder removed from note");
Response response=noteServiceImpl.removeReminder(token, noteId);
return new ResponseEntity<Response>(response,HttpStatus.OK);
	
}

@GetMapping(value="/getReminder")
public ResponseEntity<String> getReminder(@RequestHeader String token,@RequestParam Long noteId)
{
	logger.info("get all Reminder");
	String response=noteServiceImpl.getReminder(token, noteId);
	return new ResponseEntity<String>(response,HttpStatus.OK);
	
	}

@PostMapping(value="/search")	
public  List<Notes> elasticSearchNote(@RequestParam String query,@RequestHeader String token)
{
logger.info("searching");

return elasticImpl.searchData(query, token);
}

//@PutMapping(value="/updateElastic")
//public ResponseEntity<Response> updateElasticNote(@RequestParam String ,@RequestBody NoteDto noteDto,@RequestHeader String token)
//{
//	System.out.println("inside update");
//	logger.info("note details"+noteDto.toString());
//	logger.info("note id"+noteId.toString());
//	logger.info("update");
//	Response response=noteServiceImpl.updateNote(noteId, noteDto, token);
//	return new ResponseEntity<Response>(response,HttpStatus.OK);
//		}
//@PutMapping(value="/updateElastic")
// public Notes  updateElasticnote(@RequestBody Notes note)
// {
//	 logger.info("note update in elastic search");
//	 return elasticImpl.updateNote(note);
// }
}






