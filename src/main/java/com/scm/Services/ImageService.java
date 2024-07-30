package com.scm.Services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String uploadImage(MultipartFile proFileImag,String filename);
    String getYUrlFromPublicId(String publicId);

}
