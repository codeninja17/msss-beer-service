package guru.sfg.beerservice.bootstrap;

import guru.sfg.beerservice.repositories.BeerRepository;
import guru.sfg.beerservice.domain.Beer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by jt on 2019-05-17.
 */
@Component
public class BeerLoader implements CommandLineRunner {

    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";

    public static final UUID BEER_1_UUID = UUID.fromString("0a818933-087d-47f2-ad83-2f986ed087eb");
    public static final UUID BEER_2_UUID = UUID.fromString("a712d914-61ea-4623-8bd0-32c0f6545bfd");
    public static final UUID BEER_3_UUID = UUID.fromString("026cc3c8-3a0c-4083-a05b-e908048c1b08");

    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBeerObjects();
    }

    private void loadBeerObjects() {
        if(beerRepository.count() == 0){

            beerRepository.save(Beer.builder()
                    .id(BEER_1_UUID)
                    .beerName("Mango Bobs")
                    .beerStyle("IPA")
                    .quantityToBrew(200)
                    .quantityOnHand(19)
                    .upc(BEER_1_UPC)
                    .price(new BigDecimal("12.95"))
                    .build());

            beerRepository.save(Beer.builder()
                    .id(BEER_2_UUID)
                    .beerName("Galaxy Cat")
                    .beerStyle("PALE_ALE")
                    .quantityToBrew(200)
                    .quantityOnHand(12)
                    .upc(BEER_2_UPC)
                    .price(new BigDecimal("11.85"))
                    .build());

            beerRepository.save(Beer.builder()
                    .id(BEER_3_UUID)
                    .beerName("No Hammers On The Bar")
                    .beerStyle("PALE_ALE")
                    .quantityToBrew(200)
                    .quantityOnHand(18)
                    .upc(BEER_3_UPC)
                    .price(new BigDecimal("11.95"))
                    .build());
        }
    }
}
