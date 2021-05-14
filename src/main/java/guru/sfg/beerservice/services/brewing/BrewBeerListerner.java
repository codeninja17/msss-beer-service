package guru.sfg.beerservice.services.brewing;


import guru.sfg.beerservice.config.JmsConfig;
import guru.sfg.beerservice.domain.Beer;
import guru.sfg.brewery.model.events.BrewBeerEvent;
import guru.sfg.brewery.model.events.NewInventoryEvent;
import guru.sfg.beerservice.repositories.BeerRepository;
import guru.sfg.brewery.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewBeerListerner {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void brewBeer(@Payload BrewBeerEvent brewBeerEvent){
        BeerDto beerDto = brewBeerEvent.getBeerDto();

        Beer beer = beerRepository.getOne(beerDto.getId());

        beerDto.setQuantityOnHand(beer.getQuantityToBrew());

        NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);

        log.debug("Brewed beer " + beer.getQuantityOnHand() + " : QOH: " + beerDto.getQuantityOnHand());

        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);

    }

}
