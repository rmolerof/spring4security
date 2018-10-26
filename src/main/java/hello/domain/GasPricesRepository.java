package hello.domain;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GasPricesRepository extends MongoRepository<GasPricesDao, Long>, GasPricesRepositoryCustom {

}
