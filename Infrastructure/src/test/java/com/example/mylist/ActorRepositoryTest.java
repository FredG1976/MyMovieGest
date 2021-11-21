package com.example.mylist;

import com.example.mylist.domain.media.Actor;
import com.example.mylist.infrastructure.ActorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ActorRepositoryTest {
    @Autowired
    ActorRepository actorRepository;

    private Actor actor;

    @Before
    public void setUp(){
        final String actorName = "Harisson Ford";
        if(!actorRepository.existsByName(actorName)){
            actor = new Actor();
            actor.setName("Harisson Ford");
            actorRepository.save(actor);
        }
    }

    @Test
    public void actor_should_be_found_by_his_name_must_return_an_actor_success() {
        // Given
        final String actorName = "Harisson Ford";

        // When
        final Optional<Actor> actorReturned = actorRepository.findByName(actorName);

        // Then
        assertThat(actorReturned).isNotNull();
        assertThat(actorReturned.get().getId()).isGreaterThanOrEqualTo(Long.valueOf(1));
    }

    @Test
    public void actor_should_be_found_by_his_name_must_return_empty() {
        // Given
        final String actorName = "Luc Skywalker";

        // When
        final Optional<Actor> actorReturned = actorRepository.findByName(actorName);

        // Then
        assertThat(actorReturned).isEmpty();
    }

    @Test
    public void test_if_actor_exists_search_by_name_must_return_an_boolean_true() {
        // Given
        final String actorName = "Harisson Ford";

        // When
        final boolean actorReturned = actorRepository.existsByName(actorName);

        // Then
        assertThat(actorReturned).isNotNull();
        assertThat(actorReturned).isTrue();
    }

    @Test
    public void test_if_actor_exists_search_by_name_must_return_an_boolean_false() {
        // Given
        final String actorName = "Luc Skywalker";

        // When
        final boolean actorReturned = actorRepository.existsByName(actorName);

        // Then
        assertThat(actorReturned).isFalse();
    }
}
