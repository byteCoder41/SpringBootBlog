package com.bytecoder.online.SpringBootBlog.controller;

import com.bytecoder.online.SpringBootBlog.entity.Post;
import com.bytecoder.online.SpringBootBlog.payload.PostDto;
import com.bytecoder.online.SpringBootBlog.payload.PostResponse;
import com.bytecoder.online.SpringBootBlog.service.PostService;
import com.bytecoder.online.SpringBootBlog.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    PostService postService;

    public PostController(PostService postService1){

        this.postService=postService1;
    }

    // create a new post.
    @PostMapping
    public ResponseEntity<PostDto> createPost(
            @Valid @RequestBody PostDto postd){

           return new ResponseEntity<>(postService.createPost(postd), HttpStatus.CREATED);
    }

    // get all the posts from posts table.
    @GetMapping
    public PostResponse getAllPosts
        (
            @RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
        )
    {
        return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    }

    // get one post using post id.
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> postById(@PathVariable("id") long id)
    {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // update post by post id.
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePostById(@Valid @RequestBody PostDto postDto, @PathVariable("id") long id){

            PostDto p = postService.updatePostById(id,postDto);
            return new ResponseEntity<>(p,HttpStatus.OK);

    }

    // delete a post by its post id.
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable("id") long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post Deleted Successfully",HttpStatus.OK);
    }

}
