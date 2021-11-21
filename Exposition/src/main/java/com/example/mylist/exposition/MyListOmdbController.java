package com.example.mylist.exposition;

import com.example.mylist.configuration.security.JwtTokenUtil;
import com.example.mylist.domain.usermylist.UserMedia;
import com.example.mylist.domain.usermylist.UserMl;
import com.example.mylist.media.MediaService;
import com.example.mylist.media.UserMediaService;
import com.example.mylist.omdb.OmdbService;
import com.example.mylist.user.UserMlService;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.security.RolesAllowed;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

// @CrossOrigin - Ajouter uniquement pour les tests Angular qui sont sur la même machine. A supprimer par la suite.
@CrossOrigin(origins = "http://localhost:4200")
@Api(value = "Public", tags = "PUBLIC - OMDB  - MyList")
@RestController
@RequestMapping("omdb")
public class MyListOmdbController {
    @Autowired
    OmdbService omdbService;

    @Autowired
    UserMlService userMlService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    //******************** Partie OMDB ******************/
    @ApiOperation(value ="Recherche et retourne une liste de média à importer depuis l'API omdb.")
    @RolesAllowed({"USER","ADMIN"})
    @GetMapping("/search/{searchText}")
    public ResponseEntity<Object> showSearchResults(@PathVariable String searchText){
        return ResponseEntity.ok( omdbService.searchMediaByNameFromOmdbAPI(searchText));
    }

    @ApiOperation(value ="List all media for the connected user.")
    @GetMapping("/import/{imdbID}")
    public List<UserMedia> importMediaForConnectedUser(@PathVariable String imdbID) throws UnirestException, IOException {
        String auth = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        String jwt = auth.split(" ")[1];
        Optional<UserMl> userConnectedOptional = userMlService.findByLogin(jwtTokenUtil.getUsernameFromToken(jwt));
        UserMl userConnected = userConnectedOptional.get();
        Long usermlId = userConnected.getId();
        System.out.println("Import du media pour l'utilisateur : " + userConnected.getLogin() + "(id=" + usermlId+ ")");
        return omdbService.importMediaUsingImdbIdFromOmdbAPI(imdbID,usermlId);
    }

//    @ApiOperation(value ="Importe depuis l'API omdb un media depuis son imdbID.")
//    @RolesAllowed({"ADMIN"})
//    @GetMapping("/import/{imdbID}")
//    public void importMediaDataFromExternalAPISearchByImdbID(@PathVariable String imdbID) throws IOException, UnirestException {
//        omdbService.importMediaUsingImdbIdFromOmdbAPI(imdbID);
//    }
//
//    @ApiOperation(value ="Importe depuis l'API omdb un media depuis son imdbID.")
//    @RolesAllowed({"USER","ADMIN"})
//    @GetMapping("/import/{userMlID}/{imdbID}")
//    public void importMediaDataFromExternalAPISearchByImdbIDForConnectedUser(@PathVariable String imdbID, @PathVariable Long userMlID) throws IOException, UnirestException {
//        omdbService.importMediaUsingImdbIdFromOmdbAPI(imdbID,userMlID);
//    }
//
//    @ApiOperation(value ="Importe depuis l'API omdb un media depuis son imdbID.")
//    @RolesAllowed({"USER","ADMIN"})
//    @PostMapping("/import/{imdbID}")
//    public void importMediaDataFromExternalAPISearchByImdbIDForConnectedUser(@PathVariable String imdbID, @RequestBody UserMl userMl) throws IOException, UnirestException {
//        omdbService.importMediaUsingImdbIdFromOmdbAPI(imdbID,userMl);
//    }
}