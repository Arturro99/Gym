package pl.lodz.p.it.restapi.controllerImplementation;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.core.domain.Diet;
import pl.lodz.p.it.core.port.primary.DietServicePort;
import pl.lodz.p.it.restapi.controller.DietsApiDelegate;
import pl.lodz.p.it.restapi.dto.DietDetailsResponse;
import pl.lodz.p.it.restapi.dto.DietRequestPost;
import pl.lodz.p.it.restapi.dto.DietRequestPut;
import pl.lodz.p.it.restapi.dto.DietResponse;
import pl.lodz.p.it.restapi.mapper.diet.DietDetailsResponseMapper;
import pl.lodz.p.it.restapi.mapper.diet.DietRequestPostMapper;
import pl.lodz.p.it.restapi.mapper.diet.DietRequestPutMapper;
import pl.lodz.p.it.restapi.mapper.diet.DietResponseMapper;

@RestController
@AllArgsConstructor
public class DietController implements DietsApiDelegate {

    private final DietServicePort dietServicePort;

    private final DietResponseMapper dietResponseMapper;

    private final DietDetailsResponseMapper dietDetailsResponseMapper;

    private final DietRequestPostMapper dietRequestPostMapper;

    private final DietRequestPutMapper dietRequestPutMapper;

    @Override
    public ResponseEntity<DietDetailsResponse> getDiet(String number) {
        return ResponseEntity
            .ok(dietDetailsResponseMapper.toDtoModel(dietServicePort.find(number)));
    }

    @Override
    public ResponseEntity<List<DietResponse>> getDiets() {
        return ResponseEntity.ok(dietServicePort.findAll().stream()
            .map(dietResponseMapper::toDtoModel)
            .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<DietDetailsResponse> createDiet(DietRequestPost dietRequestPost) {
        Diet diet = dietRequestPostMapper.toDomainModel(dietRequestPost);
        Diet saved = dietServicePort.save(diet);
        URI location = URI.create(String.format("/diets/%s", saved.getNumber()));
        return ResponseEntity.created(location)
            .body(dietDetailsResponseMapper.toDtoModel(saved));
    }

    @Override
    public ResponseEntity<DietDetailsResponse> updateDiet(String number,
        DietRequestPut dietRequestPut) {
        Diet diet = dietRequestPutMapper.toDomainModel(dietRequestPut);
        Diet updated = dietServicePort.update(number, diet);
        return ResponseEntity.ok(dietDetailsResponseMapper.toDtoModel(updated));
    }

    @Override
    public ResponseEntity<Void> deleteDiet(String number) {
        dietServicePort.delete(number);
        return ResponseEntity.ok().build();
    }
}
