package guru.sfg.beerservice.services.order.validation;

import guru.sfg.beerservice.domain.Beer;
import guru.sfg.beerservice.repositories.BeerRepository;
import guru.sfg.brewery.model.BeerOrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@RequiredArgsConstructor
public class BeerOrderValidator {

    private final BeerRepository beerRepository;

    public Boolean validateBeerOrder(BeerOrderDto beerOrderDto){
        AtomicInteger beersNotFound = new AtomicInteger(0);
        beerOrderDto.getBeerOrderLines().stream().forEach(beerOrderLineDto -> {
            if(beerRepository.getOne(UUID.fromString(beerOrderLineDto.getUpc())) == null){
                    beersNotFound.incrementAndGet();
            }
        });
        return beersNotFound.get() == 0 ;
    }

}
