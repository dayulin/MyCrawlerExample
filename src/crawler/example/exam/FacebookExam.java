package crawler.example.exam;

import com.github.abola.crawler.CrawlerPack;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 練習：取得任一篇或多篇文章的 Reactions 總數
 *
 *
 * 重點 1. 先利用Graph Api調整出需要的資料 2. 修改程式，使用爬蟲包取得資料 3. 上傳至GitHub
 * 
 * @author Abola Lee
 *
 */
public class FacebookExam {

	public static void main(String[] args) {
		// 遠端資料路徑
		String token = "EAACEdEose0cBAOTTY3bkKJ8S8pTBybXXJaeuf0SHpg0780zjoAZBRrBUcZACTaZAFZC3sDfCStZCthjzYHGZBYLZABKo9xjxTlW21voZBsZAsDN5im9685XCAc11mjUEtDCvlaNDloENzWfm9znbUmLC9cf8GuG2k4k8CHqZCqGULVapp4ZAmeAZAywjxovJ1IgXU9wZD";
		String uri = "https://graph.facebook.com/v2.6"
				+ "/kocpctw/posts?fields=id,link,message,created_time,likes.limit(0).summary(total_count),reactions.limit(0).summary(total_count)"
				+ "&access_token=" + token;

		Elements elems = CrawlerPack.start().getFromJson(uri).select("data");
		String output = "id,reactions,message";
		// 遂筆處理
		for (Element data : elems) {
			String id = data.select("id").text();

			// FIXIT
			String reactions = data.select("reactions").text();
			String message = data.select("message").text();
			output += id + "," + reactions + "," + message+"\n";
		}
		System.out.println(output);
	}
}
