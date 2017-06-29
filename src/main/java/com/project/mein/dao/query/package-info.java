@org.hibernate.annotations.NamedQueries({

		@org.hibernate.annotations.NamedQuery(name = "getUserByUsername", query = "SELECT u FROM User u "
				+ "WHERE u.username= ?"),
		@org.hibernate.annotations.NamedQuery(name = "getRepositoryByUrl", query = "SELECT r FROM Repository r where r.url=?"),
		@org.hibernate.annotations.NamedQuery(name = "getRepositoryByUserId", query = "SELECT r FROM Repository r Join r.user  WHERE r.user.userId= ?"),
		@org.hibernate.annotations.NamedQuery(name = "getLanguageByRepoId", query = "SELECT l FROM Languages l  WHERE l.repository.repositoryId = ? and l.name=?"),
		@org.hibernate.annotations.NamedQuery(name = "getLanguageByUsername", query = "SELECT l FROM Languages l join l.repository join l.repository.user where l.repository.user.username = ?"),

})
package com.project.mein.dao.query;

