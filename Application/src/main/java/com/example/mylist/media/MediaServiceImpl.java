package com.example.mylist.media;

import com.example.mylist.domain.media.Actor;
import com.example.mylist.domain.media.Genre;
import com.example.mylist.domain.media.Media;
import com.example.mylist.infrastructure.ActorRepository;
import com.example.mylist.infrastructure.GenreRepository;
import com.example.mylist.infrastructure.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MediaServiceImpl implements MediaService {
    @Autowired
    MediaRepository mediaRepository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    ActorRepository actorRepository;

    @Override
    public Optional<Media> findById(Long id) {
        return mediaRepository.findById(id);
    }

    @Override
    public List<Media> findAll() {
        return mediaRepository.findAll();
    }

    @Override
    public List<Media> findByTitleContaining(String title) {
        return mediaRepository.findByTitleContaining(title);
    }

    @Override
    public Optional<Media> findByImdbID(String imdbid) {
        return mediaRepository.findByImdbID(imdbid);
    }

    /**
     * This function  allow to add a media into database.
     * If the media exists, the media will be updated (actors / genres / ....)
     * This function manage actors and genre (add new actor / add new genre).
     * <br>
     * @param media An object of type media.
     */
    @Transactional
    @Override
    public Media addMedia(Media media) {
        List<Actor> actorListInMovie = media.getActorList();
        List<Genre> genreListOfMovie = media.getGenreList();

        Media newMedia;
        Optional<Media> searchMedia = mediaRepository.findByImdbID(media.getImdbID());
        if(searchMedia.isPresent()) {
            newMedia = searchMedia.get();
        }
        else{
            newMedia = media;
        }
        newMedia.setActorList(new ArrayList<Actor>());
        newMedia.setGenreList(new ArrayList<Genre>());

        // Gestion des actors
        List<Actor> actorList = new ArrayList<Actor>();
        if(actorListInMovie.size()>0){
            for (Actor actor :actorListInMovie) {
                if(!actorRepository.existsByName(actor.getName())){
                    Actor newActor = new Actor();
                    newActor.setName(actor.getName());
                    actorList.add(newActor);
                }
                else{
                    Actor newActor = actorRepository.findByName(actor.getName()).get();
                    actorList.add(newActor);
                }
            }
        }

        // Gestion des genres
        List<Genre> genreList = new ArrayList<Genre>();
        if(genreListOfMovie.size()>0){
            for (Genre genre :genreListOfMovie) {
                if(!genreRepository.existsByName(genre.getName())){
                    Genre newGenre = new Genre();
                    newGenre.setName(genre.getName());
                    genreList.add(newGenre);
                }
                else{
                    Genre newGenre = genreRepository.findByName(genre.getName()).get();
                    genreList.add(newGenre);
                }
            }
        }

        newMedia.setActorList(actorList);
        newMedia.setGenreList(genreList);
        return mediaRepository.save(newMedia);
    }

//    @Transactional
//    @Override
//    public void addMediaGenre(Media media) {
//        /* Test existence Media */
//        Optional<Media> actuelMedia = mediaRepository.findByImdbID(media.getImdbID());
//        Media completedMedia = actuelMedia.get();
//
//        /* Récupération des genres du média passé en paramètre */
//        /* Test existence des genres avant insertion dans media */
//        List<Genre> genreList = media.getGenreList();
//        for (Genre genre : genreList) {
//            Optional<Genre> returnedGenre = genreRepository.findByName(genre.getName());
//
//            if (returnedGenre.isPresent()) {
//                genre.setId(returnedGenre.get().getId());
//            } else {
//                Genre newGenre = new Genre();
//                newGenre.setName(genre.getName());
//
//                genre.setId(genreRepository.save(newGenre).getId());
//
//            }
//        }
//
//        /* Option 1 : annule et remplace si le media avait déjà une liste */
//        /*         completedMedia.setGenreList(genreList); */
//        /* Option 2 : ajoute la liste à l'ancienne sans détecter de doublons */
//        /*         completedMedia.getGenreList().addAll(genreList); */
//        /* option 1 prise */
//        completedMedia.setGenreList(genreList);
//        mediaRepository.save(completedMedia);
//    }
//
//    @Transactional
//    @Override
//    public void addMediaActor(Media media) {
//        /* Test existence Media */
//
//        Optional<Media> actuelMedia = mediaRepository.findByImdbID(media.getImdbID());
//        Media completedMedia = actuelMedia.get();
//
//        /* Récupération des acteurs du média passé en paramètre */
//        /* Test existence des acteurs avant insertion dans media */
//        List<Actor> actorList = media.getActorList();
//        for (Actor actor : actorList) {
//            Optional<Actor> returnedActor = actorRepository.findByName(actor.getName());
//
//            if (returnedActor.isPresent()) {
//                actor.setId(returnedActor.get().getId());
//            } else {
//                Actor newActor = new Actor();
//                newActor.setName(actor.getName());
//
//                actor.setId(actorRepository.save(newActor).getId());
//            }
//        }
//
//        /* Option 1 : annule et remplace si le media avait déjà une liste */
//        /*                 completedMedia.setActorList(actorList); */
//        /* Option 2 : ajoute la liste à l'ancienne sans détecter de doublons */
//        /*         completedMedia.getActorList().addAll(actorList); */
//        /* option 1 prise */
//        completedMedia.setActorList(actorList);
//
//        mediaRepository.save(completedMedia);
//    }

    public List<Media> findBySerieIDOrderBySeasonOrderByEpisode(String imdbID){
        return mediaRepository.findBySeriesIDOrderBySeasonAscEpisodeAsc(imdbID);
    }

//    @Override
//    public List<Media> listAllMediaForUserMl(UserMl userMl) {
//        return null;
//    }

}