package com.odda.recouvrement.services;

import java.util.List;
import java.util.UUID;

import com.odda.recouvrement.dto.requests.TypeFraisRequest;
import com.odda.recouvrement.dto.responses.TypeFraisResponse;

public interface TypeFraisService {
   
    TypeFraisResponse save(TypeFraisRequest req);
    List<TypeFraisResponse> getAll();
    TypeFraisResponse getOne(UUID trackingId);
    TypeFraisResponse update(UUID trackingId, TypeFraisRequest req);
    void delete(UUID trackingId);
}
