package com.example.favoritepictures;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<Picture,Integer> {
    Optional<Picture> findPictureById(Integer id);
    Iterable<Picture> findByUsername(String username);
}