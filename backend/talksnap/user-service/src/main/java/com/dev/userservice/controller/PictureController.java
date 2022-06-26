package com.dev.userservice.controller;

import com.dev.response.GeneralResponse;
import com.dev.userservice.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    /**
     * upload img and update the profile image or background img
     *
     * @param auth
     * @param purpose type: "profile" or "background"
     * @param file
     * @return
     */
    @PostMapping("/imgUpdate")
    public GeneralResponse<byte[]> uploadImg(@RequestHeader("Authorization") String auth, @RequestHeader("Type") String purpose, MultipartFile file) {
        return pictureService.uploadImg(auth, purpose, file);
    }
}
