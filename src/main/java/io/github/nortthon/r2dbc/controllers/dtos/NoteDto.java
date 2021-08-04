package io.github.nortthon.r2dbc.controllers.dtos;

import java.util.List;
import lombok.Data;

@Data
public class NoteDto {

  private String title;

  private String content;

  private List<String> identifiers;
}
