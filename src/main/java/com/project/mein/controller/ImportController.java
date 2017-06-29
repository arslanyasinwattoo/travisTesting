package com.project.mein.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.mein.entity.Languages;
import com.project.mein.entity.Repository;
import com.project.mein.entity.User;
import com.project.mein.service.UserService;

@Controller
public class ImportController {

	@Autowired
	private UserService userService;
	private static final Logger logger = LoggerFactory
			.getLogger(ImportController.class);

	@RequestMapping(value = "/api/import/{username}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> importUser(
			@PathVariable("username") String username) throws Exception {
		logger.info(":/api/import/user");
		logger.debug("importUser() is executed, value {username:" + username
				+ "}", "arslan");

		logger.error("This is Error message", new Exception(
				"Importing user data"));

		// return new ResponseEntity<String>("testing 123", HttpStatus.OK);
		final String uri = "https://api.github.com/users/" + username;
		final String uriRepo = "https://api.github.com/users/" + username
				+ "/repos";
		// for sending and receiving json requests
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters",
				headers);
		// calling github rest endpoint for users
		try {
			ResponseEntity<String> result = restTemplate.exchange(uri,
					HttpMethod.GET, entity, String.class);
			logger.info("calling:api.github.com/users/{username}/repos");
			logger.debug("showUsers()-> users/" + username
					+ "/repos is executed ", "arslan");

			ObjectMapper mapper = new ObjectMapper();
			User user = new User();
			List<Repository> repository = new ArrayList<>();

			user = mapper.readValue(result.getBody(), User.class);
			user.setUsername(username);
			// handling bio limitations
			user.setBio(user.getBio().length() > 250 ? user.getBio().substring(
					0, 249) : user.getBio());
			// checking if the user exists before
			User user2 = userService.getUsersByName(user);
			if (user2.getUserId() != null) {
				user.setUserId(user2.getUserId());
				// System.out.println("user id testing advance" +
				// user.getUserId());
			}
			// if exists it updates else saves
			userService.addUser(user);
			// user = userService.getUsersByName(user);
			// calling github api endpoint for collecting all repository info of
			// user
			result = restTemplate.exchange(uriRepo, HttpMethod.GET, entity,
					String.class);
			repository = mapper.readValue(result.getBody(),
					new TypeReference<List<Repository>>() {
					});

			// for loop for getting all languages from all the repos a user has
			for (Repository repository2 : repository) {

				repository2.setUser(user);
				String uriLang = "https://api.github.com/repos/"
						+ repository2.getUser().getUsername() + "/"
						+ repository2.getName() + "/languages";
				// getting all languages of a repo

				result = restTemplate.exchange(uriLang, HttpMethod.GET, entity,
						String.class);
				logger.info("calling:api.github.com/repos/{username}/{repo}/languages");
				logger.debug("showUsers()-> repos/"
						+ repository2.getUser().getUsername() + "/"
						+ repository2.getName() + " is executed ", "arslan");

				// checks if repository already exists
				Repository repository3 = userService
						.getRepositoryByUrl(repository2);
				if (repository3.getRepositoryId() != null) {
					repository2.setRepositoryId(repository3.getRepositoryId());
				}
				String description = repository2.getDescription();
				// incase description is greater than 250
				if (description != null) {
					description = description.length() > 250 ? description
							.substring(0, 249) : description;
				}
				repository2.setDescription(description);
				// adds or updates the repositories
				userService.addRepository(repository2);
				repository2 = userService.getRepositoryByUrl(repository2);
				// System.out.println(repository2.getUrl());
				// for reading language data as their is no key and language
				// name is
				// the key itself
				JsonFactory factory = new JsonFactory();
				mapper = new ObjectMapper(factory);
				JsonNode rootNode = mapper.readTree(result.getBody());
				Iterator<Map.Entry<String, JsonNode>> fieldsIterator = rootNode
						.fields();
				while (fieldsIterator.hasNext()) {
					Languages languages2 = new Languages();
					List<Languages> list = new ArrayList<>();
					languages2.setRepository(repository2);
					Map.Entry<String, JsonNode> field = fieldsIterator.next();
					// System.out.println("Key: " + field.getKey() + "\tValue:"
					// + field.getValue());
					languages2.setName(field.getKey());
					languages2.setNumber(field.getValue().asDouble());
					if (repository2.getRepositoryId() != null) {
						Languages languages = new Languages();
						languages.setRepository(repository2);
						languages.setName(languages2.getName());
						// checking/calling id for the language based on repo id
						// and
						// name

						list = userService.getLanguageByRepoId(languages);
						// and checking if the language already exists in the db
						if (list != null) {
							for (Languages languages3 : list) {
								// if exists extra check and than set id for
								// updating instead of adding
								if (languages3.getName().equals(
										languages2.getName())) {
									languages2.setLanguagesId(languages3
											.getLanguagesId());

								}
							}

						}
					}
					// inserting or updating based on the id
					userService.addLanguages(languages2);

				}

			}

			logger.debug(result.getBody());
		} catch (HttpStatusCodeException e) {
			return ResponseEntity.status(e.getRawStatusCode()).body(null);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(
				"User data has been saved");
	}

}
