package guru.sfg.beerservice.services;

import guru.sfg.beerservice.repositories.BeerRepository;
import guru.sfg.beerservice.domain.Beer;
import guru.sfg.beerservice.web.controller.NotFoundException;
import guru.sfg.beerservice.web.mappers.BeerMapper;
import guru.sfg.brewery.model.BeerDto;
import guru.sfg.brewery.model.BeerPagedList;
import guru.sfg.brewery.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by jt on 2019-06-06.
 */
@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false ")
    @Override
    public BeerDto getById(UUID beerId) {
        return beerMapper.beerToBeerDto(
                beerRepository.findById(beerId).orElseThrow(NotFoundException::new)
        );
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return beerMapper.beerToBeerDto(beerRepository.save(beer));
    }

    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false ")
    @Override
    public BeerPagedList listBeers(Pageable pageRequest, String beerName, BeerStyleEnum beerStyle, Boolean showInventoryOnHand) {

        Page<Beer> pagedBeer;

        if(!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)){
           pagedBeer = beerRepository.getBeerByBeerNameAndBeerStyle(beerName,beerStyle,pageRequest);
        }
        else if(!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)){
            pagedBeer = beerRepository.getBeerByBeerName(beerName,pageRequest);
        }
        else if(StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)){
            pagedBeer = beerRepository.getBeerByBeerStyle(beerStyle,pageRequest);
        }
        else {
            pagedBeer = beerRepository.findAll(pageRequest);
        }
        List<BeerDto> beerList ;
        BeerPagedList beerPagedList;
        if(showInventoryOnHand!=null && showInventoryOnHand){
           beerList = pagedBeer.getContent().stream()
                    .map(beerMapper::beerToBeerDto).collect(Collectors.toList());
           beerPagedList = new BeerPagedList(beerList,pageRequest,pagedBeer.getTotalElements());
        }
        else{
            beerList = pagedBeer.getContent().stream()
                    .map(beerMapper::beerToBeerDtoWithoutInventory).collect(Collectors.toList());
            beerPagedList = new BeerPagedList(beerList,pageRequest,pagedBeer.getTotalElements());
        }
        return  beerPagedList;
    }

    @Cacheable(cacheNames = "beerUpcCache")
    @Override
    public BeerDto getByUpc(String upc) {
        return beerMapper.beerToBeerDto(beerRepository.findByUpc(upc));
    }
}
