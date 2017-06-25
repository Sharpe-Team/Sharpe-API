package com.esgi.ruc;

import com.esgi.role.*;
import com.esgi.user.UserAdapter;
import com.esgi.user.UserDto;
import com.esgi.user.UserEntity;
import com.esgi.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by thomasfouan on 08/06/2017.
 */
@Service
public class RucServiceImpl implements RucService {

	private final RucRepository rucRepository;

	private final RoleRepository roleRepository;

	private final UserRepository userRepository;

	@Autowired
	public RucServiceImpl(RucRepository rucRepository, RoleRepository roleRepository, UserRepository userRepository) {
		this.rucRepository = rucRepository;
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
	}

	@Override
	public List<RucDto> getAllLinks() {
		return rucRepository.findAll()
				.stream()
				.map(RucAdapter::entityToDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<RucDto> getLinksByRole(Long id) {
		return rucRepository.findByIdRole(id)
				.stream()
				.map(RucAdapter::entityToDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<RucDto> getLinksByUser(Long id) {
		return rucRepository.findByIdUser(id)
				.stream()
				.map(RucAdapter::entityToDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<RucDto> getLinksByCircle(Long id) {
		return rucRepository.findByIdCircle(id)
				.stream()
				.map(RucAdapter::entityToDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<RucDto> getLinksByRoleAndUser(Long idRole, Long idUser) {
		return rucRepository.findByIdRoleAndIdUser(idRole, idUser)
				.stream()
				.map(RucAdapter::entityToDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<UserDto> getLinksByRoleAndCircle(Long idRole, Long idCircle) {
		final List<RucDto> listRuc = rucRepository.findByIdRoleAndIdCircle(idRole, idCircle)
				.stream()
				.map(RucAdapter::entityToDto)
				.collect(Collectors.toList());

		final List<UserDto> result = new ArrayList<>();
		for (RucDto rucDto : listRuc) {
			result.add(getUser(rucDto.getIdUser()));
		}

		return result;
	}

	@Override
	public RucDto getLinkByUserAndCircle(Long idUser, Long idCircle) throws RucNotFoundException {
		RucEntity rucEntity = rucRepository.findByIdUserAndIdCircle(idUser, idCircle)
				.stream()
				.findFirst()
				.orElseThrow(RucNotFoundException::new);

		return RucAdapter.entityToDto(rucEntity);
	}

	@Override
	public RucDto insertLink(RucDto rucDto) {
		RucEntity rucEntity = RucAdapter.dtoToEntity(rucDto);

		rucEntity = rucRepository.save(rucEntity);

		return RucAdapter.entityToDto(rucEntity);
	}

	@Override
	public List<RucDto> deleteLink(Long idUser, Long idCircle) {
		return rucRepository.deleteAllByIdUserAndIdCircle(idUser, idCircle)
				.stream()
				.map(RucAdapter::entityToDto)
				.collect(Collectors.toList());
	}

	@Override
	public RucDto updateRole(Long idUser, Long idCircle, Long newIdRole) throws RucNotFoundException {
		// Find link
		RucDto rucDto = getLinkByUserAndCircle(idUser, idCircle);

		// Update link
		RucEntity rucEntity = RucEntity.builder()
				.id(rucDto.getId())
				.idUser(rucDto.getIdUser())
				.idCircle(rucDto.getIdCircle())
				.idRole(newIdRole)
				.build();

		// Save link
		rucEntity = rucRepository.save(rucEntity);

		return RucAdapter.entityToDto(rucEntity);
	}

	@Override
	public RucDto updateRole(Long idUser, Long idCircle, String newRoleName) throws RucNotFoundException, RoleNotFoundException {
		// Find link
		RucDto rucDto = getLinkByUserAndCircle(idUser, idCircle);

		// Find role
		RoleEntity roleEntity = roleRepository.findByName(newRoleName)
				.stream()
				.findFirst()
				.orElseThrow(RoleNotFoundException::new);

		// Update link
		RucEntity rucEntity = RucEntity.builder()
				.id(rucDto.getId())
				.idUser(rucDto.getIdUser())
				.idCircle(rucDto.getIdCircle())
				.idRole(roleEntity.getId())
				.build();

		// Save link
		rucEntity = rucRepository.save(rucEntity);

		return RucAdapter.entityToDto(rucEntity);
	}

	private UserDto getUser(Long idUser) {
		final UserEntity userEntity = userRepository.findOne(idUser);
		final UserDto userDto;
		if(userEntity == null) {
			userDto = UserDto.builder()
					.firstname("Deleted user")
					.lastname("")
					.email("")
					.build();
		} else {
			userDto = UserAdapter.entityToDto(userEntity);
			userDto.setPassword("");
		}

		return userDto;
	}
}
