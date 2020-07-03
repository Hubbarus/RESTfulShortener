package paul.programm.shortener.service;

import io.seruco.encoding.base62.Base62;
import org.apache.tomcat.util.http.parser.Host;
import org.springframework.stereotype.Service;
import paul.programm.shortener.model.UrlRepository;
import paul.programm.shortener.model.UrlSet;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class ShortenerImpl implements ShortenerInterface {

    private String hostName = "localhost:8080";
    private final Base62 base62 = Base62.createInstance();

    @Override
    public UrlSet makeItShort(UrlSet set) throws MalformedURLException {
        //Validating URL
        URL url = new URL(set.getOriginal());

        long count = Helper.getActualId();

        //Creating short URL
        byte[] arr = (count + "").getBytes();
        String tmp = new String(base62.encode(arr));

        String res = "http://" + hostName + "/" + tmp;
        set.setShortened(res);
        return set;
    }

    public String getHostName() {
        return hostName;
    }
}
