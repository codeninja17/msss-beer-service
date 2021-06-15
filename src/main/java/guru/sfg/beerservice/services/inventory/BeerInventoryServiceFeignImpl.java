package guru.sfg.beerservice.services.inventory;

import guru.sfg.beerservice.services.inventory.model.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Profile("local-discovery")
@Service
@Slf4j
@RequiredArgsConstructor
public class BeerInventoryServiceFeignImpl implements BeerInventoryService{

    private final BeerInventoryFeignClient beerInventoryFeignClient;

    @Override
    public Integer getOnhandInventory(UUID beerId) {
        log.debug("Calling Inventory Service");

        ResponseEntity<List<BeerInventoryDto>> responseEntity = beerInventoryFeignClient.getOnHandInventory(beerId);
        //sum from inventory list
        Integer onHand = Objects.requireNonNull(responseEntity.getBody())
                .stream()
                .mapToInt(BeerInventoryDto::getQuantityOnHand)
                .sum();

        return onHand;
    }
}
