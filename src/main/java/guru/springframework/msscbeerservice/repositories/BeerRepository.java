package guru.springframework.msscbeerservice.repositories;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

/**
 * Created by jt on 2019-05-17.
 */
public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {

    Page<Beer> getBeerByBeerName(String beerName, Pageable pageRequest);

    Page<Beer> getBeerByBeerNameAndBeerStyle(String beerName,BeerStyleEnum beerStyle,Pageable pageRequest);

    Page<Beer> getBeerByBeerStyle(BeerStyleEnum beerStyle,Pageable pageRequest);


}
