package com.odda.recouvrement.services;

import com.odda.recouvrement.dto.requests.AuthRequest;
import com.odda.recouvrement.dto.responses.AuthResponse;



public interface AuthService {

    public AuthResponse login(AuthRequest authReq) ;
}