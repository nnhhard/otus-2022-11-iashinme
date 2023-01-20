package ru.iashin.homework06.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iashin.homework06.dto.CommentWithBookIdNameGenreDto;
import ru.iashin.homework06.dto.CommentWithoutBookDto;
import ru.iashin.homework06.exception.ValidateException;
import ru.iashin.homework06.mapper.CommentWithBookIdNameGenreMapper;
import ru.iashin.homework06.mapper.CommentWithoutBookMapper;
import ru.iashin.homework06.model.Author;
import ru.iashin.homework06.model.Book;
import ru.iashin.homework06.model.Comment;
import ru.iashin.homework06.model.Genre;
import ru.iashin.homework06.repository.CommentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BookService bookService;
    private final CommentWithoutBookMapper commentWithoutBookMapper;
    private final CommentWithBookIdNameGenreMapper commentWithBookIdNameGenreMapper;

    @Override
    @Transactional
    public CommentWithBookIdNameGenreDto createComment(long bookId, String messageComment) {
        var book = bookService.getBookById(bookId);
        validateMessageComment(messageComment);
        return commentWithBookIdNameGenreMapper.entityToDto(commentRepository.save(
                new Comment(new Book(
                        book.getId(),
                        book.getName(),
                        book.getAuthors().stream().map(a -> new Author(a.getId(), a.getSurname(), a.getName(), a.getPatronymic())).collect(Collectors.toList()),
                        new Genre(book.getGenre().getId(), book.getName())
                ), messageComment))
        );
    }

    @Override
    @Transactional
    public CommentWithBookIdNameGenreDto updateComment(long id, String messageComment) {
        validateMessageComment(messageComment);
        Comment comment = getComment(id);
        comment.setMessageComment(messageComment);
        return commentWithBookIdNameGenreMapper.entityToDto(commentRepository.save(comment));
    }

    @Override
    @Transactional(readOnly = true)
    public CommentWithBookIdNameGenreDto getCommentById(long id) {
        return commentRepository.findById(id).map(commentWithBookIdNameGenreMapper::entityToDto).orElseThrow(
                () -> new ValidateException("Comment not find with id = " + id)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentWithoutBookDto> getAllCommentsByBookId(long bookId) {
        return commentWithoutBookMapper.entityToDto(commentRepository.findByBookId(bookId));
    }

    @Override
    @Transactional
    public void deleteCommentById(long id) {
        if (commentRepository.findById(id).isEmpty()) {
            throw new ValidateException("Comment not find with id = " + id);
        }
        commentRepository.deleteById(id);
    }

    private Comment getComment(long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new ValidateException(String.format("Comment not find with id = %d", id))
        );
    }

    private void validateMessageComment(String messageComment) {
        if (StringUtils.isBlank(messageComment)) {
            throw new ValidateException("The comment message cannot be empty or null");
        }
    }
}
