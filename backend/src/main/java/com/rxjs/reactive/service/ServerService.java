package com.rxjs.reactive.service;

import java.io.IOException;
import java.util.Collection;

import com.rxjs.reactive.model.Server;

public interface ServerService {
    Server create(Server server);

    Collection<Server> list(int limit);

    Server get(Long id);

    Server ping(String ip) throws IOException;

    Server update(Server server);

    Boolean delete(Long id);

}
