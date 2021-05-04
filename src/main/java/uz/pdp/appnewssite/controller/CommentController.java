package uz.pdp.appnewssite.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appnewssite.aop.CheckPermission;
import uz.pdp.appnewssite.entity.Comment;
import uz.pdp.appnewssite.entity.Post;
import uz.pdp.appnewssite.payload.CommentDto;
import uz.pdp.appnewssite.payload.Response;
import uz.pdp.appnewssite.service.CommentService;

import java.util.List;


@RestController
@RequestMapping("/api/comment")
public class CommentController {

    final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @CheckPermission(value = "ADD_COMMENT")
    @PostMapping
    public HttpEntity<?> addComment(@RequestBody CommentDto commentDto) {
        Response response = commentService.addComment(commentDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @CheckPermission(value = "EDIT_COMMENT")
    @PutMapping("/{id}")
    public HttpEntity<?> editComment(@PathVariable(name = "id") Long commentId, @RequestBody CommentDto commentDto) {
        Response response = commentService.editComment(commentId, commentDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasAnyAuthority('DELETE_MY_COMMENT', 'DELETE_COMMENT')")
    @PutMapping("/{id}")
    public HttpEntity<?> deleteComment(@PathVariable(name = "id") Long commentId) {
        Response response = commentService.deleteComment(commentId);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    // faqat o'zi yozgan comment larni ko'rish
    @GetMapping("/{id}")
    public HttpEntity<?> getAllMyComments(@PathVariable(name = "id") Long commentId) {
        List<Comment> commentList = commentService.findAllMyComments(commentId);
        return ResponseEntity.status(commentList != null ? 200 : 409).body(commentList);
    }

    // Berilgan id li postga yozilgan barcha comment larni ko'rish
    @GetMapping("/post/{id}")
    public HttpEntity<?> getAllCommentsByPostId(@PathVariable(name = "id") Long postId) {
        List<Comment> commentList = commentService.findAllByPostId(postId);
        return ResponseEntity.status(commentList != null ? 200 : 409).body(commentList);
    }

}
