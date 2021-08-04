package io.github.nortthon.r2dbc.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import io.github.nortthon.r2dbc.gateways.NoteGateway;
import io.github.nortthon.r2dbc.gateways.mysql.dto.Note;

@Service
@RequiredArgsConstructor
public class StreamingNotes {

  private final NoteGateway noteGateway;

  private final Flux<Note> events;

  public Flux<Note> execute(final String identifier) {
    return noteGateway.findByIdentifier(identifier)
        .concatWith(events.filter(note -> identifier.equals(note.getIdentifier())));
  }
}
