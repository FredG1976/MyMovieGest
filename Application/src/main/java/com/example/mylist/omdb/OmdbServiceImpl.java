package com.example.mylist.omdb;

import com.example.mylist.media.UserMediaService;
import com.example.mylist.user.UserMlService;
import com.example.mylist.domain.media.Media;
import com.example.mylist.domain.media.Status;
import com.example.mylist.domain.media.Type;
import com.example.mylist.domain.usermylist.UserMedia;
import com.example.mylist.domain.usermylist.UserMl;
import com.example.mylist.media.MediaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OmdbServiceImpl implements OmdbService {
    @Value("${omdb.url}")
    String omdbUrl;
    @Value("${omdb.apikey}")
    String omdbKey;

    public OmdbServiceImpl(String omdbUrl, String omdbKey) {
        this.omdbUrl = omdbUrl;
        this.omdbKey = omdbKey;
    }

    public OmdbServiceImpl() {
    }

    @Autowired
    UserMediaService userMediaService;

    @Autowired
    MediaService mediaService;

    @Autowired
    UserMlService userMlService;

    /**
     * This function allow to search a title from OMDB API.
     * The functions return a list of object....
     * @param searchText Specify the title to search on omdb.
     * @return A Json with the list of search result.
     */
    @Override
    public Object searchMediaByNameFromOmdbAPI(String searchText) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(omdbUrl+"?s="+searchText+"&apikey="+omdbKey,Object.class);
    }

    /**
     * This function import the specified media (by imdbid) from OMDB API to database.
     * @param imdbID The imdb id of the media. sample 'tt1820488'
     * @throws UnirestException Throws an exception in case of issue with omdb API connection.
     * @throws IOException Throws an exception of type IOException.
     * @return List of one or more imdbID. The input imdbId for a movie and a list of imdb for a serie.
     */
    @Transactional
    @Override
    public List<String> importMediaUsingImdbIdFromOmdbAPI(String imdbID) throws UnirestException, IOException {
        List<String> imdbList = new ArrayList<String>();
        String baseUrl = omdbUrl + "?apikey=" +omdbKey;
        // Recupération des keys de notre application
        List<String> mediaKeyList = getMediaKey();
        String jsonMediaData;

        //Recupération des données de l'API externe
        String searchUrl = baseUrl +"&i="+imdbID;
        JSONObject jsonMediaSearchResultFromAPI;
        jsonMediaSearchResultFromAPI = getJsonDataFromOmdbAPI(searchUrl);

//        String mediaResearchType = getMediaResearchType(jsonMediaSearchResultFromAPI);

        switch (jsonMediaSearchResultFromAPI.get("Type").toString().toLowerCase()) {
            case "series":
                imdbList = getAllSeasonAndEpisodeFromSerieImdbID(jsonMediaSearchResultFromAPI);
                imdbList.add(imdbID);
                for (String id : imdbList) {
                    searchUrl = baseUrl +"&i="+id;
                    jsonMediaSearchResultFromAPI = getJsonDataFromOmdbAPI(searchUrl);
                    Map<String,Object> mapMediaData = new HashMap<String,Object>();
                    for (String mediaKey : mediaKeyList ) {
                        mapMediaData.put(mediaKey,getIgnoreCase(jsonMediaSearchResultFromAPI,mediaKey));
                    }
                    jsonMediaData = convertDataFromApiToMediaData(jsonMediaSearchResultFromAPI,mapMediaData);
//                    sendToAddMdeiaEndPoint(jsonMediaData.toString());
                    saveMediaToDatabase(jsonMediaData);
                }
                break;
            default:
                imdbList.add(imdbID);
                Map<String,Object> mapMediaData = new HashMap<String,Object>();
                for (String mediaKey : mediaKeyList ) {
                    mapMediaData.put(mediaKey,getIgnoreCase(jsonMediaSearchResultFromAPI,mediaKey));
                }
                jsonMediaData = convertDataFromApiToMediaData(jsonMediaSearchResultFromAPI,mapMediaData);
                saveMediaToDatabase(jsonMediaData);
                break;
        }
        return imdbList;
    }

    /**
     * This function import the specified media (by imdbid) from OMDB API to database and associe the media to the specified user.
     * @param imdbID The imdb id of the media. sample 'tt1820488'
     * @param usermlID The usermlId to which the media must be associated (Methode GET).
     * @throws UnirestException Throws an exception in case of issue with omdb API connection.
     * @throws IOException Throws an exception of type IOException.
     */
    @Transactional
    @Override
