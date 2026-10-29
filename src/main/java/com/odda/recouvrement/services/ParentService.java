package com.odda.recouvrement.services;

import java.util.List;
import java.util.UUID;

import com.odda.recouvrement.dto.requests.ParentRequest;
import com.odda.recouvrement.dto.responses.ParentResponse;

public interface ParentService {

    ParentResponse save(ParentRequest parentReq);

    List<ParentResponse> getAll();

    ParentResponse getOne(UUID trackingId);

    ParentResponse update(UUID trackingId, ParentRequest parentReq);

    void delete(UUID trackingId);

    
    
}
