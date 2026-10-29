package com.odda.recouvrement.services;

import com.odda.recouvrement.dto.requests.ClasseRequest;
import com.odda.recouvrement.dto.responses.ClasseResponse;
import java.util.List;
import java.util.UUID;

public interface ClasseService {
    ClasseResponse save(ClasseRequest classeReq);
    List<ClasseResponse> getAll();
    ClasseResponse getOne(UUID trackingId);
    ClasseResponse update(UUID trackingId, ClasseRequest classeReq);
    void delete(UUID trackingId);
}