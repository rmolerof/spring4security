package corp.services;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import corp.domain.InvoiceSequences;

@Service
public class NextSequenceInvoiceService {
    @Autowired private MongoOperations mongo;

    public Long getNextSequence(String seqName)
    {
    	InvoiceSequences counter = mongo.findAndModify(
            query(where("_id").is(seqName)),
            new Update().inc("seq",1),
            options().returnNew(true).upsert(true),
            InvoiceSequences.class);
        return counter.getSeq();
    }
}
