package family.helpful.persist.message.consumer;


import family.helpful.persist.actions.EnumActionStatus;
 import family.helpful.persist.message.model.Transaction;
import family.helpful.persist.message.model.User;
import family.helpful.persist.repository.ProblemContentRepository;
import family.helpful.persist.repository.ProblemTitleRepository;
import family.helpful.persist.repository.TransactionRepository;
import family.helpful.persist.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.sql.DataSource;


@EnableBinding({Processor.class})
public class TransactionListener
{
    @Autowired
    private Processor processor;

    @Autowired
    ProblemTitleRepository problemTitleRepository;

    @Autowired
    ProblemContentRepository problemContentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    private DataSource dataSource;

    private static final Logger logger = LoggerFactory.getLogger(TransactionListener.class);

    @PersistenceContext
    private EntityManager em;


    @StreamListener(target = Sink.INPUT,  condition = "headers['action'] == 'sendThankCoin'")
    public void sendThankCoin(Message<Transaction> message) {

        Transaction transaction= message.getPayload();

        User sender= transaction.getSender();
        sender= userRepository.findByUsername(sender.getUsername());
        transaction.setSender(sender);

        User receiver= transaction.getReceiver();
        receiver= userRepository.findByUsername(receiver.getUsername());
        transaction.setSender(receiver);

        StoredProcedureQuery query = this.em.createStoredProcedureQuery("startTransactionProcess", Transaction.class);

        query.registerStoredProcedureParameter("senderId", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("receiverId", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("objectId", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("objectType", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("name", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("amount", Long.class, ParameterMode.IN);

        query.setParameter("senderId",   sender.getId());
        query.setParameter("receiverId", receiver.getId());
        query.setParameter("objectId",   transaction.getObjectId());
        query.setParameter("objectType",   transaction.getObjectType());
        query.setParameter("name",   transaction.getName());
        query.setParameter("amount", 1L);

        transaction= (Transaction) query.getSingleResult();

   //     transactionRepository.save(transaction);
        Message resultMessage= MessageBuilder.withPayload(transaction)
                .setHeader( "action", "sendThankCoin")
                .setHeader( "actionStatus", EnumActionStatus.SUCCESS.name())
                .build();

        processor.output().send(resultMessage);
    }

}
