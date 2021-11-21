package com.example.mylist;


import com.example.mylist.domain.media.Genre;
import com.example.mylist.infrastructure.GenreRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GenreRepositoryTest {

    @Autowired
    GenreRepository genreRepository;

     /* Test - test_find_by_genre_should_be_unsuccess */
    @Test
    public void test_find_by_genre_should_be_unsuccess() {
        String inexistingGenre = "peplumkitsch";
        Optional<Genre> returnedGenre = genreRepository.findByName(inexistingGenre);
        assertThat(returnedGenre.isPresent()).isFalse();

    }

    /* Test - test_find_by_genre_should_success */
    @Test
    public void test_find_by_genre_should_success() {
        String existingGenre = "Drama";
        Optional<Genre> returnedGenre = genreRepository.findByName(existingGenre);
        
        assertThat(returnedGenre.isPresent()).isTrue();
        assertThat(returnedGenre.get().getName()).isEqualTo(existingGenre);
    }

    /* Test - test_exists_by_genre_should_be_unsuccess */
    @Test
    public void test_exists_by_genre_should_be_unsuccess() {
        String inexistingGenre = "peplumkitsch2";
        assertThat(genreRepository.existsByName(inexistingGenre)).isFalse();
    }

    /* Test - test_exists_by_genre_should_be_successfull */
    @Test
    public void test_exists_by_genre_should_be_successfull() {
        String existingGenre = "Adventure";

        assertThat(genreRepository.existsByName(existingGenre)).isTrue();
    }


    /* Test - test_save_should_be_successfull */
    @Test
    public void test_save_genre_should_be_successfull() {
        String denominationGenre = "GenreNouveauAcreer";
        Genre nouveauGenre = new Genre();
        nouveauGenre.setName(denominationGenre);

        Genre savedGenre = genreRepository.save(nouveauGenre);

        assertThat(savedGenre).isNotNull();
        assertThat(savedGenre.getName()).isEqualTo(denominationGenre);

    }

    /* Test - test_update_should_be_successfull */
    @Test
    public void test_update_genre_should_be_successfull() {
        String denominationGenre = "Adventure";
        Long id_genre = genreRepository.findByName(denominationGenre).get().getId();

        String deno2 = "Adventures Adventures";

        Genre nouveauGenre = new Genre();
        nouveauGenre.setName(deno2);
        nouveauGenre.setId(id_genre);

        Genre savedGenre = genreRepository.save(nouveauGenre);

        assertThat(savedGenre).isNotNull();
        assertThat(savedGenre.getName()).isEqualTo(deno2);

    }

}
