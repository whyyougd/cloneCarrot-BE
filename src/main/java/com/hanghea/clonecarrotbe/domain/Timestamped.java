package com.hanghea.clonecarrotbe.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@MappedSuperclass //Entity가 자동으로 컬럼으로 인식
@EntityListeners(AuditingEntityListener.class) //생성, 변경시간을 자동으로 업데이트
public abstract class Timestamped {

        @CreatedDate // 생성일자임을 나타냄
        private String createdAt;

//        @LastModifiedDate // 마지막 수정일자임을 나타냄
//        private String modifiedAt;

        //insert 되기전에 date 날짜 형식 변환
        @PrePersist
        public void onPrePersist(){
                this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"
                ));
//                this.modifiedAt = this.modifiedAt;
        }

        //update 되기 전에 date 날짜 형식 변환
//        @PreUpdate
//        public void onPreUpdate(){
//                this.modifiedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"
//                ));
//        }
}