package com.project.mein.controller.test;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.project.mein.controller.ShowController;
import com.project.mein.entity.Languages;
import com.project.mein.entity.Repository;
import com.project.mein.entity.User;
import com.project.mein.service.UserService;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:**/WEB-INF/applicationContext.xml")
public class ShowControllerTest {

	private MockMvc mockMvc;

	@Mock
	private UserService userService;

	@InjectMocks
	private ShowController showController;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(showController).build();
	}

	@Test
	public void testGetAllUser() throws Exception {
		List<User> users = Arrays.asList(new User(1, "arslan", "wattoo",
				" FreeCities", "fatherofdragons", "stormborn@dragon.de",
				"I was born to rule the Seven Kingdoms...and I will."),
				new User(2, "Snowlala", "warum", "winterfel",
						"theknightswatch", "bastard@winterfel.de",
						"i know nothing"));
		when(userService.getAllUser()).thenReturn(users);
		mockMvc.perform(get("/api/show/users"))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(
								MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].userId").value(1))
				.andExpect(jsonPath("$[0].username").value("arslan"))
				.andExpect(jsonPath("$[1].userId").value(2))
				.andExpect(jsonPath("$[1].username").value("Snowlala"));
		verify(userService, times(1)).getAllUser();
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void testGetLangByNameSuccess() throws Exception {
		List<Languages> languages = Arrays
				.asList(new Languages(1, "HTML", 22.0), new Languages(2,
						"Java", 225.0));
		when(userService.getLanguageByUsername("arslanyasinwattoo"))
				.thenReturn(languages);
		mockMvc.perform(get("/api/show/{username}", "arslanyasinwattoo"))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(
								MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].name").value("HTML"))
				.andExpect(jsonPath("$[0].number").value(8.9069))
				.andExpect(jsonPath("$[1].name").value("Java"))
				.andExpect(jsonPath("$[1].number").value(91.0931));
		verify(userService, times(1))
				.getLanguageByUsername("arslanyasinwattoo");
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void testGetRepoByUserIdSuccess() throws Exception {
		List<Repository> repositories = Arrays.asList(new Repository(1, "x",
				"ars/x", "testing", new User(1, null, null, null, null, null,
						null)));
		when(userService.getRepositoryByUserId(1)).thenReturn(repositories);
		mockMvc.perform(get("/api/show/{id}/repos", 1))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(
								MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].repositoryId").value(1))
				.andExpect(jsonPath("$[0].name").value("x"))
				.andExpect(jsonPath("$[0].url").value("ars/x"))
				.andExpect(jsonPath("$[0].description").value("testing"));
		verify(userService, times(1)).getRepositoryByUserId(1);
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void testGetLangByRepoSuccess() throws Exception {
		List<Languages> languages = Arrays
				.asList(new Languages(1, "HTML", 22.0), new Languages(2,
						"Java", 225.0));
		when(userService.getLanguageByRepoId(1)).thenReturn(languages);
		mockMvc.perform(get("/api/show/{id}/lang", 1))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(
								MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].name").value("HTML"))
				.andExpect(jsonPath("$[0].number").value(8.9069))
				.andExpect(jsonPath("$[1].name").value("Java"))
				.andExpect(jsonPath("$[1].number").value(91.0931));
		verify(userService, times(1)).getLanguageByRepoId(1);
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void testGetByIdFail404() throws Exception {
		when(userService.getRepositoryByUserId(1)).thenReturn(null);
		mockMvc.perform(get("/api/show/{id}/repos", 1)).andExpect(
				status().isNotFound());
		verify(userService, times(1)).getRepositoryByUserId(1);
		verifyNoMoreInteractions(userService);
	}

}