package com.odda.recouvrement.controllers;


import com.odda.recouvrement.dto.requests.NiveauRequest;
import com.odda.recouvrement.dto.responses.NiveauResponse;
import com.odda.recouvrement.services.NiveauService;
import com.odda.recouvrement.utils.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import static com.odda.recouvrement.utils.AppConstant.API_BASE_URL;


@RestController
@RequestMapping(API_BASE_URL + "niveaux")
@RequiredArgsConstructor
public class NiveauController {

    private final NiveauService niveauService;

    @PostMapping
    public ResponseEntity<BaseResponse<NiveauResponse>> save(@Valid @RequestBody NiveauRequest niveauReq) {
        return ResponseEntity.status(201).body(BaseResponse.<NiveauResponse>builder().data(niveauService.save(niveauReq)).statusCode(201).description("Niveau créé avec succès").build());
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<NiveauResponse>>> getAll() {
        return ResponseEntity.ok(BaseResponse.<List<NiveauResponse>>builder().data(niveauService.getAll()).statusCode(200).description("Liste des niveaux récupérée").build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<NiveauResponse>> getOne(@PathVariable UUID trackingId) {
        return ResponseEntity.ok(BaseResponse.<NiveauResponse>builder().data(niveauService.getOne(trackingId)).statusCode(200).description("Niveau récupéré").build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<NiveauResponse>> update(@PathVariable UUID trackingId, @Valid @RequestBody NiveauRequest niveauReq) {
        return ResponseEntity.ok(BaseResponse.<NiveauResponse>builder().data(niveauService.update(trackingId, niveauReq)).statusCode(200).description("Niveau mis à jour").build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable UUID trackingId) {
        niveauService.delete(trackingId);
        return ResponseEntity.ok(BaseResponse.<Void>builder().statusCode(200).description("Niveau supprimé").build());
    }
}


