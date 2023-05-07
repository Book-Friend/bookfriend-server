package com.book.service;

import com.book.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {


    @Value("${upload.path}")
    private String imgPath;

    @Transactional
    public void updateImage(User user, MultipartFile file) {
        System.out.println("user.getEmail() = " + user.getEmail());
        UUID uuid = UUID.randomUUID();

        String imgName = uuid + "_" + UriUtils.encode(file.getOriginalFilename(), StandardCharsets.UTF_8);

        File saveFile = new File(imgPath, imgName);

        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        user.updateImage(imgPath + imgName);
    }

    public String getImagePath(String filename) {
        return imgPath + filename;
    }
}
