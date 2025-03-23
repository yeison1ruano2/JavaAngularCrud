package com.allsoft.javaangularcrud.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {
  String uploadFile(MultipartFile file,String fileName) throws IOException;
  String updateFile(MultipartFile file,String fileName) throws IOException;
}
