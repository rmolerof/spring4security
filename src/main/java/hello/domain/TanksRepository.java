package hello.domain;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TanksRepository extends MongoRepository<TanksDao, Long>, TanksRepositoryCustom {

}
