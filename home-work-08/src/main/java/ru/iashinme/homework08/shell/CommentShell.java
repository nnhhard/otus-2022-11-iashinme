package ru.iashinme.homework08.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.iashinme.homework08.service.CommentConverter;
import ru.iashinme.homework08.service.CommentService;

@ShellComponent
@RequiredArgsConstructor
public class CommentShell {

    private final CommentService commentService;
    private final CommentConverter commentConverter;

    @ShellMethod(value = "Get all comments by book id", key = {"get-comment-list", "getallc"})
    public String getCommentsByBookId(@ShellOption String bookId) {
        return commentConverter.commentDtoListToStringWithGroupByBookId(commentService.getAllCommentsByBookId(bookId));
    }

    @ShellMethod(value = "Get comment by id", key = {"get-comment-by-id", "getc"})
    public String getCommentById(@ShellOption String id) {
        return commentConverter.commentDtoToString(commentService.getCommentById(id));
    }

    @ShellMethod(value = "Create comment", key = {"create-comment", "cc"})
    public String createComment(@ShellOption String bookId, @ShellOption String message) {
        var comment = commentService.createComment(bookId, message);
        return "The comment has been added successfully! " + commentConverter.commentDtoToString(comment);
    }

    @ShellMethod(value = "Update comment", key = {"update-comment", "uc"})
    public String updateComment(@ShellOption String id,
                                @ShellOption String name) {
        var comment = commentService.updateComment(id, name);
        return "Record changed successfully! " + commentConverter.commentDtoToString(comment);
    }

    @ShellMethod(value = "Delete comment by id", key = {"delete-comment-by-id", "delc"})
    public String deleteComment(@ShellOption String id) {
        commentService.deleteCommentById(id);
        return "The genre has been successfully deleted!";
    }
}
