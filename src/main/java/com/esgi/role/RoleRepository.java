package com.esgi.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by thomasfouan on 08/06/2017.
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

	List<RoleEntity> findByName(String name);
}
