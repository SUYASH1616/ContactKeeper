package com.scm.Services.Impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.Services.ImageService;
import com.scm.helper.AppConstants;

@Service
public class ImageServiceImpl implements ImageService {

    private Cloudinary cloudinary;
    ImageServiceImpl(Cloudinary cloudinary){
        this.cloudinary=cloudinary;
    }
    @Override
    public String uploadImage(MultipartFile proFileImag,String fileName) {

        
        try {
            byte [] data=new byte[proFileImag.getInputStream().available()];
            proFileImag.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                "public_id",fileName
                
            ));
            return this.getYUrlFromPublicId(fileName);

        } catch (IOException e) {
           
            e.printStackTrace();
            return null;
        }
        
    }
    @Override
    public String getYUrlFromPublicId(String publicId) {
        return cloudinary.url()
                .transformation(new Transformation<>().width(AppConstants.CONTACT_IMAGE_WIDTH).height(AppConstants.CONTACT_IMAGE_LENGTH).crop(AppConstants.CONTACT_IMAGE_CROP))
                .generate(publicId);
    }
    

}
