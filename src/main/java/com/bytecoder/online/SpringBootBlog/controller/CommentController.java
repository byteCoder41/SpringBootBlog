package com.bytecoder.online.SpringBootBlog.controller;

import com.bytecoder.online.SpringBootBlog.payload.CommentDto;
import com.bytecoder.online.SpringBootBlog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService sc;

    private CommentController(CommentService sc)
    {
        this.sc = sc;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,@Valid @RequestBody CommentDto commentDto){

         return new ResponseEntity<>(sc.createComment(postId,commentDto),HttpStatus.CREATED);
    }
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable("postId") long postId){

        return sc.getCommentsByPostId(postId);

    }
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") long postId,@PathVariable("commentId") long commentId)
    {
        CommentDto c = sc.getCommentById(postId, commentId);
        return new ResponseEntity<>(c,HttpStatus.OK);
    }
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateCommentById
            (
            @PathVariable("postId") long postId,
            @PathVariable("commentId") long commentId,
            @Valid @RequestBody CommentDto cdt1
            )
    {
     CommentDto c1 =  sc.updateCommentById(postId,commentId,cdt1);
     return new ResponseEntity<>(c1,HttpStatus.CREATED);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment
            (
                    @PathVariable("postId") long postId,
                    @PathVariable("commentId") long commentId
            )
    {
        sc.deleteComment(postId,commentId);
        return ResponseEntity.ok("Comment Deleted Successfully");
    }

}
