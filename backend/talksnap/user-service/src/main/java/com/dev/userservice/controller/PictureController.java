package com.dev.userservice.controller;

import com.dev.response.GeneralResponse;
import com.dev.userservice.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Picture controller helps to update the user profile image storage.
 */
@RestController
@RequestMapping("/user/picture")
public class PictureController {

    private final PictureService pictureService;


    @Autowired
    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @PostMapping("/imgUpdate")
    public GeneralResponse<String> uploadImg(@RequestHeader("authorization") String auth, MultipartFile file) {
        return pictureService.uploadImg(auth, file);
    }


}
