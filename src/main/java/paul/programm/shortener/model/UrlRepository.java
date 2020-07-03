package paul.programm.shortener.model;

import org.springframework.data.repository.CrudRepository;

public interface UrlRepository extends CrudRepository<UrlSet, Long> {

    UrlSet findShortenedByOriginal(String original);

    UrlSet findOriginalByShortened(String shortened);
}
