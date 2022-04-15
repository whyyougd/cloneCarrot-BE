package com.hanghea.clonecarrotbe.controller;

import com.hanghea.clonecarrotbe.dto.StatusResponseDto;
import com.hanghea.clonecarrotbe.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class StatusController {
    private final StatusService statusService;

    @PutMapping("/api/status/{postid}")
    public StatusResponseDto updateStatus(@PathVariable Long postid, @RequestBody Map<String,String> param){
        System.out.println("/api/status/{postid}");
        if (postid == null) {
            throw new IllegalArgumentException("Null 값인 postid는 찾을 수 없습니다.");
        }
        String status = param.get("status");
        System.out.println("status: "+status);
        return statusService.updateStatus(postid, status);
    }
}
