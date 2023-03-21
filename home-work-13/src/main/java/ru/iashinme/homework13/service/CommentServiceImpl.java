package ru.iashinme.homework13.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iashinme.homework13.dto.CommentDto;
import ru.iashinme.homework13.dto.CommentWithoutBookDto;
import ru.iashinme.homework13.exception.ValidateException;
import ru.iashinme.homework13.mapper.CommentMapper;
import ru.iashinme.homework13.mapper.CommentWithoutBookMapper;
import ru.iashinme.homework13.model.Comment;
import ru.iashinme.homework13.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BookService bookService;
    private final CommentWithoutBookMapper commentWithoutBookMapper;
    private final CommentMapper commentWithBookIdNameGenreMapper;

    @Override
    @Transactional
    public CommentDto create(long bookId, String messageComment) {
        var book = bookService.findById(bookId);
        validateMessageComment(messageComment);
        return commentWithBookIdNameGenreMapper.entityToDto(commentRepository.save(
                new Comment(book.toEntity(), messageComment))
        );
    }

    @Override
    @Transactional
    public CommentDto update(long id, String messageComment) {
        validateMessageComment(messageComment);
        Comment comment = getComment(id);
        comment.setMessageComment(messageComment);
        return commentWithBookIdNameGenreMapper.entityToDto(commentRepository.save(comment));
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDto findById(long id) {
        return commentRepository.findById(id).map(commentWithBookIdNameGenreMapper::entityToDto).orElseThrow(
                () -> new ValidateException("Comment not find with id = " + id)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentWithoutBookDto> findAllByBookId(long bookId) {
        return commentWithoutBookMapper.entityToDto(commentRepository.findByBookId(bookId));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
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
