package family.helpful.persist.message.consumer;


import family.helpful.persist.actions.EnumActionStatus;
import family.helpful.persist.message.ChannelUtil;
import family.helpful.persist.message.model.*;
import family.helpful.persist.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;



@EnableBinding({Processor.class})
public class ChannelListener
{
    @Autowired
    private Processor processor;

    @Autowired
    ChannelRepository channelRepository;


    @Autowired
    ChannelContentRepository channelContentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChannelUtil channelUtil;

    private static final Logger logger = LoggerFactory.getLogger(ChannelListener.class);

    @StreamListener(target = Sink.INPUT,  condition = "headers['action'] == 'publishChannelContent'")
    public void publishChannelContent(Message<ChannelContent> message) {
        logger.info("publishChannelContent:", message);

        ChannelContent channelContent= message.getPayload();
        User user= channelContent.getUser();
        user= userRepository.findByUsername(user.getUsername());

        channelContent.setUser(user);

        Channel channel= channelRepository.findByName(channelContent.getChannel().getName());



        channelContent.setCurrentThankAmount(0L);


        if(channel==null){

            channel=channelContent.getChannel();
            logger.info("'"+ channelContent.getName()+"' is a new title");

            channel.setCurrentThankAmount(0L);

            channel= channelRepository.save(channel);
            channelContent.setFirstContent(true);

        }else{


             channelContent.setFirstContent(false);
        }

        channelContent.setChannel(channel);

        channelContent= channelContentRepository.save(channelContent);

        //TODO: Websocket relation.

         Message resultMessage= MessageBuilder.withPayload(channelContent)
                .setHeader( "action", "publishChannelContent")
                .setHeader( "actionStatus", EnumActionStatus.SUCCESS.name())
                .build();

        processor.output().send(resultMessage);
    }


    @StreamListener(target = Sink.INPUT,  condition = "headers['action'] == 'createChannel'")
    public void createChannel(Message<Channel> familyObjectMessage) {
        logger.info("createChannel:", familyObjectMessage.getPayload().getName());

        Channel channelObject= familyObjectMessage.getPayload();
        User user= channelObject.getUser();
        user= userRepository.findByUsername(user.getUsername());

        channelObject.setUser(user);

        channelObject= channelRepository.save(channelObject);


        Message resultMessage= MessageBuilder.withPayload(channelObject)
                .setHeader( "action", "createChannel")
                .setHeader( "actionStatus", EnumActionStatus.SUCCESS.name())
                .build();

        processor.output().send(resultMessage);
    }
}
