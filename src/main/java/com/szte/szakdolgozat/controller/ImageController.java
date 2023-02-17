package com.szte.szakdolgozat.controller;

import com.szte.szakdolgozat.model.Collection;
import com.szte.szakdolgozat.model.Image;
import com.szte.szakdolgozat.model.Privacy;
import com.szte.szakdolgozat.model.Tag;
import com.szte.szakdolgozat.model.request.BatchImageRequest;
import com.szte.szakdolgozat.model.request.RequestOrderType;
import com.szte.szakdolgozat.model.request.RequestTagType;
import com.szte.szakdolgozat.service.CollectionService;
import com.szte.szakdolgozat.service.ImageService;
import com.szte.szakdolgozat.service.TagService;
import com.szte.szakdolgozat.util.ImageTagger;
import com.szte.szakdolgozat.util.ImageUtils;
import lombok.AllArgsConstructor;
import net.coobird.thumbnailator.Thumbnailator;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static com.szte.szakdolgozat.util.Constants.IMAGE_PATH;
import static com.szte.szakdolgozat.util.Constants.THUMBNAIL_PATH;
import static com.szte.szakdolgozat.util.ImageUtils.loadImageThumbnailData;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;

    private final TagService tagService;
    private final ImageTagger imageTagger;
    private final CollectionService collectionService;

    @PutMapping("/getAll")
    public List<Image> getAllImages(@RequestBody BatchImageRequest request) {
        List<Image> images = imageService.getAllImages();
        //Filtering by tags
        if (request.getTags().size() != 0) {
            if (request.getRequestTagType().equals(RequestTagType.AND)) {
                images = images.stream().filter(image -> new HashSet<>(image.getTags()).containsAll(new HashSet<>(request.getTags()))).collect(Collectors.toList());
            } else {
                images = images.stream().filter(image -> image.getTags().stream().anyMatch(tag -> request.getTags().contains(tag))).collect(Collectors.toList());
            }
        }
        //Filtering by access
        if (request.getRequestUserId() != null) {
            images = images.stream().filter(image -> Objects.equals(image.getOwnerId(), request.getRequestUserId()) || image.getPrivacy() == Privacy.PUBLIC).collect(Collectors.toList());
        }
        //Filtering
        if (request.getRequestFilter() != null) {
            if (request.getRequestFilter().getNameFilterString() != null) {
                images = images.stream().filter(image -> image.getName().toLowerCase().contains(request.getRequestFilter().getNameFilterString().toLowerCase())).collect(Collectors.toList());
            }
            if (request.getRequestFilter().getMaxCount() != null) {
                images = images.subList(0, Math.min(request.getRequestFilter().getMaxCount(), images.size()));
            }
            if (request.getRequestFilter().getOwnerId() != null) {
                images = images.stream().filter(image -> Objects.equals(image.getOwnerId(), request.getRequestFilter().getOwnerId())).collect(Collectors.toList());
            }
        }
        if (request.getCollectionId() != null) {
            Collection collection = collectionService.getCollectionById(request.getCollectionId()).orElse(null);
            if (collection != null) {
                images = images.stream().filter(image -> collection.getImageIds().contains(image.getId())).collect(Collectors.toList());
            }
        }
        //Sorting
        if (request.getRequestOrderByType() != null && request.getRequestOrderType() != null) {
            switch (request.getRequestOrderByType()) {
                case TIME -> {
                    images = images.stream().sorted(Comparator.comparing(Image::getUploaded).reversed()).collect(Collectors.toList());
                }
                case ALPHABETICAL -> {
                    images = images.stream().sorted(Comparator.comparing(Image::getName).reversed()).collect(Collectors.toList());
                }
                case POPULAR -> {
                    //TODO do like ordering
                }
                case RANDOM -> {
                    Collections.shuffle(images);
                }
            }
            if (request.getRequestOrderType() == RequestOrderType.ASC) {
                Collections.reverse(images);
            }
        }
        if (request.getBatchSize() != -1) {
            int from = Math.min(request.getPageCount() * request.getBatchSize(), images.size());
            int to = Math.min(request.getPageCount() * request.getBatchSize() + request.getBatchSize(), images.size());
            images = images.subList(from, to);
        }
        loadImageThumbnailData(images);
        return images;
    }

    @GetMapping("/get/{id}")
    public Image getImageById(@PathVariable String id) {
        Image image = imageService.getImageById(id).orElse(null);
        return image;
    }

    @PutMapping("/getImagesByIds")
    public List<Image> getImagesByIds(@RequestBody List<String> imageIds) {
        List<Image> images = imageService.getAllImages();
        images = images.stream().filter(tempImage -> imageIds.contains(tempImage.getId())).collect(Collectors.toList());
        loadImageThumbnailData(images);
        return images;
    }


    @PutMapping("/insert")
    public Image insertImage(@RequestBody Image image) {
        image.setExtension(FilenameUtils.getExtension(image.getName()));
        image.setName(FilenameUtils.getBaseName(image.getName()));
        String dataString = image.getImgB64().replaceFirst("data:image/.*;base64,", "");
        byte[] data = Base64.getDecoder().decode(dataString);
        image.setImgB64(null);
        Image insertedImage = imageService.insertImage(image);
        try (OutputStream stream = new FileOutputStream(IMAGE_PATH + insertedImage.getIdWithExtension())) {
            stream.write(data);
        } catch (IOException e) {
            System.err.println(e);
        }
        try {
            BufferedImage thumbnailImage = Thumbnailator.createThumbnail(new File(IMAGE_PATH + insertedImage.getIdWithExtension()), 640, 480);
            ImageIO.write(thumbnailImage, "png", new File(THUMBNAIL_PATH + insertedImage.getId() + ".png"));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(thumbnailImage, "png", byteArrayOutputStream);
            byte[] fileContent = byteArrayOutputStream.toByteArray();
            insertedImage.setImgB64(Base64.getEncoder().encodeToString(fileContent));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String> newTags = new ArrayList<>(insertedImage.getTags());
        newTags.removeAll(tagService.getAllTags().stream().map(Tag::getName).toList());
        if (newTags.size() != 0) {
            for (String tagName : newTags) {
                Tag tag = new Tag();
                tag.setName(tagName);
                tagService.insertTag(tag);
            }
        }
        return insertedImage;
    }

    @PutMapping("/update")
    public Image updateImage(@RequestBody Image image) {
        return imageService.saveImage(image);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteImage(@PathVariable String id) {
        Image image = imageService.getImageById(id).orElse(null);
        assert image != null;
        try {
            File file = new File(IMAGE_PATH + image.getIdWithExtension());
            Files.deleteIfExists(file.toPath());
            file = new File(THUMBNAIL_PATH + image.getId() + ".png");
            Files.deleteIfExists(file.toPath());
            collectionService.getAllCollections().forEach(collection -> {
                collection.getImageIds().removeIf(tempImage -> tempImage.equals(id));
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        imageService.deleteImage(image);
    }

    @GetMapping(value = "/getImageData/{id}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getImageData(@PathVariable String id) throws IOException {
        try {
            Image image = getImageById(id);
            Path imagePath = Paths.get(IMAGE_PATH + image.getIdWithExtension());
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(imagePath));
            return ResponseEntity
                    .ok()
                    .contentLength(imagePath.toFile().length())
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/getImageDataHalfRes/{id}",
            produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> getImageDataHalfRes(@PathVariable String id) throws IOException {
        try {
            Image image = getImageById(id);
            Path imagePath = Paths.get(IMAGE_PATH + image.getIdWithExtension());
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(imagePath));
            BufferedImage fullSizeImage = ImageIO.read(new ByteArrayInputStream(resource.getByteArray()));
            BufferedImage thumbnailImage = Thumbnailator.createThumbnail(fullSizeImage, (int) (fullSizeImage.getWidth() * 0.5), (int) (fullSizeImage.getHeight() * 0.5));
            return ResponseEntity
                    .ok()
                    .contentLength(imagePath.toFile().length())
                    .contentType(MediaType.IMAGE_PNG)
                    .body(ImageUtils.toByteArray(thumbnailImage, "png"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/getImageThumbnailData/{id}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getImageThumbnailData(@PathVariable String id) throws IOException {
        try {
            Image image = getImageById(id);
            Path imagePath = Paths.get(THUMBNAIL_PATH + image.getId() + ".png");
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(imagePath));
            return ResponseEntity
                    .ok()
                    .contentLength(imagePath.toFile().length())
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/getTags")
    public List<String> getTags(@RequestBody String imageB64) {
        String dataString = imageB64.replaceFirst("data:image/.*;base64,", "");
        byte[] data = Base64.getDecoder().decode(dataString);
        return imageTagger.generateTags(data);
    }

    //TODO
    @GetMapping(value = "/getThumbnailImageForUpload/{imageB64}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getThumbnailImageForUpload(@PathVariable String imageB64) {
        try {
            String dataString = imageB64.replaceFirst("data:image/.*;base64,", "");
            byte[] data = Base64.getDecoder().decode(dataString);
            BufferedImage fullSizeImage = ImageIO.read(new ByteArrayInputStream(data));
            BufferedImage thumbnailImage = Thumbnailator.createThumbnail(fullSizeImage, 640, 480);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ImageIO.write(thumbnailImage, "jpg", byteStream);
            byte[] bytes = byteStream.toByteArray();
            return ResponseEntity
                    .ok()
                    .contentLength(bytes.length)
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping("/getSimilarImages")
    public List<Image> getSimilarImages(@RequestBody List<String> tags) {
        List<Image> images = imageService.getAllImages();
        images = images.stream().filter(image -> image.getTags().stream().distinct().filter(tags::contains).collect(Collectors.toSet()).size() > 1).collect(Collectors.toList());
        loadImageThumbnailData(images);
        return images;
    }

    @PutMapping("/getImageCountWithTag")
    public int getImageCountWithTag(@RequestBody String tag) {
        return imageService.getAllImages().stream().filter(image -> image.getTags().contains(tag)).toList().size();
    }

}
