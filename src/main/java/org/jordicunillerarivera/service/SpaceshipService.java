package org.jordicunillerarivera.service;

import org.jordicunillerarivera.dto.CreateSpaceshipDTO;
import org.jordicunillerarivera.dto.SpaceshipDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SpaceshipService {
    Page<SpaceshipDTO> findAll(Pageable pageable);
    SpaceshipDTO findById(Long id);
    Page<SpaceshipDTO> searchByName(String name, Pageable pageable);
    SpaceshipDTO create (CreateSpaceshipDTO dto);
    SpaceshipDTO update (Long id, CreateSpaceshipDTO dto);
    void delete (Long id);
}
