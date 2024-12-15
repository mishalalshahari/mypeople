package com.mypeople.Forms;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactForm {

    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String linkedInLink;
    private String githubLink;
    private String description;
    private MultipartFile profilePic;
    private boolean favorite;
}
