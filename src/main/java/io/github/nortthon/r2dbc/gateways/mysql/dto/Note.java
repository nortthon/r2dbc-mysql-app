package io.github.nortthon.r2dbc.gateways.mysql.dto;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@Table("notes")
public class Note {

  public Note(final String title, final String content, final String identifier) {
    this.title = title;
    this.content = content;
    this.identifier = identifier;
  }

  @Id
  @Column
  private Long id;

  @Column
  private String title;

  @Column
  private String content;

  @CreatedDate
  @Column("creation_date")
  private LocalDateTime creationDate;

  @Column
  private String identifier;

  @Transient
  private boolean notify;
}
