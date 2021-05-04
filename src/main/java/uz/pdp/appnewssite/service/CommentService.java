package uz.pdp.appnewssite.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.appnewssite.entity.Comment;
import uz.pdp.appnewssite.entity.Post;
import uz.pdp.appnewssite.entity.Role;
import uz.pdp.appnewssite.entity.User;
import uz.pdp.appnewssite.exceptions.ResourceNotFoundException;
import uz.pdp.appnewssite.payload.CommentDto;
import uz.pdp.appnewssite.payload.Response;
import uz.pdp.appnewssite.repository.CommentRepository;
import uz.pdp.appnewssite.repository.PostRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    final CommentRepository commentRepository;
    final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }


    public Response addComment(CommentDto commentDto) {
        Comment comment =
                new Comment(
                        commentDto.getText(),
                        postRepository.findById(commentDto.getPostId()).orElseThrow(() ->
                                new ResourceNotFoundException("Not found post with id = " + commentDto.getPostId())));
        commentRepository.save(comment);
        return new Response("Comment saved!", true);
    }

    public Response editComment(Long commentId, CommentDto commentDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            User user = (User) authentication.getPrincipal();

            Optional<Comment> optionalComment = commentRepository.findByIdAndCreatedBy(commentId, user.getId());
            if (!optionalComment.isPresent())
                return new Response("Comment was not found!", false);

            optionalComment.get().setText(commentDto.getText());
            commentRepository.save(optionalComment.get());
            return new Response("Comment updated!", true);
        }
        return new Response("Authorization empty!", false);
    }

    public Response deleteComment(Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            User user = (User) authentication.getPrincipal();
            Role role = user.getRole();

            Optional<Comment> optionalComment;
            if (role.getName().equals("ADMIN")) {
                optionalComment = commentRepository.findById(commentId);
            } else {
                optionalComment = commentRepository.findByIdAndCreatedBy(commentId, user.getId());
            }

            if (!optionalComment.isPresent())
                return new Response("Comment was not found!", false);

            commentRepository.deleteById(commentId);
            return new Response("Comment deleted!", true);
        }
        return new Response("Authorization empty!", false);
    }

    public List<Comment> findAllMyComments(Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            User user = (User) authentication.getPrincipal();
            return commentRepository.findAllByIdAndCreatedBy(commentId, user.getId());
        }
        return null;
    }

    public List<Comment> findAllByPostId(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        return optionalPost.map(commentRepository::findAllByPost).orElse(null);
    }
}
