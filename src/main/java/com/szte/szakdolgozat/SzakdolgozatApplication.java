package com.szte.szakdolgozat;

import com.szte.szakdolgozat.controller.ImageController;
import com.szte.szakdolgozat.service.ImageService;
import com.szte.szakdolgozat.service.TagService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class SzakdolgozatApplication {


    public static void main(String[] args) {
        SpringApplication.run(SzakdolgozatApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(ImageController imageController, MongoTemplate mongoTemplate, TagService tagService, ImageService imageService) {
        return args -> {
//            var images = imageService.getAllImages();
//            for (com.szte.szakdolgozat.model.Image image : images) {
//                image.setImgB64(null);
//                image.setOwnerId("63b8413b8af8d21ef3c81859");
//                imageService.saveImage(image);
//            }
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
//			List<String> tags = new ArrayList<>(List.of("equable", "poor", "giddy", "bored", "hypnotic", "ruddy", "violent", "lowly", "flashy", "difficult", "breezy", "receptive", "ambiguous", "faulty", "combative", "hapless", "curvy", "invincible", "acceptable", "jazzy", "ratty", "stupid", "evanescent", "old", "full", "obsolete", "heartbreaking", "encouraging", "paltry", "oafish")).stream().map(n -> n.replaceFirst(String.valueOf(n.charAt(0)),String.valueOf(n.charAt(0)).toUpperCase())).collect(Collectors.toList());
//			List<Image> images = imageController.getAllImages();
//			for(Image image : images){
//				String cat1 = tags.get(new Random().nextInt(tags.size()));
//				tags.remove(cat1);
//				String cat2 = tags.get(new Random().nextInt(tags.size()));
//				tags.add(cat1);
//				image.setCategories(new ArrayList<>(List.of(cat1,cat2)));
//				imageController.updateImage(image);
//			}
//
//			for (String cat : tags){
//				Category tag = new Category();
//				tag.setName(cat);
//				tagController.insertCategory(tag);
//			}
            //TAG IMAGES IN DB AND INSERT THOSE TAGS
//			List<Image> imageList = imageController.getAllImages("all");
//			Set<String> tags = new HashSet<>() {
//			};
//			for (Image image : imageList){
//				List<String> imgTags = imageController.generateTags(image);
//				image.setTags(imgTags);
//				imageController.updateImage(image);
//				tags.addAll(imgTags);
//			}
//
//			for (String tagname : tags){
//				Tag tag = new Tag();
//				tag.setName(tagname);
//				tagService.insertTag(tag);
//			}
//			System.out.println("Finished");
        };
    }

}
