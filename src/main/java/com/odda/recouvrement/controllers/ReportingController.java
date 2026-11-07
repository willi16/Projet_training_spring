package com.odda.recouvrement.controllers;

import com.odda.recouvrement.services.ReportingService;
import com.odda.recouvrement.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static com.odda.recouvrement.utils.AppConstant.API_BASE_URL;

@RestController
@RequestMapping(API_BASE_URL + "reporting")
@RequiredArgsConstructor
public class ReportingController {

    private final ReportingService reportingService;

    @GetMapping("/encaissement/jour")
    public ResponseEntity<BaseResponse<BigDecimal>> getEncaissementJour(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(BaseResponse.<BigDecimal>builder()
                .data(reportingService.getEncaissementDuJour(date))
                .statusCode(200)
                .description("Encaissement du jour récupéré.")
                .build());
    }

    @GetMapping("/taux-recouvrement")
    public ResponseEntity<BaseResponse<Double>> getTauxRecouvrement() {
        return ResponseEntity.ok(BaseResponse.<Double>builder()
                .data(reportingService.getTauxRecouvrementGlobal())
                .statusCode(200)
                .description("Taux de recouvrement global récupéré.")
                .build());
    }

    @GetMapping("/impayes-par-classe")
    public ResponseEntity<BaseResponse<Map<String, BigDecimal>>> getImpayesParClasse() {
        return ResponseEntity.ok(BaseResponse.<Map<String, BigDecimal>>builder()
                .data(reportingService.getImpayesParClasse())
                .statusCode(200)
                .description("Impayés par classe récupérés.")
                .build());
    }
}