package com.example.mylist.exposition;

import com.example.mylist.configuration.security.JwtTokenUtil;
import com.example.mylist.media.MediaService;
import com.example.mylist.model.JwtRequest;
import com.example.mylist.omdb.OmdbService;
import com.example.mylist.media.UserMediaService;
import com.example.mylist.user.UserMlService;
import com.example.mylist.domain.media.Media;
import com.example.mylist.domain.usermylist.UserMedia;
import com.example.mylist.domain.usermylist.UserMl;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.security.RolesAllowed;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

// @CrossOrigin - Ajouter uniquement pour les tests Angular qui sont sur la même machine. A supprimer par la suite.
@CrossOrigin(origins = "http://localhost:4200")
@Api(value = "Public", tags = "PUBLIC - User & Media  - MyList")
@RestController
@RequestMapping()
public class MylistController {
    @Autowired
    UserMlService userMlService;

    @Autowired
    MediaService mediaService;

    @Autowired
    UserMediaService userMediaService;

    @Autowired
    OmdbService omdbService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    //******************** Partie Media ******************/

    //********** la liste de tous les media en base **********/
    @ApiOperation(value ="Liste tous les medias")
    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/media")
    public List<Media> findAllMedia() { return mediaService.findAll(); }

    //*****************************N'a pas besoin d'apparaitre en tant que endpoint
    //********** recherche d'un media par son identifiant générique donné **********/
    @ApiOperation(value ="Recherche un media par son id")
    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/media/{id}")
    public Optional<Media> findByIdMedia(@PathVariable Long id) { return mediaService.findById(id);
    }

    //********** recherche les épisodes d'une série par son identifiant IMDB donné  **********/
    @ApiOperation(value ="Liste tous les saisons et épisodes d'une série.")
    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/media/serie/{imdbID}")
    public List<Media> findBySerieIDOrderBySeasonOrderByEpisode(@PathVariable String imdbID){
        return mediaService.findBySerieIDOrderBySeasonOrderByEpisode(imdbID);
    }

    //********** recherche les media dont le titre contient la chaine donnée **********/
    @ApiOperation(value ="Recherche et retourne une liste de medias dont le titre contient une chaine de caractères.")
    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/media/title/{title}")
    public List<Media> findByTitleContaining(@PathVariable String title) {
        return mediaService.findByTitleContaining(title);
    }

    //********** recherche un media par son identifiant IMDB donné **********/
    @ApiOperation(value ="Recherche et retourne un media par son imdbID.")
    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/media/imdbid/{imdbid}")
    public Optional<Media> findByImdbid(@PathVariable String imdbid) {
        return mediaService.findByImdbID(imdbid);
    }


    //********** ajout d'un media donné **********/
    //***** Post Post Post *****/
    @ApiOperation(value ="Ajout d'un nouveau média.")
    @RolesAllowed({"USER", "ADMIN"})
    @PostMapping("media/add")
    public void addMedia(@RequestBody Media param_media) {
        mediaService.addMedia(param_media);
    }

    //******************** Partie UserMedia ******************/

    //********** la liste de media d'un utilisateur donné **********/
    @ApiOperation(value ="Recherche et retourne la liste des media pour un utilisateur donné (recherche par id).")
    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/usermedia/{id_userMl}")
    public List<UserMedia> findByUsermylistId(@PathVariable Long id_userMl) {
        return userMediaService.findByUsermlId(id_userMl);
    }

    @ApiOperation(value ="List all media for the connected user.")
    @GetMapping("/usermedia/mymedia")
    public List<UserMedia> listMediaOfConnectedUser(){

        String auth = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        String jwt = auth.split(" ")[1];
//        System.out.println("jwt--:" + jwt);
//        System.out.println(jwtTokenUtil.getUsernameFromToken(jwt));
        Optional<UserMl> userConnectedOptional = userMlService.findByLogin(jwtTokenUtil.getUsernameFromToken(jwt));
        UserMl userConnected = userConnectedOptional.get();
        Long usermlId = userConnected.getId();
        System.out.println("Retour de la liste des medias pour l'utilisateur : " + userConnected.getLogin() + "(id=" + usermlId+ ")");
        return userMediaService.findAllByUsermlId(usermlId);
    }

    //********** MAJ de la liste de media d'un utilisateur donné **********/
    @ApiOperation(value ="Mise à jour des media d'un utilisateurs donné.")
    @RolesAllowed({"USER", "ADMIN"})
    @PutMapping("/usermedia/update")
    public UserMedia updateUserMedia(@RequestBody UserMedia userMedia_p)
    {
            return userMediaService.updateUserMedia(userMedia_p);
    }

    //********** la liste de media d'un utilisateur donné **********/
    @ApiOperation(value ="Recherche et retourne la liste des media pour un utilisateur donné (recherche par id).")
    @RolesAllowed({"USER", "ADMIN"})
    @PostMapping("/usermedia/list")
    public List<UserMedia> listMediaForUser(@RequestBody UserMl userMl) {
        System.out.println("Here");
        Long userMlID = userMl.getId();
        return userMediaService.findByUsermlId(userMlID);
    }

    //******************** Fonctions accessibles aux utilisateurs ******************/

//    //******************** Partie UserMl ******************/
//    @ApiOperation(value ="Recherche un utilisateur par son id.")
//    @RolesAllowed({"USER", "ADMIN"})
//    @GetMapping("/user/{id}")
//    public Optional<UserMl> findById(@PathVariable Long id) {
//        return userMlService.findById(id);
//    }
}