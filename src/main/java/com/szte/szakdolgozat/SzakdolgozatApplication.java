package com.szte.szakdolgozat;

import com.szte.szakdolgozat.service.ImageService;
import com.szte.szakdolgozat.service.ThumbnailService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

import static com.szte.szakdolgozat.util.Constants.IMAGE_PATH;
import static com.szte.szakdolgozat.util.Constants.THUMBNAIL_PATH;

@SpringBootApplication
@EnableMongoRepositories
public class SzakdolgozatApplication {


	public static void main(String[] args) {
		SpringApplication.run(SzakdolgozatApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(ThumbnailService thumbnailService, ImageService imageService, MongoTemplate mongoTemplate){
		return args -> {
//			Generate thumbnails
			List<String> names = List.of("pexels-achoos-philip-5997079.jpg", "pexels-alex-conchillos-3732950.jpg", "pexels-anastasia-koren-9536419.jpg", "pexels-arthouse-studio-4338103.jpg", "pexels-arthouse-studio-4344434.jpg", "pexels-arthouse-studio-4344435.jpg", "pexels-ave-calvar-martinez-3040083.jpg", "pexels-ben-mack-5707732.jpg", "pexels-chait-goli-2666607.jpg", "pexels-chait-goli-2666611.jpg", "pexels-chris-f-9522822.jpg", "pexels-cottonbro-3737639.jpg", "pexels-cottonbro-7038114.jpg", "pexels-curioso-photography-343685.jpg", "pexels-dalila-dalprat-2300712.jpg", "pexels-dominika-roseclay-2282481.jpg", "pexels-ekaterina-nt-9961807.jpg", "pexels-engin-akyurt-1435598.jpg", "pexels-erik-mclean-4061602.jpg", "pexels-eva-bronzini-5769796.jpg", "pexels-eva-bronzini-6161874.jpg", "pexels-grape-things-2647933.jpg", "pexels-hanna-yurouskaya-9732259.jpg", "pexels-hasan-albari-1493079.jpg", "pexels-hatice-noğman-7961670.jpg", "pexels-jill-burrow-5987168.jpg", "pexels-jill-burrow-5987212.jpg", "pexels-jill-burrow-6858673.jpg", "pexels-juraj-valkovic-2912388.jpg", "pexels-lucas-alves-9524941.jpg", "pexels-luis-zheji-1662284.jpg", "pexels-lukas-kloeppel-2416596.jpg", "pexels-maksim-romashkin-6151862.jpg", "pexels-maria-orlova-4969832.jpg", "pexels-maria-orlova-4969844.jpg", "pexels-markus-spiske-360592.jpg", "pexels-markus-spiske-3806764.jpg", "pexels-matheus-bertelli-4558481.jpg", "pexels-matheus-bertelli-4558579.jpg", "pexels-matheus-bertelli-4558580.jpg", "pexels-mathias-reding-6966055.jpg", "pexels-monicore-141876.jpg", "pexels-monstera-6621221.jpg", "pexels-nadexriotic-3640870.jpg", "pexels-nadexriotic-3640872.jpg", "pexels-nima-gerivani-6094219.jpg", "pexels-oyster-haus-11631746.jpg", "pexels-pavel-danilyuk-7938056.jpg", "pexels-pixabay-46188.jpg", "pexels-pixabay-248412.jpg", "pexels-rachel-claire-4819718.jpg", "pexels-rachel-claire-4819792.jpg", "pexels-rachel-claire-4846237.jpg", "pexels-rachel-claire-4846586.jpg", "pexels-sam-majid-6149151.jpg", "pexels-sarah-chai-7262453.jpg", "pexels-sides-imagery-3336922.jpg", "pexels-tima-miroshnichenko-5698160.jpg", "pexels-valdemaras-d-1752356.jpg", "pexels-yauheni-kopach-6017449.jpg", "pexels-zariflavin-🌼-3512785.jpg");
//			for(String name : names){
//				ThumbnailGenerator.generateThumbnail(name,0.1f);
//			}

//			//Insert images/thumbnails
			List<String> thumbnailUrls = names.stream().map(val -> THUMBNAIL_PATH + val).toList();
			List<String> imgUrls = names.stream().map(val -> IMAGE_PATH + val).toList();
//			for (int i = 0; i < 1; i++) {
//				for(String url : imgUrls){
//					Image image = new Image();
//					image.setName(url.split("/")[url.split("/").length-1]);
//
//					imageService.insertImage(image);
//				}
//				for(String url : thumbnailUrls){
//					Thumbnail thumbnail = new Thumbnail();
//					thumbnail.setName(url.split("/")[url.split("/").length-1]);
//					Query query = new Query();
//					query.addCriteria(Criteria.where("name").is(thumbnail.getName()));
//
//					Image image = mongoTemplate.find(query,Image.class).get(0);
//					thumbnail.setImageID(image.getId());
//
//					thumbnailService.insertThumbnail(thumbnail);
//				}
//			}
		};
	}

}
