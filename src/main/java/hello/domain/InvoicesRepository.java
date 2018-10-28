package hello.domain;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvoicesRepository extends MongoRepository<InvoiceDao, Long>, InvoicesRepositoryCustom {

}
