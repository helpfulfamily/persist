package family.helpful.persist.resource;



import family.helpful.persist.message.model.*;
import family.helpful.persist.repository.ChannelContentRepository;
import family.helpful.persist.repository.ChannelRepository;
import family.helpful.persist.repository.ProblemContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;


@RestController
@RequestMapping(value = "/channel")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChannelResource {

    @Autowired
    ChannelRepository channelRepository;

    @Autowired
    ChannelContentRepository channelContentRepository;

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
    @GetMapping(value = "/contents/{name}/{pageNumber}")
    public ChannelContentMessage getContentsByTitle(@PathVariable String name, @PathVariable int pageNumber) {
        Pageable pageWithAmountofElements = PageRequest.of(pageNumber, 10);

        ChannelContentMessage contentMessage= new ChannelContentMessage();
        try {
            name = java.net.URLDecoder.decode(name, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            // not going to happen - value came from JDK's own StandardCharsets
        }


        List<ChannelContent> contentList=  channelContentRepository.findByTitleWithAmount(name, pageWithAmountofElements);

        contentMessage.setContents(contentList);

        return contentMessage;
    }


}
