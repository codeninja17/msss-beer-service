package guru.sfg.beerservice.web.mappers;

import guru.sfg.beerservice.domain.Beer;
import guru.sfg.beerservice.web.model.BeerDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

/**
 * Created by jt on 2019-05-25.
 */
@Mapper(uses = {DateMapper.class})
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);

    BeerDto beerToBeerDtoWithoutInventory(Beer beer);

    Beer beerDtoToBeer(BeerDto dto);
}
