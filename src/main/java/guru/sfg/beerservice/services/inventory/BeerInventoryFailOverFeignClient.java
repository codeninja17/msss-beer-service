package guru.sfg.beerservice.services.inventory;

import guru.sfg.beerservice.services.inventory.model.BeerInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "inventory-failover")
public interface BeerInventoryFailOverFeignClient {

    @RequestMapping(method = RequestMethod.GET,value = "/inventory-failover")
    ResponseEntity<List<BeerInventoryDto>> getOnHandInventory();

}
