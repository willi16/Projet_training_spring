package com.odda.recouvrement.controllers;


import com.odda.recouvrement.dto.requests.ClasseRequest;
import com.odda.recouvrement.dto.responses.ClasseResponse;
import com.odda.recouvrement.services.ClasseService;
import com.odda.recouvrement.utils.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import static com.odda.recouvrement.utils.AppConstant.API_BASE_URL;


@RestController
@RequestMapping(API_BASE_URL + "classes")
@RequiredArgsConstructor
public class ClasseController {

    private final ClasseService classeService;

    @PostMapping
    public ResponseEntity<BaseResponse<ClasseResponse>> save(@Valid @RequestBody ClasseRequest classeReq) {
        return ResponseEntity.status(201).body(BaseResponse.<ClasseResponse>builder().data(classeService.save(classeReq)).statusCode(201).description("Classe créée avec succès").build());
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<ClasseResponse>>> getAll() {
        return ResponseEntity.ok(BaseResponse.<List<ClasseResponse>>builder().data(classeService.getAll()).statusCode(200).description("Liste des classes récupérée").build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ClasseResponse>> getOne(@PathVariable UUID trackingId) {
        return ResponseEntity.ok(BaseResponse.<ClasseResponse>builder().data(classeService.getOne(trackingId)).statusCode(200).description("Classe récupérée").build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<ClasseResponse>> update(@PathVariable UUID trackingId, @Valid @RequestBody ClasseRequest classeReq) {
        return ResponseEntity.ok(BaseResponse.<ClasseResponse>builder().data(classeService.update(trackingId, classeReq)).statusCode(200).description("Classe mise à jour").build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable UUID trackingId) {
        classeService.delete(trackingId);
        return ResponseEntity.ok(BaseResponse.<Void>builder().statusCode(200).description("Classe supprimée").build());
    }
}


