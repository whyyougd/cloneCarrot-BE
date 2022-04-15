package com.hanghea.clonecarrotbe.controller;

import com.hanghea.clonecarrotbe.dto.StatusRequestDto;
import com.hanghea.clonecarrotbe.dto.StatusResponseDto;
import com.hanghea.clonecarrotbe.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StatusController {
    private final StatusService statusService;

    @PutMapping("/api/status/{postid}")
    public StatusResponseDto updateStatus(@PathVariable Long postid, @RequestBody StatusRequestDto statusRequestDto){
        System.out.println("/api/status/{postid}");
        if (postid == null) {
            throw new IllegalArgumentException("Null 값인 postid는 찾을 수 없습니다.");
        }
        System.out.println("statusRequestDto : "+statusRequestDto.getStatus());
        return statusService.updateStatus(postid, statusRequestDto);
    }
}
