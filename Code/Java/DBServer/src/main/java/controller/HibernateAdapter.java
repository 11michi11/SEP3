package controller;

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
import java.util.List;

public class HibernateAdapter {
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @SuppressWarnings("Duplicates")
    public static List executeQuery(String searchTerm, Class classObj, String... fields) {
        SessionFactory sessionFactory = HibernateAdapter.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        EntityManager em = session.getEntityManagerFactory().createEntityManager();
        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
        em.getTransaction().begin();
// create native Lucene query unsing the query DSL
// alternatively you can write the Lucene query using the Lucene query parser
// or the Lucene programmatic API. The Hibernate Search DSL is recommended though
        QueryBuilder qb = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(classObj).get();
        org.apache.lucene.search.Query luceneQuery = qb.keyword()
                .onFields(fields)
                .ignoreFieldBridge()
                .matching(searchTerm)
                .createQuery();
// wrap Lucene query in a javax.persistence.Query
        javax.persistence.Query jpaQuery =
                fullTextEntityManager.createFullTextQuery(luceneQuery, classObj);
// execute search
        List result = jpaQuery.getResultList();
        em.getTransaction().commit();
        em.close();
        System.out.println(result);
        transaction.commit();
        return result;
    }

    public static void addObject(Object obj) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
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
        try (Session session = sessionFactory.openSession()) {
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
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.delete(obj);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public static List executeQueryAdvancedSearch(String isbn, String title, String author, int year, Book.Category category) {
        SessionFactory sessionFactory = HibernateAdapter.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        EntityManager em = session.getEntityManagerFactory().createEntityManager();
        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
        em.getTransaction().begin();

// create native Lucene query unsing the query DSL
// alternatively you can write the Lucene query using the Lucene query parser
// or the Lucene programmatic API. The Hibernate Search DSL is recommended though
        QueryBuilder qb = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(Book.class).get();

        org.apache.lucene.search.Query luceneQuery = qb.bool()
                .should(qb.keyword()
                        .onFields("bookIsbn")
                        .ignoreFieldBridge()//for enum
                        .ignoreAnalyzer()
                        .matching(isbn)
                        .createQuery())
                .should(qb.keyword()
                        .onFields("title")
                        .ignoreFieldBridge()//for enum
                        .ignoreAnalyzer()
                        .matching(title)
                        .createQuery())
                .should(qb.keyword()
                        .onFields("author")
                        .ignoreFieldBridge()//for enum
                        .ignoreAnalyzer()
                        .matching(author)
                        .createQuery())
                .should(qb.keyword()
                        .onFields("year")
                        .matching(year)
                        .createQuery())
                .should(qb.keyword()
                        .onFields("category")
                        .ignoreFieldBridge()//for enum
                        .ignoreAnalyzer()
                        .matching(category)
                        .createQuery())
                .createQuery();

// wrap Lucene query in a javax.persistence.Query
        javax.persistence.Query jpaQuery =
                fullTextEntityManager.createFullTextQuery(luceneQuery, Book.class);

// execute search
        List result = jpaQuery.getResultList();
        em.getTransaction().commit();
        em.close();
        System.out.println(result);
        transaction.commit();
        return result;
    }

    public static List searchInLibrary(String searchTerm, String libraryId) {
        SessionFactory sessionFactory = HibernateAdapter.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        EntityManager em = session.getEntityManagerFactory().createEntityManager();
        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
        em.getTransaction().begin();

// create native Lucene query unsing the query DSL
// alternatively you can write the Lucene query using the Lucene query parser
// or the Lucene programmatic API. The Hibernate Search DSL is recommended though
        QueryBuilder qb = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(LibraryStorage.class).get();

        org.apache.lucene.search.Query luceneQuery = qb.bool()
                .must(qb.keyword()
                        .onFields("book.bookIsbn", "book.title", "book.author", "book.year", "book.category")
                        .ignoreFieldBridge()
                        .matching(searchTerm)
                        .createQuery())
                .must(qb.keyword()
                        .onField("library.library")
                        .matching(libraryId)
                        .createQuery())
                .createQuery();

// wrap Lucene query in a javax.persistence.Query
        javax.persistence.Query jpaQuery =
                fullTextEntityManager.createFullTextQuery(luceneQuery, LibraryStorage.class);

// execute search
        List result = jpaQuery.getResultList();
        em.getTransaction().commit();
        em.close();
        System.out.println(result);
        transaction.commit();
        return result;
    }

    public static List searchInBookStore(String searchTerm, String bookstoreId) {
        SessionFactory sessionFactory = HibernateAdapter.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        EntityManager em = session.getEntityManagerFactory().createEntityManager();
        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
        em.getTransaction().begin();

// create native Lucene query unsing the query DSL
// alternatively you can write the Lucene query using the Lucene query parser
// or the Lucene programmatic API. The Hibernate Search DSL is recommended though
        QueryBuilder qb = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(BookStoreStorage.class).get();

        org.apache.lucene.search.Query luceneQuery = qb.bool()
                .must(qb.keyword()
                        .onFields("book.bookIsbn", "book.title", "book.author", "book.year", "book.category")
                        .ignoreFieldBridge()
                        .matching(searchTerm)
                        .createQuery())
                .must(qb.keyword()
                        .onField("bookstore.bookstore")
                        .matching(bookstoreId)
                        .createQuery())
                .createQuery();

// wrap Lucene query in a javax.persistence.Query
        javax.persistence.Query jpaQuery =
                fullTextEntityManager.createFullTextQuery(luceneQuery, BookStoreStorage.class);

// execute search
        List result = jpaQuery.getResultList();
        em.getTransaction().commit();
        em.close();
        System.out.println(result);
        transaction.commit();
        return result;
    }

    public static List advancedSearchInLibrary(String libraryId, String isbn, String title, String author, int year, Book.Category category){
        SessionFactory sessionFactory = HibernateAdapter.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        EntityManager em = session.getEntityManagerFactory().createEntityManager();
        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
        em.getTransaction().begin();

// create native Lucene query unsing the query DSL
// alternatively you can write the Lucene query using the Lucene query parser
// or the Lucene programmatic API. The Hibernate Search DSL is recommended though
        QueryBuilder qb = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(LibraryStorage.class).get();

        org.apache.lucene.search.Query luceneQuery = qb.bool()
                .must(qb.bool()
                        .should(qb.keyword()
                                .onFields("book.bookIsbn")
                                .ignoreFieldBridge()//for enum
                                .matching(isbn)
                                .createQuery())
                        .should(qb.keyword()
                                .onFields("book.title")
                                .ignoreFieldBridge()//for enum
                                .matching(title)
                                .createQuery())
                        .should(qb.keyword()
                                .onFields("book.author")
                                .ignoreFieldBridge()//for enum
                                .matching(author)
                                .createQuery())
                        .should(qb.keyword()
                                .onFields("book.year")
                                .matching(year)
                                .createQuery())
                        .should(qb.keyword()
                                .onFields("book.category")
                                .ignoreFieldBridge()//for enum
                                .ignoreAnalyzer()
                                .matching(category)
                                .createQuery())
                        .createQuery())
                .must(qb.keyword()
                        .onField("library.library")
                        .matching(libraryId)
                        .createQuery())
                .createQuery();

// wrap Lucene query in a javax.persistence.Query
        javax.persistence.Query jpaQuery =
                fullTextEntityManager.createFullTextQuery(luceneQuery, LibraryStorage.class);

// execute search
        List result = jpaQuery.getResultList();
        em.getTransaction().commit();
        em.close();
        System.out.println(result);
        transaction.commit();
        return result;
    }

    public static List advancedSearchInBookStore(String bookstoreId, String isbn, String title, String author, int year, Book.Category category){
        SessionFactory sessionFactory = HibernateAdapter.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        EntityManager em = session.getEntityManagerFactory().createEntityManager();
        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
        em.getTransaction().begin();

// create native Lucene query unsing the query DSL
// alternatively you can write the Lucene query using the Lucene query parser
// or the Lucene programmatic API. The Hibernate Search DSL is recommended though
        QueryBuilder qb = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(BookStoreStorage.class).get();

        org.apache.lucene.search.Query luceneQuery = qb.bool()
                .must(qb.bool()
                        .should(qb.keyword()
                                .onFields("book.bookIsbn")
                                .ignoreFieldBridge()//for enum
                                .ignoreAnalyzer()
                                .matching(isbn)
                                .createQuery())
                        .should(qb.keyword()
                                .onFields("book.title")
                                .ignoreFieldBridge()//for enum
                                .ignoreAnalyzer()
                                .matching(title)
                                .createQuery())
                        .should(qb.keyword()
                                .onFields("book.author")
                                .ignoreFieldBridge()//for enum
                                .ignoreAnalyzer()
                                .matching(author)
                                .createQuery())
                        .should(qb.keyword()
                                .onFields("book.year")
                                .matching(year)
                                .createQuery())
                        .should(qb.keyword()
                                .onFields("book.category")
                                .ignoreFieldBridge()//for enum
                                .ignoreAnalyzer()
                                .matching(category)
                                .createQuery())
                        .createQuery())
                .must(qb.keyword()
                        .onField("bookstore.bookstore")
                        .matching(bookstoreId)
                        .createQuery())
                .createQuery();

// wrap Lucene query in a javax.persistence.Query
        javax.persistence.Query jpaQuery =
                fullTextEntityManager.createFullTextQuery(luceneQuery, BookStoreStorage.class);

// execute search
        List result = jpaQuery.getResultList();
        em.getTransaction().commit();
        em.close();
        System.out.println(result);
        transaction.commit();
        return result;
    }

    private static void rebuildLuceneIndex() throws InterruptedException {
        HibernateAdapter db = new HibernateAdapter();
        SessionFactory sessionFactory = HibernateAdapter.getSessionFactory();
        FullTextSession fullTextSession = Search.getFullTextSession(sessionFactory.openSession());
        fullTextSession.createIndexer().startAndWait();
    }

    public static void main(String[] args) throws InterruptedException {

//        HibernateAdapter.searchInLibrary("testisbn","ce78ef57-77ec-4bb7-82a2-1a78d3789aef");

               HibernateAdapter.rebuildLuceneIndex();
    }

}