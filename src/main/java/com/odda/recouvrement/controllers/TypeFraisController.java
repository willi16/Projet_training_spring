package com.odda.recouvrement.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.odda.recouvrement.dto.requests.TypeFraisRequest;
import com.odda.recouvrement.dto.responses.TypeFraisResponse;
import com.odda.recouvrement.services.TypeFraisService;
import com.odda.recouvrement.utils.BaseResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static com.odda.recouvrement.utils.AppConstant.API_BASE_URL;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController 
@RequestMapping (API_BASE_URL + "types-frais")
@RequiredArgsConstructor 
public class TypeFraisController {
    
    private final TypeFraisService typeService;

    @PostMapping
    public ResponseEntity<BaseResponse<TypeFraisResponse>> save(@Valid @RequestBody TypeFraisRequest typeReq) {
                
        return ResponseEntity.status(201).body(
            BaseResponse.<TypeFraisResponse>builder()
                .data(typeService.save(typeReq))
                .statusCode(201)
                .description("Type de frais créé.")
                .build()
        );
    }


    @GetMapping
    public ResponseEntity<BaseResponse<List<TypeFraisResponse>>> getAll() {
        return ResponseEntity.ok(
            BaseResponse.<List<TypeFraisResponse>>builder()
                .data(typeService.getAll())
                .statusCode(200)
                .description("Liste recupérée")
                .build()
        );  
    }


    @GetMapping("/{trackingId}")
    public ResponseEntity<BaseResponse<TypeFraisResponse>> getOne(@PathVariable UUID trackingId) {
        return ResponseEntity.ok(
            BaseResponse.<TypeFraisResponse>builder()
                .data(typeService.getOne(trackingId))
                .statusCode(200)
                .description("Détail récupéré.")
                .build()
        );
    }


    @PutMapping("/{trackingId}")
    public ResponseEntity<BaseResponse<TypeFraisResponse>> update(@PathVariable UUID trackingId, @Valid @RequestBody TypeFraisRequest typeReq) {
        return ResponseEntity.ok(
            BaseResponse.<TypeFraisResponse>builder()
            .data(typeService.update(trackingId, typeReq))
            .statusCode(200)
            .description("Mis à jour.")
            .build()
        );
    }
    


    @DeleteMapping("/{trackingId}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable UUID trackingId) {
        typeService.delete(trackingId);
        return ResponseEntity.ok(
            BaseResponse.<Void>builder()
            .statusCode(200)
            .description("Supprimé.")
            .build()
        );
    }
}
