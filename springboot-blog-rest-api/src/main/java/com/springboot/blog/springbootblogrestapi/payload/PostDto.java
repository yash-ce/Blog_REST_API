package com.springboot.blog.springbootblogrestapi.payload;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDto {
    private Long id;

    @NotEmpty
    @Size(min = 2, message = "Post title should  have minimum 2 characters")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Post description should  have minimum 10 characters")
    private String description;

    @NotEmpty
    private String content;

    private Set<CommentDto> comments;
}
