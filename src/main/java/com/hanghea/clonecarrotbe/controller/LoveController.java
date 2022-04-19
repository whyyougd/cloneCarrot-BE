package com.hanghea.clonecarrotbe.controller;

import com.hanghea.clonecarrotbe.dto.LoveGetResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LoveController {
    private final com.hanghea.clonecarrotbe.service.LoveService LoveService;

    @GetMapping("/api/like/{postid}/{username}")
    public LoveGetResponseDto getLikes(@PathVariable Long postid, @PathVariable String username){
        System.out.println("/api/like/{postid}/{username}");
        System.out.println("username: "+username);
        System.out.println("postid: "+postid);
        return LoveService.getLove(postid, username);
    }

}
