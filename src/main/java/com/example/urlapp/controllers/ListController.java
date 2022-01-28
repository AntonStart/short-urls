package com.example.urlapp.controllers;

import com.example.urlapp.entity.UrlLine;
import com.example.urlapp.services.UrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

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
        //Осущевстляем поиск по id
        Optional<UrlLine> urlLine = urlService.findByUrlCrop(urlCrop);
        //Если такой id есть - redirect
        if (urlLine.isPresent()) resp.sendRedirect(urlLine.get().getUrlFull());
        //если id нет - Возвращаем ошибку клиенту - в базе нет такого адреса
        else resp.sendError(404, "This id is not register");
    }

    //обработка GET-запроса - вывод списка всех зарегистрированных URL
    @GetMapping("/")
    public Iterable<UrlLine> showAllUrls() {
        // формируем список всех сущностей из нашего urlRepo
        return urlService.findAll();
    }

    //обработка POST-запроса от клиента - регистрация нового URL
    //определён в list.mustache через action = "/"
    @PostMapping("/")
    public String addNewUrl(@RequestParam String urlFull, Map<String, Object> model) {
        //если ссылка отсутствует в бд
        if (!urlService.findByUrlFull(urlFull).isPresent()) {
            //создаём экземпляр нашей сущности
            UrlLine urlLine = new UrlLine(urlFull, 0, 0);
            //сохраняем его в urlRepo
            urlService.save(urlLine);
            // формируем список всех сущностей из нашего urlRepo
            Iterable<UrlLine> urlLines = urlService.findAll();
            // запихиваем в модель полученный список
            model.put("urlLines", urlLines);
            return "list";
        } else {
            // формируем список всех сущностей из нашего urlRepo
            Iterable<UrlLine> urlLines = urlService.findAll();
            // запихиваем в модель полученный список
            model.put("urlLines", urlLines);
            return "list";
        }
    }
    //обработка POST-запроса от клиента - фильтр
    //определён в list.mustache через action = "filter"
    @PostMapping("/filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        //задаём список
        Iterable<UrlLine> urlLines;
        //если параметр filter пришел не пустой и не null
        if (filter != null && !filter.isEmpty())
            //фильтруем с помощью метода определенного нами в репозитории
            urlLines = urlService.findByUrlFullContains(filter);
            //иначе выдаём полный список
        else urlLines = urlService.findAll();
        // запихиваем в модель полученный список
        model.put("urlLines", urlLines);
        return "list";
    }

    @PostMapping("/deleteId")
    public String removeById(@RequestParam Integer id, Map<String, Object> model) {
        LOG.info("Req: {}", id);
        urlService.deleteById(id);
        // формируем список всех сущностей из нашего urlRepo
        Iterable<UrlLine> urlLines = urlService.findAll();
        // запихиваем в модель полученный список
        model.put("urlLines", urlLines);
        return "list";
    }

    @PostMapping("/deleteUrl")
    public String removeByUrl(@RequestParam String url, Map<String, Object> model) {
        LOG.info("Req: {}", url);
        urlService.deleteByUrl(url);
        // формируем список всех сущностей из нашего urlRepo
        Iterable<UrlLine> urlLines = urlService.findAll();
        // запихиваем в модель полученный список
        model.put("urlLines", urlLines);
        return "list";
    }
}
