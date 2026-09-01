package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;

@Mapper
public interface CredentialMapper {
    @Delete("DELETE FROM CREDENTIALS WHERE pwdid=#{pwdid} AND userid=#{userid}")
    int delete(Credential credential);

    @Update("UPDATE CREDENTIALS SET pwdid = #{pwdid}, urlfield = #{urlfield}, usernamefield = #{usernamefield}, " + 
            "passwordcipher = #{passwordcipher}, userid = #{userid} WHERE pwdid = #{pwdid} AND userid = #{userid}")
    int updateCredential(Credential credential);

    @Insert("INSERT INTO CREDENTIALS (urlfield, usernamefield, passwordcipher, userid ) " +
            "VALUES(#{urlfield}, #{usernamefield}, #{passwordcipher}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "pwdid")
    int insert(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE pwdid=#{pwdid}")
    Credential getCredentialById(Integer pwdid);

    @Select("SELECT * FROM CREDENTIALS WHERE userid=#{userid}")
    List<Credential> getAllUserCredentials(Integer userid);        
}
