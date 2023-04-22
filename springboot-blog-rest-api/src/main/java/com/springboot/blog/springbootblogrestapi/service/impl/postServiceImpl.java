package com.springboot.blog.springbootblogrestapi.service.impl;


import java.util.List;

import java.util.stream.Collectors;

// import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.payload.PostDto;
import com.springboot.blog.springbootblogrestapi.payload.PostResponse;
import com.springboot.blog.springbootblogrestapi.repository.PostRepository;
import com.springboot.blog.springbootblogrestapi.service.PostService;

@Service
public class postServiceImpl implements PostService{

    private PostRepository postRepository;
    private ModelMapper mapper;

    // @Autowired
    public postServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        // TODO Auto-generated method stub

        //convert DTO to entity 
        Post post =  mapToEntity(postDto);

        Post newPost = postRepository.save(post);

        //Convert entity to dto

        PostDto postResponse = mapToDTO(newPost);

        return postResponse;
        // throw new UnsupportedOperationException("Unimplemented method 'createPost'");
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        // TODO Auto-generated method stub

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() 
        : Sort.by(sortBy).descending(); 

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> listOfPosts = posts.getContent();
        List<PostDto> content =  listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;

        // throw new UnsupportedOperationException("Unimplemented method 'getAllPosts'");
    }


    //Entity into DTO
    private PostDto mapToDTO(Post post){

        PostDto postDto = mapper.map(post, PostDto.class);
        // PostDto postDto = new PostDto();
        // postDto.setId(post.getId());
        // postDto.setTitle(post.getTitle());
        // postDto.setDescription(post.getDescription());
        // postDto.setContent(post.getContent());

        return postDto;
    }

    //Converted Dto to Entity
    private Post mapToEntity(PostDto postDto){
        Post post  = mapper.map(postDto, Post.class);
        
        // Post post =new Post();
        // post.setTitle(postDto.getTitle());
        // post.setDescription(postDto.getDescription());
        // post.setContent(postDto.getContent());

        return post;

    }

    @Override
    public PostDto getPostById(Long id) {
        // TODO Auto-generated method stub

        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post", "id", id));
        return mapToDTO(post);
        // throw new UnsupportedOperationException("Unimplemented method 'getPostById'");
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        // TODO Auto-generated method stub
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatePost = postRepository.save(post);
        return mapToDTO(updatePost);
        // throw new UnsupportedOperationException("Unimplemented method 'updatePost'");
    }

    @Override
    public void deletePostById(Long id) {
        // TODO Auto-generated method stub

        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post", "id", id));
        postRepository.delete(post);
        // throw new UnsupportedOperationException("Unimplemented method 'deletePostById'");
    }
    
}
