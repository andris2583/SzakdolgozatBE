package com.szte.szakdolgozat;

import com.szte.szakdolgozat.controller.CategoryController;
import com.szte.szakdolgozat.controller.ImageController;
import com.szte.szakdolgozat.models.Category;
import com.szte.szakdolgozat.models.Image;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.szte.szakdolgozat.util.Constants.IMAGE_PATH;

@SpringBootApplication
@EnableMongoRepositories
public class SzakdolgozatApplication {


	public static void main(String[] args) {
		SpringApplication.run(SzakdolgozatApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(ImageController imageController, MongoTemplate mongoTemplate, CategoryController categoryController){
		return args -> {

//			//INIT DB
//
//			File folder = new File("C:/Users/Andras/Desktop/Egyetem/Szakdolgozat/BackEnd/szakdolgozat/src/main/resources/mock-imgs/");
//			File[] listOfFiles = folder.listFiles();;
//			for (int i = 0; i!= listOfFiles.length; i++){
//				String name = listOfFiles[i].getName();
//				Image image = new Image();
//				image.setName(name);
//				image.setUploaded(new Date(System.currentTimeMillis()));
//				image.setLocation("Szeged");
//				byte[] fileContent = new byte[0];
//				try {
//					fileContent = FileUtils.readFileToByteArray(new File("C:/Users/Andras/Desktop/Egyetem/Szakdolgozat/BackEnd/szakdolgozat/src/main/resources/mock-imgs/"+name));
//				} catch (IOException e) {
//					throw new RuntimeException(e);
//				}
//				String encodedString = Base64.getEncoder().encodeToString(fileContent);
//				image.setImgB64("data:image/png;base64,"+encodedString);
//				imageController.insertImage(image);
//			}
//
//			//ASSIGN RANDOM CATEGORIES TO IMAGES
//
//
//			List<String> categories = new ArrayList<>(List.of("equable", "poor", "giddy", "bored", "hypnotic", "ruddy", "violent", "lowly", "flashy", "difficult", "breezy", "receptive", "ambiguous", "faulty", "combative", "hapless", "curvy", "invincible", "acceptable", "jazzy", "ratty", "stupid", "evanescent", "old", "full", "obsolete", "heartbreaking", "encouraging", "paltry", "oafish")).stream().map(n -> n.replaceFirst(String.valueOf(n.charAt(0)),String.valueOf(n.charAt(0)).toUpperCase())).collect(Collectors.toList());
//			List<Image> images = imageController.getAllImages();
//			for(Image image : images){
//				String cat1 = categories.get(new Random().nextInt(categories.size()));
//				categories.remove(cat1);
//				String cat2 = categories.get(new Random().nextInt(categories.size()));
//				categories.add(cat1);
//				image.setCategories(new ArrayList<>(List.of(cat1,cat2)));
//				imageController.updateImage(image);
//			}
//
//			for (String cat : categories){
//				Category category = new Category();
//				category.setName(cat);
//				categoryController.insertCategory(category);
//			}

		};
	}

}
