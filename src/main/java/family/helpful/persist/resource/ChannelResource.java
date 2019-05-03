package family.helpful.persist.resource;



import family.helpful.persist.message.model.Channel;
import family.helpful.persist.message.model.ChannelMessage;
 import family.helpful.persist.repository.ChannelRepository;
import family.helpful.persist.repository.ProblemContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/channel")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChannelResource {

    @Autowired
    ChannelRepository channelRepository;
    @Autowired
    ProblemContentRepository problemContentRepository;

    @GetMapping(value = "/all/{amount}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ChannelMessage getAll(@PathVariable int amount) {
        Pageable pageWithAmountofElements = PageRequest.of(0, amount);

        ChannelMessage channelMessage= new ChannelMessage();

        channelMessage.setChannelList(channelRepository.getAllWithAmount(pageWithAmountofElements));

        return channelMessage;
    }
    @GetMapping(value = "/{channelName}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Channel getChannel(@PathVariable String channelName) {
        Channel channel = (Channel) channelRepository.findByName(channelName);
        return channel;
    }

}
