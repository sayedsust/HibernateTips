package org.thoughts.on.java.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestQueryCache {

	Logger log = Logger.getLogger(this.getClass().getName());

	private EntityManagerFactory emf;

	@Before
	public void init() {
		emf = Persistence.createEntityManagerFactory("my-persistence-unit");
	}

	@After
	public void close() {
		emf.close();
	}

	@Test
	public void selectAuthors() {
		log.info("... selectAuthors ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		Session s = em.unwrap(Session.class);
		Query<Author> q = s.createQuery("SELECT a FROM Author a WHERE id = :id", Author.class);
		q.setParameter("id", 1L);
		q.setCacheable(true);
		log.info(q.getSingleResult());
		
		log.info(q.getSingleResult());

		em.getTransaction().commit();
		em.close();
	}
}
