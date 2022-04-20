package com.hanghea.clonecarrotbe.controller;

import com.hanghea.clonecarrotbe.dto.LoveGetResponseDto;
import com.hanghea.clonecarrotbe.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LoveController {
    private final com.hanghea.clonecarrotbe.service.LoveService LoveService;

    @GetMapping("/api/like/{postid}")
    public LoveGetResponseDto getLikes(@PathVariable Long postid,  @AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println("/api/like/{postid}/{username}");
        String username = userDetails.getUsername();
        System.out.println("username: "+username);
        System.out.println("postid: "+postid);
        return LoveService.getLove(postid, username);
    }

}
