package uz.pdp.appnewssite.service;

import org.springframework.stereotype.Service;
import uz.pdp.appnewssite.entity.Post;
import uz.pdp.appnewssite.payload.Response;
import uz.pdp.appnewssite.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Response addPost(Post post) {
        postRepository.save(post);
        return new Response("Post added!", true);
    }

    public Response editPost(Long id, Post post) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if(!optionalPost.isPresent())
            return new Response("Such post id was not found!", false);

        optionalPost.get().setTitle(post.getTitle());
        optionalPost.get().setText(post.getText());
        optionalPost.get().setUrl(post.getUrl());

        postRepository.save(optionalPost.get());
        return new Response("Post updated!", true);
    }

    public Response deletePost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if(!optionalPost.isPresent())
            return new Response("Such post id was not found!", false);

        postRepository.deleteById(id);

        return new Response("Post deleted!", true);
    }

    public List<Post> findAllById(Long id) {
        List<Post> postList = postRepository.findAllByCreatedBy(id);
        return postList;
    }


    public List<Post> findAll() {
        return postRepository.findAll();
    }


}
