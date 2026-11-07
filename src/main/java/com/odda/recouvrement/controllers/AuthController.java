package com.odda.recouvrement.controllers;

import com.odda.recouvrement.dto.requests.AuthRequest;
import com.odda.recouvrement.dto.responses.AuthResponse;
import com.odda.recouvrement.services.implementation.AuthServiceImpl;
import com.odda.recouvrement.utils.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.odda.recouvrement.utils.AppConstant.API_BASE_URL;

@RestController
@RequestMapping(API_BASE_URL + "auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authServiceimpl;

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<AuthResponse>> login(@Valid @RequestBody AuthRequest authReq) {
        AuthResponse res = authServiceimpl.login(authReq);
        return ResponseEntity.ok(BaseResponse.<AuthResponse>builder()
                .data(res)
                .statusCode(200)
                .description("Connexion réussie.")
                .build());
    }
}