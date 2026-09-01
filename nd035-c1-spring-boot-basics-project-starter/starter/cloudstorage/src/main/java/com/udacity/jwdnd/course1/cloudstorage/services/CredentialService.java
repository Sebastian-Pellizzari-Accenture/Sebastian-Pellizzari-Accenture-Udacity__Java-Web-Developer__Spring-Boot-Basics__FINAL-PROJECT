package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;

@Service
public class CredentialService {
    CredentialMapper credentialMapper;
    

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public void addCredential(Credential credential){
        credentialMapper.insert(credential);
    }

    public List<Credential> getAllUserCredentials(Integer userId) {
        List<Credential> credentials = credentialMapper.getAllUserCredentials(userId);
        return credentials;
    }

    public Credential getById(Integer pwdid) {
        return credentialMapper.getCredentialById(pwdid);
    }

    public void deleteById(Credential credential) {
        credentialMapper.delete(credential);
    }

    public void updateCredential(Integer pwdid, String urlfield, String usernamefield, String passwordcipher, Integer userid) {
        Credential credential = credentialMapper.getCredentialById(pwdid);
        credential.setUrlfield(urlfield);
        credential.setUsernamefield(usernamefield);
        credential.setPasswordcipher(passwordcipher);
        credentialMapper.updateCredential(credential);
    }
}
