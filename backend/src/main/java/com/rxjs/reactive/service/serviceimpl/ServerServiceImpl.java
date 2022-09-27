package com.rxjs.reactive.service.serviceimpl;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rxjs.reactive.enumeration.Status;
import com.rxjs.reactive.model.Server;
import com.rxjs.reactive.repository.ServerRepo;
import com.rxjs.reactive.service.ServerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class ServerServiceImpl implements ServerService {

    private final ServerRepo serverRepo;

    @Override
    public Server create(Server server) {
        log.info("Saving new server : {}", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepo.save(server);
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers");
        return serverRepo.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Fetching  server by id:{}", id);

        return serverRepo.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Update server : {}", server.getName());
        return serverRepo.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Delete server by id: {} ", id);
        serverRepo.deleteById(id);
        return true;
    }

    @Override
    public Server ping(String ip) throws IOException {
        log.info("Pinging server ip : {}", ip);
        Server server = serverRepo.findByIpAddress(ip);
        InetAddress address = InetAddress.getByName(ip);
        server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
        serverRepo.save(server);
        return server;
    }

    private String setServerImageUrl() {
        String[] imageNews = { "server1.png", "server2.png", "server3.png", "server4.png" };
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/server/image" + imageNews[new Random().nextInt(4)]).toUriString();
    }

}
