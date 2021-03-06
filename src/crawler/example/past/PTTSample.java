package crawler.example.past;

import com.github.abola.crawler.CrawlerPack;

import org.apache.commons.logging.impl.SimpleLog;
import org.jsoup.nodes.Document;
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
        // =============================================取主文======================================================//
        //如果遇到畫面有詢問問題者...多半就是在Cookie存值改變即可
        Elements jsoup = CrawlerPack.start().addCookie("over18", "1").getFromHtml(uri).select("#main-content");
        System.out.println(jsoup.select("span,div").remove().text().substring(11));
        System.out.println("========================================================================================");
        // =============================================取主文======================================================//
        System.out.println(CrawlerPack.start()
                        // 參數設定
                        .addCookie("over18", "1") // 設定cookie 要先用瀏覽器 檢察cookies 是不適
                        // .setRemoteEncoding("big5")// 設定遠端資料文件編碼
                        // 選擇資料格式 (三選一)
                        .getFromHtml(uri).select(".hl.push-tag:contains(推)+span,.hl.push-tag:contains(噓)+span")
                // .select(".hl.push-tag:contains(推)")
                // .select("#maib-context")
                // .getFromHtml(uri)
                // .getFromXml(uri)

                // 這兒開始是 Jsoup Document 物件操作
                // .select(".css .selector ")

        );


        Document jsoup1 = CrawlerPack.start()

                // 參數設定
                .addCookie("over18","1")	// 設定cookie

                // 選擇資料格式 (三選一)
                .getFromHtml(uri);

        // 這兒開始是 Jsoup Document 物件操作
        jsoup1.select("#main-content > span").remove();
        jsoup1.select("#main-content > div").remove();
    }
}
