package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
public class CredentialController {
    private final CredentialService credentialService;
    private final UserService userService;
    private final EncryptionService encryptionService;

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

    private void removeUploadError(Model model){
        model.addAttribute("upload_success_msg", null);
    }

    public CredentialController(CredentialService credentialService, UserService userService, EncryptionService encryptionService){
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/pwd/edit")
    public String get_home_view(@RequestParam("fileId") Integer fileId, Model model, Authentication authentication) {
        populateCredentialList(model, authentication);
        removeUploadError(model);
        return "home";
    }

    @GetMapping("/pwd/upload")
    public String get_home_upload(Model model, Authentication authentication) {
        populateCredentialList(model, authentication);
        return "home";
    }

    @GetMapping("/pwd/delete")
    public String get_home_delete(@RequestParam("noteid") Integer noteid, Model model, Authentication authentication) {
        populateCredentialList(model, authentication);
        removeUploadError(model);
        return "home";
    }

    @PostMapping("/pwd/update")
    public String view(@RequestParam("pwdid") Integer pwdid,
                        @RequestParam("url") String url,
                        @RequestParam("username") String username, 
                        @RequestParam("password") String password, 
                        Model model, 
                        Authentication authentication
    ) {
        System.out.println("Update Credential");

        User user = userService.getUser(authentication.getName());
        removeUploadError(model);
        String key = getEncryptionKey(user.getPassword());
        password = encryptionService.encryptValue(password, key);
        credentialService.updateCredential(pwdid, url, username, password, pwdid);
        
        populateCredentialList(model, authentication);
        model.addAttribute("activeTab", "credentials");
        return "home";
    }

    @PostMapping("/pwd/edit")
    public String editNote(@RequestParam Integer pwdid, Model model, Authentication authentication) {
        System.out.println("Edit Credential");
        populateCredentialList(model, authentication);

        User user = userService.getUser(authentication.getName());
        Credential cipherCred = credentialService.getById(pwdid);
        String key = getEncryptionKey(user.getPassword());
        String plainCred = encryptionService.decryptValue(cipherCred.getPasswordcipher(), key);

        cipherCred.setPasswordcipher(plainCred);
        model.addAttribute("editCredential", cipherCred);

        model.addAttribute("activeTab", "credentials");
        return "home";
    }

    @PostMapping("/pwd/delete")
    public String delete(@RequestParam("pwdid") Integer pwdid, Model model, Authentication authentication) {
        credentialService.deleteById(credentialService.getById(pwdid));
        populateCredentialList(model, authentication);
        removeUploadError(model);

        model.addAttribute("activeTab", "credentials");
        return "home";
    }

    private String getEncryptionKey(String user_pwd_here){
        byte[] user_pwd_here_bytes = Arrays.copyOf(user_pwd_here.getBytes(), 16); // aes assumes exactly 16 bytes
        String encoded_key = Base64.getEncoder().encodeToString(user_pwd_here_bytes);
        return encoded_key;
    }
    
    @PostMapping("/pwd/upload")
    public String upload(
        @RequestParam("url") String url,
        @RequestParam("username") String username,
        @RequestParam("password") String password,
        Authentication authentication, 
        Model model
    ) throws IOException{
        
        System.out.println("ADDING PWD");

        // get the user pwd from this website as a encryption key -> that key we have to know when decrypting the credentials
        //          and its no good if we use a random key...
        
        User user = userService.getUser(authentication.getName());
        String user_pwd_here = user.getPassword(); 
        String encoded_key = getEncryptionKey(user_pwd_here);
        
        password = encryptionService.encryptValue(password, encoded_key);
        Credential credential = new Credential();
        credential.setPasswordcipher(password);
        credential.setUrlfield(url);
        credential.setUsernamefield(username);
        credential.setUserid(user.getUserId());

        credentialService.addCredential(credential);
        removeUploadError(model);
        populateCredentialList(model, authentication);

        model.addAttribute("activeTab", "credentials");
        return "home";
    }
}
