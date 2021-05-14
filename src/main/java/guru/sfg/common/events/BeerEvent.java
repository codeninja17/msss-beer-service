package guru.sfg.common.events;

import guru.sfg.beerservice.web.model.BeerDto;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerEvent implements Serializable {

    static final long serialVersionUID = -7274264193267392243L;

    private BeerDto beerDto;




}
