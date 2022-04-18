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

    private String title;

    private String content;

    private int price;

//    private List<Image> imageList;

    private String categoryName;

}
