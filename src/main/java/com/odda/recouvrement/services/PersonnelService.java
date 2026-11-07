package com.odda.recouvrement.services;

import com.odda.recouvrement.dto.requests.PersonnelRequest;
import com.odda.recouvrement.dto.responses.PersonnelResponse;

import java.util.List;
import java.util.UUID;

public interface PersonnelService {
    
    
    PersonnelResponse save(PersonnelRequest req);
    
    List<PersonnelResponse> getAll();
    
    PersonnelResponse getOne(UUID trackingId);
    
    PersonnelResponse update(UUID trackingId, PersonnelRequest req);
    
    void delete(UUID trackingId);
}