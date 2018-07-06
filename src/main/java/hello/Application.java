package hello;

import java.util.List;
import org.apache.log4j.Logger;

import org.apache.log4j.LogManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hello.domain.Domain;
import hello.domain.DomainRepository;

@SpringBootApplication
public class Application {
	
	private static Logger logger = LogManager.getLogger(Application.class);
	
	public static void main(String[] args) throws Throwable {
			SpringApplication.run(Application.class, args);
	}
	
	@Bean
	CommandLineRunner init(DomainRepository domainRepository) {
		return args -> {
			/*Domain toFind = new Domain();
			toFind.setId(new ObjectId("5b3eeccd73a7032959363d8a"));
			Domain obj = domainRepository.findOne(Example.of(toFind));
			logger.info(obj);*/
			
			//logger.info(System.getProperty("java.io.tmpdir"));
			
			
			Domain obj2 = domainRepository.findFirstByDomain("google.com");
			logger.info(obj2);
			
			Domain obj3 = domainRepository.findByDomainAndDisplayAds("youtube.com", false);
			logger.info(obj3);
			
			Domain obj4 = domainRepository.findCustomByDomain("youtube.com");
			logger.info(obj3);
			
			List<Domain> obj5List = domainRepository.findCustomByRegExDomain(".com");
			logger.info(obj5List);
			
			int n = domainRepository.updateDomain("mkyong.com", true);
			logger.info("Number of Records updated: " + n);
			
		};
	}
	
	
    //remove _class
        /*MappingMongoConverter converter =
                new MappingMongoConverter(mongoDbFactory, new MongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));*/
/*    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory,
                                       MongoMappingContext context) {

        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), context);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));

        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);

        return mongoTemplate;

    }*/
}