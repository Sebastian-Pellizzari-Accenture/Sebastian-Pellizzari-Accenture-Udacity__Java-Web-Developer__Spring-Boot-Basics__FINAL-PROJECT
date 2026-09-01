package com.udacity.jwdnd.course1.cloudstorage;

import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.udacity.jwdnd.course1.cloudstorage.model.User;

@SpringBootApplication
@MappedTypes(User.class)
@MapperScan("com.udacity.jwdnd.course1.cloudstorage.mapper")
public class CloudStorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudStorageApplication.class, args);
	}

}
