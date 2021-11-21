package com.example.mylist.media;

import com.example.mylist.domain.media.Status;
import com.example.mylist.domain.usermylist.UserMedia;
import com.example.mylist.domain.usermylist.UserMl;
import com.example.mylist.infrastructure.MediaRepository;
import com.example.mylist.infrastructure.UserMediaRepository;
import com.example.mylist.infrastructure.UserMlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserMediaServiceImpl implements UserMediaService{

    @Autowired
    UserMediaRepository userMediaRepository;

    @Autowired
    UserMlRepository userMlRepository;

    @Autowired
    MediaRepository mediaRepository;

    @Override
    public List<UserMedia> findByUsermlId(Long id) {
        return userMediaRepository.findByUsermlId(id);
    }

    @Override
    public List<UserMedia> findAll() {

        return userMediaRepository.findAll();
    }

    @Override
    public List<UserMedia> findByMediaId(Long id) {
        return userMediaRepository.findByMediaId(id);
    }

    @Override
    public Optional<UserMedia> findByUsermlIdAndMediaId(Long id_user, Long id_media) {
        return userMediaRepository.findByUsermlIdAndMediaId(id_user, id_media);
    }

    @Override
    public UserMedia updateUserMedia(UserMedia userMedia) {

        Status savedStatus = userMedia.getStatus();
        Float savedOwnRating = userMedia.getOwnRating();
        String savedOwnComment = userMedia.getOwnComment();

        Optional<UserMedia> userMediaOptional =
                userMediaRepository.findByUsermlIdAndMediaId(
                        userMedia.getUserml().getId(),userMedia.getMedia().getId());
        if (userMediaOptional.isPresent()) {
            //*** c'est une modification des annotations
            userMedia = userMediaOptional.get();
            userMedia.setStatus(savedStatus);
            userMedia.setOwnRating(savedOwnRating);
            userMedia.setOwnComment(savedOwnComment);

        } else {
            //*** c'est une création de liste (UserMl et Media)
            userMedia.setUserml(userMlRepository.getById(userMedia.getUserml().getId()));
            userMedia.setMedia(mediaRepository.getById(userMedia.getMedia().getId()));
            userMedia.setStatus(Status.TOWATCH);
            userMedia.setOwnComment(null);
            userMedia.setOwnRating(null);

        }
        return userMediaRepository.save(userMedia);
    }

    @Override
    public List<UserMedia> findByUsermlId(UserMl userMl) {
        Long userMlId = userMl.getId();
        return userMediaRepository.findByUsermlId(userMlId);
    }

    @Override
    public List<UserMedia> findAllByUsermlId(Long userMlId) {
        return userMediaRepository.findAllByUsermlId(userMlId);
    }

    @Override
    public void removeUserMedia(UserMedia userMedia) {

    }
}
