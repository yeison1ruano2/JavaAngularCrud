package com.allsoft.javaangularcrud.services;
import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {
  private final Cloudinary cloudinary;
  public CloudinaryService(
          Dotenv dotenv
  ){
    String cloudName = dotenv.get("CLOUDINARY_CLOUD_NAME");
    String apiKey = dotenv.get("CLOUDINARY_API_KEY");
    String apiSecret = dotenv.get("CLOUDINARY_API_SECRET");
    this.cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", cloudName,
            "api_key", apiKey,
            "api_secret", apiSecret
    ));
  }

  @SuppressWarnings("unchecked")
  public String uploadFile(MultipartFile file,String fileName) throws IOException {
    Map<String,Object> params = ObjectUtils.asMap(
            "public_id", fileName,
            "use_filename",false,
                    "unique_filename",false,
                    "overwrite",false
    );
    Map<String,Object> uploadResult =this.cloudinary.uploader().upload(file.getBytes(),params);
    return (String) uploadResult.get("secure_url");
  }

  @SuppressWarnings("unchecked")
  public String updateFile(MultipartFile file, String fileName) throws IOException{
    Map<String,Object> params = ObjectUtils.asMap(
            "public_id", fileName,
            "use_filename",false,
            "unique_filename",false,
            "overwrite",true
    );
    Map<String,Object> uploadResult =this.cloudinary.uploader().upload(file.getBytes(),params);
    return (String) uploadResult.get("secure_url");
  }
}
