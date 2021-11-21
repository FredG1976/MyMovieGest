package com.example.mylist;

import com.example.mylist.domain.media.Media;
import com.example.mylist.domain.media.Status;
import com.example.mylist.domain.media.Type;
import com.example.mylist.domain.usermylist.UserMedia;
import com.example.mylist.domain.usermylist.UserMl;
import com.example.mylist.infrastructure.MediaRepository;
import com.example.mylist.infrastructure.UserMediaRepository;
import com.example.mylist.infrastructure.UserMlRepository;
import com.example.mylist.media.UserMediaService;
import com.example.mylist.media.UserMediaServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UserMediaServiceImplTest {

    @Mock
    UserMediaRepository userMediaRepositoryMock;

    @Mock
    UserMlRepository userMlRepositoryMock;

    @Mock
    MediaRepository mediaRepositoryMock;

    @InjectMocks
    UserMediaService userMediaServiceUnderTest = new UserMediaServiceImpl();

    UserMedia userMedia10 = new UserMedia();
    UserMedia userMedia11 = new UserMedia();
    UserMedia userMedia22 = new UserMedia();
    UserMedia userMedia33 = new UserMedia();
    UserMedia userMedia34 = new UserMedia();
    List<UserMedia> userMediaList = new ArrayList<>();
    List<UserMedia> userMediaList11 = new ArrayList<>();
    List<UserMedia> userMediaListMediaA = new ArrayList<>();
    List<UserMedia> userMediaList33 = new ArrayList<>();

    UserMl userMl11 = new UserMl();
    UserMl userMl22 = new UserMl();
    UserMl userMl33 = new UserMl();

    Media mediaA = new Media();
    Media mediaB = new Media();
    Media mediaC = new Media();
    Media mediaD = new Media();

    @Before
    public void Setup() {
        userMl11.setId(11L);
        userMl11.setLogin("LoginUser11");
        userMl22.setId(22L);
        userMl22.setLogin("LoginUser22");

        userMl33.setId(33L);
        userMl33.setLogin("LoginUser33");
        userMl33.setPassword("PasswordLoginUser33");
        userMl33.setFullName("User33-User33");

        mediaA.setId(55555L);
        mediaA.setTitle("Film Film 55555");
        mediaA.setType(Type.MOVIE);
        mediaB.setId(66666L);
        mediaB.setTitle("Film Film 66666");
        mediaB.setType(Type.MOVIE);
        mediaC.setId(77777L);
        mediaC.setTitle("Film Film 77777");
        mediaC.setType(Type.MOVIE);

        mediaD.setId(88888L);
        mediaD.setTitle("Film Film 88888");
        mediaD.setType(Type.MOVIE);
        mediaD.setImdbID("88888IMDBID");
        mediaD.setRuntime("888 min");
        mediaD.setSeriesID(null);
        mediaD.setYear("2008");
        mediaD.setDirector("88888Directeur");
        mediaD.setPlot("88888Description");
        mediaD.setPoster(null);
        mediaD.setImdbRating(null);
        mediaD.setTotalSeasons(0);
        mediaD.setSeason(0);
        mediaD.setEpisode(0);


        userMedia10.setId(1111111100L);
        userMedia10.setMedia(mediaA);
        userMedia10.setUserml(userMl11);
        userMedia10.setStatus(Status.TOWATCH);

        userMedia11.setId(111111111L);
        userMedia11.setMedia(mediaB);
        userMedia11.setUserml(userMl11);
        userMedia11.setStatus(Status.TOWATCH);

        userMedia22.setId(2222222222L);
        userMedia22.setMedia(mediaA);
        userMedia22.setUserml(userMl22);
        userMedia22.setStatus(Status.INPROGRESS);

        userMedia33.setId(3333333333L);
        userMedia33.setMedia(mediaC);
        userMedia33.setUserml(userMl33);
        userMedia33.setStatus(Status.WATCHED);

        userMedia34.setId(33333331434L);
        userMedia34.setMedia(mediaD);
        userMedia34.setUserml(userMl33);
        userMedia34.setStatus(Status.TOWATCH);
        userMedia34.setOwnRating(0F);
        userMedia34.setOwnComment("Comment");

        userMediaList.add(userMedia33);
        userMediaList.add(userMedia11);
        userMediaList.add(userMedia22);
        userMediaList.add(userMedia10);

        userMediaList11.add(userMedia11);
        userMediaList11.add(userMedia10);

        userMediaListMediaA.add(userMedia22);
        userMediaListMediaA.add(userMedia10);

        userMediaList33.add(userMedia33);

//    List<UserMedia> findByUsermlId(Long id); OK
//    List<UserMedia> findAll(); OK
//    List<UserMedia> findByMediaId(Long id); OK
//    Optional<UserMedia> findByUsermlIdAndMediaId(Long id_user, Long id_media); OK
//    UserMedia updateUserMedia(UserMedia userMedia);
    }

    @Test
    public void test_findAll_UserMedia() {
        //When
        Mockito.when(userMediaRepositoryMock.findAll()).thenReturn(userMediaList);
        List<UserMedia> retrievedUserMediaList = userMediaServiceUnderTest.findAll();

        //Then
        assertThat(retrievedUserMediaList.size()).isEqualTo(4);
        assertThat(retrievedUserMediaList.get(0).getMedia().getTitle()).isEqualTo(userMedia33.getMedia().getTitle());
        assertThat(retrievedUserMediaList.get(1).getUserml().getLogin()).isEqualTo(userMl11.getLogin());
        assertThat(retrievedUserMediaList.get(2).getStatus()).isEqualTo(userMedia22.getStatus());
        assertThat(retrievedUserMediaList.get(3).getId()).isEqualTo(userMedia10.getId());

        ////      //should_be_unsuccessful
//        assertThat(retrievedUserMediaList.size()).isEqualTo(5);
//        assertThat(retrievedUserMediaList.get(0).getMedia().getTitle()).isEqualTo(userMedia11.getMedia().getTitle());
//        assertThat(retrievedUserMediaList.get(1).getUserml().getLogin()).isEqualTo(userMl22.getLogin());
//        assertThat(retrievedUserMediaList.get(2).getStatus()).isEqualTo(userMedia33.getStatus());
//        assertThat(retrievedUserMediaList.get(3).getId()).isEqualTo(userMedia11.getId());
    }

    @Test
    public void test_findbyUserMlId_UserMedia() {
        //When
        Mockito.when(userMediaRepositoryMock.findByUsermlId(userMl11.getId())).thenReturn(userMediaList11);
        List<UserMedia> retrievedUserMediaList = userMediaServiceUnderTest.findByUsermlId(userMl11.getId());

        //Then
        assertThat(retrievedUserMediaList.size()).isEqualTo(2);
        assertThat(retrievedUserMediaList.get(0).getStatus()).isEqualTo(userMedia11.getStatus());
        assertThat(retrievedUserMediaList.get(1).getStatus()).isEqualTo(userMedia10.getStatus());

        ////      //should_be_unsuccessful
//        assertThat(retrievedUserMediaList.size()).isEqualTo(2);
//        assertThat(retrievedUserMediaList.get(0).getStatus()).isEqualTo(userMedia11.getStatus());
//        assertThat(retrievedUserMediaList.get(1).getStatus()).isEqualTo(userMedia10.getStatus());
    }

    @Test
    public void test_findbyMediaId_UserMedia() {
        //When
        Mockito.when(userMediaRepositoryMock.findByMediaId(mediaA.getId())).thenReturn(userMediaListMediaA);
        List<UserMedia> retrievedUserMediaList = userMediaServiceUnderTest.findByMediaId(mediaA.getId());

        //Then
        assertThat(retrievedUserMediaList.size()).isEqualTo(2);
        assertThat(retrievedUserMediaList.get(0).getUserml().getId()).isEqualTo(userMl22.getId());
        assertThat(retrievedUserMediaList.get(1).getUserml().getId()).isEqualTo(userMl11.getId());

        ////      //should_be_unsuccessful
//        assertThat(retrievedUserMediaList.get(0).getUserml().getId()).isEqualTo(userMl33.getId());
//        assertThat(retrievedUserMediaList.get(1).getUserml().getId()).isEqualTo(userMl22.getId());
    }

    @Test
    public void test_findByUserMlIdAndMediaId() {
        //When
        Mockito.when(userMediaRepositoryMock.findByUsermlIdAndMediaId(userMl33.getId(),mediaC.getId())).thenReturn( Optional.of(userMedia33));
        Optional<UserMedia> userMediaOptional = userMediaServiceUnderTest.findByUsermlIdAndMediaId(userMl33.getId(),mediaC.getId());

        //Then
        assertThat(userMediaOptional.isPresent()).isTrue();
        assertThat(userMediaOptional.get().getId()).isEqualTo(userMedia33.getId());
        assertThat(userMediaOptional.get().getStatus()).isEqualTo(userMedia33.getStatus());
        assertThat(userMediaOptional.get().getMedia().getTitle()).isEqualTo(mediaC.getTitle());

        ////      //should_be_unsuccessful
//        assertThat(userMediaOptional.get().getId()).isEqualTo(userMedia22.getId());
//        assertThat(userMediaOptional.get().getStatus()).isEqualTo(userMedia33.getStatus());
//        assertThat(userMediaOptional.get().getMedia().getTitle()).isEqualTo(mediaA.getTitle());
    }

//    @Test
//    public void test_createUserMedia_inexistingUserMedia_should_succeed() {
//        //When
//        Mockito.when(userMediaRepositoryMock.findByUsermlIdAndMediaId(userMl33.getId(),mediaD.getId())).thenReturn(Optional.empty());
//        Mockito.when(userMlRepositoryMock.getById(userMl33.getId())).thenReturn(userMl33);
//        Mockito.when(mediaRepositoryMock.getById(mediaD.getId())).thenReturn(mediaD);
//        Mockito.when(userMediaRepositoryMock.save(userMedia34)).thenReturn(userMedia34);
//        UserMedia savedUserMedia = userMediaServiceMock.updateUserMedia(userMedia34);
//
//        //Then
//        assertThat(savedUserMedia).isNotNull();
//        assertThat(savedUserMedia.getStatus()).isEqualTo(userMedia34.getStatus());
//        assertThat(savedUserMedia.getUserml().getId()).isEqualTo(userMl33);
//        assertThat(savedUserMedia.getMedia().getId()).isEqualTo(mediaD.getId());
//
//    }

    @Test
    public void test_updateUserMedia_existingUserMedia_should_succeed() {
        //Complement

        //When
        Mockito.when(userMediaRepositoryMock.findByUsermlIdAndMediaId(userMl33.getId(),mediaD.getId())).thenReturn(Optional.of(userMedia34));
//        Mockito.when(userMlRepositoryMock.getById(userMl33.getId())).thenReturn(userMl33);
//        Mockito.when(mediaRepositoryMock.getById(mediaD.getId())).thenReturn(mediaD);
        Mockito.when(userMediaRepositoryMock.save(userMedia34)).thenReturn(userMedia34);
        userMedia34.setStatus(Status.INPROGRESS);

        UserMedia savedUserMedia = userMediaServiceUnderTest.updateUserMedia(userMedia34);

        //Then
        assertThat(savedUserMedia).isNotNull();
        assertThat(savedUserMedia.getStatus()).isEqualTo(Status.INPROGRESS);
    }

    @Test
    public void test_createUserMedia_inexistingUserMedia_should_succeed() {
        //Given
        final Status createdStatus = Status.TOWATCH;

        UserMedia inexistingUserMedia = new UserMedia();
        inexistingUserMedia.setMedia(mediaD);
        inexistingUserMedia.setUserml(userMl33);

        //When
        Mockito.when(userMediaRepositoryMock.findByUsermlIdAndMediaId(userMl33.getId(),mediaD.getId())).thenReturn(Optional.empty());
        Mockito.when(userMlRepositoryMock.getById(userMl33.getId())).thenReturn(userMl33);
        Mockito.when(mediaRepositoryMock.getById(mediaD.getId())).thenReturn(mediaD);
        Mockito.when(userMediaRepositoryMock.save(inexistingUserMedia)).thenReturn(userMedia34);

        userMedia34.setStatus(createdStatus);
        UserMedia savedUserMedia = userMediaServiceUnderTest.updateUserMedia(inexistingUserMedia);

        //Then
        assertThat(savedUserMedia).isNotNull();
        assertThat(savedUserMedia.getStatus()).isEqualTo(userMedia34.getStatus());

        assertThat(savedUserMedia.getUserml().getId()).isEqualTo(userMl33.getId());
        assertThat(savedUserMedia.getMedia().getId()).isEqualTo(mediaD.getId());
        assertThat(savedUserMedia.getStatus()).isEqualTo(createdStatus);
    }
}
