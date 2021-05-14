package guru.sfg.beerservice.services.brewing;


import guru.sfg.beerservice.config.JmsConfig;
import guru.sfg.beerservice.domain.Beer;
import guru.sfg.common.events.BrewBeerEvent;
import guru.sfg.beerservice.repositories.BeerRepository;
import guru.sfg.beerservice.services.inventory.BeerInventoryService;
import guru.sfg.beerservice.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewBeerService {

    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final BeerMapper beerMapper;
    private final JmsTemplate jmsTemplate;


    @Scheduled(fixedRate = 5000)
    public void brewBeer(){

        List<Beer> beerList = beerRepository.findAll();

        beerList.stream().forEach(beer -> {
            Integer invOnHand = beerInventoryService.getOnhandInventory(beer.getId());
            log.debug("inventory in hand : {}",invOnHand);
            log.debug("Quantity on hand : {}",beer.getQuantityOnHand());

            if(beer.getQuantityOnHand() > invOnHand){
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE,new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
            }

        });
    }

}
