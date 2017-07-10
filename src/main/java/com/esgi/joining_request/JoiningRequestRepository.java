package com.esgi.joining_request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by thomasfouan on 10/07/2017.
 */
@Repository
public interface JoiningRequestRepository extends JpaRepository<JoiningRequestEntity, Long> {

	List<JoiningRequestEntity> findByIdUser(Long id);

	List<JoiningRequestEntity> findByIdCircle(Long id);
}
