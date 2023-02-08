package ru.iashinme.homework08.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iashinme.homework08.dto.CommentDto;
import ru.iashinme.homework08.exception.ValidateException;
import ru.iashinme.homework08.mapper.CommentMapper;
import ru.iashinme.homework08.model.Comment;
import ru.iashinme.homework08.repository.BookRepository;
import ru.iashinme.homework08.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final CommentMapper commentWithoutBookMapper;

    @Override
    @Transactional
    public CommentDto createComment(String bookId, String messageComment) {
        validateMessageComment(messageComment);
        var book = bookRepository.findById(bookId).orElseThrow(
                () -> new ValidateException("Book not find with bookId = " + bookId)
        );

        return commentWithoutBookMapper.entityToDto(commentRepository.save(
                new Comment(book, messageComment))
        );
    }

    @Override
    @Transactional
    public CommentDto updateComment(String id, String messageComment) {
        validateMessageComment(messageComment);
        Comment comment = getComment(id);
        comment.setMessageComment(messageComment);
        return commentWithoutBookMapper.entityToDto(commentRepository.save(comment));
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDto getCommentById(String id) {
        return commentRepository.findById(id).map(commentWithoutBookMapper::entityToDto).orElseThrow(
                () -> new ValidateException("Comment not find with id = " + id)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getAllCommentsByBookId(String bookId) {
        return commentWithoutBookMapper.entityToDto(commentRepository.findByBookId(bookId));
    }

    @Override
    @Transactional
    public void deleteCommentById(String id) {
        if (commentRepository.findById(id).isEmpty()) {
            throw new ValidateException("Comment not find with id = " + id);
        }
        commentRepository.deleteById(id);
    }

    private Comment getComment(String id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new ValidateException("Comment not find with id = " + id)
        );
    }

    private void validateMessageComment(String messageComment) {
        if (StringUtils.isBlank(messageComment)) {
            throw new ValidateException("The comment message cannot be empty or null");
        }
    }
}
