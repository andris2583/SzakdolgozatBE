package com.szte.szakdolgozat.controller;

import com.szte.szakdolgozat.model.Collection;
import com.szte.szakdolgozat.model.CollectionType;
import com.szte.szakdolgozat.model.Image;
import com.szte.szakdolgozat.service.CollectionService;
import com.szte.szakdolgozat.service.ImageService;
import com.szte.szakdolgozat.service.TagService;
import com.szte.szakdolgozat.util.ImageUtils;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/dashboard")
public class DashboardController {
    private final ImageService imageService;
    private final TagService tagService;
    private final CollectionService collectionService;


    private static final String[][] seasonalTags =
            {
                    {"Christmas", "Snow", "Ice", "Frost", "Winter"},
                    {"Breakfast", "Yellow", "Food", "Spring", "Interaction"},
                    {"Sunlight", "Sunrise", "Beach", "Vacation", "Sand"},
                    {"Vegetable", "Produce", "Autumn", "Wildlife", "Wilderness"}
            };


    private static final Integer[] seasons = {
            0, 0, 1, 1, 1, 2,
            2, 2, 3, 3, 3, 0
    };

    @GetMapping("/getSeasonalTags")
    public String[] getSeasonalTags() {
        return seasonalTags[seasons[new Date().getMonth()]];
    }

    @PutMapping("/getFavouriteTags")
    public String[] getFavouriteTags(@RequestBody String userId) {
        Collection favouriteCollection = this.collectionService.getAllCollections().stream()
                .filter(collection -> Objects.equals(collection.getUserId(), userId) && collection.getType().equals(CollectionType.FAVOURITE))
                .toList().get(0);
        List<Image> favouriteImages = imageService.getAllImages();
        favouriteImages = favouriteImages.stream().filter(tempImage -> favouriteCollection.getImageIds().contains(tempImage.getId())).collect(Collectors.toList());
        if (favouriteImages.size() == 0) {
            return new String[0];
        }
        Map<String, Integer> favouriteTags = new HashMap<>();
        favouriteImages.forEach(favouriteImage -> {
            favouriteImage.getTags().forEach(favouriteTag -> {
                if (favouriteTags.containsKey(favouriteTag)) {
                    favouriteTags.put(favouriteTag, favouriteTags.get(favouriteTag) + 1);
                } else {
                    favouriteTags.put(favouriteTag, 1);
                }
            });
        });
        Map<String, Integer> finalFavouriteTags = sortByValue(favouriteTags);
        if (finalFavouriteTags.size() == 0) {
            return new String[]{};
        } else {
            return new String[]{
                    new ArrayList<String>(finalFavouriteTags.keySet()).get(0),
                    new ArrayList<String>(finalFavouriteTags.keySet()).get(1),
                    new ArrayList<String>(finalFavouriteTags.keySet()).get(2),
                    new ArrayList<String>(finalFavouriteTags.keySet()).get(3),
                    new ArrayList<String>(finalFavouriteTags.keySet()).get(4),
            };
        }
    }

    @PutMapping("/getSimilarToUserImages")
    public List<Image> getSimilarToUserImages(@RequestBody String userId) {
        List<Image> allImages = this.imageService.getAllImages();
        List<Image> userImages = allImages.stream().filter(image -> image.getOwnerId().equals(userId)).toList();
        if (userImages.size() == 0) {
            return userImages;
        }
        List<Image> notUserImages = allImages.stream().filter(image -> !image.getOwnerId().equals(userId)).toList();
        List<Image> returnImages = new ArrayList<>();
        int sameTagCount = 5;
        while (sameTagCount > 0) {
            int finalSameTagCount = sameTagCount;
            List<Image> finalReturnImages = returnImages;
            userImages.forEach(userImage -> {
                notUserImages.forEach(notUserImage -> {
                    if (userImage.getTags().stream().filter(userImageTag -> notUserImage.getTags().contains(userImageTag)).toList().size() == finalSameTagCount
                            && !finalReturnImages.contains(notUserImage)) {
                        finalReturnImages.add(notUserImage);
                    }
                });
            });
            sameTagCount--;
        }
        returnImages = returnImages.subList(0, Math.min(10, returnImages.size() - 1));
        ImageUtils.loadImageThumbnailData(returnImages);
        return returnImages;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);
        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

}
