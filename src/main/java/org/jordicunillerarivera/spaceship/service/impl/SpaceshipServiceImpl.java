package org.jordicunillerarivera.spaceship.service.impl;

import org.jordicunillerarivera.spaceship.config.AppConstants;
import org.jordicunillerarivera.spaceship.dto.CreateSpaceshipDTO;
import org.jordicunillerarivera.spaceship.dto.SpaceshipDTO;
import org.jordicunillerarivera.spaceship.exception.ResourceNotFoundException;
import org.jordicunillerarivera.spaceship.model.Spaceship;
import org.jordicunillerarivera.spaceship.repository.SpaceshipRepository;
import org.jordicunillerarivera.spaceship.service.SpaceshipService;
import org.jordicunillerarivera.spaceship.service.kafka.SpaceshipEventProducer;
import org.jordicunillerarivera.spaceship.service.mapper.SpaceshipMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SpaceshipServiceImpl implements SpaceshipService {
    private final SpaceshipRepository repository;
    private final SpaceshipEventProducer eventProducer;

    @Autowired
    public SpaceshipServiceImpl (SpaceshipRepository repository,
                                 SpaceshipEventProducer eventProducer) {
        this.repository = repository;
        this.eventProducer = eventProducer;
    }

    @Override
    public Page<SpaceshipDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(SpaceshipMapper::toDto);
    }

    @Override
    @Cacheable(value = "spaceships", key = "#id")
    public SpaceshipDTO findById(Long id) {
        return repository.findById(id)
                .map(SpaceshipMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.SPACESHIP_NOT_FOUND_ID + id));
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
        SpaceshipDTO result = SpaceshipMapper.toDto(saved);

        eventProducer.sendCreated(result);
        return result;
    }

    @Override
    @Transactional
    @CachePut(value = "spaceships", key = "#id")
    public SpaceshipDTO update(Long id, CreateSpaceshipDTO dto) {
        Spaceship existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.SPACESHIP_NOT_FOUND_ID + id));

        existing.setName(dto.name());
        existing.setModel(dto.model());
        existing.setManufacturer(dto.manufacturer());
        existing.setCrewCapacity(dto.crewCapacity());

        Spaceship updated = repository.save(existing);
        SpaceshipDTO result = SpaceshipMapper.toDto(updated);

        eventProducer.sendUpdated(result);
        return result;
    }

    @Override
    @Transactional
    @CacheEvict(value = "spaceships", key = "#id")
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(AppConstants.SPACESHIP_NOT_FOUND_ID + id);
        }
        repository.deleteById(id);
        eventProducer.sendDeleted(id);
    }
}
