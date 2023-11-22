package com.bytecoder.online.SpringBootBlog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
    private long id;

    @NotEmpty(message = "Name can't be empty")
    private String name;

    @Email(message = "Email must be valid")
    @NotEmpty(message = "Email can't be empty")
    private String email;

    @NotEmpty(message = "msg can't be empty")
    @Size(min = 10,message = "must be at least 10 chars long")
    private String msg;
}
