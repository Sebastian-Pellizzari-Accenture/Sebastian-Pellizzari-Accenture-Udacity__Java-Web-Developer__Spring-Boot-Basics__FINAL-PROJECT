package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;

@Service
public class NoteService {
    NoteMapper noteMapper;
    

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public void addNote(Note note) throws IllegalArgumentException{
        if (note.getNotetitle()== ""){
            throw new IllegalArgumentException("[ERROR] Note title is empty.");
        }
        
        noteMapper.insert(note);
    }

    public List<Note> getAllUserNotes(Integer userId) {
        List<Note> notes = noteMapper.getAllUserNotes(userId);
        return notes;
    }

    public Note getById(Integer noteid) {
        return noteMapper.getNoteById(noteid);
    }

    public void deleteById(Integer noteid) {
        noteMapper.deleteById(noteid);
    }

    public void updateNote(Integer noteId, String noteTitle, String noteDescription) {
        Note note = noteMapper.getNoteById(noteId);
        note.setNotedescription(noteDescription);
        note.setNotetitle(noteTitle);
        noteMapper.updateNote(note);
    }
}
