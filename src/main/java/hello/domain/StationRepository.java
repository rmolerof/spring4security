package hello.domain;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StationRepository extends MongoRepository<StationDao, Long>, StationRepositoryCustom {

}
