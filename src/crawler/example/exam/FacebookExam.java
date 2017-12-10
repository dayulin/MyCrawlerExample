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
			data.select("reactions").text();
			// FIXIT
			
			String reactions = data.select("reactions").text();
			String message = data.select("message").text();
			//=================love=====================================//
			String love="";
			
			String uri1 = "https://graph.facebook.com/v2.6"
					+ "/"+id
					+"?fields=reactions.type(LOVE).limit(0).summary(total_count).as(reactions_love)"
									+",reactions.type(WOW).limit(0).summary(total_count).as(reactions_wow)"
									+",reactions.type(HAHA).limit(0).summary(total_count).as(reactions_haha)"
									+",reactions.type(SAD).limit(0).summary(total_count).as(reactions_sad)"
									+",reactions.type(ANGRY).limit(0).summary(total_count).as(reactions_angry)"
									+ "&access_token=" + token;
			Elements elemss = CrawlerPack.start().getFromJson(uri1).select("summary");
			for (Element dataDetail : elemss) {
				love=dataDetail.select("total_count").text();
			}
			reactions = data.select("reactions_love summary totalcount").text();
			//======================================================//
			output += id + "," + reactions + "," + message+ "," + love  + "\n";
		}
		System.out.println(output);
	}
}
