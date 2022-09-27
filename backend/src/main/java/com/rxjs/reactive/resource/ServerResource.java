package com.rxjs.reactive.resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rxjs.reactive.enumeration.Status;
import com.rxjs.reactive.model.Response;
import com.rxjs.reactive.model.Server;
import com.rxjs.reactive.service.serviceimpl.ServerServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerResource {
        private final ServerServiceImpl serverService;

        @GetMapping
        public ResponseEntity<Response> getServers() {
                return ResponseEntity
                                .ok(Response.builder().timeStamp(LocalDateTime.now())
                                                .data(Map.of("servers", serverService.list(30)))
                                                .message("Server Retrived").status(HttpStatus.OK)
                                                .statusCode(HttpStatus.OK.value()).build());

        }

        @GetMapping("/ping/{ipAddress}")
        public ResponseEntity<Response> getPingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
                Server server = serverService.ping(ipAddress);
                return ResponseEntity
                                .ok(Response.builder().timeStamp(LocalDateTime.now()).data(Map.of("server", server))
                                                .message(server.getStatus() == Status.SERVER_UP ? "Ping success"
                                                                : "Ping failed")
                                                .status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());

        }

        @PostMapping("/save")
        public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server) throws IOException {
                return ResponseEntity
                                .ok(Response.builder().timeStamp(LocalDateTime.now())
                                                .data(Map.of("server", serverService.create(server)))
                                                .message("Server created")
                                                .status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());

        }

        @GetMapping("/{id}")
        public ResponseEntity<Response> getServer(@PathVariable("id") Long id) throws IOException {

                return ResponseEntity
                                .ok(Response.builder().timeStamp(LocalDateTime.now())
                                                .data(Map.of("server", serverService.get(id)))
                                                .message("Server retrived")
                                                .status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());

        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) throws IOException {

                return ResponseEntity
                                .ok(Response.builder().timeStamp(LocalDateTime.now())
                                                .data(Map.of("deleted", serverService.delete(id)))
                                                .message("Server deleted")
                                                .status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());

        }

        @GetMapping(path = "/image/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
        public byte[] deleteServer(@PathVariable("fileName") String fileName) throws IOException {
                return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "Downloads/images/" + fileName));
        }

}
