package com.odda.recouvrement.services;

import com.odda.recouvrement.dto.requests.NiveauRequest;
import com.odda.recouvrement.dto.responses.NiveauResponse;
import java.util.List;
import java.util.UUID;

public interface NiveauService {
    NiveauResponse save(NiveauRequest niveauReq);
    List<NiveauResponse> getAll();
    NiveauResponse getOne(UUID trackingId);
    NiveauResponse update(UUID trackingId, NiveauRequest niveauReq);
    void delete(UUID trackingId);
}
