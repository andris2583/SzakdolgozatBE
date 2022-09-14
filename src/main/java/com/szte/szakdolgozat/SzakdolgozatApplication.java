package com.szte.szakdolgozat;

import com.szte.szakdolgozat.controller.ImageController;
import com.szte.szakdolgozat.controller.ThumbnailController;
import com.szte.szakdolgozat.models.Image;
import com.szte.szakdolgozat.models.Thumbnail;
import com.szte.szakdolgozat.service.ImageService;
import com.szte.szakdolgozat.service.ThumbnailService;
import com.szte.szakdolgozat.util.ThumbnailGenerator;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.szte.szakdolgozat.util.Constants.IMAGE_PATH;
import static com.szte.szakdolgozat.util.Constants.THUMBNAIL_PATH;

@SpringBootApplication
@EnableMongoRepositories
public class SzakdolgozatApplication {


	public static void main(String[] args) {
		SpringApplication.run(SzakdolgozatApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(ImageController imageController, MongoTemplate mongoTemplate){
		return args -> {
//			File folder = new File(IMAGE_PATH);
//			File[] listOfFiles = folder.listFiles();;
//			for (int i = 0; i!= listOfFiles.length; i++){
//				String name = listOfFiles[i].getName();
//				Image image = new Image();
//				image.setName(name);
//				image.setUploaded(new Date(System.currentTimeMillis()));
//				image.setLocation("Szeged");
//				byte[] fileContent = new byte[0];
//				try {
//					fileContent = FileUtils.readFileToByteArray(new File(IMAGE_PATH+name));
//				} catch (IOException e) {
//					throw new RuntimeException(e);
//				}
//				String encodedString = Base64.getEncoder().encodeToString(fileContent);
//				image.setImgB64("data:image/png;base64,"+encodedString);
//				imageController.insertImage(image);
//			}

		};
	}

}
