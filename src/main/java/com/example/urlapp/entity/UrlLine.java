package com.example.urlapp.entity;

import jdk.jfr.DataAmount;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Objects;
import java.util.Random;

@Entity // This tells Hibernate to make a table out of this class
public class UrlLine {
    //говорит что поле id есть первичный ключ с автоинкрементом
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String urlFull;

    private String urlCrop;

    private Integer count;

    private Integer uniqueCount;

    public UrlLine() {
    }

    public UrlLine(String url, Integer count, Integer uniqueCount) {
        this.urlFull = url;
        this.urlCrop = urlToMiniUrl(url);
        this.count = count;
        this.uniqueCount = uniqueCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrlFull() {
        return urlFull;
    }

    public void setUrlFull(String name) {
        this.urlFull = name;
    }

    public String getUrlCrop() {
        return urlCrop;
    }

    public void setUrlCrop(String email) {
        this.urlCrop = email;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Integer getUniqueCount() {
        return uniqueCount;
    }

    public void setUniqueCount(int uniqueCount) {
        this.uniqueCount = uniqueCount;
    }

    //метод для укорачивания ссылки(примитивный алгоритм)
    private static String urlToMiniUrl(String url) {
        String crop = "";
        Random r = new Random();
        for (int i = 0; i < 4; i++) {
            char c = (char)(r.nextInt(26) + 'a');
            crop = crop + c;
        }
        return crop + r.nextInt(100000);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlLine urlLine = (UrlLine) o;
        return Objects.equals(urlFull, urlLine.urlFull) && Objects.equals(urlCrop, urlLine.urlCrop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urlFull, urlCrop);
    }
}
