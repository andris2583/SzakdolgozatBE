package com.szte.szakdolgozat;

import com.szte.szakdolgozat.controller.*;
import com.szte.szakdolgozat.service.ImageViewMapService;
import com.szte.szakdolgozat.service.TagService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class SzakdolgozatApplication {


    public static void main(String[] args) {
        SpringApplication.run(SzakdolgozatApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(ImageController imageController, CollectionController collectionController, UserController userController, TagController tagController, AuthController authController, ImageViewMapService imageViewMapService, TagService tagService) {
        return args -> {
            //INIT imageViewMap
//            var map = new HashMap<String, Integer>();
//            imageService.getAllImages().forEach(image -> {
//                map.put(image.getId(), 0);
//            });
//            var imageViewMap = new ImageViewMap();
//            imageViewMap.setImageViewMap(map);
//            imageViewMapService.insertImageViewMap(imageViewMap);
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
//            List<Image> images = imageService.getAllImages();
//            for (Image image : images) {
//                File fi = new File(IMAGE_PATH + image.getIdWithExtension());
//                byte[] data = Files.readAllBytes(fi.toPath());
//                image.getProperties().put("size", data.length);
//                imageService.saveImage(image);
//            }
//            imageService.getAllImages().forEach(image -> {
////                63bad48193e8d914434184ee -> 640dc6aa9aad2c0a02c9ae4d
////                640dc5402bff6b1e3af0e141 -> 640dc6bd9aad2c0a02c9ae4f
//                if (Objects.equals(image.getOwnerId(), "640dc6bd9aad2c0a02c9ae4f")) {
//                    image.setOwnerId("640dc8afc8b9971febbb5677");
//                    imageService.saveImage(image);
//                }
////                if (Objects.equals(image.getOwnerId(), "63f0e44713df4436bec21418")) {
////                    image.setOwnerId("640dc6bd9aad2c0a02c9ae4f");
////                    imageService.saveImage(image);
////                }
//            });
//            collectionService.getAllCollections().forEach(collection -> {
//                if (Objects.equals(collection.getUserId(), "640dc6bd9aad2c0a02c9ae4f")) {
//                    collection.setUserId("640dc8afc8b9971febbb5677");
//                    collectionService.saveCollection(collection);
//                }
////                if (Objects.equals(collection.getUserId(), "63f0e44713df4436bec21418")) {
////                    collection.setUserId("640dc6bd9aad2c0a02c9ae4f");
////                    collectionService.saveCollection(collection);
////                }
//            });
//            System.out.println("Finished");
//            DatabaseGenerator.run(imageController, collectionController, userController, tagController, authController, imageViewMapService, tagService);
        };
    }

}
