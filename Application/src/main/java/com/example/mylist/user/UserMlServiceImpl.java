package com.example.mylist.user;

import com.example.mylist.MyListException.UserMlException;
import com.example.mylist.domain.usermylist.UserMedia;
import com.example.mylist.domain.usermylist.UserMl;
import com.example.mylist.infrastructure.UserMediaRepository;
import com.example.mylist.infrastructure.UserMlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserMlServiceImpl implements UserMlService {

    @Autowired
    UserMlRepository userMlRepository;

    @Autowired
    UserMediaRepository userMediaRepository;

    @Override
    public Optional<UserMl> findById(Long id) {
        return userMlRepository.findById(id);
    }

    @Override
    public List<UserMl> findAll() {
        return userMlRepository.findAll();
    }

    @Override
    public UserMl addUser(UserMl userMl) throws UserMlException {
        if(userMlRepository.existsByLogin(userMl.getLogin())){
            throw new UserMlException("Login already exists in database.");
        }
        return userMlRepository.save(userMl);
    }

    @Override
    public Optional<UserMl> findByLogin(String login) {
        return userMlRepository.findByLogin(login);
    }

    @Override
    public void removeUSer(UserMl userMl) {
        //on doit d'abord verifier que cette utilisateur à des medias associées et les supprimer si besoin.
        List<UserMedia> userMediaList = userMediaRepository.findByUsermlId(userMl.getId());
        for ( UserMedia userMediaToDelete : userMediaList) {
            userMediaRepository.delete(userMediaToDelete);
        }
        userMlRepository.delete(userMl);
    }
}