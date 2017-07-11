package com.esgi.joining_request;

import com.esgi.role.RoleNotFoundException;
import com.esgi.user.UserDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by thomasfouan on 10/07/2017.
 */
public interface JoiningRequestService {

	@Transactional(readOnly = true)
	List<JoiningRequestDto> getAllJoiningRequests();

	@Transactional(readOnly = true)
	List<JoiningRequestDto> getJoiningRequestsByUser(Long id);

	@Transactional(readOnly = true)
	List<UserDto> getJoiningRequestsByCircle(Long id);

	@Transactional
	JoiningRequestDto insertJoiningRequest(JoiningRequestDto joiningRequestDto);

	@Transactional
	void deleteJoiningRequest(Long id, boolean isAccepted) throws JoiningRequestNotFoundException, RoleNotFoundException;

	@Transactional
	void deleteJoiningRequestsByIdUserAndIdCircle(Long idUser, Long idCircle, boolean isAccepted) throws RoleNotFoundException;
}
