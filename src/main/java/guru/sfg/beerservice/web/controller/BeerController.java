package guru.sfg.beerservice.web.controller;

import guru.sfg.beerservice.services.BeerService;
import guru.sfg.brewery.model.BeerDto;
import guru.sfg.brewery.model.BeerPagedList;
import guru.sfg.brewery.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by jt on 2019-05-12.
 */
@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

    private final BeerService beerService;

    private static int DEFAULT_PAGE_NUMBER = 0;
    private static int DEFAULT_PAGE_SIZE = 10;

    @GetMapping
    public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value = "pageSize",required = false) Integer pageSize,
                                                   @RequestParam(value = "pageNumber",required = false) Integer pageNumber,
                                                   @RequestParam(value = "beerName",required = false) String beerName,
                                                   @RequestParam(value = "beerStyle",required = false) BeerStyleEnum beerStyle,
                                                   @RequestParam(value = "showInventoryOnHand",required = false) Boolean showInventoryOnHand){

        if(null == pageNumber || pageNumber < 0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        if(null == pageSize || pageSize <0){
            pageSize = DEFAULT_PAGE_SIZE;
        }

        return new ResponseEntity<BeerPagedList>(beerService.listBeers(PageRequest.of(pageNumber,pageSize),
                beerName,beerStyle,showInventoryOnHand),HttpStatus.OK);

    }

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId){
        return new ResponseEntity<>(beerService.getById(beerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveNewBeer(@RequestBody @Validated BeerDto beerDto){
        return new ResponseEntity<>(beerService.saveNewBeer(beerDto), HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity updateBeerById(@PathVariable("beerId") UUID beerId, @RequestBody @Validated BeerDto beerDto){
        return new ResponseEntity<>(beerService.updateBeer(beerId, beerDto), HttpStatus.NO_CONTENT);
    }

}
