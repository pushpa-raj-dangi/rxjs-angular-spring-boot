package com.rxjs.reactive.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rxjs.reactive.model.Server;

public interface ServerRepo extends JpaRepository<Server, Long> {
    Server findByIpAddress(String ipAddress);

}
