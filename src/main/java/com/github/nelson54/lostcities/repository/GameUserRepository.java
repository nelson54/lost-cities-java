package com.github.nelson54.lostcities.repository;

import com.github.nelson54.lostcities.domain.GameUser;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the GameUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameUserRepository extends JpaRepository<GameUser, Long> {
    GameUser findByUserId(Long id);
}
