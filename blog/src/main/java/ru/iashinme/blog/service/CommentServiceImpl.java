package ru.iashinme.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iashinme.blog.dto.CommentDto;
import ru.iashinme.blog.dto.CommentRequestDto;
import ru.iashinme.blog.exception.ValidateException;
import ru.iashinme.blog.mapper.CommentMapper;
import ru.iashinme.blog.model.Comment;
import ru.iashinme.blog.model.Post;
import ru.iashinme.blog.model.User;
import ru.iashinme.blog.repository.CommentRepository;
import ru.iashinme.blog.repository.PostRepository;

import java.util.List;
import java.util.Objects;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    @Transactional
    public
    CommentDto save(CommentRequestDto commentRequestDto, User user) {

        Post post = postRepository.findById(commentRequestDto.getPostId()).orElseThrow(
                () -> new ValidateException("Post not found!")
        );

        Comment comment = Comment
                    .builder()
                    .text(commentRequestDto.getText())
                    .author(user)
                    .post(post)
                    .time(now())
                    .build();

        return commentMapper.entityToDto(commentRepository.save(comment));
    }

    @Override
    public CommentDto edit(CommentRequestDto commentRequestDto, User user) {

        var comment = commentRepository.findById(commentRequestDto.getId())
                .orElseThrow(
                        () -> new ValidateException("Comment not found!")
                );

        if(!Objects.equals(comment.getAuthor().getId(), user.getId())) {
            throw new ValidateException("You are not the author of the comment!");
        }

        comment.setText(commentRequestDto.getText());

        return commentMapper.entityToDto(commentRepository.save(comment));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findAllByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Override
    public CommentDto findById(Long id) {
        var comment = commentRepository.findById(id).orElseThrow(
                () -> new ValidateException("Comment not found!")
        );

        return commentMapper.entityToDto(comment);
    }

    @Override
    @Transactional
    public void delete(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
