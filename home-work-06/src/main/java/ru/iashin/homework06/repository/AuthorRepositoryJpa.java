package ru.iashin.homework06.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.iashin.homework06.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryJpa implements AuthorRepository {

    private final EntityManager em;

    @Override
    public List<Author> findAll() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public Author save(Author author) {
        if (author.getId() == 0) {
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
        }
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from Author a " +
                "where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public long count() {
        return em.createQuery(
                "select count(a) from Author a"
                , Long.class).getSingleResult();
    }
}
