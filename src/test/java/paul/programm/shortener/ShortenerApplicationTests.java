package paul.programm.shortener;

import io.seruco.encoding.base62.Base62;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import paul.programm.shortener.model.UrlRepository;
import paul.programm.shortener.model.UrlSet;
import paul.programm.shortener.service.Helper;
import paul.programm.shortener.service.ShortenerImpl;

import java.net.MalformedURLException;
import java.sql.Timestamp;

@SpringBootTest
class ShortenerApplicationTests {

	@Autowired
	UrlRepository repository;
	Base62 base62 = Base62.createInstance();

	@Test
	void makeItShortTest() throws MalformedURLException {
		UrlSet set = new UrlSet();
		set.setOriginal("http://yandex.ru");

		UrlSet set1 = new UrlSet();
		set1.setOriginal("not an URL");

		UrlSet set2 = new UrlSet();
		set2.setOriginal("https://www.google.com/search?sxsrf=ALeKk00rJyuImHrod5YxQA1YphHBHG_QKQ%3A1593775221617&source=hp&ei=dRT_XqO1I5CKrwSR4bq4AQ&q=dggsdg+dsg+sdg&oq=dggsdg+dsg+sdg&gs_lcp=CgZwc3ktYWIQAzIHCCEQChCgATIHCCEQChCgAToECAAQClD2EFixGGCSHWgAcAB4AIAB0wKIAbQIkgEHMC40LjAuMZgBAKABAaoBB2d3cy13aXo&sclient=psy-ab&ved=0ahUKEwjjw7yv-7DqAhUQxYsKHZGwDhcQ4dUDCAc&uact=5");

		ShortenerImpl shortener = new ShortenerImpl();
		long count = Helper.getActualId();
		String s = new String(base62.encode((count + "").getBytes()));

		UrlSet res = shortener.makeItShort(set);
		UrlSet res2 = shortener.makeItShort(set2);

		Assert.assertEquals("http://" + shortener.getHostName() + "/" + s, res.getShortened());
		Assert.assertEquals("http://" + shortener.getHostName() + "/" + s, res2.getShortened());

		try {
			UrlSet res1 = shortener.makeItShort(set1);
		} catch (Exception e) {
			Assert.assertEquals(MalformedURLException.class, e.getClass());
		}
	}

	@Test
	void getActualIdTest() {
		long count = 0;
		if (repository.count() != 0) {
			Iterable<UrlSet> iter = repository.findAll();
			for (UrlSet set : iter) {
				if (count < set.getId()) {
					count = set.getId();
				}
			}
		}
		count++;

		Assert.assertEquals(count, Helper.getActualId());
	}

	@Test
	void isAvailableTest() {
		long now1 = System.currentTimeMillis();
		UrlSet set = new UrlSet();
		set.setTimestamp(new Timestamp(now1));

		Assert.assertTrue(Helper.isAvailable(set));

		long now2 = System.currentTimeMillis();
		UrlSet set1 = new UrlSet();
		set1.setTimestamp(new Timestamp(now2 - 60 * 10 * 1000));

		Assert.assertTrue(Helper.isAvailable(set1));

		long now3 = System.currentTimeMillis();
		UrlSet set2 = new UrlSet();
		set2.setTimestamp(new Timestamp(now3 - 60 * 10 * 1001));

		Assert.assertFalse(Helper.isAvailable(set2));
	}
}
