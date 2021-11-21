package com.example.mylist;

import com.example.mylist.domain.media.Media;
import com.example.mylist.domain.media.Status;
import com.example.mylist.domain.usermylist.UserMedia;
import com.example.mylist.domain.usermylist.UserMl;
import com.example.mylist.infrastructure.MediaRepository;
import com.example.mylist.infrastructure.UserMediaRepository;
import com.example.mylist.infrastructure.UserMlRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserMediaRepositoryTest {

    @Autowired
    UserMediaRepository userMediaRepository;

    @Autowired
    UserMlRepository userMlRepository;

    @Autowired
    MediaRepository mediaRepository;

    @BeforeAll
    public void initialize_tests_data() {
        /* chargement de la base via import.sql */
    }

    /* Test - test_find_all_usermedia_by_userMyListId_should_be_unsuccess */
    @Test
    public void test_find_all_usermedia_by_userMyListId_should_be_unsucess() {
        Long inexistingUserId = 1234567890L;

        List<UserMedia> userMediaList = userMediaRepository.findByUsermlId(inexistingUserId);

        assertThat(userMediaList.size()).isEqualTo(0);

    }

    /* Test - Liste des media d'un utilisateur */
    @Test
    public void test_find_all_usermedia_by_userMyListId_should_be_successfull() {
        Long existingUserId = 2L;
        String existingMediaImdb21 = "tt0082971";
        String existingMediaImdb22 = "tt0097576";
        String existingMediaImdb23 = "tt0087469";
        Status existingMediaStatus21 = Status.TOWATCH;
        Status existingMediaStatus22 = Status.WATCHED;
        Status existingMediaStatus23 = Status.INPROGRESS;

        List<UserMedia> userMediaList = userMediaRepository.findByUsermlId(existingUserId);

        assertThat(userMediaList.size()).isEqualTo(3);
        assertThat(userMediaList.get(0).getMedia().getImdbID()).isEqualTo(existingMediaImdb21);
        assertThat(userMediaList.get(1).getMedia().getImdbID()).isEqualTo(existingMediaImdb22);
        assertThat(userMediaList.get(2).getMedia().getImdbID()).isEqualTo(existingMediaImdb23);
        assertThat(userMediaList.get(0).getStatus()).isEqualTo(existingMediaStatus21);
        assertThat(userMediaList.get(1).getStatus()).isEqualTo(existingMediaStatus22);
        assertThat(userMediaList.get(2).getStatus()).isEqualTo(existingMediaStatus23);

     }



    /* Test - test_find_all_usermedia_by_mediaId_should_be_unsuccessfull */
    @Test
    public void test_find_all_usermedia_by_mediaId_should_be_unsucessfull() {
        Long inexistingUserId = 1234567890L;

        List<UserMedia> userMediaList = userMediaRepository.findByMediaId(inexistingUserId);

        assertThat(userMediaList.size()).isEqualTo(0);

    }

    /* Test - Liste des media d'un utilisateur */
    /* attention restitution de la liste suivant un ordre (insertion, autre ?) n'ayant pas mis de tri - à adapter */
    @Test
    public void test_find_all_usermedia_by_mediaId_should_be_successfull() {
        Long existingMediaId = 3L;
        Status existingStatus3_2 = Status.WATCHED;
        Status existingStatus3_1 = Status.TOWATCH;

        List<UserMedia> userMediaList = userMediaRepository.findByMediaId(existingMediaId);

        assertThat(userMediaList.size()).isEqualTo(2);
        assertThat(userMediaList.get(0).getStatus()).isEqualTo(existingStatus3_2);
        assertThat(userMediaList.get(1).getStatus()).isEqualTo(existingStatus3_1);

    }

    /* Test infructueux - identifiants inexistants */
    @Test
    public void test_findbyUsermlIdAndMediaId_should_be_unsucessfull() {
        Long inexistingUserMlId = 1234567890L;
        Long inexistingMediaId = 1234567890L;

        Optional<UserMedia> optionalUserMedia =
                userMediaRepository.findByUsermlIdAndMediaId(inexistingUserMlId, inexistingMediaId);

        assertThat(optionalUserMedia.isPresent()).isFalse();
    }

    /* Test infructueux - identifiants existants mais liste inexistant */
    @Test
    public void test_findbyUsermlIdAndMediaId_should_be_unsuccessfull_2() {
        Long existingUserMlId = 2L;
        Long existingMediaId = 4L;

        Optional<UserMedia> optionalUserMedia =
                userMediaRepository.findByUsermlIdAndMediaId(existingUserMlId, existingMediaId);

        assertThat(optionalUserMedia.isPresent()).isFalse();
    }

    /* Test fructueux - identifiants existants et liste inexistant */
    @Test
    public void test_findbyUsermlIdAndMediaId_should_succeed() {
        Long existingUserMlId = 1L;
        Long existingMediaId = 4L;

        Optional<UserMedia> optionalUserMedia =
                userMediaRepository.findByUsermlIdAndMediaId(existingUserMlId, existingMediaId);

        assertThat(optionalUserMedia.isPresent()).isTrue();
    }

    /* Test fructueux - Création d'un userMedia */
    @Test
    public void test_create_userMedia_should_succeed() {
        final String ownComment = "Avis personnel";
        final Float ownRating = 0F;
        final Status expectedStatus = Status.TOWATCH;
        final Long mediaId = 4L;
        final String mediaImdb = "tt0367882";

        final Long userMlId = 3L;
        final UserMl userMl = userMlRepository.getById(userMlId) ;

        final Media media = mediaRepository.getById(mediaId);

        UserMedia userMedia = new UserMedia();

        userMedia.setOwnComment(ownComment);
        userMedia.setOwnRating(ownRating);
        userMedia.setStatus(Status.TOWATCH);
        userMedia.setUserml(userMl);
        userMedia.setMedia(media);

        UserMedia returnedUserMedia = userMediaRepository.save(userMedia);

        assertThat(returnedUserMedia).isNotNull();
        assertThat(returnedUserMedia.getStatus()).isEqualTo(expectedStatus);

    }

    /* Test fructueux - Mise à jour d'un userMedia pour les annotations */
    @Test
    public void test_update_userMedia_should_succeed() {
        final String updateOwnComment = "Avis personnel - Avis personnel";
        final Float updateOwnRating = 3F;
        final Status updateStatus = Status.INPROGRESS;
        final Long mediaId = 1L;
        final Long userMlId = 2L;

        final UserMl userMl = userMlRepository.getById(userMlId) ;
        final Media media = mediaRepository.getById(mediaId);

        Optional<UserMedia> optionalUserMedia = userMediaRepository.findByUsermlIdAndMediaId(userMlId, mediaId);

        UserMedia userMedia = optionalUserMedia.get() ;

        userMedia.setOwnComment(updateOwnComment);
        userMedia.setOwnRating(updateOwnRating);
        userMedia.setStatus(updateStatus);
        userMedia.setUserml(userMl);
        userMedia.setMedia(media);

//        System.out.println(userMedia.toString());


        UserMedia savedUserMedia = userMediaRepository.save(userMedia);

        assertThat(savedUserMedia).isNotNull();
        assertThat(savedUserMedia.getStatus()).isEqualTo(updateStatus);
        assertThat(savedUserMedia.getOwnRating()).isEqualTo(updateOwnRating);
        assertThat(savedUserMedia.getOwnComment()).isEqualTo(updateOwnComment);

//        System.out.println(savedUserMedia.toString());
    }
}
