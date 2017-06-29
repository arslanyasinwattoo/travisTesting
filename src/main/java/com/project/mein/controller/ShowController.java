package com.project.mein.controller;

import java.text.DecimalFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.project.mein.entity.Languages;
import com.project.mein.entity.Repository;
import com.project.mein.entity.User;
import com.project.mein.service.UserService;

@Controller
public class ShowController {
	@Autowired
	private UserService userService;
	private static final Logger logger = LoggerFactory
			.getLogger(ShowController.class);

	@RequestMapping(value = "", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public String[] home() throws Exception {
		logger.info("Instructions page");
		logger.debug("debugging");
		// logger.error("This is Error message", new Exception(
		// "home page instructions"));
		String[] instructions = {
				"work flow and apis",
				"/api/import/{username}",
				"imports users from github.",
				"saves or updates data in mysql from users,repositories and languages of repositories",
				"/api/show/users",
				"fetches all users from user table",
				"/api/show/{id}/repos",
				"fetches repositories based on user id",
				"/api/show/{id}/lang",
				"fetches languages of repository which belonged to a particular user",
				"show languages used and percentage of contribution in the particular repository",
				"/api/show/{username}",
				"shows languages used and percentage of contribution in all therepositories" };
		return instructions;
	}

	@RequestMapping(value = "/api/show/users", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<List<User>> showUsers() throws Exception {
		logger.info("calling:/api/Show/users");
		logger.debug("showUsers() is executed", "arslan");
		logger.error("This is Error message", new Exception("Show all users"));

		List<User> list = userService.getAllUser();
		if (list != null) {

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/show/{id}/repos", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<List<Repository>> showUserRepos(
			@PathVariable("id") Integer id) throws Exception {
		logger.info("calling:/api/Show/{id}/repos");
		logger.debug("showUserRepos() is executed value{id:" + id + "}",
				"arslan");
		logger.error("This is Error message", new Exception(
				"Show all user repos"));

		List<Repository> list = userService.getRepositoryByUserId(id);
		if (list != null) {
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Repository>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/show/{id}/lang", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<List<Languages>> showReposLang(
			@PathVariable("id") Integer id) throws Exception {
		logger.info("calling:/api/Show/{id}/lang");
		logger.debug("showRepoLang() is executed", "arslan");
		logger.error("This is Error message", new Exception(
				"Show all Languages of repo"));

		List<Languages> list = userService.getLanguageByRepoId(id);
		if (list != null) {
			double total = 0;
			for (Languages languages : list) {
				total = total + languages.getNumber();
				System.out.println(total);
			}
			for (Languages languages : list) {
				// System.out.println(languages.getNumber());
				// System.out.println(total);
				String sum = new DecimalFormat("##.####").format(languages
						.getNumber() / total * 100);
				languages.setNumber(Double.parseDouble(sum));
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Languages>>(list, HttpStatus.OK);

	}

	@RequestMapping(value = "/api/show/{username}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<List<Languages>> showUserLangList(
			@PathVariable("username") String username) throws Exception {
		logger.info("calling:/api/Show/{username}");
		logger.debug(
				"showUsers() is executed value{username:" + username + "}",
				"arslan");
		logger.error("This is Error message", new Exception(
				"Show all languages of a user"));

		List<Languages> list = userService.getLanguageByUsername(username);
		double total = 0;
		if (list != null) {

			for (Languages languages : list) {
				total = total + languages.getNumber();
				System.out.println(total);
			}
			for (Languages languages : list) {

				String sum = new DecimalFormat("##.####").format(languages
						.getNumber() / total * 100);
				languages.setNumber(Double.parseDouble(sum));
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<Languages>>(list, HttpStatus.OK);
	}

}
