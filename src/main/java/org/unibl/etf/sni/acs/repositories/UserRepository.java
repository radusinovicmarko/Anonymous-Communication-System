package org.unibl.etf.sni.acs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.sni.acs.models.entities.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);
}
