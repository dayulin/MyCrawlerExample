package crawler.example;

import com.github.abola.crawler.CrawlerPack;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class newTest1 {
    final static String mongodbServer = "128.199.204.20"; // your host name
    final static String mongodbDB = "stock";

    static String stockNumber;

    // 每次取得最後50筆交易的內容
    static String uri_format = "https://news.cnyes.com/news/id/%s";
    static String uri_format2 = "view-source:https://udn.com/news/story/7238/%s?from=udn-catebreaknews_ch2";
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

//        getReortByNumber(newsid);
        String lawtype="使用牌照稅法_一Ｏ三年版";
        String newsid2="70404";
        for(int i=85284;i<=85488;i++) {
            getReortByNumber2(lawtype,String.valueOf(i));
        }
    }


    /**
     * 依輸入的股號取得資料
     * @param stockNumber
     */
    static void getReortByNumber(String newsid){

        // 遠端資料路徑
       String uri = String.format(uri_format, newsid);
       // String uri ="https://news.cnyes.com/news/id/3989673";
        System.out.println("Call remote uri:" + uri);

        // 取得交易明細資料
        Elements transDetail =
                CrawlerPack.start()
                        .getFromHtml(uri)
                        .select("article:eq(0)").select("div:eq(1)").select("p");
//                        .select("article:eq(0) + time:eq(1)");
                        // 目標含有  成 交 明 細  的table
                        // <td align="center" width="240">2330 台積電 成 交 明 細</td>
//                        .select("#itemprop") ;

        // 分解明細資料表格itemprop="articleBody"
        System.out.println("-----------------------------------------------------");
        System.out.println(transDetail);
//        List<DBObject> parsedTransDetail = parseTransDetail(transDetail);
//        System.out.println(parsedTransDetail);

        // 儲存明細
        //saveTransDetail(parsedTransDetail);
    }

    static void getReortByNumber2  (String filename,String newsid){

        // 遠端資料路徑
       //String uri = String.format(uri_format2, newsid);
//        String uri ="https://udn.com/news/story/7238/2877211?from=udn-catebreaknews_ch2";
       // String uri ="https://udn.com/news/story/7251/2877117?from=udn-catebreaknews_ch2";
        String uri ="http://www.ttc.gov.tw/ct.asp?xItem="+newsid+"&CtNode=97&CtUnit=40&BaseDSD=31";
        //System.out.println("Call remote uri:" + uri);

        // 取得交易明細資料
        Elements transDetail =
                CrawlerPack.start().setRemoteEncoding("big5")
                        .getFromHtml(uri)
                        .select("#ListTable2");
//                        .select("article:eq(0) + time:eq(1)");
                        // 目標含有  成 交 明 細  的table
                        // <td align="center" width="240">2330 台積電 成 交 明 細</td>
//                        .select("#itemprop") ;

        // 分解明細資料表格itemprop="articleBody"
        //System.out.println("======================================================");
        System.out.println( newsid);
       // System.out.println( transDetail.select("p").toString().replace("<p>","").replace("</p>",""));
       // System.out.println("======================================================");
        //System.out.println(transDetail.select(".story_bady_info_author").select("span").toString().replace("<span>","").replace("</span>",""));
        String parsedTransDetails = parseTransDetails(transDetail);
//        if(!exists("r:\\"+filename+".csv"))
//            createNewFile("r:\\"+filename+".csv");

        try {

            FileWriter writer = new FileWriter("r:\\"+filename+".csv", true);
            String str = parsedTransDetails;
            writer.write(str);
            writer.write("\n");
            writer.close();
//            BufferedWriter bufWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("d:\\"+filename+".csv",true),"big5"));
//            bufWriter.write(parsedTransDetails);
//            bufWriter.write("\n");
//            bufWriter.close();



        }
        catch(IOException ioe){
            System.out.print(ioe);
        }

        // 儲存明細
        //saveTransDetail(parsedTransDetail);
    }

    public static boolean exists(String path){
        return new File(path).exists();
    }

    /**
     * 建立新檔(檔案已存在會刪除舊檔並建新檔)
     * @param path
     */
    public static void createNewFile(String path){
        try{
            File file = new File(path);
            file.createNewFile();
        }catch(IOException e){
            e.printStackTrace();
        }
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
//        String day = transDetail
//                .select("td:matchesOwn(資料日期)")
//                .text().substring(5,14);

        // 取出 header 以外的所有交易資料
        for(Element detail: transDetail.select("table tr:gt(0)") ){

            Map<String, String> data = new HashMap<>();
            //System.out.println(detail);
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
//            data.put("day", day);
            //String(e5.getBytes("big5"),"utf-8")

            data.put("11", detail.select("TH:eq(0)").text());
            data.put("22", detail.select("TD:eq(1)").text());


            result.add( new BasicDBObject(data) );
        }
        return result;
    }

    static String parseTransDetails(Elements transDetail){

        String result="";
        for(Element detail: transDetail.select("table tr:gt(0)") ){

            Map<String, String> data = new HashMap<>();
            //System.out.println(detail);
            result+= detail.select("TH:eq(0)").text();
            result+=",";
            result+=  detail.select("TD:eq(1)").text();
            result+=",";
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

//
//<article class="_1mD theme-article" data-reactid="255">
//<figure class="_mIq" itemprop="image" itemscope itemtype="https://schema.org/ImageObject" data-reactid="256">
//<meta itemprop="url" content="https://cimg.cnyes.cool/prod/news/3989673/m/244477dbcd3a142b1662993cd9d922c6.jpg" data-reactid="257">
//<meta itemprop="width" content="414" data-reactid="258">
//<meta itemprop="height" content="233" data-reactid="259">
//<img src="https://cimg.cnyes.cool/prod/news/3989673/m/244477dbcd3a142b1662993cd9d922c6.jpg" srcset="https://cimg.cnyes.cool/prod/news/3989673/m/244477dbcd3a142b1662993cd9d922c6.jpg 1x,https://cimg.cnyes.cool/prod/news/3989673/m/244477dbcd3a142b1662993cd9d922c6.jpg 2x,https://cimg.cnyes.cool/prod/news/3989673/m/244477dbcd3a142b1662993cd9d922c6.jpg 3x" alt="鴻海艦隊航向陸股，台灣資本市場剉咧等。(鉅亨網資料照)" data-reactid="260">
//<figcaption itemprop="caption" data-reactid="261">
//        鴻海艦隊航向陸股，台灣資本市場剉咧等。(鉅亨網資料照)
//</figcaption>
//</figure>
//<section class="_82F" data-reactid="262">
//<nav class="_155" data-reactid="263">
//<h6 class="_21V" data-reactid="264">相關個股</h6>
//<a class="_1Ff" href="https://stock.cnyes.com/market/TSE:2317:STOCK" target="_blank" data-ga-category="NewsContent" data-ga-action="QuickStock" data-ga-label="鴻海(2317)" rel="noopener noreferrer" data-reactid="265"><span class="_1E-" data-reactid="266">鴻海</span><span class="_1Xw" data-reactid="267">2317</span></a>
//<a class="_1Ff" href="https://stock.cnyes.com/market/TSE:4958:STOCK" target="_blank" data-ga-category="NewsContent" data-ga-action="QuickStock" data-ga-label="臻鼎-KY(4958)" rel="noopener noreferrer" data-reactid="268"><span class="_1E-" data-reactid="269">臻鼎-KY</span><span class="_1Xw" data-reactid="270">4958</span></a>
//</nav>
//<div itemprop="articleBody" data-reactid="271">
//<p>正當今年冬天最強寒流即將抵台之際，向來有台股人氣王的鴻海 (<a href="https://stock.cnyes.com/market/TSE:2317:STOCK">2317-TW</a>) 一則子公司富士康工業互聯網決議赴中國 A 股掛牌的重大訊息，讓台股感受到堪稱有史以來最冷的寒冬。</p>
//<p>根據統計，台股目前上市公司有 906 家，上櫃公司有 742 家，如果再加上興櫃公司&nbsp;281 家，在台灣的上市櫃公司共計有 1929 家，根據中華徵信所統計，鴻海旗下共有 936 家分子企業，遍布全球五大洲 38 個國家地區，如果扣除台灣所屬 80 家分子企業，鴻海集團在海外共計設立了 856 家分子企業，再扣除 325 家控股公司，海外實質營運的公司也達 531 家之多。</p>
//<p>換言之，光是一個鴻海集團，就等於有超過台股掛牌 4 分之 1 多家的企業，如今鴻海董事長郭台銘決定讓富士康工業互聯網 (FII，Foxconn Industrial Internet Co., Ltd.) 赴中國掛牌，產業人士分析，最大的原因，應該是與企業無法有效籌資有關。</p>
//<p>產業人士表示，台灣資本市場對企業來說，只剩下初次公開發行 (IPO) 的募資功能，至於其他現金增資、公司債及可轉換公司債等，能夠籌募到的資金也相對有限，而政策導引短期投機，也增加企業募資難度。</p>
//<p>台灣企業赴中國掛牌，一直是資本市場神經最敏感的一塊，但卻也是企業不得不面對的部分，尤其台灣資本市場本益比相對偏低是事實，台灣企業面對中國競爭對手往往可以輕鬆募到較多資金，進而發展企業，甚至反過來併購台灣企業，技術能力高的企業，或許還可以跟中國企業談合資，差一點的，就只能悶著頭挨打而沒有反擊的能力。</p>
//<p>之前鴻海轉投資的印刷電路板廠臻鼎 - KY(<a href="https://stock.cnyes.com/market/TSE:4958:STOCK">4958-TW</a>) 決議讓子公司鵬鼎赴中國掛牌，這次則是母公司鴻海直接出手，富士康工業互聯網赴中國掛牌已難擋，後續會有怎麼樣的連鎖反應，更是觀察重點。</p>
//<hr style="height: 0; border-width: 1px; visibility: hidden">
//</div>
//<div data-reactid="272">
//<nav class="_LGv theme-sharing _35m" data-reactid="273">
//<a data-ga-category="NewsContent" data-ga-action="top" data-ga-label="left" data-reactid="274"><span class="_2me _YL8 theme-svg-icon theme-facebook" data-reactid="275">
//<svg width="28" height="28" viewbox="0 0 28 28" xmlns="http://www.w3.org/2000/svg">
//<title>facebook comment</title>
//<path d="M0 .997C0 .447.44 0 .997 0h26.006c.55 0 .997.44.997.997v26.006c0 .55-.44.997-.997.997H.997A.992.992 0 0 1 0 27.003V.997zm18.958 26.997l.148-11.024h4.864v-4.864h-4.864V10.17c0-.783.502-1.305.933-1.305h3.931V4h-3.931c-3.017 0-5.472 2.943-5.472 6.211v1.895H11v4.864h3.567v11.024h4.391z" fill="#000" fill-rule="evenodd" />
//</svg></span></a>
//<a data-ga-category="NewsContent" data-ga-action="ChangeFont" data-ga-label="left_big" data-reactid="276"><span class="_2me _YL8 theme-svg-icon theme-font-size" data-reactid="277">
//<svg width="32" height="32" viewbox="0 0 32 32" xmlns="http://www.w3.org/2000/svg">
//<title>FONT SIZE</title>
//<g fill="none" fill-rule="evenodd">
//<rect fill="#000" width="32" height="32" rx="1" />
//<path d="M13.957 22.186l-.88-2.89H8.652l-.88 2.89H5L9.283 10h3.146l4.3 12.186h-2.772zm-1.495-5.047a854.45 854.45 0 0 1-1.373-4.441 12.536 12.536 0 0 1-.22-.814c-.183.709-.706 2.46-1.57 5.255h3.163zm12.02 5.047l-.49-1.262h-.066c-.426.537-.865.909-1.316 1.116-.45.208-1.039.312-1.764.312-.89 0-1.592-.255-2.104-.764-.512-.51-.768-1.234-.768-2.175 0-.985.345-1.711 1.034-2.179.689-.467 1.728-.726 3.117-.776l1.61-.05v-.407c0-.94-.482-1.41-1.444-1.41-.742 0-1.614.223-2.615.672l-.839-1.71c1.068-.56 2.253-.839 3.553-.839 1.245 0 2.2.272 2.864.814.664.542.996 1.367.996 2.473v6.185h-1.768zm-.747-4.3l-.98.033c-.736.022-1.283.155-1.643.398-.36.244-.54.615-.54 1.113 0 .714.41 1.07 1.229 1.07.586 0 1.055-.168 1.407-.506.351-.337.527-.786.527-1.345v-.763z" fill="#FFF" />
//</g>
//</svg></span></a>
//<a data-ga-category="NewsContent" data-ga-action="Print" data-ga-label="left" href="/news/id/3989673/print" data-reactid="278"><span class="_2me _YL8 theme-svg-icon theme-print" data-reactid="279">
//<svg width="32" height="32" viewbox="0 0 32 32" xmlns="http://www.w3.org/2000/svg">
//<title>ICON PRINT</title>
//<path d="M24.797 0H7.167l-.88 4.571h19.388L24.797 0zm-6.511 32l7.695-5.714h-7.695V32zM5.714 32h11.429v-6.857h9.143V16H5.714v16zm3.429-13.714h13.714v1.143H9.143v-1.143zm0 3.428h13.714v1.143H9.143v-1.143zM0 5.714V19.43h4.571v-4.572H27.43v4.572H32V5.714H0zm5.144 5.715a1.713 1.713 0 1 1-.004-3.427 1.713 1.713 0 0 1 .004 3.427zm4.571 0A1.714 1.714 0 1 1 9.713 8a1.714 1.714 0 0 1 .002 3.429z" fill-rule="evenodd" />
//</svg></span></a>
//<a href="#fb-comments" class="_XWT" data-reactid="280"><span class="_2me _YL8 theme-svg-icon theme-show-comment" data-reactid="281">
//<svg width="28" height="28" viewbox="0 0 28 28" xmlns="http://www.w3.org/2000/svg">
//<title>78981DD6-B6FA-435C-B969-0D6ABD01805C</title>
//<path d="M0 22h5v6l6-6h11V6H0v16zM5 9h6v1H5V9zm0 4h12v1H5v-1zm0 4h12v1H5v-1zM6 0v5h17v15l2 3v-6h3V0H6z" fill-rule="evenodd" />
//</svg></span><span class="_1Uz" data-reactid="282">0</span></a>
//</nav>
//</div>
//</section>
//<section class="_3zs" data-reactid="283">
//<h2 class="_2V3 theme-slick" data-reactid="284"><span data-reactid="285">延伸閱讀</span></h2>
//<ul class="theme-recommended-news-ul" data-reactid="286">
//<li data-reactid="287"><a title="三大法人賣超12億元 台積電、鴻海遭賣天數續寫今年最長" href="/news/id/3989438" data-reactid="288">三大法人賣超12億元 台積電、鴻海遭賣天數續寫今年最長</a></li>
//<li data-reactid="289"><a title="鴻海子公司FII啟動中國A股掛牌 1/31股臨會討論" href="/news/id/3989652" data-reactid="290">鴻海子公司FII啟動中國A股掛牌 1/31股臨會討論</a></li>
//</ul>
//</section>
//</article>
