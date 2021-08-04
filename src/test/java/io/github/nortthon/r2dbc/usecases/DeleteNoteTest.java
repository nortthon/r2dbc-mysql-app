package io.github.nortthon.r2dbc.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import io.github.nortthon.r2dbc.gateways.NoteGateway;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class DeleteNoteTest {

  @Mock
  private NoteGateway noteGateway;

  @InjectMocks
  private DeleteNote deleteNote;

  @Test
  void testDeleteExistentNote() {
    final var id = 1L;

    when(noteGateway.delete(id)).thenReturn(Mono.empty());

    StepVerifier.create(deleteNote.execute(id))
        .expectSubscription()
        .verifyComplete();

    verify(noteGateway, times(1)).delete(id);
  }
}