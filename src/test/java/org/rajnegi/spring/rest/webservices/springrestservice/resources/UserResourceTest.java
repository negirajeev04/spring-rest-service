package org.rajnegi.spring.rest.webservices.springrestservice.resources;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rajnegi.spring.rest.webservices.springrestservice.bean.User;
import org.rajnegi.spring.rest.webservices.springrestservice.repository.UserResourceRepository;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
public class UserResourceTest {

	@Test
	public void testJson() throws Exception {

		UserResourceRepository mockUserRepo = mock(UserResourceRepository.class);
		UserResource userController = new UserResource();
		userController.setUserRepo(mockUserRepo);

		List<User> mockList = Arrays.asList(new User(1, "Rajeev", new Date()), new User(2, "Laxmi", new Date()),
				new User(3, "Laisha", new Date()));
		when(mockUserRepo.findAll()).thenReturn(mockList);

		MockMvc mockMvc = standaloneSetup(userController).build();
		mockMvc.perform(get("/jpa/users").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(mockList.size()))).andExpect(jsonPath("$[0].name", is("Rajeev")));

		verify(mockUserRepo).findAll();
	}

	@Test
	public void testXML() throws Exception {

		UserResourceRepository mockUserRepo = mock(UserResourceRepository.class);
		UserResource userController = new UserResource();
		userController.setUserRepo(mockUserRepo);

		List<User> mockList = Arrays.asList(new User(1, "Rajeev", new Date()), new User(2, "Laxmi", new Date()),
				new User(3, "Laisha", new Date()));
		when(mockUserRepo.findAll()).thenReturn(mockList);

		MockMvc mockMvc = standaloneSetup(userController).build();
		mockMvc.perform(get("/jpa/users").accept(MediaType.APPLICATION_XML)).andExpect(status().isOk())
				.andExpect(xpath("//item").nodeCount(mockList.size()))
				.andExpect(xpath("//item[name='Rajeev']").exists());

		verify(mockUserRepo).findAll();
	}
	
}
