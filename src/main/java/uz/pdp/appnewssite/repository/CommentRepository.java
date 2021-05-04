package uz.pdp.appnewssite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appnewssite.entity.Comment;
import uz.pdp.appnewssite.entity.Post;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByIdAndCreatedBy(Long id, Long createdBy);

    Optional<Comment> findByIdAndCreatedBy(Long id, Long createdBy);

    List<Comment> findAllByPost(Post post);
}
