package com.hanghea.clonecarrotbe.dto;

import com.hanghea.clonecarrotbe.domain.Category;
import com.hanghea.clonecarrotbe.domain.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@Builder
public class PostResponseDto {
    private String username;
    private String title;
    private String content;
    private int price;
//    private List<Image> imageList;
    private String categoryName;
}
