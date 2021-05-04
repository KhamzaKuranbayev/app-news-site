package uz.pdp.appnewssite.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appnewssite.aop.CheckPermission;
import uz.pdp.appnewssite.entity.Post;
import uz.pdp.appnewssite.payload.Response;
import uz.pdp.appnewssite.service.PostService;

import java.util.List;


@RestController
@RequestMapping("/api/post")
public class PostController {

    final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @CheckPermission(value = "ADD_POST")
    @PostMapping
    public HttpEntity<?> addPost(@RequestBody Post post) {
        Response response = postService.addPost(post);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @CheckPermission(value = "EDIT_POST")
    @PutMapping("/{id}")
    public HttpEntity<?> editPost(@PathVariable Long id, @RequestBody Post post) {
        Response response = postService.editPost(id, post);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @CheckPermission(value = "DELETE_POST")
    @PutMapping("/{id}")
    public HttpEntity<?> deletePost(@PathVariable Long id) {
        Response response = postService.deletePost(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    // faqat o'zi qo'shgan post larni olish
    @GetMapping("/{id}")
    public HttpEntity<?> getAllMyPosts(@PathVariable Long id) {
        List<Post> postList = postService.findAllById(id);
        return ResponseEntity.ok(postList);
    }

    @GetMapping
    public HttpEntity<?> getAllPosts() {
        List<Post> postList = postService.findAll();
        return ResponseEntity.ok(postList);
    }



}
