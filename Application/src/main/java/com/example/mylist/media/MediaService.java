package com.example.mylist.media;

import com.example.mylist.domain.media.Media;
import com.example.mylist.domain.usermylist.UserMl;

import java.util.List;
import java.util.Optional;

public interface MediaService {

    Optional<Media> findById(Long id);

    List<Media> findAll();

    List<Media> findByTitleContaining(String title);

    Optional<Media> findByImdbID(String imdbid);

    Media addMedia(Media media);

//    void addMediaGenre(Media media);
//
//    void addMediaActor(Media media);

    List<Media> findBySerieIDOrderBySeasonOrderByEpisode(String imdbID);

//    //Add fonction with userml filtering
//    List<Media> listAllMediaForUserMl(UserMl userMl);
}