package family.helpful.persist.resource;


import family.helpful.persist.message.model.*;
import family.helpful.persist.repository.DialogContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping(value = "/dialog")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DialogResource {


    @Autowired
    DialogContentRepository dialogContentRepository;


    @GetMapping(value = "/contents/{senderID}/{receiverID}/{pageNumber}")
    public DialogContentMessage  getContentsBySenderAndReceiver(@PathVariable String senderID,
                                                    @PathVariable String receiverID,
                                                    @PathVariable int pageNumber) {
        Pageable pageWithAmountofElements = PageRequest.of(pageNumber, 10);

        DialogContentMessage contentMessage= new DialogContentMessage();



        List<DialogContent> contentList=  dialogContentRepository.findContentsBySenderAndReceiver(senderID,
                                                                                                    receiverID,
                                                                                                pageWithAmountofElements);

        if(contentList!=null){
                Collections.reverse(contentList);
         }
        contentMessage.setContents(contentList);

        return contentMessage;
    }


}
