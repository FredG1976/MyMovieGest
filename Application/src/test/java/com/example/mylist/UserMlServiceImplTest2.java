package com.example.mylist;

import com.example.mylist.MyListException.UserMlException;
import com.example.mylist.domain.usermylist.UserMl;
import com.example.mylist.infrastructure.UserMlRepository;
import com.example.mylist.user.UserMlService;
import com.example.mylist.user.UserMlServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { UserMlServiceImpl.class })
public class UserMlServiceImplTest2 {
    @Autowired
    private UserMlService userMlService;

    @Autowired
    private PasswordEncoder passwordEncoderMock;

    @MockBean
    private UserMlRepository userMlRepositoryMock;

    UserMl userMl;

    @Before
    public void setUP(){
        System.out.println("BeforeEach");
    }

    @Test
    public void test_1() throws UserMlException {
        System.out.println("test_1");
    }

    @Test
    public void test_2() throws UserMlException {
        System.out.println("test_2");
    }

    @Test
    public void addUserMl() throws UserMlException {

        userMl.setPassword("Usermlpassword");
        userMl.setLogin("Usermllogin");
        userMl.setFullName("UserFullName");
        userMl.setAdmin(false);
        Mockito.when(userMlRepositoryMock.save(userMl)).thenReturn(userMl);

        userMlService.addUser(userMl);
    }

    @Bean
    public PasswordEncoder passwordEncoderMock()
    {
        return new BCryptPasswordEncoder();
    }


}
