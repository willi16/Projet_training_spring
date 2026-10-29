package com.odda.recouvrement.controllers;

import com.odda.recouvrement.dto.requests.ParentRequest;
import com.odda.recouvrement.dto.responses.ParentResponse;
import com.odda.recouvrement.services.ParentService;
import com.odda.recouvrement.utils.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import static com.odda.recouvrement.utils.AppConstant.API_BASE_URL;


@RestController
@RequestMapping(API_BASE_URL + "parents")
@RequiredArgsConstructor
public class ParentController {

    private final ParentService parentService;

    @PostMapping
    public ResponseEntity<BaseResponse<ParentResponse>> save(@Valid @RequestBody ParentRequest parentReq) {
        ParentResponse response = parentService.save(parentReq);
        return ResponseEntity.status(201).body(
            BaseResponse.<ParentResponse>builder()
                .data(response)
                .statusCode(201)
                .description("Parent enregistré avec succès")
                .build()
        );
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<ParentResponse>>> getAll() {
        return ResponseEntity.ok(
            BaseResponse.<List<ParentResponse>>builder()
                .data(parentService.getAll())
                .statusCode(200)
                .description("Liste des parents récupérée")
                .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ParentResponse>> getOne(@PathVariable UUID trackingId) {
        return ResponseEntity.ok(
            BaseResponse.<ParentResponse>builder()
                .data(parentService.getOne(trackingId))
                .statusCode(200)
                .description("Parent récupéré")
                .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<ParentResponse>> update(@PathVariable UUID trackingId, @Valid @RequestBody ParentRequest parentReq) {
        return ResponseEntity.ok(
            BaseResponse.<ParentResponse>builder()
                .data(parentService.update(trackingId, parentReq))
                .statusCode(200)
                .description("Parent mis à jour avec succès")
                .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable UUID trackingId) {
        parentService.delete(trackingId);
        return ResponseEntity.ok(
            BaseResponse.<Void>builder()
                .statusCode(200)
                .description("Parent supprimé avec succès")
                .build()
        );
    }
}