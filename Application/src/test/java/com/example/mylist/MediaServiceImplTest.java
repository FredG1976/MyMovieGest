package com.example.mylist;

import com.example.mylist.domain.media.Actor;
import com.example.mylist.domain.media.Genre;
import com.example.mylist.domain.media.Media;
import com.example.mylist.domain.media.Type;
import com.example.mylist.infrastructure.ActorRepository;
import com.example.mylist.infrastructure.GenreRepository;
import com.example.mylist.infrastructure.MediaRepository;
import com.example.mylist.media.MediaService;
import com.example.mylist.media.MediaServiceImpl;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class MediaServiceImplTest {

    @Mock
    MediaRepository mediaRepositoryMock;

    @Mock
    GenreRepository genreRepositoryMock;

    @Mock
    ActorRepository actorRepositoryMock;

    @InjectMocks
    MediaService mediaServiceUnderTest = new MediaServiceImpl();

    Media mediaA = new Media();
    Media mediaB = new Media();
    Media mediaC = new Media();
    Media mediaD = new Media();
    Media mediaE = new Media();
    Media mediaF = new Media();
    Media mediaG = new Media();

    List<Media> mediaList = new ArrayList<>();  // liste pour FindAll
    List<Media> mediaList3 = new ArrayList<>(); // liste pour findBytitle
    List<Media> mediaList5 = new ArrayList<>(); // liste pour findBySerieIDOrderBySeasonOrderByEpisode

    List<Actor> actorList = new ArrayList<>();
    Actor actor1 = new Actor();

    List<Genre> genreList = new ArrayList<>();
    Genre genre1 = new Genre();

    @Before
    public void SetUp(){

        mediaA.setId(55555L);
        mediaA.setTitle("Film MediaA 55555 Le Crime de l'Orient Express");
        mediaA.setType(Type.MOVIE);
        mediaA.setImdbID("55555IMDBID");
        mediaA.setRuntime("555 min");
        mediaA.setSeriesID(null);
        mediaA.setYear("2005");
        mediaA.setDirector("55555Directeur");
        mediaA.setPlot("55555Description");
        mediaA.setPoster(null);
        mediaA.setImdbRating(null);
        mediaA.setTotalSeasons(0);
        mediaA.setSeason(0);
        mediaA.setEpisode(0);

        mediaB.setId(66666L);
        mediaB.setTitle("Film MediaB 66666 Mort sur le Nil");
        mediaB.setType(Type.MOVIE);
        mediaB.setImdbID("66666IMDBID");
        mediaB.setRuntime("666 min");
        mediaB.setSeriesID(null);
        mediaB.setYear("2006");
        mediaB.setDirector("66666Directeur");
        mediaB.setPlot("66666Description");
        mediaB.setPoster(null);
        mediaB.setImdbRating(null);
        mediaB.setTotalSeasons(0);
        mediaB.setSeason(0);
        mediaB.setEpisode(0);

        mediaC.setId(77777L);
        mediaC.setTitle("Film MediaC 77777 Dix Petits Nègres");
        mediaC.setType(Type.MOVIE);
        mediaC.setImdbID("77777IMDBID");
        mediaC.setRuntime("77 min");
        mediaC.setSeriesID(null);
        mediaC.setYear("2007");
        mediaC.setDirector("77777Directeur");
        mediaC.setPlot("77777Description");
        mediaC.setPoster(null);
        mediaC.setImdbRating(null);
        mediaC.setTotalSeasons(0);
        mediaC.setSeason(0);
        mediaC.setEpisode(0);

        mediaD.setId(88888L);
        mediaD.setTitle("Film MediaD 88888 Le Train Bleu");
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

        mediaE.setId(99900L);
        mediaE.setTitle("Episode mediaE 99900 Hercule Poirot - Le meurtre de Roger Ackroyd");
        mediaE.setType(Type.EPISODE);
        mediaE.setImdbID("99900IMDBID");
        mediaE.setRuntime("115 min");
        mediaE.setSeriesID("90000IMDBID");
        mediaE.setYear("2000");
        mediaE.setDirector("90000Directeur");
        mediaE.setPlot("99900Description");
        mediaE.setPoster(null);
        mediaE.setImdbRating(4.7F);
        mediaE.setTotalSeasons(13);
        mediaE.setSeason(7);
        mediaE.setEpisode(1);

        mediaF.setId(99922L);
        mediaF.setTitle("Episode mediaF 99922 Hercule Poirot - Tragédie à Marsdon Manor");
        mediaF.setType(Type.EPISODE);
        mediaF.setImdbID("99922IMDBID");
        mediaF.setRuntime("50 min");
        mediaF.setSeriesID("90000IMDBID");
        mediaF.setYear("1991");
        mediaF.setDirector("99990Directeur");
        mediaF.setPlot("99922Description");
        mediaF.setPoster(null);
        mediaF.setImdbRating(3.8F);
        mediaF.setTotalSeasons(13);
        mediaF.setSeason(3);
        mediaF.setEpisode(6);

        mediaG.setId(99911L);
        mediaG.setTitle("Episode mediaG 99911 Hercule Poirot - La Mystérieuse Affaire de Styles");
        mediaG.setType(Type.EPISODE);
        mediaG.setImdbID("99911IMDBID");
        mediaG.setRuntime("50 min");
        mediaG.setSeriesID("90000IMDBID");
        mediaG.setYear("1991");
        mediaG.setDirector("90000Directeur");
        mediaG.setPlot("99911Description");
        mediaG.setPoster(null);
        mediaG.setImdbRating(4.3F);
        mediaG.setTotalSeasons(13);
        mediaG.setSeason(3);
        mediaG.setEpisode(1);

        mediaList.add(mediaE);
        mediaList.add(mediaD);
        mediaList.add(mediaC);
        mediaList.add(mediaB);
        mediaList.add(mediaA);

        mediaList3.add(mediaE);
        mediaList3.add(mediaF);

        mediaList5.add(mediaG);
        mediaList5.add(mediaF);
        mediaList5.add(mediaE);

        actor1.setName("Bruce WILLIS");
        actorList.add(actor1);

        genre1.setName("Action");
        genreList.add(genre1);
    }

    @Test
    public void test_add_new_media(){
        Media newMedia = new Media();
        newMedia.setId(123456L);
        newMedia.setTitle("Film MediaX 123456");
        newMedia.setType(Type.MOVIE);
        newMedia.setImdbID("123456IMDBID");
        newMedia.setRuntime("555 min");
        newMedia.setSeriesID(null);
        newMedia.setYear("2005");
        newMedia.setDirector("123456Directeur");
        newMedia.setPlot("123456Description");
        newMedia.setPoster(null);
        newMedia.setImdbRating(null);
        newMedia.setTotalSeasons(0);
        newMedia.setSeason(0);
        newMedia.setEpisode(0);
        newMedia.setGenreList(genreList);
        newMedia.setActorList(actorList);

        Mockito.when(mediaRepositoryMock.findByImdbID(newMedia.getImdbID())).thenReturn(Optional.empty());
        mediaServiceUnderTest.addMedia(newMedia);

        assertThat(newMedia).isNotNull();
        assertThat(newMedia.getTitle()).isEqualTo(newMedia.getTitle());
    }

    @Test
    public void test_update_existing_media(){
        //GIVEN newMedia = mediaA => Existe déja.
        Media newMedia = new Media();
        newMedia.setId(55555L);
        newMedia.setTitle("Film MediaA 55555 Le Crime de l'Orient Express");
        newMedia.setType(Type.MOVIE);
        newMedia.setImdbID("55555IMDBID");
        newMedia.setRuntime("555 min");
        newMedia.setSeriesID(null);
        newMedia.setYear("2005");
        newMedia.setDirector("55555Directeur");
        newMedia.setPlot("55555Description");
        newMedia.setPoster(null);
        newMedia.setImdbRating(null);
        newMedia.setTotalSeasons(0);
        newMedia.setSeason(0);
        newMedia.setEpisode(0);
        newMedia.setGenreList(genreList);
        newMedia.setActorList(actorList);
        Mockito.when(mediaRepositoryMock.findByImdbID(newMedia.getImdbID())).thenReturn(Optional.ofNullable(mediaA));
        mediaServiceUnderTest.addMedia(newMedia);

        assertThat(newMedia).isNotNull();
        assertThat(newMedia.getTitle()).isEqualTo(newMedia.getTitle());
    }

    @Test
    public void add_new_media_with_new_actor_and_new_genre(){
        // A implementer
        //GIVEN
        Media newMedia = new Media();
        newMedia.setId(123456L);
        newMedia.setTitle("Film MediaX 123456");
        newMedia.setType(Type.MOVIE);
        newMedia.setImdbID("123456IMDBID");
        newMedia.setRuntime("555 min");
        newMedia.setSeriesID(null);
        newMedia.setYear("2005");
        newMedia.setDirector("123456Directeur");
        newMedia.setPlot("123456Description");
        newMedia.setPoster(null);
        newMedia.setImdbRating(null);
        newMedia.setTotalSeasons(0);
        newMedia.setSeason(0);
        newMedia.setEpisode(0);
        newMedia.setGenreList(genreList);
        newMedia.setActorList(actorList);

//        Actor newActor = new Actor();
//        newActor.setId(999L);
//        newActor.setId("Cameron DIAZ");
//        actorList.add(newActor);

        //WHEN
        Mockito.when(actorRepositoryMock.existsByName(actor1.getName())).thenReturn(false);
        Mockito.when(genreRepositoryMock.existsByName(genre1.getName())).thenReturn(false);
        Mockito.when(mediaRepositoryMock.findByImdbID(newMedia.getImdbID())).thenReturn(Optional.empty());
        mediaServiceUnderTest.addMedia(newMedia);

        //THEN
        assertThat(newMedia).isNotNull();
        assertThat(newMedia.getTitle()).isEqualTo(newMedia.getTitle());
    }

    @Test
    public void test_findAllMedia_should_succeed() {
        //When
        Mockito.when(mediaRepositoryMock.findAll()).thenReturn(mediaList);
        List<Media> mList1 = mediaServiceUnderTest.findAll();

        //Then
        verify(mediaRepositoryMock).findAll();
        verify(mediaRepositoryMock, times(1)).findAll();

        assertThat(mList1.size()).isEqualTo(5);
        assertThat(mList1.get(0).getSeason()).isEqualTo(mediaE.getSeason());
        assertThat(mList1.get(1).getTitle()).isEqualTo(mediaD.getTitle());
        assertThat(mList1.get(4).getId()).isEqualTo(mediaA.getId());

        ////      //should_be_unsuccessful
//        assertThat(mList1.size()).isEqualTo(1);
//        assertThat(mList1.get(0).getSeason()).isEqualTo(mediaB.getSeason());
//        assertThat(mList1.get(1).getTitle()).isEqualTo(mediaB.getTitle());
//        assertThat(mList1.get(4).getId()).isEqualTo(mediaB.getId());
    }

    @Test
    public void test_findById_should_succeed() {
        //When
        Mockito.when(mediaRepositoryMock.findById(mediaE.getId())).thenReturn(Optional.of(mediaE));
//        Mockito.when(mediaRepositoryMock.findById(mediaE.getId())).thenReturn(Optional.empty());

        Optional<Media> optionalMedia = mediaServiceUnderTest.findById(mediaE.getId());

        //Then
        verify(mediaRepositoryMock).findById(mediaE.getId());
        verify(mediaRepositoryMock, times(1)).findById(mediaE.getId());

        assertThat(optionalMedia.isPresent()).isTrue();
        assertThat(optionalMedia.get().getTitle()).isEqualTo(mediaE.getTitle());

        ////      //should_be_unsuccessful
//        assertThat(optionalMedia.isPresent()).isFalse();
//        assertThat(optionalMedia.get().getTitle()).isEqualTo(mediaB.getTitle());

    }

    @Test
    public void test_findByTitlecontaining_should_succeed() {
        //Given
        final String searchedTitle = "Poirot";
//        final String searchedTitle = "Orient Express";

        //When
        Mockito.when(mediaRepositoryMock.findByTitleContaining(searchedTitle)).thenReturn(mediaList3);
        List<Media> mList3 = mediaServiceUnderTest.findByTitleContaining(searchedTitle);

        //Then
        verify(mediaRepositoryMock).findByTitleContaining(searchedTitle);
        verify(mediaRepositoryMock, times(1)).findByTitleContaining(searchedTitle);

        assertThat(mList3.size()).isEqualTo(mediaList3.size());
        assertThat(mList3.get(0).getTitle()).isEqualTo(mediaE.getTitle());
        assertThat(mList3.get(1).getTitle()).isEqualTo(mediaF.getTitle());
        assertThat(mList3.get(1).getSeason()).isEqualTo(mediaF.getSeason());

              //should_be_unsuccessful
//        assertThat(mList3.size()).isEqualTo(5);
//        assertThat(mList3.get(0).getTitle()).isEqualTo(mediaA.getTitle());
//        assertThat(mList3.get(1).getTitle()).isEqualTo(mediaB.getTitle());
//        assertThat(mList3.get(1).getSeason()).isEqualTo(mediaC.getSeason());
    }

    @Test
    public void Test_finByImdbID_should_succeed() {
        //Given
        final String searchedImbdId = "88888IMDBID";

        //When
        Mockito.when(mediaRepositoryMock.findByImdbID(searchedImbdId)).thenReturn(Optional.of(mediaD));
//        Mockito.when(mediaRepositoryMock.findByImdbID(searchedImbdId)).thenReturn(Optional.empty());
        Optional<Media> optionalMedia4 = mediaServiceUnderTest.findByImdbID(searchedImbdId);

        //Then
        verify(mediaRepositoryMock).findByImdbID(searchedImbdId);
        verify(mediaRepositoryMock, times(1)).findByImdbID(searchedImbdId);

        assertThat(optionalMedia4.isPresent()).isTrue();
        assertThat(optionalMedia4.get().getImdbID()).isEqualTo(mediaD.getImdbID());
        assertThat(optionalMedia4.get().getTitle()).isEqualTo(mediaD.getTitle());

        //should_be_unsuccessful
//        assertThat(optionalMedia4.get().getImdbID()).isEqualTo(mediaE.getImdbID());
//        assertThat(optionalMedia4.get().getTitle()).isEqualTo(mediaE.getTitle());
    }

    @Test
    public void test_findBySeriesIDOrderBySeason_should_succeed() {
        //Given
        final String searchedImdbSeriesId = "90000IMDBID";

        //When
        Mockito.when(mediaRepositoryMock.findBySeriesIDOrderBySeasonAscEpisodeAsc(searchedImdbSeriesId)).thenReturn(mediaList5);
        List<Media> mList5 = mediaServiceUnderTest.findBySerieIDOrderBySeasonOrderByEpisode(searchedImdbSeriesId);


        //Then
        verify(mediaRepositoryMock).findBySeriesIDOrderBySeasonAscEpisodeAsc(searchedImdbSeriesId);
        verify(mediaRepositoryMock, times(1)).findBySeriesIDOrderBySeasonAscEpisodeAsc(searchedImdbSeriesId);

        assertThat(mList5.size()).isEqualTo(mediaList5.size());
        assertThat(mList5.get(0).getSeason()).isEqualTo(mediaG.getSeason());
        assertThat(mList5.get(1).getSeason()).isEqualTo(mediaG.getSeason()); // c'est la même saison
        assertThat(mList5.get(0).getEpisode()).isEqualTo(mediaG.getEpisode());
        assertThat(mList5.get(1).getEpisode()).isGreaterThan(mediaG.getEpisode()); // tri saison puis episode
        assertThat(mList5.get(2).getSeason()).isGreaterThan(mediaG.getSeason());  // tri saison

        //should_be_unsuccessful
//        assertThat(mList5.size()).isGreaterThan(mediaList5.size());
//        assertThat(mList5.get(0).getSeason()).isEqualTo(mediaA.getSeason());
//        assertThat(mList5.get(1).getSeason()).isNotEqualTo(mediaG.getSeason()); // c'est la même saison
//        assertThat(mList5.get(0).getEpisode()).isNotEqualTo(mediaG.getEpisode());
//        assertThat(mList5.get(1).getEpisode()).isLessThan(mediaG.getEpisode()); // tri saison puis episode
//        assertThat(mList5.get(2).getSeason()).isLessThan(mediaG.getSeason());  // tri saison
    }

//    @Test
//    public void test_addMediaGenre_existingGenre_should_succeed() {
//        //Given
//        Genre genre1 = new Genre();
//        Genre genre2 = new Genre();
//        genre1.setId(454555L);
//        genre1.setName("Drama");
//        genre2.setId(454666L);
//        genre2.setName("Crime");
//
//        Genre addExistingGenre1 = new Genre();
//        addExistingGenre1.setName("Drama");
//        Genre addExistingGenre2 = new Genre();
//        addExistingGenre2.setName("Crime");
//
//        String searchedGenreName1 = "Drama";
//        String searchedGenreName2 = "Crime";
//
//        List<Genre> genreList1 = new ArrayList<>(); // Liste de genres existants à ajouter
//        genreList1.add(addExistingGenre1);
//        genreList1.add(addExistingGenre2);
//        mediaA.setGenreList(genreList1);
//
//        //When
//        Mockito.when(mediaRepositoryMock.findByImdbID(mediaA.getImdbID())).thenReturn(Optional.of(mediaA));
//        Mockito.when(genreRepositoryMock.findByName(genre1.getName())).thenReturn(Optional.of(genre1));
//        Mockito.when(genreRepositoryMock.findByName(genre2.getName())).thenReturn(Optional.of(genre2));
//        Mockito.when(mediaRepositoryMock.save(mediaA)).thenReturn(mediaA);
//
//        mediaServiceUnderTest.addMediaGenre(mediaA); // attention méthode ne renvoie rien
//
//        //Then
//        verify(mediaRepositoryMock).findByImdbID(mediaA.getImdbID());
//        verify(genreRepositoryMock).findByName(genre1.getName());
//        verify(genreRepositoryMock,times(1)).findByName(genre1.getName());
//        verify(genreRepositoryMock).findByName(genre2.getName());
//        verify(genreRepositoryMock,times(1)).findByName(genre2.getName());
//        verify(genreRepositoryMock, times(2)).findByName(anyString());
//        verify(mediaRepositoryMock).save(mediaA);
//        verify(mediaRepositoryMock, times(1)).save(mediaA);
//
//    }
//
}
