package crawler.example;

import com.github.abola.crawler.CrawlerPack;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * 練習： 實價登錄資料取得
 * 
 * 重點 1. 練習找出實價登錄公開資料源 2. zip 格式資料如何取出指定檔案 3. 爬蟲包可解析中文TagName，試著使用看看
 * 
 * @author Abola Lee
 *
 */
public class RealPrice {
	public static void main(String[] args) {

		String uri = "zip:http://plvr.land.moi.gov.tw" + "/Download?type=zip&fileName=lvr_landxml.zip"
				+ "!/B_LVR_LAND_A.XML";// ! 後面是加zip檔內的指定檔案
		// ====================================================
		// 縣市代碼:
		// C,基隆市
		// A,臺北市
		// F,新北市
		// H,桃園縣
		// O,新竹市
		// J,新竹縣
		// K,苗栗縣
		// B,臺中市
		// M,南投縣
		// N,彰化縣
		// P,雲林縣
		// I,嘉義市
		// Q,嘉義縣
		// D,臺南市
		// E,高雄市
		// T,屏東縣
		// G,宜蘭縣
		// U,花蓮縣
		// V,臺東縣
		// X,澎湖縣
		// W,金門縣
		// Z,連江縣
		// =================================
		// 交易類別:
		// A:不動產買賣
		// B:預售屋買賣
		// C:不動產租賃
		// ====================================
		Document jsoupDoc = CrawlerPack.start().getFromXml(uri);

		// 印出整份 XML 資料
		// System.out.println(jsoupDoc.toString());

		// print head
		System.out.println("鄉鎮市區,都市土地使用分區,土地區段位置或建物區門牌,總價元,單價每平方公尺");

		for (Element elem : jsoupDoc.select("買賣")) {
			System.out.print("\"" + elem.select("鄉鎮市區").text() + "\"");
			System.out.print(",\"" + elem.select("都市土地使用分區").text() + "\"");
			System.out.print(",\"" + elem.select("土地區段位置或建物區門牌").text() + "\"");
			System.out.print("," + elem.select("總價元").text());
			System.out.print("," + elem.select("單價每平方公尺").text());
			System.out.println();
		}

	}

}
