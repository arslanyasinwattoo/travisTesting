//package com.project.mein.controller.test;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.verifyNoMoreInteractions;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.Arrays;
//import java.util.List;
//import static org.mockito.Mockito.*;
//import static org.mockito.Mockito.doNothing;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import com.project.mein.controller.ImportController;
//import com.project.mein.entity.User;
//import com.project.mein.service.UserService;
//
//@WebAppConfiguration
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:**/WEB-INF/applicationContext.xml")
//public class ImportControllerTest {
//
//	private MockMvc mockMvc;
//
//	@Mock
//	private UserService userService;
//
//	@InjectMocks
//	private ImportController importController;
//
//	@Before
//	public void init() {
//		MockitoAnnotations.initMocks(this);
//		mockMvc = MockMvcBuilders.standaloneSetup(importController).build();
//	}
//
//	@Test
//	public void testImportUser() throws Exception {
//		User user = new User(
//				null,
//				"arslanyasinwattoo",
//				"arslan",
//				"germany",
//				"none",
//				null,
//				"swalla swallaaaaaaaaaaaaaaaaaaaaaaaasdasdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//		doNothing().when(userService).addUser(user);
//		mockMvc.perform(get("/api/import/{username}", "arslanyasinwattoo"))
//				.andExpect(status().isOk())
//				.andExpect(
//						content().contentType(
//								MediaType.APPLICATION_JSON_UTF8_VALUE));
//		verify(userService, times(1)).addUser(user);
//		verifyNoMoreInteractions(userService);
//	}
//
//}
