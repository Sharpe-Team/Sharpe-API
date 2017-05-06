package com.esgi.circle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by thomasfouan on 29/03/2017.
 *
 * Class to manage the Circle in the repository.
 */
@Repository
public interface CircleRepository extends JpaRepository<CircleEntity, Long> {

	List<CircleEntity> findAllByType(Short type);
}
