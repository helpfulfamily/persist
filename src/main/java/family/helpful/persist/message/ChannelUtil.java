package family.helpful.persist.message;

import family.helpful.persist.message.model.Channel;
import family.helpful.persist.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@Component
public class ChannelUtil {
    @Autowired
    ChannelRepository channelRepository;
    public List saveAll(List<Channel> entities) {

        List<Channel> result = new ArrayList();
        Iterator var3 = entities.iterator();

        while(var3.hasNext()) {
            Channel entity =(Channel) var3.next();
            Channel entityFromDb= channelRepository.findByName(entity.getName());
            if(entityFromDb==null){
                entity = channelRepository.save(entity);
            }else{
                entity= entityFromDb;
            }

            result.add(entity);
        }

        return result;
    }
}
