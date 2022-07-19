package ru.otus.mappers.entity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.otus.jpa.entities.Rooms;
import ru.otus.mappers.EntityMapper;
import ru.otus.mappers.qualifiers.SubMapper;
import ru.otus.models.organization.RoomsModel;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {PlacementsEntityMapper.class})
public abstract class RoomsEntityMapper implements EntityMapper<RoomsModel, Rooms> {
    @Override
    @Mappings({
            @Mapping(target = "placement", source = "model.placement", qualifiedBy = {SubMapper.class})
    })
    public abstract Rooms toEntity(RoomsModel model);

    @Override
    @Mappings({
            @Mapping(target = "placement", source = "entity.placement", qualifiedBy = {SubMapper.class})
    })
    public abstract RoomsModel toModel(Rooms entity);
}
