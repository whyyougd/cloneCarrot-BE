package com.hanghea.clonecarrotbe.service;

import com.hanghea.clonecarrotbe.domain.Main;
import com.hanghea.clonecarrotbe.domain.Status;
import com.hanghea.clonecarrotbe.dto.StatusResponseDto;
import com.hanghea.clonecarrotbe.repository.MainRepository;
import com.hanghea.clonecarrotbe.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class StatusService {
    private final StatusRepository statusRepository;
    private final MainRepository mainRepository;
    private final MainService mainService;

    @Transactional
    public StatusResponseDto updateStatus(Long postid, String status) {
        Main main = mainRepository.findById(postid).orElseThrow(
                () -> new NullPointerException("해당 게시글은 존재하지 않습니다.")
        );

        // 바꾸고자 하는 상태명을 찾는 중
        Status newStatus = statusRepository.findByStatus(status);

        System.out.println("newStatus: "+newStatus.getStatus());

        main.setStatus(newStatus);

        return new StatusResponseDto(postid,newStatus);
    }
}
