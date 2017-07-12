package com.esgi.joining_request;

import com.esgi.role.RoleEntity;
import com.esgi.role.RoleNotFoundException;
import com.esgi.role.RoleRepository;
import com.esgi.ruc.RucEntity;
import com.esgi.ruc.RucRepository;
import com.esgi.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by thomasfouan on 10/07/2017.
 */
@Service
public class JoiningRequestServiceImpl implements JoiningRequestService {

	private final JoiningRequestRepository joiningRequestRepository;

	private final RucRepository rucRepository;

	private final RoleRepository roleRepository;

	private final UserService userService;

	@Autowired
	public JoiningRequestServiceImpl(JoiningRequestRepository joiningRequestRepository, RucRepository rucRepository, RoleRepository roleRepository, UserService userService) {
		this.joiningRequestRepository = joiningRequestRepository;
		this.rucRepository = rucRepository;
		this.roleRepository = roleRepository;
		this.userService = userService;
	}

	@Override
	public List<JoiningRequestDto> getAllJoiningRequests() {
		return joiningRequestRepository.findAll()
				.stream()
				.map(JoiningRequestAdapter::entityToDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<JoiningRequestDto> getJoiningRequestsByUser(Long id) {
		return joiningRequestRepository.findByIdUser(id)
				.stream()
				.map(JoiningRequestAdapter::entityToDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<UserDto> getJoiningRequestsByCircle(Long id) {
		return joiningRequestRepository.findByIdCircle(id)
				.stream()
				.map(joiningRequestEntity -> {
					try {
						return userService.getUser(joiningRequestEntity.getIdUser());
					} catch (UserNotFoundException e) {
						return null;
					}
				})
				.collect(Collectors.toList());
	}

	@Override
	public JoiningRequestDto insertJoiningRequest(JoiningRequestDto joiningRequestDto) {
		JoiningRequestEntity joiningRequestEntity = JoiningRequestAdapter.dtoToEntity(joiningRequestDto);

		joiningRequestEntity = joiningRequestRepository.save(joiningRequestEntity);

		return JoiningRequestAdapter.entityToDto(joiningRequestEntity);
	}

	@Override
	public void deleteJoiningRequest(Long id, boolean isAccepted) throws JoiningRequestNotFoundException, RoleNotFoundException {
		JoiningRequestEntity joiningRequestEntity = joiningRequestRepository.findOne(id);

		if(joiningRequestEntity == null) {
			throw new JoiningRequestNotFoundException();
		}

		joiningRequestRepository.delete(id);

		// If the joining request has been accepted, add a ruc for the user and the circle of the joining request
		if(isAccepted) {
			addRucForAcceptedJoiningRequest(joiningRequestEntity);
		}
	}

	@Override
	public void deleteJoiningRequestsByIdUserAndIdCircle(Long idUser, Long idCircle, boolean isAccepted) throws RoleNotFoundException {
		List<JoiningRequestEntity> deletedJoiningRequests = joiningRequestRepository.deleteAllByIdUserAndIdCircle(idUser, idCircle);

		if(isAccepted && deletedJoiningRequests != null && !deletedJoiningRequests.isEmpty()) {
			addRucForAcceptedJoiningRequest(deletedJoiningRequests.get(0));
		}
	}

	private void addRucForAcceptedJoiningRequest(JoiningRequestEntity deletedJoiningRequest) throws RoleNotFoundException {
		// Find role
		RoleEntity roleEntity = roleRepository.findByName("USER")
				.stream()
				.findFirst()
				.orElseThrow(RoleNotFoundException::new);

		RucEntity rucEntity = RucEntity.builder()
				.idUser(deletedJoiningRequest.getIdUser())
				.idCircle(deletedJoiningRequest.getIdCircle())
				.idRole(roleEntity.getId())
				.build();

		rucRepository.save(rucEntity);
	}
}
