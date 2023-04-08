package ru.practicum.ewm.comment.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admin/comment")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminCommentController {
    private final AdminCommentService adminCommentService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable Long commentId) {
        adminCommentService.delete(commentId);
    }

}
