package com.bytecoder.online.SpringBootBlog.service;

import com.bytecoder.online.SpringBootBlog.payload.PostDto;
import com.bytecoder.online.SpringBootBlog.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir);
    PostDto getPostById(long id);
    PostDto updatePostById(long id,PostDto postDto);
    void deletePostById(long id);
}
