package paul.programm.shortener.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paul.programm.shortener.model.UrlRepository;
import paul.programm.shortener.model.UrlSet;
import paul.programm.shortener.service.Helper;
import paul.programm.shortener.service.ShortenerImpl;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Timestamp;

@RestController
public class MainController {

    private final ShortenerImpl shortener;
    private UrlRepository urlRepository;
    private long lastId = 0;

    @Autowired
    public MainController(ShortenerImpl shortener, UrlRepository urlRepository) {
        this.shortener = shortener;
        this.urlRepository = urlRepository;
    }

    @PostMapping(value = "/")
    @ApiOperation(value = "Makes short URL",
    notes = "Returns JSON object with short URL, original URL, id in Database and creation timestamp",
    response = UrlSet.class)
    public ResponseEntity<?> shortIt(@RequestBody UrlSet set) {

        if (set.getOriginal() == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (urlRepository.findShortenedByOriginal(set.getOriginal()) != null) {
            return new ResponseEntity<>(urlRepository.findShortenedByOriginal(set.getOriginal()), HttpStatus.OK);
        }

        try {
            UrlSet res = shortener.makeItShort(set);
            urlRepository.save(res);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (MalformedURLException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/")
    @ApiOperation(value = "Returns original URL",
            notes = "Returns JSON object by short URL",
            response = UrlSet.class)
    public ResponseEntity<?> returnOriginal(@RequestBody UrlSet set) {
        if (set.getShortened() == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (urlRepository.findOriginalByShortened(set.getShortened()) != null) {
            return new ResponseEntity<>(urlRepository.findOriginalByShortened(set.getShortened()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //redirecting
    @GetMapping(value = "/{str}")
    @ApiOperation(value = "Redirects to original URL",
            notes = "When click on short URL redirects to original URL")
    public ResponseEntity<?> goToOrig(@PathVariable(name = "str") String str, HttpServletResponse response) {
        Iterable<UrlSet> list = urlRepository.findAll();

        for (UrlSet tmp : list) {
            if (Helper.isAvailable(tmp)) {
                if (tmp.getShortened().endsWith("/" + str)) {
                    try {
                        response.sendRedirect(tmp.getOriginal());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                urlRepository.deleteById(tmp.getId());
                return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
