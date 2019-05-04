package family.helpful.persist.message.consumer;


import family.helpful.persist.actions.EnumActionStatus;
import family.helpful.persist.message.model.ObservationRequestSignal;

import family.helpful.persist.message.model.User;

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
import org.springframework.messaging.MessagingException;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import javax.transaction.Transactional;


@EnableBinding({Processor.class})
public class ObservationListener
{
    @Autowired
    private Processor processor;

    @Autowired
    UserRepository userRepository;


    private static final Logger logger = LoggerFactory.getLogger(ObservationListener.class);

    @PersistenceContext
    private EntityManager em;

    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = { Exception.class })
    @StreamListener(target = Sink.INPUT,  condition = "headers['action'] == 'sendObservationRequestSignal'")
    public void sendObservationRequestSignal(Message<ObservationRequestSignal> message){

        ObservationRequestSignal observationRequestSignal= message.getPayload();


        User  observer= userRepository.findByUsername(observationRequestSignal.getObserverUsername());


        Long observerUserId= observer.getId();

        Long channelId= observationRequestSignal.getChannelId();



        StoredProcedureQuery query = this.em.createStoredProcedureQuery("sendObservationRequestSignal");

        query.registerStoredProcedureParameter("observerUserId", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("channelId", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("lastObserverAmountChannel", Long.class, ParameterMode.OUT);

        query.setParameter("observerUserId",  observerUserId);
        query.setParameter("channelId", channelId);


        Long lastObserverAmountChannel= (Long) query.getOutputParameterValue("lastObserverAmountChannel");
        observationRequestSignal.setCurrentObserverAmount(lastObserverAmountChannel);
   //     transactionRepository.save(transaction);
        Message resultMessage= MessageBuilder.withPayload(observationRequestSignal)
                .setHeader( "action", "sendObservationRequestSignal")
                .setHeader( "actionStatus", EnumActionStatus.SUCCESS.name())
                .build();

        processor.output().send(resultMessage);

    }

}
