package guru.sfg.beerservice.repositories;

import guru.sfg.beerservice.domain.Beer;
import guru.sfg.brewery.model.BeerStyleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by jt on 2019-05-17.
 */
public interface BeerRepository extends JpaRepository<Beer, UUID> {

    Page<Beer> getBeerByBeerName(String beerName, Pageable pageRequest);

    Page<Beer> getBeerByBeerNameAndBeerStyle(String beerName,BeerStyleEnum beerStyle,Pageable pageRequest);

    Page<Beer> getBeerByBeerStyle(BeerStyleEnum beerStyle,Pageable pageRequest);

    Beer findByUpc(String upc);


}
