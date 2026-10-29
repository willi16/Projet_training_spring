package com.odda.recouvrement.services;

import com.odda.recouvrement.dto.requests.AnneeScolaireRequest;
import com.odda.recouvrement.dto.responses.AnneeScolaireResponse;
import java.util.List;
import java.util.UUID;

public interface AnneeScolaireService {
    AnneeScolaireResponse save(AnneeScolaireRequest anneeReq);
    List<AnneeScolaireResponse> getAll();
    AnneeScolaireResponse getOne(UUID trackingId);
    AnneeScolaireResponse update(UUID trackingId, AnneeScolaireRequest anneeReq);
    void delete(UUID trackingId);
}