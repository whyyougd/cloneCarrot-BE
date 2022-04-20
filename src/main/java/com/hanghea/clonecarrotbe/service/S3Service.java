package com.hanghea.clonecarrotbe.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hanghea.clonecarrotbe.domain.Image;
import com.hanghea.clonecarrotbe.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private AmazonS3 s3Client;

    @Value("AKIAYSXIHCEE6P6YNEOW")
    private String accessKey;

    @Value("vetS3pRGHazUFRcQHWjCxn0iBhnnZ7hSD6efiRXs")
    private String secretKey;

    @Value("ap-northeast-2")
    private String region;

    @Value("mycarrot-s3-bucket")
    private String bucket;

    private final ImageRepository imageRepository;

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    // 최초 게시글 작성
    public List<String> upload(List<MultipartFile> files){
        List<String> imageList = new ArrayList<>();

        for(MultipartFile file: files){
            String fileName = createFileName(file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try(InputStream inputStream = file.getInputStream()) {
                s3Client.putObject(new PutObjectRequest(bucket,fileName,inputStream,objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
//                return s3Client.getUrl(bucket, fileName).toString();
                imageList.add(s3Client.getUrl(bucket,fileName).toString());
            }catch (IOException e){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"파일 업로드에 실패하셨습니다");
            }
        }
        return imageList;


    }

    // 글 수정 시 기존 s3에 있는 이미지 정보 삭제
    public String upload(MultipartFile file, String newFilePath){
        String fileName = createFileName(file.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        if(!"".equals(newFilePath) && newFilePath != null){
            boolean isExistObject = s3Client.doesObjectExist(bucket,newFilePath);
            if(isExistObject){
                s3Client.deleteObject(bucket,newFilePath);
            }
        }

        try(InputStream inputStream = file.getInputStream()) {
            s3Client.putObject(new PutObjectRequest(bucket,fileName,inputStream,objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            return s3Client.getUrl(bucket, fileName).toString();
        }catch (IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"파일 업로드에 실패하셨습니다");
        }

    }


    // 글 수정(+ 기존 s3에 있는 이미지 정보 삭제)
    public List<String> update(Long itemId, List<MultipartFile> files) {
        //이미지 삭제 후 재업로드
        delete(itemId);
        return upload(files);
    }
    //기존 s3에 있는 기존 이미지 정보 삭제
    public void delete(Long itemId){
        List<Image> lastImages = imageRepository.findByPost_PostId(itemId);
        System.out.println("삭제할 이전 경로들 : " +lastImages);

        for(Image lastImage : lastImages){
            if (!"".equals(lastImage.getImageurl()) && lastImage.getImageurl() != null) {
                String lastImageUrl = lastImage.getImageurl();
                lastImageUrl = lastImageUrl.replace("https://mycarrot-s3-bucket.s3.ap-northeast-2.amazonaws.com/", "");
                boolean isExistObject = s3Client.doesObjectExist(bucket, lastImageUrl);
                System.out.println("지워야할 url 주소 : " +lastImage.getImageurl());
                System.out.println("앞에 지운 url 주소 : " + lastImageUrl);
                System.out.println("isExistObject : " +isExistObject);
                if (isExistObject) {
                    s3Client.deleteObject(bucket, lastImageUrl);
                }
            }
            imageRepository.deleteById(lastImage.getImageid());
        }
    }


    private String createFileName(String fileName) {
        // 먼저 파일 업로드 시, 파일명을 난수화하기 위해 random으로 돌립니다.
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        // file 형식이 잘못된 경우를 확인하기 위해 만들어진 로직이며,
        // 파일 타입과 상관없이 업로드할 수 있게 하기 위해 .의 존재 유무만 판단
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }

    }
}
