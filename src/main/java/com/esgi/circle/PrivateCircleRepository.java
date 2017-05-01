package com.esgi.circle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by thomasfouan on 01/05/2017.
 */
@Repository
public interface PrivateCircleRepository extends JpaRepository<PrivateCircleEntity, Long> {

	List<PrivateCircleEntity> findByIdUser1AndAndIdUser2(Long idUser1, Long idUser2);
}
