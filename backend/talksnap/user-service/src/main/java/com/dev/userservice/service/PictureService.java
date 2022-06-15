package com.dev.userservice.service;

import com.dev.auth.Auth;
import com.dev.response.GeneralResponse;
import com.dev.response.HTTPResult;
import com.dev.user.User;
import com.dev.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class PictureService {

    private final UserRepository userRepository;

    @Autowired
    public PictureService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public GeneralResponse<String> uploadImg(String auth, MultipartFile file) {
        // verify auth
        Map<String, Object> payload = Auth.verify(auth);
        if (payload != null) {
            // get user id
            Long userId = (Long) payload.get("userId");
            // query the user
            User user = userRepository.findById(userId).get();
            // extract the img file
            // save the img
            try {
                user.setProfileImg(file.getBytes());
                return HTTPResult.ok(user.toString());
            } catch (IOException e) {
                return HTTPResult.fail("Cannot upload the image, please try again.");
            }
        }
        return HTTPResult.fail("Please login first.");
    }

}
