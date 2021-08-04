package io.github.nortthon.r2dbc.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import io.github.nortthon.r2dbc.gateways.NoteGateway;

@Service
@RequiredArgsConstructor
public class DeleteNote {

  private final NoteGateway noteGateway;

  public Mono<Void> execute(final Long id) {
    return noteGateway.delete(id);
  }
}
