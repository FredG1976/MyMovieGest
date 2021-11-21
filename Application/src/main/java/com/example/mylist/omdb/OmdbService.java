package com.example.mylist.omdb;

import com.example.mylist.domain.media.Media;
import com.example.mylist.domain.usermylist.UserMedia;
import com.example.mylist.domain.usermylist.UserMl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.util.List;

public interface OmdbService {
    Object searchMediaByNameFromOmdbAPI(String searchText);

    List<String> importMediaUsingImdbIdFromOmdbAPI(String imdbID) throws UnirestException, IOException;
//    List<UserMedia> importMediaUsingImdbIdFromOmdbAPI(String imdbID) throws UnirestException, IOException;

    List<UserMedia> importMediaUsingImdbIdFromOmdbAPI(String imdbID, Long usermlID) throws UnirestException, IOException;

    List<String> importMediaUsingImdbIdFromOmdbAPI(String imdbID, UserMl userMl) throws UnirestException, IOException;

    Media saveMediaToDatabase(String jsonStringResponse) throws IOException;
}
