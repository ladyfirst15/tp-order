package myProject_LSP;

import myProject_LSP.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }
    @Autowired
    OrderRepository orderRepository;
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverCookQtyChecked_CookCancelUpdate(@Payload CookQtyChecked cookQtyChecked){

        if(cookQtyChecked.isMe()){
            System.out.println("##### listener CookCancelUpdate : " + cookQtyChecked.toJson());

            Optional<Order> orderOptional = orderRepository.findById(cookQtyChecked.getOrderId());
            Order order = orderOptional.get();
            order.setStatus(cookQtyChecked.getStatus());
            orderRepository.save(order);
        }
    }

}
