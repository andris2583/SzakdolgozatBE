package com.szte.szakdolgozat.util;

import com.szte.szakdolgozat.controller.*;
import com.szte.szakdolgozat.model.Collection;
import com.szte.szakdolgozat.model.*;
import com.szte.szakdolgozat.model.auth.User;
import com.szte.szakdolgozat.model.auth.UserRoleEnum;
import com.szte.szakdolgozat.model.auth.payload.SignupRequest;
import com.szte.szakdolgozat.model.request.BatchImageRequest;
import com.szte.szakdolgozat.service.ImageViewMapService;
import com.szte.szakdolgozat.service.TagService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DatabaseGenerator {
    private static List<String> userNames = List.of("Cosmo Dumuzid", "Pachamama Douglas", "Daedalus Wangi", "Henri Silva", "Kiril Theofilus", "Naveed Yaritza", "Rúnar Øyvind", "Bláthnat Fumihito", "Eithne Sigurd", "Deror Lucrécia");
    private static List<String> tagIds = new ArrayList<>();
    private static Map<String, String> tagIdNameMap = new HashMap<>();

    private static void initTagIdNameMap() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Andras\\Desktop\\Egyetem\\Szakdolgozat\\BackEnd\\szakdolgozat\\src\\main\\resources\\tagger\\class-descriptions.csv"));
        String line = null;
        while ((line = br.readLine()) != null) {
            String str[] = line.split(",");
            tagIdNameMap.put(str[0], str[1]);
        }
    }

    private static void initTagIds() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Andras\\Desktop\\Egyetem\\Szakdolgozat\\BackEnd\\szakdolgozat\\src\\main\\resources\\tagger\\classes-trainable.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            tagIds.add(line);
        }
        br.close();
    }

    public static void run(
            ImageController imageController,
            CollectionController collectionController,
            UserController userController,
            TagController tagController,
            AuthController authController,
            ImageViewMapService imageViewMapService,
            TagService tagService
    ) {
        try {
            initTagIds();
            initTagIdNameMap();
            //USERS
            userNames.forEach(username -> {
                SignupRequest signupRequest = new SignupRequest();
                signupRequest.setUsername(username);
                signupRequest.setPassword(username);
                signupRequest.setEmail(username + "@gmail.com");
                signupRequest.setRole(Set.of(UserRoleEnum.ROLE_USER.toString()));
                authController.registerUser(signupRequest);
            });
            //IMAGES
            List<User> users = userController.getAll();
            List<Image> images = imageController.getAllImages(new BatchImageRequest());
            ImageViewMap map = new ImageViewMap();
            images.forEach(image -> {
                image.setOwnerId(users.get(new Random().nextInt(0, 10)).getId());
                imageController.updateImage(image);
                map.getImageViewMap().put(image.getId(), new Random().nextInt(0, 20));
                List<String> newTags = new ArrayList<>(image.getTags());
                newTags.removeAll(tagService.getAllTags().stream().map(Tag::getName).toList());
                if (newTags.size() != 0) {
                    for (String tagName : newTags) {
                        Tag tag = new Tag();
                        tag.setName(tagName);
                        tagService.insertTag(tag);
                    }
                }
            });
            imageViewMapService.saveImageViewMap(map);
            //COLLECTIONS
            users.forEach(user -> {
                for (int i = 0; i < 5; i++) {
                    Set<String> imageIds = new HashSet<>();
                    for (int j = 0; j < 10; j++) {
                        imageIds.add(images.get(new Random().nextInt(0, images.size())).getId());
                    }
                    //TODO add list of images to it
                    Collection collection = new Collection(tagIdNameMap.values().stream().toList()
                            .get(new Random().nextInt(0, 5000)), imageIds, user.getId(),
                            new Random().nextInt(0, 10) < 5 ? Privacy.PUBLIC : Privacy.PRIVATE, CollectionType.CUSTOM);
                    collectionController.insertCollection(collection);
                }
            });
            System.out.println("DB done");
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
