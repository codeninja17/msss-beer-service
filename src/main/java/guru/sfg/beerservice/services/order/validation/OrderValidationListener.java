package guru.sfg.beerservice.services.order.validation;

import guru.sfg.beerservice.config.JmsConfig;
import guru.sfg.brewery.model.BeerOrderDto;
import guru.sfg.brewery.model.events.ValidateBeerOrderRequest;
import guru.sfg.brewery.model.events.ValidateOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderValidationListener  {

    private final JmsTemplate jmsTemplate;
    private final BeerOrderValidator beerOrderValidator;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER)
    public void validateOrder(@Payload ValidateBeerOrderRequest validateBeerOrderRequest){
        Boolean isValid = beerOrderValidator.validateBeerOrder(validateBeerOrderRequest.getBeerOrderDto());

        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESULT, ValidateOrderResult
                .builder().isValid(isValid).beerOrderId(validateBeerOrderRequest.getBeerOrderDto().getId()).build());

        log.info("Order Validated for order id {} , {}",validateBeerOrderRequest.getBeerOrderDto().getId(),isValid);

    }

}
