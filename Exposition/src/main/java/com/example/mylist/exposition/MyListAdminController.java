package com.example.mylist.exposition;

import com.example.mylist.domain.usermylist.UserMedia;
import com.example.mylist.domain.usermylist.UserMl;
import com.example.mylist.media.UserMediaService;
import com.example.mylist.user.UserMlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

// @CrossOrigin - Ajouter uniquement pour les tests Angular qui sont sur la même machine. A supprimer par la suite.
@CrossOrigin(origins = "http://localhost:4200")
@Api(value = "Admin", tags = "PRIVATE - Admin - MyList API")
@RestController
@RequestMapping("admin")
public class MyListAdminController {

    @Autowired
    UserMlService userMlService;

    @Autowired
    UserMediaService userMediaService;

    //******************** Fonctions accessibles à l'administrateur ******************/

    //********** tous les utilisateurs **********/
    @ApiOperation(value ="Liste tous les utilisateurs de l'application.")
    @RolesAllowed({"ADMIN"})
    @GetMapping("user")
    public List<UserMl> findAll() {
        return userMlService.findAll();
    }

    //********** tous les utilisateurs **********/
    @ApiOperation(value ="Supprime un utilisateur.")
    @RolesAllowed({"ADMIN"})
    @PostMapping("user/delete")
    public List<UserMl> deleteUser(@RequestBody UserMl userMl) {
        userMlService.removeUSer(userMl);
        return userMlService.findAll();
    }

    //********** toutes les listes des utilisateurs **********/
    @ApiOperation(value ="Liste tous les utilisateurs et ces medias associés.")
    @RolesAllowed({"ADMIN"})
    @GetMapping("usermedia")
    public List<UserMedia> findAllUserMedia() {
        return userMediaService.findAll();
    }

    //********** toutes les listes des utilisateurs pointant vers un media donné **********/
    @ApiOperation(value ="Liste tous les utilisateurs pour un media donné (recherche par Id de media).")
    @RolesAllowed({"ADMIN"})
    @GetMapping("media/{id_m}")
    public List<UserMedia> findByMediaId(@PathVariable Long id_m) {
        return userMediaService.findByMediaId(id_m);
    }
}
