package es.narriaza.rsocket.server.zero.controller;

import es.narriaza.rsocket.server.zero.controller.dto.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class RSocketController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RSocketController.class);

    @MessageMapping("request-response")
    Message requestResponse(Message request){
        LOGGER.info("Received request-response message {}", request);
        return new Message("nikoServer","ResponseFromRequest");
    }

}
