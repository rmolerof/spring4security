package hello.domain;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RUCsRepository extends MongoRepository<RUCDao, Long> {
	
	RUCDao findFirstByRuc(String ruc);
}
