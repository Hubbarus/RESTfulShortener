package paul.programm.shortener.service;

import io.seruco.encoding.base62.Base62;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import paul.programm.shortener.model.UrlSet;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class ShortenerImpl implements ShortenerInterface {

    @Value("${server.address}")
    private String hostName;
    @Value("${server.port}")
    private String port;
    private final Base62 base62 = Base62.createInstance();

    @Override
    public UrlSet makeItShort(UrlSet set) throws MalformedURLException {
        //Validating URL
        URL url = new URL(set.getOriginal());

        long count = Helper.getActualId();

        //Creating short URL
        byte[] arr = (count + "").getBytes();
        String tmp = new String(base62.encode(arr));

        String res = "http://" + hostName + ":" + port + "/" + tmp;
        set.setShortened(res);
        return set;
    }

    public String getHostName() {
        return hostName;
    }
}
