package com.example.urlapp.services;

import com.example.urlapp.entity.UrlLine;
import com.example.urlapp.repos.UrlRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UrlService {

    @Autowired
    private final UrlRepo urlRepo;

    public UrlService(UrlRepo urlRepo) {
        this.urlRepo = urlRepo;
    }

    //методы
    //строятся на основании Table 2.3. Supported keywords inside method names
    // из https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
    //метод поиска по id
    Optional<UrlLine> findById(Integer id){
        return urlRepo.findById(id);
    }
    //метод поиска по короткой ссылке
    public Optional<UrlLine> findByUrlCrop(String urlCrop){
        return urlRepo.findByUrlCrop(urlCrop);
    }
    //метод поиска по длинной ссылке
    public Optional<UrlLine> findByUrlFull(String urlFull){
        return urlRepo.findByUrlFull(urlFull);
    }
    //метод поиска по частичному совпадению
    public List<UrlLine> findByUrlFullContains(String pieceOfUrl){
        return urlRepo.findByUrlFullContains(pieceOfUrl);
    }
    //метод удаления по id
    public Boolean deleteById(Integer id) {
        if (urlRepo.findById(id).isPresent()) {
            urlRepo.deleteById(id);
            return true;
        } else return false;
    }

    public Iterable<UrlLine> findAll() {
        return urlRepo.findAll();
    }

    public UrlLine save(UrlLine urlLine) {
        return urlRepo.save(urlLine);
    }

    /*public Boolean deleteByUrl(String url) {
        List<UrlLine> urlLines = urlRepo.findByUrlFullContains(url);
        if (!urlLines.isEmpty()) {
            urlRepo.deleteById(urlLines.get(0).getId());
            return true;
        }
        else return false;
    }*/

}
