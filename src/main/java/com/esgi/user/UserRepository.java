package com.esgi.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by thomasfouan on 04/03/2017.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	List<UserEntity> findByUsername(String username);
}
