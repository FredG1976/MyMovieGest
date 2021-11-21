package com.example.mylist.infrastructure;

import com.example.mylist.domain.usermylist.UserMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserMediaRepository extends JpaRepository<UserMedia, Long> {

    List<UserMedia> findByUsermlId(Long id);

    List<UserMedia> findByMediaId(Long id);

    List<UserMedia> findAllByUsermlId(Long id);

    Optional<UserMedia> findByUsermlIdAndMediaId(Long id_user, Long id_media);


}
