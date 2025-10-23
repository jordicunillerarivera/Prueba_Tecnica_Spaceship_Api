package org.jordicunillerarivera.service.mapper;

import org.jordicunillerarivera.dto.CreateSpaceshipDTO;
import org.jordicunillerarivera.dto.SpaceshipDTO;
import org.jordicunillerarivera.model.Spaceship;

public final class SpaceshipMapper {
    private SpaceshipMapper() {}

    public static SpaceshipDTO toDto(Spaceship s) {
        if (s == null) return null;
        return new SpaceshipDTO(s.getId(), s.getName(), s.getModel(), s.getManufacturer(), s.getCrewCapacity());
    }

    public static Spaceship toEntity(CreateSpaceshipDTO dto) {
        if (dto == null) return null;
        Spaceship s = new Spaceship();
        s.setName(dto.name());
        s.setModel(dto.model());
        s.setManufacturer(dto.manufacturer());
        s.setCrewCapacity(dto.crewCapacity());
        return s;
    }
}
