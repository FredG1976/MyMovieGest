package com.example.mylist;

import com.example.mylist.domain.media.Media;
import com.example.mylist.domain.media.Status;
import com.example.mylist.domain.usermylist.UserMedia;
import com.example.mylist.domain.usermylist.UserMl;
import com.example.mylist.media.MediaService;
import com.example.mylist.omdb.OmdbService;
import com.example.mylist.omdb.OmdbServiceImpl;
import com.example.mylist.media.UserMediaService;
import com.example.mylist.media.UserMediaServiceImpl;
import com.example.mylist.user.UserMlService;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(MockitoJUnitRunner.class)
@TestPropertySource(properties = {"spring.config.location=classpath:application.yml" })
public class OmdbServiceImplTest {
    @Value("${omdb.url}")
    String omdbUrl = "https://www.omdbapi.com/";
    @Value("${omdb.apikey}")
    String omdbKey = "c8a9f5ba";

    @Mock
    MediaService mediaServiceMock;

    @Mock
    UserMlService userMlServiceMock;

    @Mock
    UserMediaService userMediaServiceMock;

    @InjectMocks
    OmdbService omdbServiceInject = new OmdbServiceImpl(omdbUrl,omdbKey);

    @InjectMocks
    OmdbService omdbServiceInject2 = new OmdbServiceImpl();

    @InjectMocks
    UserMediaService userMediaServiceInject = new UserMediaServiceImpl();

    private List<Media> mediaListMock;

    private UserMl userMlMock;

    @Value("${spring.application.name}")
    private String appName;

    private Media media1;
    private Media media2;
    private Media media3;

    @Before
    public void setUp() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        mediaListMock=new ArrayList<Media>();

        // Movie Titanic
        media1 = new Media();
        media1.setId(111L);
        media1.setImdbID("tt0120338");
        media1.setTitle("Titanic");

        //Serie - ROME
        media2 =new Media();
        media2.setId(222L);
        media2.setImdbID("tt0384766");
        media2.setTitle("Game of Thrones");
        mediaListMock.add(media2);

        media3 =new Media();
        media3.setId(333L);
        media3.setImdbID("tt0000333");
        media3.setTitle("Title media 3");
        mediaListMock.add(media3);

        userMlMock = new UserMl();
        userMlMock.setId(1L);
        userMlMock.setLogin("UserMock");
        userMlMock.setFullName("UserMockFullName");
        userMlMock.setAdmin(false);
        userMlMock.setPassword(passwordEncoder.encode("password"));
    }

    @Test
    public void import_Movie_From_External_API_Success_Return_Media() throws UnirestException, IOException {
        //GIVEN - MEDIA 1

        //WHEN
        List<String> savedMediaList;
        Mockito.doReturn(media1).when(mediaServiceMock).addMedia(Matchers.any(Media.class));
        savedMediaList = omdbServiceInject.importMediaUsingImdbIdFromOmdbAPI(media1.getImdbID());

        //THEN
        assertThat(savedMediaList).isNotNull();
        assertThat(savedMediaList).size().isEqualTo(1);
        assertThat(savedMediaList.get(0)).isEqualTo(media1.getImdbID());
    }

    @Test
    public void import_Serie_From_External_API_Success_Return_Media() throws UnirestException, IOException {
        //GIVEN - MEDIA 2

        //WHEN
        List<String> savedMediaList;
        Mockito.doReturn(media2).when(mediaServiceMock).addMedia(Matchers.any(Media.class));
        savedMediaList = omdbServiceInject.importMediaUsingImdbIdFromOmdbAPI(media2.getImdbID());

        //THEN
        assertThat(savedMediaList).isNotNull();
        // It is a serie, so two episodes minimum
        assertThat(savedMediaList).size().isGreaterThanOrEqualTo(2);
//        assertThat(savedMediaList.get(0)).isEqualTo(media2.getImdbID());

    }

    @Test
    public void import_media_for_a_specific_user() throws UnirestException, IOException {
        //GIVEN - Media1 +
        UserMl userMl = new UserMl();
        userMl.setId(999L);
        userMl.setFullName("Jean Naimarre");
        userMl.setLogin("UserLogin");
        userMl.setPassword("UserPassword");

        UserMedia userMedia = new UserMedia();
        userMedia.setId(222L);
        userMedia.setMedia(media1);
        userMedia.setUserml(userMl);
        userMedia.setStatus(Status.INPROGRESS);
        userMedia.setOwnRating(0F);
        userMedia.setOwnComment("No comment...");

        //WHEN
        List<String> savedMediaList;
        Mockito.when(mediaServiceMock.findByImdbID(media1.getImdbID())).thenReturn(java.util.Optional.ofNullable(media1));
        Mockito.doReturn(media1).when(mediaServiceMock).addMedia(Matchers.any(Media.class));
        savedMediaList = omdbServiceInject.importMediaUsingImdbIdFromOmdbAPI(media1.getImdbID(),userMl);

        //THEN
        assertThat(savedMediaList).isNotNull();
        assertThat(savedMediaList).size().isEqualTo(1);
        assertThat(savedMediaList.get(0)).isEqualTo(media1.getImdbID());
    }

    @Test
    public void import_media_for_a_specific_user_by_userid_return_userMedia() throws UnirestException, IOException {
        //GIVEN - Media1 +
        UserMl userMl = new UserMl();
        userMl.setId(999L);

        UserMedia userMedia = new UserMedia();
        userMedia.setId(222L);
        userMedia.setMedia(media1);
        userMedia.setUserml(userMl);
        userMedia.setStatus(Status.INPROGRESS);
        userMedia.setOwnRating(0F);
        userMedia.setOwnComment("No comment...");

        //WHEN
        List<UserMedia> savedMediaList;
        Mockito.when(userMlServiceMock.findById(userMl.getId())).thenReturn(java.util.Optional.of(userMl));
        Mockito.when(mediaServiceMock.findByImdbID(media1.getImdbID())).thenReturn(java.util.Optional.ofNullable(media1));
        Mockito.doReturn(media1).when(mediaServiceMock).addMedia(Matchers.any(Media.class));
        savedMediaList = omdbServiceInject.importMediaUsingImdbIdFromOmdbAPI(media1.getImdbID(),userMl.getId());

        //THEN
        assertThat(savedMediaList).isNotNull();
        assertThat(savedMediaList).size().isEqualTo(1);
        assertThat(savedMediaList.get(0).getMedia().getImdbID()).isEqualTo(media1.getImdbID());
        assertThat(savedMediaList.get(0).getUserml().getId()).isEqualTo(userMl.getId());
    }

    @Test
    public void search_media_on_omdb(){
        //GIVEN
        String searchText = "Star Wars Episode I The Phantom Menace";
        String imdbID = "tt0120915";

        //WHEN
        Object searchResult = omdbServiceInject.searchMediaByNameFromOmdbAPI(searchText);
        List<String> resList = (List<String>) ((LinkedHashMap) searchResult).get("Search");


        //THEN
        assertThat(((LinkedHashMap) searchResult).get("Response")).isEqualTo("True");
        assertThat(resList.size()).isGreaterThanOrEqualTo(1);
    }
}