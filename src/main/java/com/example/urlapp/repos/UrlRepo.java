package com.example.urlapp.repos;

// This will be AUTO IMPLEMENTED by Spring into a Bean called urlRepo
// CRUD refers Create, Read, Update, Delete
import com.example.urlapp.entity.UrlLine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UrlRepo extends CrudRepository<UrlLine, Integer> {
    //методы CRUD для текущего репозитория строятся на основе CrudRepository
    //Table 2.3. Supported keywords inside method names
    // из https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/jpa.repositories.html#jpa.query-methods.query-creation

    //метод поиска по id
    Optional<UrlLine> findById(Integer id);

    //метод поиска по короткой ссылке
    Optional<UrlLine> findByUrlCrop(String urlCrop);

    //метод поиска по длинной ссылке
    Optional<UrlLine> findByUrlFull(String urlFull);

    //метод поиска по частичному совпадению
    List<UrlLine> findByUrlFullContains(String pieceOfUrl);
    //метод удаления по id
    @Override
    void deleteById(Integer integer);
}
