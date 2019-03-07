package hello.domain;
import java.util.Date;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TanksRepository extends MongoRepository<TanksDao, Long>, TanksRepositoryCustom {
	
	TanksDao findFirstByDate(Date date);
}
