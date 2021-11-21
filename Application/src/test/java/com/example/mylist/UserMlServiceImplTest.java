package com.example.mylist;

import com.example.mylist.MyListException.UserMlException;
import com.example.mylist.domain.usermylist.UserMl;
import com.example.mylist.infrastructure.UserMlRepository;
import com.example.mylist.user.UserMlService;
import com.example.mylist.user.UserMlServiceImpl;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class UserMlServiceImplTest {
    UserMl userMl1 = new UserMl();
    UserMl userMl2 = new UserMl();
    List<UserMl> userMlList = new ArrayList<UserMl>();

    @Mock
    UserMlRepository userMlRepositoryMock;

    @InjectMocks
    UserMlService userMlServiceMock = new UserMlServiceImpl();

    @Before
    public void SetUp() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        userMl1.setFullName("Utilisateur lambda 1");
        userMl1.setLogin("LoginUtilisateur1");
        userMl1.setPassword(passwordEncoder.encode("pass1"));
        userMl1.setId(555L);

        userMl2.setFullName("Utilisateur lambda 2");
        userMl2.setLogin("LoginUtilisateur2");
        userMl2.setPassword(passwordEncoder.encode("pass2"));
        userMl2.setId(888L);

        userMlList.add(userMl1);
        userMlList.add(userMl2);
    }

    @Test
    public void test_findAll_UserMl()  {
        //When
        Mockito.when(userMlRepositoryMock.findAll()).thenReturn(userMlList);
        List<UserMl> retrievedUserMlList = userMlServiceMock.findAll();

        //Then
        assertThat(retrievedUserMlList.size()).isEqualTo(2);
        assertThat((retrievedUserMlList.get(0).getFullName())).isEqualTo(userMl1.getFullName());
        assertThat((retrievedUserMlList.get(1).getLogin())).isEqualTo(userMl2.getLogin());
        verify(userMlRepositoryMock, times(1)).findAll();
    }

    @Test
    public void test_findUserMl_By_Id() {
        //When
        Mockito.when(userMlRepositoryMock.findById(userMl2.getId())).thenReturn(Optional.of(userMl2));

        Optional<UserMl> userMlOptional = userMlServiceMock.findById(userMl2.getId());
        UserMl retrievedUserMl = userMlOptional.get();

        //Then
//        assertThat(retrievedUserMl.getFullName()).isEqualTo(userMl1.getFullName());
        assertThat(retrievedUserMl.getFullName()).isEqualTo(userMl2.getFullName());
    }

    @Test
    public void test_add_new_UserMl() throws UserMlException {
        //When
        Mockito.when(userMlRepositoryMock.save(userMl1)).thenReturn(userMl1);
        UserMl savedUserMl = userMlServiceMock.addUser(userMl1);
        //Then
        assertThat(savedUserMl.getFullName()).isEqualTo(userMl1.getFullName());
    }

    @Test(expected = UserMlException.class)
    public void test_add_existing_UserMl() throws UserMlException {
        //GIVEN
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserMl user = new UserMl();
        user.setId(555L);
        user.setFullName("Utilisateur lambda 1");
        user.setLogin("LoginUtilisateur1");
        user.setPassword(passwordEncoder.encode("pass1"));

        //When
        Mockito.when(userMlRepositoryMock.existsByLogin(user.getLogin())).thenReturn(true);
        UserMl savedUserMl = userMlServiceMock.addUser(user);

        //Then
    }
}
