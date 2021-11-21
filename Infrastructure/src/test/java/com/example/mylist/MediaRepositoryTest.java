package com.example.mylist;

import com.example.mylist.domain.media.Media;
import com.example.mylist.infrastructure.MediaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MediaRepositoryTest {

    @Autowired
    MediaRepository mediaRepository;

    Media newMedia = new Media();

    @Before
    public void setup(){
        newMedia.setTitle("Asterix");
        newMedia.setImdbID("imdbidAsterix");
        mediaRepository.save(newMedia);
    }

    @Test
    public void find_media_using_title_contains_return_list_of_media() {
        // Given
        String searchString = "Asterix";

        // When
        List<Media> mediaList = mediaRepository.findByTitleContaining(searchString);

        // Then
        assertThat(mediaList).isNotNull();
        assertThat(mediaList.size()).isGreaterThan(0);
    }

    @Test
    public void find_media_using_title_contains_return_O_elements() {
//        Media newMedia = new Media();
//        newMedia.setTitle("Asterix");
//        mediaRepository.save(newMedia);

        // Given
        String searchString = "Title_Not_Exists";

        // When
        List<Media> mediaList = mediaRepository.findByTitleContaining(searchString);

        // Then
        assertThat(mediaList).isNotNull();
        assertThat(mediaList.size()).isEqualTo(0);
    }

    @Test
    public void find_media_using_imdbid_return_1_elements() {
//        Media newMedia = new Media();
//        newMedia.setTitle("Asterix");
//        newMedia.setImdbID("imdbidAsterix");
        mediaRepository.save(newMedia);

        // Given
        String searchString = "imdbidAsterix";

        // When
        Optional<Media> mediaList = mediaRepository.findByImdbID(searchString);

        // Then
        assertThat(mediaList).isNotNull();
        assertThat(mediaList.get().getImdbID()).isEqualTo(searchString);
    }

    @Test
    public void find_media_using_imdbid_return_0_elements() {
//        Media newMedia = new Media();
//        newMedia.setTitle("Asterix");
//        newMedia.setImdbID("imdbidAsterix");
//        mediaRepository.save(newMedia);

        // Given
        String searchString = "imdbid:StarWars";

        // When
        Optional<Media> mediaList = mediaRepository.findByImdbID(searchString);

        // Then
        assertThat(mediaList).isEmpty();
    }

    @Test
    public void test_if_imdbID_media_exists_return_true(){
        //GIVEN
        String searchImdbID = newMedia.getImdbID();

        //WHEN
        boolean isMesdiaExists = mediaRepository.existsByImdbID(searchImdbID);

        //THEN
        assertThat(isMesdiaExists).isNotNull();
        assertThat(isMesdiaExists).isTrue();
    }

    @Test
    public void test_if_imdbID_media_exists_return_false(){
        //GIVEN
        String searchImdbID = "FakeImdbID";

        //WHEN
        boolean isMesdiaExists = mediaRepository.existsByImdbID(searchImdbID);

        //THEN
        assertThat(isMesdiaExists).isNotNull();
        assertThat(isMesdiaExists).isFalse();
    }
}