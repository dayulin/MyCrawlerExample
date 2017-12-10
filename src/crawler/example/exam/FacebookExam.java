package crawler.example.exam;

import com.github.abola.crawler.CrawlerPack;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 練習：取得任一篇或多篇文章的 Reactions 總數
 * <p>
 * <p>
 * 重點 1. 先利用Graph Api調整出需要的資料 2. 修改程式，使用爬蟲包取得資料 3. 上傳至GitHub
 *
 * @author Abola Lee
 */
public class FacebookExam {

    public static void main(String[] args) {
        // 遠端資料路徑
        String token = "EAACEdEose0cBAGe4DKk1YuFuhmuFjKIKn4yapu4EoFHGxYfixb3W0BWFmALRmcMJMnylcpAs3GmQiPkviYMFWYfwkzoI2QvnjfKzKzzeYrEcLV4bedQhJyEtRM4yk2ZAgtyMEUsPPJH7ZABG9alb2ZAqvpmJLNH3wW4lwbSHeMIN8ZBTiFn5t0bzZCZB7l3RsZD";
        String uri = "https://graph.facebook.com/v2.6"
                + "/judgead/posts?fields=id,link,message,created_time,likes.limit(0).summary(total_count),reactions.limit(0).summary(total_count)"
                + "&access_token=" + token;

        Elements elems = CrawlerPack.start().getFromJson(uri).select("data");
        String output = "id,reactions,message";
        // 遂筆處理
        for (Element data : elems) {
            String id = data.select("id").text();

            // FIXIT

            String reactions = data.select("reactions").text();
            String message = data.select("message").text();
            //=================love=====================================//
            String love = "";
            String WOW = "";
            String HAHA = "";
            String SAD = "";
            String ANGRY = "";
            String uri1 = "https://graph.facebook.com/v2.6"
                    + "/" + id
                    + "?fields=reactions.type(LOVE).limit(0).summary(total_count).as(reactions_love)"
                    + ",reactions.type(WOW).limit(0).summary(total_count).as(reactions_wow)"
                    + ",reactions.type(HAHA).limit(0).summary(total_count).as(reactions_haha)"
                    + ",reactions.type(SAD).limit(0).summary(total_count).as(reactions_sad)"
                    + ",reactions.type(ANGRY).limit(0).summary(total_count).as(reactions_angry)"
                    + "&access_token=" + token;

            //            System.out.println(uri1);
            love = CrawlerPack.start().getFromJson(uri1).select("reactions_love summary total_count").text();
            WOW = CrawlerPack.start().getFromJson(uri1).select("reactions_wow summary total_count").text();
            HAHA = CrawlerPack.start().getFromJson(uri1).select("reactions_haha summary total_count").text();
            SAD = CrawlerPack.start().getFromJson(uri1).select("reactions_sad summary total_count").text();
            ANGRY = CrawlerPack.start().getFromJson(uri1).select("reactions_angry summary total_count").text();

            reactions = data.select("reactions summary total_count").text();
            //======================================================//
            output += "COTENT_ID："+id  +
                    ",LOVE:" + love+                //代表愛心圖案表情符號數
                    ",WOW:" + WOW +                 //代表wow圖案表情符號數
                    ",HAHA:" + HAHA +               //代表ㄏㄏ圖案表情符號數
                    ",SAD:" + SAD +                 //代表傷心圖案表情符號數
                    ",ANGRY:" + ANGRY +             //代表生氣圖案表情符號數
                    ",reactions:" + reactions+
                    ",message:" + message + "\n";
        }
        System.out.println(output);
    }
}
