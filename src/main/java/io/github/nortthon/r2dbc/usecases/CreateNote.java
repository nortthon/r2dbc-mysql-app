package io.github.nortthon.r2dbc.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import io.github.nortthon.r2dbc.gateways.NoteGateway;
import io.github.nortthon.r2dbc.gateways.mysql.dto.Note;

@Service
@RequiredArgsConstructor
public class CreateNote {

  private final NoteGateway noteGateway;

  private final Sinks.Many<Note> sink;

  public Mono<Note> execute(final Note note) {
    return noteGateway.save(note)
        .doOnNext(n -> n.setNotify(true))
        .doOnNext(sink::tryEmitNext); //TODO: send it to queue (warning: looking at distributed applications)
  }
}
