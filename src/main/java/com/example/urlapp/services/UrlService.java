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

    //поиск сущности из таблицы по id
    Optional<UrlLine> findById(Integer id){
        return urlRepo.findById(id);
    }
    //поиск сущности из таблицы по короткой ссылке
    public Optional<UrlLine> findByUrlCrop(String urlCrop){
        return urlRepo.findByUrlCrop(urlCrop);
    }
    //поиск сущности из таблицы по длинной ссылке
    public Optional<UrlLine> findByUrlFull(String urlFull){
        return urlRepo.findByUrlFull(urlFull);
    }
    //поиск по частичному совпадению
    public List<UrlLine> findByUrlFullContains(String pieceOfUrl){
        return urlRepo.findByUrlFullContains(pieceOfUrl);
    }
    //удаление сущности из таблицы по id
    public Boolean deleteById(Integer id) {
        if (urlRepo.findById(id).isPresent()) {
            urlRepo.deleteById(id);
            return true;
        } else return false;
    }
    //список со всем содержимым таблицы
    public Iterable<UrlLine> findAll() {
        return urlRepo.findAll();
    }
    //сохранение/обновление сущности
    public UrlLine save(UrlLine urlLine) {
        return urlRepo.save(urlLine);
    }

}
