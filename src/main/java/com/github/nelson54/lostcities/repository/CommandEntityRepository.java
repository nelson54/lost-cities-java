package com.github.nelson54.lostcities.repository;

import com.github.nelson54.lostcities.domain.CommandEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CommandEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommandEntityRepository extends JpaRepository<CommandEntity, Long> {

}
