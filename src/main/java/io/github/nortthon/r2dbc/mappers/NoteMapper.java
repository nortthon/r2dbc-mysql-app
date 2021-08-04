package io.github.nortthon.r2dbc.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import reactor.core.publisher.Flux;
import io.github.nortthon.r2dbc.controllers.dtos.NoteDto;
import io.github.nortthon.r2dbc.gateways.mysql.dto.Note;

@Mapper
public interface NoteMapper {

  NoteMapper INSTANCE = Mappers.getMapper(NoteMapper.class);

  default Flux<Note> noteDtoToNote(NoteDto noteDTO) {
    return Flux.fromIterable(noteDTO.getIdentifiers())
        .map(identifier -> new Note(noteDTO.getTitle(), noteDTO.getContent(), identifier));
  };
}
