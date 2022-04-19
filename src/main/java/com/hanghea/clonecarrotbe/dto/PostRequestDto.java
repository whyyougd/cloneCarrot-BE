package com.hanghea.clonecarrotbe.dto;

import com.hanghea.clonecarrotbe.domain.Category;
import com.hanghea.clonecarrotbe.domain.Image;
import lombok.*;

import java.util.List;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequestDto {

    private String username;
    private String title;
    private String content;

    private Long price;
    private List<String> imageList;
    private Long categoryid;

}
