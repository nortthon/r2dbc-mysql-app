package io.github.nortthon.r2dbc.gateways;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import io.github.nortthon.r2dbc.gateways.mysql.dto.Note;

public interface NoteGateway {

  Flux<Note> findAll();

  Flux<Note> findByIdentifier(String identifier);

  Mono<Note> save(Note note);

  Mono<Void> delete(Long id);
}
