package com.bridgelabz.fundoonotes.util;

import com.bridgelabz.fundoonotes.notes.model.Notes;

public class NoteContainer {
	private Notes note;
	private NoteOperation noteOperation;
	public Notes getNote() {
		return note;
	}
	public void setNote(Notes note) {
		this.note = note;
	}
	public NoteOperation getNoteOperation() {
		return noteOperation;
	}
	public void setNoteOperation(NoteOperation noteOperation) {
		this.noteOperation = noteOperation;
	}
	@Override
	public String toString() {
		return "NoteContainer [note=" + note + ", noteOperation=" + noteOperation + "]";
	}
	

}
