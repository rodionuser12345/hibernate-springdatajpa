package rodion.springproject.springdatajpa.dao.hibernate;

import org.springframework.stereotype.Component;
import rodion.springproject.springdatajpa.dao.AuthorDao;
import rodion.springproject.springdatajpa.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Component
public class AuthorDaoImplHibernate implements AuthorDao {

    private final EntityManagerFactory emf;

    public AuthorDaoImplHibernate(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    @Override
    public Author getById(Long id) {
        return getEntityManager().find(Author.class, id);
    }

    @Override
    public Author findAuthorByFistNameAndLastName(String firstName, String lastName) {
        TypedQuery<Author> query = getEntityManager().createNamedQuery("find_by_name", Author.class);
        query.setParameter("first_name", firstName);
        query.setParameter("last_name", lastName);
        return query.getSingleResult();
    }

    @Override
    public Author findAuthorByNameCriteria(String firstName, String lastName) {

        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);

            Root<Author> root = criteriaQuery.from(Author.class);

            ParameterExpression<String> firstNameParam = criteriaBuilder.parameter(String.class);
            ParameterExpression<String> lastNameParam = criteriaBuilder.parameter(String.class);

            Predicate firstNamePred = criteriaBuilder.equal(root.get("firstName"), firstNameParam);
            Predicate lastNamePred = criteriaBuilder.equal(root.get("lastName"), lastNameParam);

            criteriaQuery.select(root).where(criteriaBuilder.and(firstNamePred, lastNamePred));

            TypedQuery<Author> query = em.createQuery(criteriaQuery);
            query.setParameter(firstNameParam, firstName);
            query.setParameter(lastNameParam, lastName);

            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Author> findAll() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Author> query = em.createNamedQuery("author_find_all", Author.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Author> listAuthorByLastNameLike(String lastName) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT a from Author a WHERE a.lastName LIKE :last_name ");
            query.setParameter("last_name", lastName + "%");
            return (List<Author>) query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Author save(Author author) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(author);
        em.flush();
        em.getTransaction().commit();
        return author;
    }

    @Override
    public Author update(Author author) {
        EntityManager em = getEntityManager();
        em.joinTransaction();
        em.merge(author);
        em.flush();
        em.clear();
        return em.find(Author.class, author.getId());
    }

    @Override
    public void deleteAuthorById(Long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Author toDelete = em.find(Author.class, id);
        em.remove(toDelete);
        em.flush();
        em.getTransaction().commit();
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
