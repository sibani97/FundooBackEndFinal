package com.bridgelabz.fundoonotes.elasticsearch;

import java.util.List;

import com.bridgelabz.fundoonotes.notes.model.Notes;

public interface ElasticsearchService {
public Notes createNote(Notes note);
public Notes updateNotes(Notes note);
public void deleteNote(Long  noteId);
public List<Notes> searchNotes(String quary,Long noteId);
//Notes createNote(Notes note);

}
