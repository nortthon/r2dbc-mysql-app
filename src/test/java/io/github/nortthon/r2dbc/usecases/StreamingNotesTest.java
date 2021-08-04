package io.github.nortthon.r2dbc.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import io.github.nortthon.r2dbc.gateways.NoteGateway;
import io.github.nortthon.r2dbc.gateways.mysql.dto.Note;

import static java.time.LocalDateTime.now;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class StreamingNotesTest {

  @Mock
  private NoteGateway noteGateway;

  @Test
  void testStreamingNotes() {
    final var identifier = "identifier 1";

    final var note = new Note();
    note.setId(0L);
    note.setTitle("title");
    note.setContent("content");
    note.setIdentifier(identifier);
    note.setCreationDate(now());

    final var note1 = new Note();
    note1.setId(1L);
    note1.setTitle("title 1");
    note1.setContent("content 1");
    note1.setIdentifier(identifier);
    note1.setCreationDate(now());

    final var note2 = new Note();
    note2.setId(2L);
    note2.setTitle("title 2");
    note2.setContent("content 2");
    note2.setIdentifier("identifier 2");
    note2.setCreationDate(now());

    when(noteGateway.findByIdentifier(identifier)).thenReturn(Flux.just(note));
    final var events = Flux.just(note1, note2);

    var streamingNotes = new StreamingNotes(noteGateway, events);

    final var execute = streamingNotes.execute(identifier);

    StepVerifier.create(execute.log())
        .expectNextCount(2)
        .verifyComplete();

    StepVerifier.create(execute.log())
        .expectNext(note, note1)
        .verifyComplete();

    verify(noteGateway, times(1)).findByIdentifier(identifier);
  }
}