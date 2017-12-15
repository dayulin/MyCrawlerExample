package crawler.example;

import com.github.abola.crawler.CrawlerPack;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class newTest1 {
    final static String mongodbServer = "128.199.204.20"; // your host name
    final static String mongodbDB = "stock";

    static String stockNumber;

    // 每次取得最後50筆交易的內容
    static String uri_format = "https://news.cnyes.com/news/id/?";

    public static void main(String[] args) {
        String newsid="3989673";
        // 要有輸入參數
//		if ( args.length >= 1){
//			stockNumber = args[0];
//		}
//		else{
//			// 沒輸入參數
//			System.out.println("未輸入股號");
//
//			// 技巧：NullPointerException 才能正確中止 Jenkins 的 job
//			String forkException=null;
//			forkException.toString();
//		}

        getReortByNumber(newsid);
    }


    /**
     * 依輸入的股號取得資料
     * @param stockNumber
     */
    static void getReortByNumber(String newsid){

        // 遠端資料路徑
        String uri = String.format(uri_format, newsid);

        System.out.println("Call remote uri:" + uri);

        // 取得交易明細資料
        Elements transDetail =
                CrawlerPack.start()
                        .setRemoteEncoding("big5")// 設定遠端資料文件編碼
                        .getFromHtml(uri)
                        // 目標含有  成 交 明 細  的table
                        // <td align="center" width="240">2330 台積電 成 交 明 細</td>
                        .select("#itemprop") ;

        // 分解明細資料表格itemprop="articleBody"
        List<DBObject> parsedTransDetail = parseTransDetail(transDetail);
        System.out.println(parsedTransDetail);
        // 儲存明細
        //saveTransDetail(parsedTransDetail);
    }

    /**
     * 將明細資料表格，分解成 Mongodb物件的集合
     *
     * @param transDetail
     * @return
     */
    static List<DBObject> parseTransDetail(Elements transDetail){

        List<DBObject> result = new ArrayList<>();

        // 將以下分解出資料日期中的 105/03/25
        // <td width="180">資料日期：105/03/25</td>
        String day = transDetail
                .select("td:matchesOwn(資料日期)")
                .text().substring(5,14);

        // 取出 header 以外的所有交易資料
        for(Element detail: transDetail.select("td > table tr:gt(0)") ){

            Map<String, String> data = new HashMap<>();

/*			資料格式範例
  			<tr align="center" bgcolor="#ffffff" height="25">
			 <td>09:45:46</td>
			 <td class="low">158.00</td>
			 <td>158.50</td>
			 <td>158.50</td>
			 <td>－</td>
			 <td>1</td>
			</tr>
*/
            data.put("stock", stockNumber);
            data.put("day", day);

            data.put("time", detail.select("td:eq(0)").text());
            data.put("buy", detail.select("td:eq(1)").text());
            data.put("sell", detail.select("td:eq(2)").text());
            data.put("strike", detail.select("td:eq(3)").text());
            data.put("volume", detail.select("td:eq(5)").text());

            result.add( new BasicDBObject(data) );
        }
        return result;
    }

    /**
     * 將分解完的明細資料全部存回mongodb
     *
     * @param parsedTransDetail
     */
    static void saveTransDetail(List<DBObject> parsedTransDetail){

        MongoClient mongoClient ;
        try {
            mongoClient = new MongoClient( mongodbServer );

            DB db = mongoClient.getDB( mongodbDB );

            db.getCollection("TransDetail").insert(parsedTransDetail);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
