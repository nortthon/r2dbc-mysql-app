package io.github.nortthon.r2dbc.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.test.StepVerifier;
import io.github.nortthon.r2dbc.gateways.NoteGateway;
import io.github.nortthon.r2dbc.gateways.mysql.dto.Note;

import static java.time.LocalDateTime.now;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class CreateNoteTest {

  @Mock
  private NoteGateway noteGateway;

  @Mock
  private Sinks.Many<Note> sink;

  @InjectMocks
  private CreateNote createNote;

  @Test
  void testCreateNewNote() {
    final var note = new Note();
    note.setId(1L);
    note.setTitle("title 1");
    note.setContent("content 1");
    note.setIdentifier("identifier 1");
    note.setCreationDate(now());

    when(noteGateway.save(note)).thenReturn(Mono.just(note));
    when(sink.tryEmitNext(note)).thenReturn(Sinks.EmitResult.OK);

    StepVerifier.create(createNote.execute(note))
        .expectNext(note)
        .verifyComplete();

    verify(noteGateway, times(1)).save(any());
  }
}