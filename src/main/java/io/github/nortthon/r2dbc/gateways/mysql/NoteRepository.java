package io.github.nortthon.r2dbc.gateways.mysql;

import io.github.nortthon.r2dbc.gateways.mysql.dto.Note;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface NoteRepository extends ReactiveCrudRepository<Note, Long> {

  Flux<Note> findAllByIdentifier(String identifier);

}
