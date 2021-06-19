package guru.sfg.beerservice.services.inventory;

import guru.sfg.beerservice.services.inventory.model.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BeerInventoryFailOverFeignClientImpl implements BeerInventoryFeignClient{

    private final BeerInventoryFailOverFeignClient beerInventoryFailOverFeignClient;

    @Override
    public ResponseEntity<List<BeerInventoryDto>> getOnHandInventory(UUID beerId) {
        return beerInventoryFailOverFeignClient.getOnHandInventory();
    }
}
