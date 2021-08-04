package io.github.nortthon.r2dbc.configs;

import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import io.github.nortthon.r2dbc.gateways.mysql.dto.Note;

@Configuration
public class SSEConfiguration {

  @Bean
  public Many<Note> sink() {
    return Sinks.many().replay().limit(Duration.ZERO);
  }

  @Bean
  public Flux<Note> events(final Many<Note> sink) {
    return sink.asFlux();
  }
}
