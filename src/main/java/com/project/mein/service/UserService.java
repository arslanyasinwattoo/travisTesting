package com.project.mein.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.mein.dao.GenericDao;
import com.project.mein.entity.Languages;
import com.project.mein.entity.Repository;
import com.project.mein.entity.User;

@Service
public class UserService {
	@Autowired
	private GenericDao genericDao;

	// Curd functions for user
	public void addUser(User user) throws Exception {
		genericDao.insertOrUpdate(user);
	}

	@Transactional
	public User getUsersByName(User user) throws Exception {

		List<User> list = genericDao.findByNamedQuery("getUserByUsername",
				user.getUsername());
		for (User user2 : list) {
			user = user2;
		}
		return user;
	}

	public List<User> getAllUser() throws Exception {
		// TODO Auto-generated method stub
		return genericDao.findAll(User.class);
	}

	// curd function for repository
	public void addRepository(Repository repository) throws Exception {
		genericDao.insertOrUpdate(repository);
	}

	public Repository getRepositoryByUrl(Repository repository)
			throws Exception {
		List<Repository> list = genericDao.findByNamedQuery(
				"getRepositoryByUrl", repository.getUrl());
		for (Repository repository2 : list) {
			repository = repository2;
		}
		return repository;
	}

	public List<Repository> getRepositoryByUserId(Integer id) throws Exception {

		return genericDao.findByNamedQuery("getRepositoryByUserId", id);
	}

	public List<Repository> getAllRepository() throws Exception {
		// TODO Auto-generated method stub
		return genericDao.findAll(Languages.class);
	}

	// curd functions for lanuages
	public void addLanguages(Languages languages) throws Exception {
		genericDao.insertOrUpdate(languages);
	}

	public List<Languages> getAllLanguages() throws Exception {
		// TODO Auto-generated method stub
		return genericDao.findAll(Languages.class);
	}

	public List<Languages> getLanguageByRepoId(Languages languages)
			throws Exception {

		return genericDao.findByNamedQuery("getLanguageByRepoId", languages
				.getRepository().getRepositoryId(), languages.getName());
	}

	public List<Languages> getLanguageByRepoId(Integer id) throws Exception {

		return genericDao.findByNamedQuery("getLanguageByRepoId", id);
	}

	public List<Languages> getLanguageByUsername(String username)
			throws Exception {

		return genericDao.findByNamedQuery("getLanguageByUsername", username);
	}

}
