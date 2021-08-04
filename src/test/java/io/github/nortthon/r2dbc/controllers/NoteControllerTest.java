package io.github.nortthon.r2dbc.controllers;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import io.github.nortthon.r2dbc.controllers.dtos.NoteDto;
import io.github.nortthon.r2dbc.gateways.mysql.dto.Note;
import io.github.nortthon.r2dbc.usecases.CreateNote;
import io.github.nortthon.r2dbc.usecases.DeleteNote;
import io.github.nortthon.r2dbc.usecases.StreamingNotes;

import static java.time.LocalDateTime.now;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebFluxTest(NoteController.class)
class NoteControllerTest {

  @MockBean
  private StreamingNotes streamingNotes;

  @MockBean
  private CreateNote createNote;

  @MockBean
  private DeleteNote deleteNote;

  @Autowired
  private WebTestClient webTestClient;

  @Test
  void testFindNotes() {
    final var identifier = "leonidas";

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

    when(streamingNotes.execute(identifier)).thenReturn(Flux.just(note, note1));

    webTestClient.get()
        .uri(builder -> builder.path("/notes")
            .queryParam("identifier", identifier)
            .build())
        .attribute("identifier", identifier)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Note.class).hasSize(2);
  }

  @Test
  void testSaveNewNote() {
    final var note = new Note();
    note.setId(1L);
    note.setTitle("title 1");
    note.setContent("content 1");
    note.setIdentifier("identifier 1");
    note.setCreationDate(now());

    when(createNote.execute(any())).thenReturn(Mono.just(note));

    final var noteDto = new NoteDto();
    noteDto.setTitle("title 1");
    noteDto.setContent("content 1");
    noteDto.setIdentifiers(List.of("identifier 1"));

    webTestClient.post()
        .uri("/notes")
        .contentType(APPLICATION_JSON)
        .bodyValue(noteDto)
        .exchange()
        .expectStatus().isCreated()
        .expectBodyList(Note.class).hasSize(1);
  }

  @Test
  void testDeleteSuccess() {
    webTestClient.delete()
        .uri("/notes/{id}", 1)
        .exchange()
        .expectStatus().isNoContent();

    verify(deleteNote, times(1)).execute(1L);
  }
}