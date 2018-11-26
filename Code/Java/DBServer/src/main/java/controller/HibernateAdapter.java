package controller;

import controller.repositories.BookRepository;
import controller.repositories.BookRepository.BookNotFoundException;
import controller.repositories.CustomerRepository;
import controller.repositories.CustomerRepository.CustomerEmailException;
import model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class HibernateAdapter {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return ourSessionFactory;
    }

    public static void addObject(Object obj) {
        Transaction tx = null;
        try (Session session = ourSessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(obj);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public static void updateObject(Object obj) {
        Transaction tx = null;
        try (Session session = ourSessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.saveOrUpdate(obj);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public static void deleteObject(Object obj) {
        Transaction tx = null;
        try (Session session = ourSessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.delete(obj);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    private static void rebuildLuceneIndex() throws InterruptedException {
        HibernateAdapter db = new HibernateAdapter();
        SessionFactory sessionFactory = HibernateAdapter.getSessionFactory();
        FullTextSession fullTextSession = Search.getFullTextSession(sessionFactory.openSession());
        fullTextSession.createIndexer().startAndWait();
    }

    public static void main(String[] args) throws InterruptedException {
        SessionFactory sessionFactory = HibernateAdapter.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        EntityManager em = session.getEntityManagerFactory().createEntityManager();
        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
        em.getTransaction().begin();

// create native Lucene query unsing the query DSL
// alternatively you can write the Lucene query using the Lucene query parser
// or the Lucene programmatic API. The Hibernate Search DSL is recommended though
        QueryBuilder qb = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(Book.class).get();
        org.apache.lucene.search.Query luceneQuery = qb.keyword()
                .onFields("title", "bookIsbn", "author", "year", "category")
                .ignoreFieldBridge()
                .matching("fantasy 2014")
                .createQuery();

// wrap Lucene query in a javax.persistence.Query
        javax.persistence.Query jpaQuery =
                fullTextEntityManager.createFullTextQuery(luceneQuery, Book.class);

// execute search
        List result = jpaQuery.getResultList();

        em.getTransaction().commit();
        em.close();

        System.out.println(result);
//      HibernateAdapter.rebuildLuceneIndex();
    }

}