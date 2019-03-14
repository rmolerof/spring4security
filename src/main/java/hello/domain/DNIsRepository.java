package hello.domain;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DNIsRepository extends MongoRepository<DNIDao, Long> {
	
	DNIDao findFirstByDni(String dni);
}
