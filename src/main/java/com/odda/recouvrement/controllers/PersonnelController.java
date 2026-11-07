package com.odda.recouvrement.controllers;

import com.odda.recouvrement.dto.requests.PersonnelRequest;
import com.odda.recouvrement.dto.responses.PersonnelResponse;
import com.odda.recouvrement.services.PersonnelService;
import com.odda.recouvrement.utils.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.odda.recouvrement.utils.AppConstant.API_BASE_URL;

@RestController
@RequestMapping(API_BASE_URL + "personnels")
@RequiredArgsConstructor
public class PersonnelController {

    private final PersonnelService personnelService;

    @PostMapping
    public ResponseEntity<BaseResponse<PersonnelResponse>> create(@Valid @RequestBody PersonnelRequest personnelReq) {
        return ResponseEntity.status(201).body(
            BaseResponse.<PersonnelResponse>builder()
                .data(personnelService.save(personnelReq))
                .statusCode(201)
                .description("Personnel créé avec succès.")
                .build()
        );
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<PersonnelResponse>>> getAll() {
        return ResponseEntity.ok(
            BaseResponse.<List<PersonnelResponse>>builder()
                .data(personnelService.getAll())
                .statusCode(200)
                .description("Liste des personnels récupérée.")
                .build()
        );
    }

    @GetMapping("/{trackingId}")
    public ResponseEntity<BaseResponse<PersonnelResponse>> getOne(@PathVariable UUID trackingId) {
        return ResponseEntity.ok(
            BaseResponse.<PersonnelResponse>builder()
                .data(personnelService.getOne(trackingId))
                .statusCode(200)
                .description("Personnel récupéré.")
                .build()
        );
    }

    @PutMapping("/{trackingId}")
    public ResponseEntity<BaseResponse<PersonnelResponse>> update(@PathVariable UUID trackingId, @Valid @RequestBody PersonnelRequest personnelReq) {
        return ResponseEntity.ok(
            BaseResponse.<PersonnelResponse>builder()
                .data(personnelService.update(trackingId, personnelReq))
                .statusCode(200)
                .description("Personnel mis à jour.")
                .build()
        );
    }

    @DeleteMapping("/{trackingId}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable UUID trackingId) {
        personnelService.delete(trackingId);
        return ResponseEntity.ok(
            BaseResponse.<Void>builder()
                .statusCode(200)
                .description("Personnel désactivé (soft delete).")
                .build()
        );
    }
}