package crawler.example.past;

import com.github.abola.crawler.CrawlerPack;

import org.apache.commons.logging.impl.SimpleLog;
import org.jsoup.select.Elements;

public class PTTSample {
	// commit test test
	public static void main(String[] args) {

		// set to debug level
		// CrawlerPack.setLoggerLevel(SimpleLog.LOG_LEVEL_DEBUG);

		// turn off logging
		CrawlerPack.setLoggerLevel(SimpleLog.LOG_LEVEL_OFF);

		// 遠端資料路徑
		String uri = "https://www.ptt.cc/bbs/marriage/M.1512832927.A.535.html";
        Elements jj = CrawlerPack.start().addCookie("over18", "1").getFromHtml(uri).select("#main-content");
        System.out.println(jj.select("span,div").remove().text());

		System.out.println(CrawlerPack.start()

				// 參數設定
				.addCookie("over18", "1") // 設定cookie 要先用瀏覽器 檢察cookies 是不適
				// .setRemoteEncoding("big5")// 設定遠端資料文件編碼

				// 選擇資料格式 (三選一)
				.getFromHtml(uri)
				 .select(".hl.push-tag:contains(推)+span")
				// .select(".hl.push-tag:contains(推)")
//				.select("#maib-context")
//		 .getFromHtml(uri)
//		 .getFromXml(uri)

//		 這兒開始是 Jsoup Document 物件操作
//		 .select(".css .selector ")

		);
	}
}
