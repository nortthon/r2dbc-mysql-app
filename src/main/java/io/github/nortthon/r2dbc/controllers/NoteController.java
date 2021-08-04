package io.github.nortthon.r2dbc.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import io.github.nortthon.r2dbc.controllers.dtos.NoteDto;
import io.github.nortthon.r2dbc.gateways.mysql.dto.Note;
import io.github.nortthon.r2dbc.mappers.NoteMapper;
import io.github.nortthon.r2dbc.usecases.CreateNote;
import io.github.nortthon.r2dbc.usecases.DeleteNote;
import io.github.nortthon.r2dbc.usecases.StreamingNotes;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/notes")
public class NoteController {

  private final StreamingNotes streamingNotes;

  private final CreateNote createNote;

  private final DeleteNote deleteNote;

  @GetMapping(produces = TEXT_EVENT_STREAM_VALUE)
  public Flux<Note> findAll(@RequestParam final String identifier) {
    return streamingNotes.execute(identifier);
  }

  @PostMapping
  @ResponseStatus(CREATED)
  public Flux<Note> save(@RequestBody final Mono<NoteDto> note) {
    return note.flatMapMany(NoteMapper.INSTANCE::noteDtoToNote)
        .flatMap(createNote::execute);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(NO_CONTENT)
  public Mono<Void> delete(@PathVariable final Long id) {
    return deleteNote.execute(id);
  }
}
