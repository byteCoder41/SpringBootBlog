package com.bytecoder.online.SpringBootBlog.repository;

import com.bytecoder.online.SpringBootBlog.entity.Comment;
import com.bytecoder.online.SpringBootBlog.payload.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
   List<Comment> findByPostId(long postId);
}
