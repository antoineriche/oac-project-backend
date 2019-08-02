package com.gaminho.oacproject.project;

import com.gaminho.oacproject.dao.ProjectTypeRepository;
import com.gaminho.oacproject.exception.project.InvalidTypeException;
import com.gaminho.oacproject.exception.project.NoTypeException;
import com.gaminho.oacproject.exception.project.TypeNotFoundException;
import com.gaminho.oacproject.model.ProjectType;
import com.gaminho.oacproject.web.service.ProjectTypeService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.gaminho.oacproject.utils.DefaultValues.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProjectTypeServiceTests {

	@Mock
	private ProjectTypeRepository typeRepository;
	@InjectMocks
	private ProjectTypeService typeService;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}


	// GET ALL PROJECT TYPE

	@Test
	public void test_getAllTypes() {
		List<ProjectType> list = new ArrayList<>();
		list.add(DEFAULT_PROJECT_TYPE_1);
		list.add(DEFAULT_PROJECT_TYPE_2);
		when(typeRepository.findAll()).thenReturn(list);

		List<ProjectType> list2 = typeService.getAllProjectTypes();
		assertNotNull(list2);
		assertEquals(2, list2.size());
		assertEquals(DEFAULT_PROJECT_TYPE_1.getLabel(), list2.get(0).getLabel());
	}

	@Test
	public void test_getAllTypesWithEmptyDB() throws NoTypeException {
		expectedEx.expect(NoTypeException.class);
		expectedEx.expectMessage("No project type.");
		typeService.getAllProjectTypes();
	}

	// GET PROJECT TYPE BY ID

	@Test
	public void test_getTypeById(){
		when(typeRepository.findById(DEFAULT_PROJECT_TYPE_1.getId()))
				.thenReturn(Optional.of(DEFAULT_PROJECT_TYPE_1));

		assertTrue(typeRepository.findById(DEFAULT_PROJECT_TYPE_1.getId()).isPresent());
		ProjectType projectType = typeService.getProjectTypeWithId(DEFAULT_PROJECT_TYPE_1.getId());
		assertEquals(projectType, DEFAULT_PROJECT_TYPE_1);
	}

	@Test
	public void test_getTypeByIdWithNotFoundException() throws TypeNotFoundException {
		expectedEx.expect(TypeNotFoundException.class);
		expectedEx.expectMessage("Project type with id 3 does not exist.");
		typeService.getProjectTypeWithId(3);
	}

	// SAVE PROJECT TYPE

	@Test
	public void test_saveType(){
		when(typeRepository.save(DEFAULT_PROJECT_TYPE_1)).thenReturn(DEFAULT_PROJECT_TYPE_1);
		ProjectType newProjectType = typeService.saveProjectType(DEFAULT_PROJECT_TYPE_1);
		assertEquals(DEFAULT_PROJECT_TYPE_1, newProjectType);
	}

	@Test
	public void test_saveTypeWithInvalidData() throws InvalidTypeException {
		expectedEx.expect(InvalidTypeException.class);
		expectedEx.expectMessage("Invalid project type.");

		ProjectType projectType = new ProjectType();
		projectType.setId(59L);
		when(typeRepository.save(projectType)).thenReturn(projectType);
		typeService.saveProjectType(projectType);
	}

	// UPDATE PROJECT TYPE

	@Test
	public void test_updateType(){

		when(typeRepository.findById(DEFAULT_PROJECT_TYPE_1.getId()))
				.thenReturn(Optional.of(DEFAULT_PROJECT_TYPE_1));

		ProjectType projectTypeUpdate = new ProjectType();
		projectTypeUpdate.setId(DEFAULT_PROJECT_TYPE_1.getId());
		projectTypeUpdate.setLabel("test");

		when(typeRepository.findById(projectTypeUpdate.getId())).thenReturn(Optional.of(projectTypeUpdate));

		ProjectType projectType2 = typeService.updateProjectType(projectTypeUpdate.getId(), projectTypeUpdate);
		assertEquals(projectType2, projectTypeUpdate);
	}

	@Test
	public void test_updateTypeWithNotFoundException() throws TypeNotFoundException {
		expectedEx.expect(TypeNotFoundException.class);
		expectedEx.expectMessage("Project type with id 8 does not exist.");
		typeService.updateProjectType(8L, DEFAULT_PROJECT_TYPE_1);
	}

	// DELETE PROJECT TYPE

	@Test
	public void test_deleteType(){
		when(typeRepository.findById(DEFAULT_PROJECT_TYPE_1.getId()))
				.thenReturn(Optional.of(DEFAULT_PROJECT_TYPE_1));

		typeService.deleteProjectType(DEFAULT_PROJECT_TYPE_1.getId());
		verify(typeRepository, times(1)).deleteById(DEFAULT_PROJECT_TYPE_1.getId());
	}

	@Test
	public void test_deleteTypeByIdWithNotFoundException() throws TypeNotFoundException {
		expectedEx.expect(TypeNotFoundException.class);
		expectedEx.expectMessage("Project type with id 8 does not exist.");
		typeService.deleteProjectType(8L);
	}

	// DELETE ALL

	@Test
	public void test_deleteAllTypes() {
		when(typeRepository.count()).thenReturn(2L);
		typeService.deleteAllTypes();
		verify(typeRepository, times(1)).deleteAll();
	}

	@Test
	public void test_deleteAllTypesWithEmptyList() throws NoTypeException {
		expectedEx.expect(NoTypeException.class);
		expectedEx.expectMessage("No project type.");
		when(typeRepository.count()).thenReturn(0L);
		typeService.deleteAllTypes();
	}

}
