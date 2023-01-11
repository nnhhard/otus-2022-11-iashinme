package ru.iashin.homework06.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.iashin.homework06.model.Genre;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryJpa implements GenreRepository {

    private final EntityManager em;

    @Override
    public List<Genre> findAll() {
        return em.createQuery("select g from Genre g", Genre.class).getResultList();
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == 0) {
            em.persist(genre);
            return genre;
        } else {
            return em.merge(genre);
        }
    }

    @Override
    public Optional<Genre> findById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from Genre g " +
                "where g.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public long count() {
        return em.createQuery(
                "select count(g) from Genre g"
                , Long.class).getSingleResult();
    }
}

