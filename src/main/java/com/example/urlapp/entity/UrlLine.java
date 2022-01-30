package com.example.urlapp.entity;

import org.springframework.data.jpa.repository.Modifying;

import javax.persistence.*;
import java.util.Objects;
import java.util.Random;

@Entity // This tells Hibernate to make a table out of this class
public class UrlLine {
    //говорит что поле id есть первичный ключ с автоинкрементом
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    // поле - полный URL
    private String urlFull;
    // поле - сгенерированный короткий URL
    private String urlCrop;
    // поле - счётчик неуникальных переходов по urlCrop
    private Integer count;
    // поле - счётчик уникальных переходов по urlCrop
    private Integer uniqueCount;

    public UrlLine() {
    }

    public UrlLine(String url) {
        this.urlFull = url;
        this.urlCrop = urlToMiniUrl();
        this.count = 0;
        this.uniqueCount = 0;
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

    //метод для укорачивания UrlFull
    // (примитивный алгоритм 4 буквы + случайное число до 10000)
    // в последствии следует заменить другим алгоритмом
    //или осуществлять проверку на потенциальные коллизии
    private static String urlToMiniUrl() {
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
