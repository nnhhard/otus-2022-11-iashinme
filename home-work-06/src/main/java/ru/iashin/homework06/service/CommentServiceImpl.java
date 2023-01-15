package ru.iashin.homework06.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iashin.homework06.dto.CommentDto;
import ru.iashin.homework06.exception.ValidateException;
import ru.iashin.homework06.mapper.CommentMapper;
import ru.iashin.homework06.model.Comment;
import ru.iashin.homework06.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BookService bookService;
    private final CommentMapper commentMapper;

    @Override
    @Transactional
    public CommentDto createComment(long bookId, String messageComment) {
        var book = bookService.getBookById(bookId);
        validateMessageComment(messageComment);
        return commentMapper.entityToDto(commentRepository.save(new Comment(book.getId(), messageComment)));
    }

    @Override
    @Transactional
    public CommentDto updateComment(long id, String messageComment) {
        validateMessageComment(messageComment);
        Comment comment = getComment(id);
        comment.setMessageComment(messageComment);
        return commentMapper.entityToDto(commentRepository.save(comment));
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDto getCommentById(long id) {
        return commentRepository.findById(id).map(commentMapper::entityToDto).orElseThrow(
                () -> new ValidateException("Comment not find with id = " + id)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getAllCommentsByBookId(long bookId) {
        return commentMapper.entityToDto(commentRepository.findByBookId(bookId));
    }

    @Override
    @Transactional
    public void deleteCommentById(long id) {
        if(commentRepository.findById(id).isEmpty()) {
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
