package com.example.urlapp.controllers;

import com.example.urlapp.entity.UrlLine;
import com.example.urlapp.services.UrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping
public class ListController {

    private static final Logger LOG = LoggerFactory.getLogger(ListController.class);

    private final UrlService urlService;

    @Autowired
    public ListController(UrlService urlService) {
        this.urlService = urlService;
    }

    //обработка GET-запроса - redirect to full url
    @GetMapping("/{urlCrop}")
    public void redirectToFullUrl(
            @PathVariable String urlCrop,
            HttpServletResponse resp
    ) throws IOException {
        LOG.info("Req: {}", urlCrop);
        //Осущевстляем поиск по urlCrop(короткая ссылка)
        Optional<UrlLine> urlLine = urlService.findByUrlCrop(urlCrop);
        //Если такой urlCrop есть
        if (urlLine.isPresent()) {
            //увеличиваем счётчик неуникальных redirect'ов
            final int count = urlLine.get().getCount() + 1;
            urlLine.get().setCount(count);
            urlService.save(urlLine.get());
            // перенаправляем на urlFull(длинная зарегестрированная ссылка)
            resp.sendRedirect(urlLine.get().getUrlFull());
        }
            //если id нет в нашей б.д. Возвращаем ошибку клиенту - в базе нет такого адреса
        else {
            //Возвращаем ошибку клиенту - в базе нет такого адреса
            resp.sendError(404, "This id is not register");
        }
    }

    //обработка GET-запроса - вывод списка всех зарегистрированных URL
    @GetMapping("/")
    public Iterable<UrlLine> showAllUrls() {
        // формируем список всех сущностей из нашего urlRepo
        return urlService.findAll();
    }
    //POST - регистрация/добавление в б.д. новый urlLine
    @PostMapping("/")
    public UrlLine addNewUrlJson(@RequestBody UrlLine model) {
        // создаём экземпляр UrlLine на основе
        Optional<UrlLine> existingLine = urlService.findByUrlFull(model.getUrlFull());
        if (existingLine.isPresent()) {
            return existingLine.get();
        } else {
            return urlService.save(new UrlLine(model.getUrlFull()));
        }
    }

    //обработка GET-запроса от клиента - поиск
    //
    @GetMapping("/search")
    public Iterable<UrlLine> filter(@RequestParam String filter) {
        if (filter != null && !filter.isEmpty()) {
            return urlService.findByUrlFullContains(filter);
        } else {
            return urlService.findAll();
        }
    }

    @DeleteMapping("/{id}")
    public boolean removeById(@PathVariable Integer id) {
        LOG.info("Req: {}", id);
        return urlService.deleteById(id);
    }

}