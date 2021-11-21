package com.example.mylist.user;

import com.example.mylist.MyListException.UserMlException;
import com.example.mylist.domain.usermylist.UserMl;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface UserMlService {

    Optional<UserMl> findById(Long id);

    List<UserMl> findAll();

    UserMl addUser(UserMl userMl) throws UserMlException;

    Optional<UserMl> findByLogin(String login);

    void removeUSer(UserMl userMl);

//    StringBuffer getUsernamePasswordLoginInfo(Principal user);
//
//    StringBuffer getOauth2LoginInfo(Principal user);
}
