package com.example.mylist.domain.media;

import com.example.mylist.domain.usermylist.UserMedia;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("type")
    @Enumerated(EnumType.STRING)
    private Type type;
    @JsonProperty("imdbID")
    private String imdbID;
    @JsonProperty("title")
    private String title;
    @JsonProperty("runtime")
    private String runtime;
    @JsonProperty("seriesID")
    private String seriesID;
    @JsonProperty("year")
    private String year;
    @JsonProperty("director")
    private String director;
    @JsonProperty("plot")
    private String plot;
    @JsonProperty("poster")
    private String poster;
    @JsonProperty("imdbRating")
    private Float imdbRating;
    @JsonProperty("totalSeasons")
    private int totalSeasons;
    @JsonProperty("season")
    private int season;
    @JsonProperty("episode")
    private int episode;

    /* un media a un ou plusieurs genres et un genre peut être associé à un ou plusieurs media */
    @JsonProperty("genreList")
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable
            (name="MEDIA_GENRE",
                    joinColumns={ @JoinColumn(name="MEDIA_ID", referencedColumnName="id") },
                    inverseJoinColumns={ @JoinColumn(name="GENRE_ID", referencedColumnName="id", unique=false) }
            )
    private List<Genre> genreList;

    /* un media a un ou plusieurs acteurs ET un acteur peut jouer dans plusieurs media */
    @JsonProperty("actorList")
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable
            (name="MEDIA_ACTOR",
                    joinColumns={ @JoinColumn(name="MEDIA_ID", referencedColumnName="id") },
                    inverseJoinColumns={ @JoinColumn(name="ACTOR_ID", referencedColumnName="id", unique=false) }
            )
    private List<Actor> actorList;

    /* getters et setters */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getSeriesID() {
        return seriesID;
    }

    public void setSeriesID(String seriesID) {
        this.seriesID = seriesID;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Float getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(Float imdbRating) {
        this.imdbRating = imdbRating;
    }

    public int getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(int totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public List<Genre> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
    }

    public List<Actor> getActorList() {
        return actorList;
    }

    public void setActorList(List<Actor> actorList) {
        this.actorList = actorList;
    }

    public Media() {
    }

    public Media(Long id, Type type, String imdbID, String title, String runtime, String seriesID, String year, String director, String plot, String poster, Float imdbRating, int totalSeasons, int season, int episode, List<Genre> genreList, List<Actor> actorList) {
        this.id = id;
        this.type = type;
        this.imdbID = imdbID;
        this.title = title;
        this.runtime = runtime;
        this.seriesID = seriesID;
        this.year = year;
        this.director = director;
        this.plot = plot;
        this.poster = poster;
        this.imdbRating = imdbRating;
        this.totalSeasons = totalSeasons;
        this.season = season;
        this.episode = episode;
        this.genreList = genreList;
        this.actorList = actorList;
    }

    @Override
    public String toString() {
        return "Media{" +
                "id=" + id +
                ", type=" + type +
                ", imdbID='" + imdbID + '\'' +
                ", title='" + title + '\'' +
                ", runtime='" + runtime + '\'' +
                ", seriesID='" + seriesID + '\'' +
                ", year='" + year + '\'' +
                ", director='" + director + '\'' +
                ", plot='" + plot + '\'' +
                ", poster='" + poster + '\'' +
                ", imdbRating=" + imdbRating +
                ", totalSeasons=" + totalSeasons +
                ", season=" + season +
                ", episode=" + episode +
                ", genreList=" + genreList +
                ", actorList=" + actorList +
                '}';
    }
}
