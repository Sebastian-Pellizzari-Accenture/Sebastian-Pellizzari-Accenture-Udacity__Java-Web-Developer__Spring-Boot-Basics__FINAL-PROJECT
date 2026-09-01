package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    private void populateNoteList(Model model, Authentication authentication){
        User user = userService.getUser(authentication.getName());
        List<Note> notes = noteService.getAllUserNotes(user.getUserId());
        model.addAttribute("notes", notes);
    }

    private void removeUploadError(Model model){
        model.addAttribute("upload_success_msg", null);
    }

    public NoteController(NoteService noteService, UserService userService){
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping("/note/edit")
    public String get_home_view(@RequestParam("fileId") Integer fileId, Model model, Authentication authentication) {
        populateNoteList(model, authentication);
        removeUploadError(model);
        return "home";
    }

    @GetMapping("/note/upload")
    public String get_home_upload(Model model, Authentication authentication) {
        populateNoteList(model, authentication);
        return "home";
    }

    @GetMapping("/note/delete")
    public String get_home_delete(@RequestParam("noteid") Integer noteid, Model model, Authentication authentication) {
        populateNoteList(model, authentication);
        removeUploadError(model);
        return "home";
    }

    @PostMapping("/note/update")
    public String view(@RequestParam("noteId") Integer noteId,
                        @RequestParam("noteTitle") String noteTitle,
                        @RequestParam("noteDescription") String noteDescription, 
                        Model model, 
                        Authentication authentication
    ) {
        System.out.println("Update NOTE");
        removeUploadError(model);
        noteService.updateNote(noteId, noteTitle, noteDescription);
        populateNoteList(model, authentication);
        model.addAttribute("activeTab", "notes");
        return "home";
    }

    @PostMapping("/note/edit")
    public String editNote(@RequestParam Integer noteid, Model model, Authentication authentication) {
        System.out.println("Edit NOTE");
        populateNoteList(model, authentication);
        model.addAttribute("editNote", noteService.getById(noteid));
        model.addAttribute("activeTab", "notes");
        return "home";
    }

    @PostMapping("/note/delete")
    public String delete(@RequestParam("noteid") Integer noteid, Model model, Authentication authentication) {
        System.out.println("NOTEID==" + noteid);
        noteService.deleteById(noteid);
        populateNoteList(model, authentication);
        removeUploadError(model);
        model.addAttribute("activeTab", "notes");
        return "home";
    }
    
    @PostMapping("/note/upload")
    public String upload(
        @RequestParam("noteTitle") String notetitle,
        @RequestParam("noteDescription") String notedescription,
        Authentication authentication, 
        Model model
    ) throws IOException{
        try {
            System.out.println("ADDING NOTE");
            Note note = new Note();
            note.setNotedescription(notedescription);
            note.setNotetitle(notetitle);
            note.setUserid(userService.getUser(authentication.getName()).getUserId());
            noteService.addNote(note);
            removeUploadError(model);
        }
        catch(IllegalArgumentException e){
            model.addAttribute("upload_success_msg", e.getMessage());
        }
        populateNoteList(model, authentication);
        model.addAttribute("activeTab", "notes");
        return "home";
    }
}
