package paul.programm.shortener.service;

import paul.programm.shortener.model.UrlRepository;
import paul.programm.shortener.model.UrlSet;

import java.net.MalformedURLException;

public interface ShortenerInterface {

    UrlSet makeItShort(UrlSet set) throws MalformedURLException;
}
