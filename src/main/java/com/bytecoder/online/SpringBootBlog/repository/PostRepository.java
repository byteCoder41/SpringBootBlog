package com.bytecoder.online.SpringBootBlog.repository;

import com.bytecoder.online.SpringBootBlog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

}
