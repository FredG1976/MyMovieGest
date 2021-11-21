package com.example.mylist;

import com.example.mylist.domain.usermylist.UserMl;
import com.example.mylist.infrastructure.UserMlRepository;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserMlRepositoryTest {



    @Autowired
    UserMlRepository userMlRepository;

    @Before
    public void initialize_tests_data() {
        /* Pour les tests chargement préalable des occurrences de UserMyList, Media à partir de import sql  */
        /* gestion de profils à mettre en place (developpement, exploitation) */
        /* sauvegarde dans répertoire partagé */
    }

    /* Test recherche par identifiant */
    @Test
    public void get_existing_userMl_should_be_successfull() {
        final Long existantId = 1L;
        final String fullName = "Xavier Duflot";
        Optional<UserMl> returnedUser = userMlRepository.findById(existantId);

        assertThat(returnedUser.isPresent()).isTrue();
        assertThat(returnedUser.get().getFullName()).isEqualTo(fullName);
    }

    /* Test non existence utilisateur provoquant une exception à compléter */
    /*    @Test(expected = RuntimeException.class) */
    @Test
    public void get_inexistant_userMl_with_id_should_return_nothing() {
        Long inexistantId = 1234567890L;
        Optional<UserMl> userMyList = userMlRepository.findById(inexistantId);

        assertThat(userMyList.isPresent()).isFalse();

//        assertAll(() -> Optional
//                .ofNullable(userMyListRepository.findById(inexistantId))
//                .orElseThrow(RuntimeException.class::new));

    }

    /* Test création utilisateur */
    @Test
    public void test_save_new_userMl_should_be_successfull() {
        final String fullName = "Alexandra Delalande";
        final Boolean isAdmin = true;
        final String login = "loginAlexandraDelalande";
        final String password = "passwordAlexandraDelalande";

        UserMl userMl = new UserMl();

        userMl.setFullName(fullName);
        userMl.setAdmin(isAdmin);
        userMl.setLogin(login);
        userMl.setPassword(password);

        UserMl returnedSavedUser = userMlRepository.save(userMl);

        System.out.println(returnedSavedUser);
        assertThat(returnedSavedUser).isNotNull();
        assertThat(returnedSavedUser.getFullName()).isEqualTo(fullName);
        assertThat(returnedSavedUser.getLogin()).isEqualTo(login);
        assertThat(returnedSavedUser.getPassword()).isEqualTo(password);
    }

    /* Le test se fait par rappport aux chargements initiaux (import.sql) */
    @Test
    public void test_findAll_UserMl_should_be_successfull() {
        List<UserMl> uList = userMlRepository.findAll();
        assertThat(uList.size()).isEqualTo(3);

    }

}
