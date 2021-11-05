package pl.lodz.p.it.restapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.core.domain.Diet;
import pl.lodz.p.it.core.port.primary.DietServicePort;
import pl.lodz.p.it.restapi.dto.DietRequestPost;
import pl.lodz.p.it.restapi.dto.DietRequestPut;
import pl.lodz.p.it.restapi.dto.DietResponse;
import pl.lodz.p.it.restapi.mapper.diet.DietRequestPostMapper;
import pl.lodz.p.it.restapi.mapper.diet.DietRequestPutMapper;
import pl.lodz.p.it.restapi.mapper.diet.DietResponseMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class DietController implements DietsApiDelegate {

    private final DietServicePort dietServicePort;

    private final DietResponseMapper dietResponseMapper;

    private final DietRequestPostMapper dietRequestPostMapper;

    private final DietRequestPutMapper dietRequestPutMapper;

    @Override
    public ResponseEntity<DietResponse> getDiet(String number) {
        return ResponseEntity.ok(dietResponseMapper.toDtoModel(dietServicePort.find(number)));
    }

    @Override
    public ResponseEntity<List<DietResponse>> getDiets() {
        return ResponseEntity.ok(dietServicePort.findAll().stream()
                .map(dietResponseMapper::toDtoModel)
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<DietResponse> createDiet(DietRequestPost dietRequestPost) {
        Diet diet = dietRequestPostMapper.toDomainModel(dietRequestPost);
        Diet saved = dietServicePort.save(diet);
        return ResponseEntity.ok(dietResponseMapper.toDtoModel(saved));
    }

    @Override
    public ResponseEntity<DietResponse> updateDiet(String number, DietRequestPut dietRequestPut) {
        Diet diet = dietRequestPutMapper.toDomainModel(dietRequestPut);
        Diet updated = dietServicePort.update(number, diet);
        return ResponseEntity.ok(dietResponseMapper.toDtoModel(updated));
    }

    @Override
    public ResponseEntity<Void> deleteDiet(String number) {
        dietServicePort.delete(number);
        return ResponseEntity.ok().build();
    }
}
