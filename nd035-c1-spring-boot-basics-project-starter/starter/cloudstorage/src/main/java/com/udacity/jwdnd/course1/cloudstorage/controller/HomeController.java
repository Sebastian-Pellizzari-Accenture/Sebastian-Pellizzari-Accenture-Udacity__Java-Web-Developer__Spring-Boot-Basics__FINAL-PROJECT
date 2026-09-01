package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;
import java.net.http.HttpClient;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class HomeController {

    private final FileService fileService;
    private final UserService userService;
    private final NoteService noteService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    public HomeController(
        FileService fileService, 
        UserService userService, 
        NoteService noteService, 
        CredentialService credentialService, 
        EncryptionService encryptionService){
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    private String getEncryptionKey(String user_pwd_here){
        byte[] user_pwd_here_bytes = Arrays.copyOf(user_pwd_here.getBytes(), 16); // aes assumes exactly 16 bytes
        String encoded_key = Base64.getEncoder().encodeToString(user_pwd_here_bytes);
        return encoded_key;
    }

    private void populateCredentialListCiphered(Model model, Authentication authentication){
        System.out.println("[INFO] Populating credentials !!");
        User user = userService.getUser(authentication.getName());
        List<Credential> credentials = credentialService.getAllUserCredentials(user.getUserId());
        for (Credential c: credentials){
            System.out.println(c.getUrlfield() + "|" + c.getPasswordcipher());
        }
        model.addAttribute("credentialsCiphered", credentials);

    }
    private void populateCredentialListPlain(Model model, Authentication authentication){
        User user = userService.getUser(authentication.getName());
        List<Credential> credentials = credentialService.getAllUserCredentials(user.getUserId());

        // get the encryption key
        String encryption_key =  getEncryptionKey(userService.getUser(authentication.getName()).getPassword());
        List<Credential> newCredentials = new ArrayList<>();
        for(int i=0; i<credentials.size(); i++){
            Credential newCred = credentials.get(i);
            String encrPwd = newCred.getPasswordcipher();
            String plainPwd = encryptionService.decryptValue(encrPwd, encryption_key);
            newCred.setPasswordcipher(plainPwd);
            newCredentials.add(newCred);
        }

        model.addAttribute("credentialsPlain", newCredentials);
    }

    private void populateCredentialList(Model model, Authentication authentication){
        populateCredentialListPlain(model, authentication);
        populateCredentialListCiphered(model, authentication);
    }

    private void populateFileList(Model model, Authentication authentication){
        User user = userService.getUser(authentication.getName());
        List<File> files = fileService.getAllUserFiles(user.getUserId());
        model.addAttribute("files", files);
    }

    private void populateNoteList(Model model, Authentication authentication){
        User user = userService.getUser(authentication.getName());
        List<Note> notes = noteService.getAllUserNotes(user.getUserId());
        model.addAttribute("notes", notes);
    }

    private void removeUploadError(Model model){
        model.addAttribute("upload_success_msg", null);
    }

    @GetMapping("/home")
    public String get_home(Model model, Authentication authentication) {
        removeUploadError(model);
        model.addAttribute("selectedFile", null);
        populateFileList(model, authentication);
        populateNoteList(model, authentication);
        populateCredentialList(model, authentication);
        return "home";
    }

    @GetMapping("/home/view")
    public String get_home_view(@RequestParam("fileId") Integer fileId, Model model, Authentication authentication) {
        removeUploadError(model);
        populateFileList(model, authentication);
        File file = fileService.getFileById(fileId);
        model.addAttribute("selectedFile", file);
        System.out.println("POST:" + file.getFilename());
        return "home";
    }

    @GetMapping("/home/upload")
    public String get_home_upload(Model model, Authentication authentication) {
        populateFileList(model, authentication);
        return "home";
    }

    @GetMapping("/home/delete")
    public String get_home_delete(Model model, Authentication authentication) {
        removeUploadError(model);
        populateFileList(model, authentication);
        return "home";
    }

    @PostMapping("/home/view")
    public ResponseEntity<byte[]> view(@RequestParam("fileId") Integer fileId, Model model, Authentication authentication) {
        removeUploadError(model);
        populateFileList(model, authentication);
        // model.addAttribute("selectedFile", file);
        // System.out.println("POST:" + file.getFilename());

        // downloads the file
        model.addAttribute("activeTab", "files");
        File file = fileService.getFileById(fileId);
        return ResponseEntity.ok()
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachement; filename=\"" + file.getFilename() + "\""
            )
            .body(file.getFiledata());
    }
    @PostMapping("/home/delete")
    public String delete(@RequestParam("fileId") Integer fileId, Model model, Authentication authentication) {
        System.out.println("some very complex delete logic");
        removeUploadError(model);
        fileService.deleteById(fileId);
        populateFileList(model, authentication);

        model.addAttribute("activeTab", "files");
        return "home";
    }
    
    @PostMapping("/home/upload")
    public String upload(@RequestParam("fileUpload") MultipartFile file, Authentication authentication, Model model) throws IOException{
        // System.out.println("some very complex upload logic");
        
        try {
            fileService.upload(file, authentication.getName());
            removeUploadError(model);
        }
        catch (IllegalArgumentException e){
            model.addAttribute("upload_success_msg", e.getMessage());
        }

        populateFileList(model, authentication);

        model.addAttribute("activeTab", "files");
        return "home";
    }
}
