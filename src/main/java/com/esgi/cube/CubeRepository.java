package com.esgi.cube;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by thomasfouan on 16/06/2017.
 */
@Repository
public interface CubeRepository extends JpaRepository<CubeEntity, Long> {

	List<CubeEntity> findByIdLine(Long idLine);
}
