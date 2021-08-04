package io.github.nortthon.r2dbc.gateways.mysql;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import io.github.nortthon.r2dbc.gateways.NoteGateway;
import io.github.nortthon.r2dbc.gateways.mysql.dto.Note;

@Component
@RequiredArgsConstructor
public class NoteGatewayMysqlImpl implements NoteGateway {

  private final NoteRepository repository;

  @Override
  public Flux<Note> findAll() {
    return repository.findAll();
  }

  @Override
  public Flux<Note> findByIdentifier(final String identifier) {
    return repository.findAllByIdentifier(identifier);
  }

  @Override
  public Mono<Note> save(final Note note) {
    return repository.save(note);
  }

  @Override
  public Mono<Void> delete(Long id) {
    return repository.deleteById(id);
  }
}
