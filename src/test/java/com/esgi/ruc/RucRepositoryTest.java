package com.esgi.ruc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

/**
 * Created by thomasfouan on 08/06/2017.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@RucData
@ActiveProfiles("test")
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class RucRepositoryTest {

	@Autowired
	private RucRepository rucRepository;

	@Test
	public void should_get_all_links_by_idRole() {
		Long id = 1L;

		List<RucEntity> list = rucRepository.findByIdRole(id);

		assertThat(list).hasSize(2);
	}

	@Test
	public void should_not_get_link_for_unknown_idRole() {
		Long id = 3L;

		List<RucEntity> list = rucRepository.findByIdRole(id);

		assertThat(list).hasSize(0);
	}

	@Test
	public void should_get_all_links_by_idUser() {
		Long id = 2L;

		List<RucEntity> list = rucRepository.findByIdUser(id);

		assertThat(list).hasSize(2);
	}

	@Test
	public void should_not_get_link_for_unknown_idUser() {
		Long id = 3L;

		List<RucEntity> list = rucRepository.findByIdUser(id);

		assertThat(list).hasSize(0);
	}

	@Test
	public void should_get_all_links_by_idCircle() {
		Long id = 1L;

		List<RucEntity> list = rucRepository.findByIdCircle(id);

		assertThat(list).hasSize(2);
	}

	@Test
	public void should_not_get_link_for_unknown_idCircle() {
		Long id = 3L;

		List<RucEntity> list = rucRepository.findByIdCircle(id);

		assertThat(list).hasSize(0);
	}

	@Test
	public void should_get_all_links_by_idRole_and_idUser() {
		Long idRole = 1L;
		Long idUser = 1L;

		List<RucEntity> list = rucRepository.findByIdRoleAndIdUser(idRole, idUser);

		assertThat(list).hasSize(1);
		assertThat(list.get(0).getIdCircle()).isEqualTo(1L);
	}

	@Test
	public void should_get_all_links_by_idRole_and_idCircle() {
		Long idRole = 1L;
		Long idCircle = 2L;

		List<RucEntity> list = rucRepository.findByIdRoleAndIdCircle(idRole, idCircle);

		assertThat(list).hasSize(1);
		assertThat(list.get(0).getIdUser()).isEqualTo(2L);
	}

	@Test
	public void should_get_all_links_by_idUser_and_idCircle() {
		Long idUser = 1L;
		Long idCircle = 2L;

		List<RucEntity> list = rucRepository.findByIdUserAndIdCircle(idUser, idCircle);

		assertThat(list).hasSize(1);
		assertThat(list.get(0).getIdRole()).isEqualTo(2L);
	}

	@Test
	public void should_delete_link_by_idUser_and_idCircle() {
		Long idUser = 1L;
		Long idCircle = 1L;

		rucRepository.deleteAllByIdUserAndIdCircle(idUser, idCircle);

		List<RucEntity> rucs = rucRepository.findAll();
		assertThat(rucs).hasSize(3);
	}
}
