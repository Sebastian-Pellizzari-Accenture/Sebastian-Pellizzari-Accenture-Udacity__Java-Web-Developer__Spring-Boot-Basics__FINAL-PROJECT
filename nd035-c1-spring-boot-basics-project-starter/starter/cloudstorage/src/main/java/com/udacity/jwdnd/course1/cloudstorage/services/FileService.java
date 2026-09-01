package com.udacity.jwdnd.course1.cloudstorage.services;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

import ch.qos.logback.core.util.FileSize;

@Service
public class FileService {
    private final UserService userService;
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.userService = userService;
        this. fileMapper = fileMapper;
    }
    
    public void upload(MultipartFile multipartFile, String username) throws IOException, IllegalArgumentException {
        // Required information of user
        User user = userService.getUser(username);
        Integer userid = user.getUserId();

        // Required information of file
        String filename = multipartFile.getOriginalFilename();
        String contenttype = multipartFile.getContentType();
        String filesize = String.valueOf(multipartFile.getSize());
        byte[] filedata = multipartFile.getBytes();

        // push file onto the database
        File file = new File();
        file.setFilename(filename);
        file.setContenttype(contenttype);
        file.setFilesize(filesize);
        file.setUserid(userid);
        file.setFiledata(filedata);

        // check if the file already exists for this user. --> display an error if it does
        File file_in_db = fileMapper.view(file);
        if (file_in_db != null) {
            throw new IllegalArgumentException("[ERROR] File" + filename + "already exists for this user.\n Delete the old file before proceeding!");
        }
        else if (Integer.parseInt(filesize) == 0 && filename == "") {
            throw new IllegalArgumentException("[ERROR] Attempt of uploading an empty file was blocked!");
        }
        else fileMapper.insert(file);
    }

    public void deleteById(Integer fileId){
        fileMapper.deleteById(fileId);
    }

    public File getFileById(Integer fileId){
        return fileMapper.getFileById(fileId);
    }

    public List<File> getAllUserFiles(Integer userid){
        return fileMapper.getAllUserFiles(userid);
    }
}
