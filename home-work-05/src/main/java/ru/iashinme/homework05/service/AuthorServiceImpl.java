package ru.iashinme.homework05.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.iashinme.homework05.dao.AuthorDao;
import ru.iashinme.homework05.domain.Author;
import ru.iashinme.homework05.exception.ValidateException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    public int countAuthors() {
        return authorDao.count();
    }

    @Override
    public long createAuthor(String authorSurname, String authorName, String authorPatronymic) {
        Author author = getValidateAuthor(0, authorSurname, authorName, authorPatronymic);
        return authorDao.insert(author);
    }

    @Override
    public int updateAuthor(long id, String authorSurname, String authorName, String authorPatronymic) {
        Author author = getValidateAuthor(id, authorSurname, authorName, authorPatronymic);
        return authorDao.update(author);
    }

    @Override
    public Author getAuthorById(long id) {
        return authorDao.getById(id);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.getAll();
    }

    @Override
    public int deleteAuthorById(long id) {
        return authorDao.deleteById(id);
    }

    private Author getValidateAuthor(long id, String authorSurname, String authorName, String authorPatronymic) {
        if(StringUtils.isBlank(authorName))
            throw new ValidateException("Author name is null or empty!");
        if(StringUtils.isBlank(authorSurname))
            throw new ValidateException("Author surname is null or empty!");
        return new Author(id, authorSurname, authorName, authorPatronymic);
    }
}
