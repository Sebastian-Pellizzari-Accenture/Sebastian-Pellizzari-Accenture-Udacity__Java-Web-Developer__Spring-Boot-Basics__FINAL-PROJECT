package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Delete("DELETE FROM FILES WHERE fileId=#{fileId} AND userid=#{userid}")
    int delete(File file);

    @Delete("DELETE FROM FILES WHERE fileId=#{fileId}")
    int deleteById(Integer fileId);

    @Select("SELECT * FROM FILES WHERE filename=#{filename} AND userid=#{userid}")
    File view(File file);

    @Select("SELECT * FROM FILES WHERE fileId=#{fileId}")
    File getFileById(Integer fileId);

    @Select("SELECT * FROM FILES WHERE userid=#{userid}")
    List<File> getAllUserFiles(Integer userid);
}
