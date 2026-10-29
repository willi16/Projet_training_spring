package com.odda.recouvrement.services;

import java.util.List;
import java.util.UUID;

import com.odda.recouvrement.dto.requests.ApprenantRequest;
import com.odda.recouvrement.dto.responses.ApprenantResponse;

public interface ApprenantService {

   ApprenantResponse save(ApprenantRequest apprenantRequest);
    
   List<ApprenantResponse> getAll();

   ApprenantResponse getOne(UUID trackingId);

   ApprenantResponse update(UUID trackingId ,ApprenantRequest apprenantRequest );

   void delete(UUID trackingId);


} 
