package com.bytecoder.online.SpringBootBlog.service.impl;

import com.bytecoder.online.SpringBootBlog.entity.Comment;
import com.bytecoder.online.SpringBootBlog.entity.Post;
import com.bytecoder.online.SpringBootBlog.exception.BlogApiException;
import com.bytecoder.online.SpringBootBlog.exception.ResourceNotFoundException;
import com.bytecoder.online.SpringBootBlog.payload.CommentDto;
import com.bytecoder.online.SpringBootBlog.repository.CommentRepository;
import com.bytecoder.online.SpringBootBlog.repository.PostRepository;
import com.bytecoder.online.SpringBootBlog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    private CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository, ModelMapper modelMapper){
        this.commentRepository=commentRepository;
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }
    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment c = mapToComment(commentDto);
        Post p = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",postId));
        c.setPost(p);
        Comment newComment = commentRepository.save(c);
        return mapToDto(newComment);

    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {

        List<Comment> comments= commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());

    }

    @Override
    public CommentDto getCommentById(long postId,long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id",commentId));
        if(!Objects.equals(comment.getPost().getId(), post.getId()))
        {
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment not found");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateCommentById(long postId, long commentId,CommentDto commentDto) {
        Post post  = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id",commentId));
        if(!Objects.equals(comment.getPost().getId(), post.getId()))
        {
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment not found");
        }
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setMsg(commentDto.getMsg());

        Comment c1 = commentRepository.save(comment);
        return mapToDto(c1);

    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id",commentId));
        if(!Objects.equals(comment.getPost().getId(), post.getId()))
        {
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment not found");
        }
        commentRepository.delete(comment);

    }

    private CommentDto mapToDto(Comment comment)
    {
        CommentDto c = modelMapper.map(comment,CommentDto.class);
        return c;
    }

    private Comment mapToComment(CommentDto comment)
    {
        Comment c = modelMapper.map(comment,Comment.class);
        return c;
    }

}
