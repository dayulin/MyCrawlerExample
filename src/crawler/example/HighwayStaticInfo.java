package crawler.example;

import com.github.abola.crawler.CrawlerPack;

/**
 * 練習 高公局即時路況資料
 * 
 * @author Abola Lee
 *
 */
public class HighwayStaticInfo {

	public static void main(String[] args) {
		
		// 遠端資料路徑
		//gzip 只會壓縮一個檔案
		String uri = "gz:http://tisvcloud.freeway.gov.tw/roadlevel_info.xml.gz";
		//String uri = "gz:http://tisvcloud.freeway.gov.tw/roadlevel_value5.xml.gz";
//		System.out.println(
//				CrawlerPack.start()
//			    	.getFromXml(uri)
//				CrawlerPack.start().getFromJson(uri);
//
//		);
		System.out.println(	CrawlerPack.start().getFromXml(uri));
	}
}
