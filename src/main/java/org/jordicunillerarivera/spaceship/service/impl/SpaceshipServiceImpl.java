package org.jordicunillerarivera.spaceship.service.impl;

import org.jordicunillerarivera.spaceship.dto.CreateSpaceshipDTO;
import org.jordicunillerarivera.spaceship.dto.SpaceshipDTO;
import org.jordicunillerarivera.spaceship.exception.ResourceNotFoundException;
import org.jordicunillerarivera.spaceship.model.Spaceship;
import org.jordicunillerarivera.spaceship.repository.SpaceshipRepository;
import org.jordicunillerarivera.spaceship.service.SpaceshipService;
import org.jordicunillerarivera.spaceship.service.mapper.SpaceshipMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SpaceshipServiceImpl implements SpaceshipService {
    private final SpaceshipRepository repository;

    @Autowired
    public SpaceshipServiceImpl (SpaceshipRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<SpaceshipDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(SpaceshipMapper::toDto);
    }

    @Override
    public SpaceshipDTO findById(Long id) {
        return repository.findById(id)
                .map(SpaceshipMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Spaceship not found with id " + id));
    }

    @Override
    public Page<SpaceshipDTO> searchByName(String name, Pageable pageable) {
        return repository.findByNameContainingIgnoreCase(name, pageable)
                .map(SpaceshipMapper::toDto);
    }

    @Override
    @Transactional
    public SpaceshipDTO create(CreateSpaceshipDTO dto) {
        Spaceship entity = SpaceshipMapper.toEntity(dto);
        Spaceship saved = repository.save(entity);
        return SpaceshipMapper.toDto(saved);
    }

    @Override
    @Transactional
    public SpaceshipDTO update(Long id, CreateSpaceshipDTO dto) {
        Spaceship existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Spaceship not found with id " + id));

        existing.setName(dto.name());
        existing.setModel(dto.model());
        existing.setManufacturer(dto.manufacturer());
        existing.setCrewCapacity(dto.crewCapacity());

        Spaceship updated = repository.save(existing);
        return SpaceshipMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Spaceship not found with id " + id);
        }
        repository.deleteById(id);
    }
}
