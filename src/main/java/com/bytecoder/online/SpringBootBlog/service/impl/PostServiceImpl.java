package com.bytecoder.online.SpringBootBlog.service.impl;

import com.bytecoder.online.SpringBootBlog.entity.Post;
import com.bytecoder.online.SpringBootBlog.exception.ResourceNotFoundException;
import com.bytecoder.online.SpringBootBlog.payload.PostDto;
import com.bytecoder.online.SpringBootBlog.payload.PostResponse;
import com.bytecoder.online.SpringBootBlog.repository.PostRepository;
import com.bytecoder.online.SpringBootBlog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository,ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = mapToPost(postDto);

        Post newPost = postRepository.save(post);

        return  mapToDto(newPost);

    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by(sortBy));
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> postList = posts.getContent();
        List<PostDto> content= postList.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }


    @Override
    public PostDto getPostById(long id) {
        Post p = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
        return mapToDto(p);
    }

    @Override
    public PostDto updatePostById(long id,PostDto postDto) {
        Post p = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
        p.setContent(postDto.getContent());
        p.setDescription(postDto.getDescription());
        p.setTitle(postDto.getTitle());
        Post p1 = postRepository.save(p);

        return mapToDto(p1);
    }

    @Override
    public void deletePostById(long id) {
        Post p = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
        postRepository.delete(p);
    }

    private PostDto mapToDto(Post p){
        PostDto pdto = modelMapper.map(p,PostDto.class);
        return pdto;
    }
    private Post mapToPost(PostDto pd){
        Post p = modelMapper.map(pd,Post.class);
        return p;
    }

}
