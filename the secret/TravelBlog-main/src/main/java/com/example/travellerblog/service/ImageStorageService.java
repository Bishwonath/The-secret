/**
 * @author Ujjwal Pandey
 * @version 1.1
 * @since 2022-02-19
 */

package com.example.travellerblog.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface ImageStorageService {



    void init() throws IOException;



    String saveImage(MultipartFile file) throws IOException;



    MultipartFile loadImage(String imgPath, boolean fullPath) throws IOException;



    void delete(String imgPath, boolean fullPath) throws IOException;
}
