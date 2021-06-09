package io.gsealy.netty;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TestController {


  @GetMapping(path = "/", produces = MediaType.TEXT_PLAIN_VALUE)
  public Mono<String> text() {
    return Mono.just("test");
  }

  @PostMapping(path = "/", produces = MediaType.TEXT_PLAIN_VALUE)
  public Mono<String> post(@RequestBody String body) {
    return Mono.justOrEmpty(body);
  }
}