//    public List<String> importMediaUsingImdbIdFromOmdbAPI(String imdbID, Long usermlID) throws UnirestException, IOException {
//        List<String> imdbIdList = importMediaUsingImdbIdFromOmdbAPI(imdbID);
//
//        for (String imdbId: imdbIdList) {
//            UserMedia userMedia = new UserMedia();
//            UserMl userMl = userMlService.findById(usermlID).get();
//            Media media = mediaService.findByImdbID(imdbId).get();
//            userMedia.setMedia(media);
//            userMedia.setUserml(userMl);
//            userMedia.setStatus(Status.TOWATCH);
//            userMedia.setOwnRating(0F);
//            userMedia.setOwnComment("");
//            userMediaService.updateUserMedia(userMedia);
//        }
//        return imdbIdList;
//    }
    public List<UserMedia> importMediaUsingImdbIdFromOmdbAPI(String imdbID, Long usermlID) throws UnirestException, IOException {
        List<String> imdbIdList = importMediaUsingImdbIdFromOmdbAPI(imdbID);
        List<UserMedia> userMediaList = new ArrayList<>();
        UserMedia userMedia;

        for (String imdbId: imdbIdList) {
            UserMl userMl = userMlService.findById(usermlID).get();
            Media media = mediaService.findByImdbID(imdbId).get();
            if(userMediaService.findByUsermlIdAndMediaId(userMl.getId(),media.getId()).isPresent()) {
                userMedia = userMediaService.findByUsermlIdAndMediaId(userMl.getId(),media.getId()).get();
            } else  {
                userMedia = new UserMedia();
                userMedia.setMedia(media);
                userMedia.setUserml(userMl);
                userMedia.setStatus(Status.TOWATCH);
                userMedia.setOwnRating(0F);
                userMedia.setOwnComment("");
                userMediaService.updateUserMedia(userMedia);
                userMediaList.add(userMedia);
            }

        }
        return userMediaList;
    }

    /**
     * This function import the specified media (by imdbid) from OMDB API to database and associe the media to the specified user.
     * @param imdbID The imdb id of the media. sample 'tt1820488'
     * @param userMl The userml to which the media must be associated (Methode POST).
     * @throws UnirestException Throws an exception in case of issue with omdb API connection.
     * @throws IOException Throws an exception of type IOException.
     */
    @Transactional
    @Override
    public List<String> importMediaUsingImdbIdFromOmdbAPI(String imdbID, UserMl userMl) throws UnirestException, IOException {
        List<String> imdbIdList = importMediaUsingImdbIdFromOmdbAPI(imdbID);

        for (String imdbId: imdbIdList) {
            UserMedia userMedia = new UserMedia();
            Media media = mediaService.findByImdbID(imdbId).get();
            userMedia.setMedia(media);
            userMedia.setUserml(userMl);
            userMedia.setStatus(Status.TOWATCH);
            userMedia.setOwnRating(0F);
            userMedia.setOwnComment("");
            userMediaService.updateUserMedia(userMedia);
        }
        return imdbIdList;
    }

    private List<String> getAllSeasonAndEpisodeFromSerieImdbID(JSONObject jsonMediaSearchResultFromAPI) throws UnirestException {
        final String regex = "\"imdbID\\\":\\\"\\w*\"";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        List<String> urlList = new ArrayList<String>();
        int numberTotalOfSeason = Integer.parseInt(jsonMediaSearchResultFromAPI.get("totalSeasons").toString());
        String imdbID = jsonMediaSearchResultFromAPI.get("imdbID").toString();
        String baseUrlSeason = omdbUrl + "?apikey=" +omdbKey;
        String urlSeason;
//        String urlEpisode;
        JSONObject jsonSeasonDetails;

        for (int i = 1; i <= numberTotalOfSeason; i++) {
            urlSeason = baseUrlSeason + "&i=" + imdbID + "&season=" +i;
            jsonSeasonDetails = getJsonDataFromOmdbAPI(urlSeason);
            Matcher matcher = pattern.matcher(jsonSeasonDetails.toString());
            while (matcher.find()) {
                urlList.add(matcher.group(0).replace("\"","").replace("imdbID:",""));
            }
        }
        return urlList;
    }

    private  String convertDataFromApiToMediaData(JSONObject jsonObjectFromApi, Map<String,Object> mediaData){
//        JSONObject jsonObjectMediaData = new JSONObject();
        StringBuilder stringJsonActor;
        StringBuilder stringJsonGenre;
        StringBuilder stringJsonMedia = new StringBuilder("{");
        for (String key : mediaData.keySet())
        {
            Object value = mediaData.get(key);
            switch (key){
                case "genreList":
                    String Genres = jsonObjectFromApi.get("Genre").toString();
                    String[] listOfGenres = Genres.split(",");
                    stringJsonGenre = new StringBuilder("[");
                    for (String genre : listOfGenres){
                        stringJsonGenre.append("{\"name\":\"").append(genre.trim()).append("\"},");
                    }
                    stringJsonGenre = new StringBuilder((stringJsonGenre + "]").replace(",]", "]"));
                    stringJsonMedia.append("\"").append(key).append("\":").append(stringJsonGenre).append(",");
                    break;
                case "actorList":
                    String Actors = jsonObjectFromApi.get("Actors").toString();
                    String[] listOfActors = Actors.split(",");
                    stringJsonActor = new StringBuilder("[");
                    for (String actor : listOfActors) {
                        stringJsonActor.append("{\"name\":\"").append(actor.trim()).append("\"},");
                    }
                    stringJsonActor = new StringBuilder((stringJsonActor + "]").replace(",]", "]"));
                    stringJsonMedia.append("\"").append(key).append("\":").append(stringJsonActor).append(",");
                    break;
                case "type":
                    switch (value.toString().toLowerCase()){
                        case "movie":
                            stringJsonMedia.append("\"").append(key).append("\":\"").append(Type.MOVIE.ordinal()).append("\",");
                            break;
                        case "series":
                            stringJsonMedia.append("\"").append(key).append("\":\"").append(Type.SERIE.ordinal()).append("\",");
                            break;
                        case "episode":
                            stringJsonMedia.append("\"").append(key).append("\":\"").append(Type.EPISODE.ordinal()).append("\",");
                            break;
                    }
                    break;
                case "plot":
                    value = value.toString().replace("\"","'");
                    stringJsonMedia.append("\"").append(key).append("\":\"").append(value).append("\",");
                    break;
                default:
                    stringJsonMedia.append("\"").append(key).append("\":\"").append(value).append("\",");
                    break;
            }
        }
        stringJsonMedia = new StringBuilder((stringJsonMedia + "}").replace(",}", "}").replace("N/A", ""));
        return stringJsonMedia.toString();
    }

    private  JSONObject getJsonDataFromOmdbAPI(String fullUrl) throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> jsonDataFromAPI = Unirest.post(fullUrl).body("").asString();
        return new JSONObject(jsonDataFromAPI.getBody());
    }

    private  Object getIgnoreCase(JSONObject jsonObject, String key) {
        for (String currentKey : jsonObject.keySet()) {
            if (currentKey.equalsIgnoreCase(key)) {
                return jsonObject.get(currentKey);
            }
        }
        return null;
    }

    private  List<String> getMediaKey(){
        String regex = "[a-zA-Z]*=";
        String jsonKey;
        List<String> mediaKeyList = new ArrayList<String>();

        Media media = new Media();
        // Recupération des champs de la classe média à recupérér dans les données fournies par l'API OMDB
        String omdbListItemString = media.toString();
        String className = omdbListItemString.substring(0,omdbListItemString.indexOf("{"));
        omdbListItemString = omdbListItemString.replace(className,"").replace("{","").replace("}","");
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcherMyApp = pattern.matcher(omdbListItemString);
        while (matcherMyApp.find()) {
            jsonKey = matcherMyApp.group(0).replace("=","");
            mediaKeyList.add(jsonKey);
        }
        return mediaKeyList;
    }

    public Media saveMediaToDatabase(String jsonStringResponse) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Media media = objectMapper.readValue(jsonStringResponse,Media.class);
        return mediaService.addMedia(media);
    }

}
