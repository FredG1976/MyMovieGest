package com.example.mylist.media;

import com.example.mylist.domain.media.Media;
import com.example.mylist.domain.usermylist.UserMedia;
import com.example.mylist.domain.usermylist.UserMl;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;

public interface UserMediaService {

    List<UserMedia> findByUsermlId(Long id);

    List<UserMedia> findAll();

    List<UserMedia> findByMediaId(Long id);

    Optional<UserMedia> findByUsermlIdAndMediaId(Long id_user, Long id_media);

    UserMedia updateUserMedia(UserMedia userMedia);

    List<UserMedia> findByUsermlId(UserMl userMl);

    List<UserMedia> findAllByUsermlId(Long id);

    void removeUserMedia(UserMedia user);


}
