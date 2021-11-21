package com.example.mylist.infrastructure;

import com.example.mylist.domain.media.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

    List<Media> findByTitleContaining(String title);

    List<Media> findBySeriesIDOrderBySeason(String title);

    List<Media> findBySeriesIDOrderBySeasonAscEpisodeAsc(String title);

    Optional<Media> findByImdbID(String imdbid);

    Boolean existsByImdbID(String ImdbID);

    List<Media> findBySeriesID(String ImdbID);

//    list<Media> findAllAnd
}
