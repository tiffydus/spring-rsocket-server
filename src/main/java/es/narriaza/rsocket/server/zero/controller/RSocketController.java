package es.narriaza.rsocket.server.zero.controller;

import es.narriaza.rsocket.server.zero.controller.dto.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.stream.Stream;

@Controller
public class RSocketController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RSocketController.class);

    @MessageMapping("request-response")
    Message requestResponse(Message request){
        LOGGER.info("Received request-response message {}", request);
        return new Message("nikoServer","ResponseFromRequest");
    }

    @MessageMapping("request-response-flux")
    Mono<Message> requestResponseFlux(Mono<Message> request){
        LOGGER.info("Received request-response message {}", request);
        return Mono.just(new Message("nikoServer","ResponseFromRequest"));
    }

    @MessageMapping("fire-and-forget")
    Mono<Void> fireAndForget(Mono<Message> request){
        LOGGER.info("Received fire and forget message {}", request);
        // TODO do something
        return Mono.empty();
    }

    @MessageMapping("request-stream")
    public Flux<Message> requestStream(Message request){
        LOGGER.info("Received request stream message {}", request);
        // TODO do something
        var stream = Stream.generate(() -> new Message(
                "nikoServer"+request.getOrigin(),
                "Response: "+request.getInteraction()
        ));

        return Flux.fromStream(stream).checkpoint("Stream added")
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(m -> LOGGER.debug("Message: {}", m));
    }

    @MessageMapping("channel")
    Flux<Message> channel(Flux<Message> request){
        LOGGER.info("Received channel message {}", request);
        // TODO do something
        return Flux.empty();
    }

}
