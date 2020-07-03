package paul.programm.shortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import paul.programm.shortener.model.UrlRepository;
import paul.programm.shortener.model.UrlSet;

import java.sql.Timestamp;

@Service
public class Helper {

    private static UrlRepository repository;

    @Autowired
    public Helper(UrlRepository repository) {
        this.repository = repository;
    }

    public static boolean isAvailable(UrlSet urlSet) {
        long now = new Timestamp(System.currentTimeMillis()).getTime();
        long current = urlSet.getTimestamp().getTime();
        long delta = 10 * 60 * 1000;

        if (now - current > delta) {
            return false;
        }

        return true;
    }

    public static long getActualId() {
        long count = 0;
        if (repository.count() != 0) {
            Iterable<UrlSet> list = repository.findAll();
            for (UrlSet tmp : list) {
                if (tmp.getId() > count) {
                    count = tmp.getId();
                }
            }
        }
        return ++count;
    }
}
