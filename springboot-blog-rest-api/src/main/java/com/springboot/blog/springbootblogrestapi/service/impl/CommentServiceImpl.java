package com.springboot.blog.springbootblogrestapi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.blog.springbootblogrestapi.entity.Comment;
import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.BlogApiException;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.payload.CommentDto;
import com.springboot.blog.springbootblogrestapi.repository.CommentRepository;
import com.springboot.blog.springbootblogrestapi.repository.PostRepository;
import com.springboot.blog.springbootblogrestapi.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    // @Autowired
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;


    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }


    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        // TODO Auto-generated method stub

        Comment comment = mapToEntity(commentDto);
        //retrive post entity by Id

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        
        comment.setPost(post);

        Comment newComment = commentRepository.save(comment);
        return mapToDTO(newComment);
        // throw new UnsupportedOperationException("Unimplemented method 'createComment'");
    }


    private CommentDto mapToDTO(Comment comment){

        CommentDto commentDto = mapper.map(comment, CommentDto.class);
        // CommentDto commentDto = new CommentDto();
        // commentDto.setId(comment.getId());
        // commentDto.setName(comment.getName());
        // commentDto.setBody(comment.getBody());
        // commentDto.setEmail(comment.getEmail());
        return commentDto;
    }


    private Comment mapToEntity(CommentDto commentDto){

        Comment comment = mapper.map(commentDto, Comment.class);
        // Comment comment = new Comment();
        // comment.setId(commentDto.getId());
        // comment.setName(commentDto.getName());
        // comment.setBody(commentDto.getBody());
        // comment.setEmail(commentDto.getEmail());

        return comment;
    }


    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        // TODO Auto-generated method stub

        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());

        // throw new UnsupportedOperationException("Unimplemented method 'getCommentsByPostId'");
    }


    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        // TODO Auto-generated method stub

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to Post");
        }

        return mapToDTO(comment);
        // throw new UnsupportedOperationException("Unimplemented method 'getCommentById'");
    }


    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest) {
        // TODO Auto-generated method stub
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to Post");
        }

        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        Comment updatedComment = commentRepository.save(comment);

        return mapToDTO(updatedComment);


        // throw new UnsupportedOperationException("Unimplemented method 'updateComment'");
    }


    @Override
    public void deletecomment(Long postId, Long commentId) {
        // TODO Auto-generated method stub

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to Post");
        }

        commentRepository.delete(comment);
        // throw new UnsupportedOperationException("Unimplemented method 'deletecomment'");
    }


    

}
