package guru.sfg.beerservice.services;

import guru.sfg.beerservice.web.model.BeerDto;
import guru.sfg.beerservice.web.model.BeerPagedList;
import guru.sfg.beerservice.web.model.BeerStyleEnum;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Created by jt on 2019-06-06.
 */
public interface BeerService {
    BeerDto getById(UUID beerId);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);

    BeerPagedList listBeers(Pageable pageRequest, String beerName, BeerStyleEnum beerStyle, Boolean showInventoryOnHand);
}
