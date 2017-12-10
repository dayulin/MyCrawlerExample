package crawler.example;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.github.abola.crawler.CrawlerPack;

/**
 * 資料探索練習 Facebook Graph Api Search
 * 
 * 重點 1. 利用Graph Api調整出需要的資料 2. 取得一組Access Token (試著使用 long term token) 3.
 * 試著用『excel』或任何最簡易的方式，對資料進行探索
 * 
 * @author Abola Lee
 *
 */
public class FacebookExample {

	public static void main(String[] args) {

		// 遠端資料路徑
		// [query sample]
		// search?fields=name,id,likes,talking_about_count&limit=1000&q=靠北&type=page
		// String uri =
		// "https://graph.facebook.com/v2.11"
		// +
		// "/search?q=%E9%9D%A0%E5%8C%97&type=page&limit=1000&fields=name,id,likes,talking_about_count"
		// +
		// "&access_token=EAACEdEose0cBAHnatFDWQUxXqxBTfkR0scfIIHRKiJpnra0x0H7thi7CtXbxQvoFF7F9iEVi02JcyOt8Nwld3BoZCBjddZBl3bCtQSxzgvtoTo9RORVOF6CkhCOKyY6EhANAEzPot6ALOZBiLAwai8dMhPrsHojAUOW8ZBZBoWYFhbZCt5q8ftgCydHrKkvXkZD";

		// String uri =
		// "https://graph.facebook.com/v2.5"
		// +
		// "/search?q=java&type=page&limit=1000&fields=name,id,likes,talking_about_count"
		// +
		// "&access_token=EAACEdEose0cBAHnatFDWQUxXqxBTfkR0scfIIHRKiJpnra0x0H7thi7CtXbxQvoFF7F9iEVi02JcyOt8Nwld3BoZCBjddZBl3bCtQSxzgvtoTo9RORVOF6CkhCOKyY6EhANAEzPot6ALOZBiLAwai8dMhPrsHojAUOW8ZBZBoWYFhbZCt5q8ftgCydHrKkvXkZD";
		String sQueryItem = "";
		try {
			sQueryItem = URLEncoder.encode("吃到飽", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String token = "EAACEdEose0cBAGe4DKk1YuFuhmuFjKIKn4yapu4EoFHGxYfixb3W0BWFmALRmcMJMnylcpAs3GmQiPkviYMFWYfwkzoI2QvnjfKzKzzeYrEcLV4bedQhJyEtRM4yk2ZAgtyMEUsPPJH7ZABG9alb2ZAqvpmJLNH3wW4lwbSHeMIN8ZBTiFn5t0bzZCZB7l3RsZD";
		// String uri = "https://graph.facebook.com/v2.5" + "/search?q=" + sQueryItem
		// + "&type=page&limit=1000&fields=name,id,likes,talking_about_count"
		// +
		// "&access_token=EAACEdEose0cBAHnatFDWQUxXqxBTfkR0scfIIHRKiJpnra0x0H7thi7CtXbxQvoFF7F9iEVi02JcyOt8Nwld3BoZCBjddZBl3bCtQSxzgvtoTo9RORVOF6CkhCOKyY6EhANAEzPot6ALOZBiLAwai8dMhPrsHojAUOW8ZBZBoWYFhbZCt5q8ftgCydHrKkvXkZD";
		// String uri = "https://graph.facebook.com/v2.5" + "/search?q=" + sQueryItem
		// + "&type=page&limit=1000&fields=name,id,likes,talking_about_count"
		// + "&access_token="+token;

		// Jsoup select 後回傳的是 Elements 物件
		// [data sample]
		// ----
		// {
		// "data": [
		// {
		// "name": "靠北工程師",
		// "id": "1632027893700148",
		// "likes": 174587,
		// "talking_about_count": 188119 //真正存活帳號
		// }
		// }

		// judgead?fields=id,about?fields=id,about
		// 基本-設定顯示欄位
		//
		// judgead?fields=id,name,about,category,likes,link,talking_about_count,website
		// 粉絲團基本資料
		//
		// String uri = "https://graph.facebook.com/v2.6"
		// +
		// "/judgead?fields=id,name,about,category,likes,link,talking_about_count,website&access_token="
		// + token;
		// judgead/insights/metric=page_fans_country&since=2017-01-01&until=2017-01-05
		// 粉絲人數-國家
		//
		// yahootwnews/feed?fields=message,likes.limit(1),created_time&until=1451779200&since=1451606400&limit=100
		// yahoo with limt
		//
		// yahootwnews/feed?fields=message,likes.limit(1).summary(true)
		// feed with likes summary
		//
		// search?q=靠北&type=page&limit=1000&fields=name,id,likes,talking_about_count,fan_count
		// 2.5->2.6 likes變 fa_count
		//
		// 109249609124014_1185877771461187?fields=reactions.type(LOVE).limit(0).summary(total_count).as(reactions_love),reactions.type(WOW).limit(0).summary(total_count).as(reactions_wow),reactions.type(HAHA).limit(0).summary(total_count).as(reactions_haha),reactions.type(SAD).limit(0).summary(total_count).as(reactions_sad),reactions.type(ANGRY).limit(0).summary(total_count).as(reactions_angry)
		// 取得所有reactions sample
		//
		// judgead/posts?fields=id,link,message,created_time,likes.limit(0).summary(total_count),reactions.limit(0).summary(total_count)
		// 文章(Posts)-統計數量
		//
		// crazyck101/feed?fields=id,link,message,created_time,likes.limit(1000)&since=1480849200&until=1480856400
		//
		// search?q=礁溪&type=page&locale=zh_tw
		// 查詢-頁面名稱
		String uri = "https://graph.facebook.com/v2.5" + "/search?q=" + sQueryItem
				+ "&type=place&center=25.0280078,121.5412632&distance=5000&access_token=" + token;
		// search?q=吃到飽&type=place&center=25.0280078,121.5412632&distance=5000
		// search?q=咖啡&type=place&center=25.033210,121.543632&distance=50
		// 查詢-地點
		//
		// search?type=place&center=25.033718,121.543426&distance=500&locale=zh_tw
		// 查詢功能-方圓500公尺的地點
		//
		// crazyck101/feed?fields=id,link,message,created_time,likes.limit(0).summary(true)&since=1480849200&until=1480856400
		// 狂新聞42集按讚數
		Elements elems = CrawlerPack.start().getFromJson(uri).select("data");

		String output = "id,名稱,按讚數,討論人數\n";

		// 遂筆處理
		for (Element data : elems) {
			String id = data.select("id").text();
			String name = data.select("name").text();
			String likes = data.select("likes").text();
			String talking_about_count = data.select("talking_about_count").text();

			output += id + ",\"" + name + "\"," + likes + "," + talking_about_count + "\n";
		}

		System.out.println(output);
	}
}
