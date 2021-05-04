package uz.pdp.appnewssite.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    @NotNull(message = "Text required!")
    private String text;

    @NotNull(message = "Post id required!")
    private Long postId;

}
