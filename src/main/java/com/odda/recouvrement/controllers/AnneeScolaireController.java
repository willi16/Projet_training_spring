package com.odda.recouvrement.controllers;

import com.odda.recouvrement.dto.requests.AnneeScolaireRequest;
import com.odda.recouvrement.dto.responses.AnneeScolaireResponse;
import com.odda.recouvrement.services.AnneeScolaireService;
import com.odda.recouvrement.utils.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import static com.odda.recouvrement.utils.AppConstant.API_BASE_URL;


@RestController
@RequestMapping(API_BASE_URL + "annees-scolaires")
@RequiredArgsConstructor
public class AnneeScolaireController {
    private final AnneeScolaireService service;

    @PostMapping
    public ResponseEntity<BaseResponse<AnneeScolaireResponse>> save(@Valid @RequestBody AnneeScolaireRequest req) {
        return ResponseEntity.status(201).body(BaseResponse.<AnneeScolaireResponse>builder().data(service.save(req))
                .statusCode(201).description("Année créée").build());
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<AnneeScolaireResponse>>> getAll() {
        return ResponseEntity.ok(BaseResponse.<List<AnneeScolaireResponse>>builder().data(service.getAll())
                .statusCode(200).description("Liste récupérée").build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<AnneeScolaireResponse>> getOne(@PathVariable UUID trackingId) {
        return ResponseEntity.ok(BaseResponse.<AnneeScolaireResponse>builder().data(service.getOne(trackingId)).statusCode(200)
                .description("Détail récupéré").build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<AnneeScolaireResponse>> update(@PathVariable UUID trackingId,
            @Valid @RequestBody AnneeScolaireRequest req) {
        return ResponseEntity.ok(BaseResponse.<AnneeScolaireResponse>builder().data(service.update(trackingId, req))
                .statusCode(200).description("Année mise à jour").build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable UUID trackingId) {
        service.delete(trackingId);
        return ResponseEntity.ok(BaseResponse.<Void>builder().statusCode(200).description("Année supprimée").build());
    }
}
