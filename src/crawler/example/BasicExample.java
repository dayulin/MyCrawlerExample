package crawler.example;

import com.github.abola.crawler.CrawlerPack;
import org.apache.commons.logging.impl.SimpleLog;

/**
 * 爬蟲包程式的全貌，就只有這固定的模式
 * 
 * @author Abola Lee
 *
 */
public class BasicExample {
	// commit test test
	public static void main(String[] args) {

		// set to debug level
		//CrawlerPack.setLoggerLevel(SimpleLog.LOG_LEVEL_DEBUG);

		// turn off logging
		CrawlerPack.setLoggerLevel(SimpleLog.LOG_LEVEL_OFF);

		// 遠端資料路徑
		String uri = "http://ptx.transportdata.tw/MOTC/v2/Bus/EstimatedTimeOfArrival/City/Taipei?$top=200&$format=json";

		System.out.println(
				CrawlerPack.start()
				
				// 參數設定
			    //.addCookie("key","value")	// 設定cookie
				//.setRemoteEncoding("big5")// 設定遠端資料文件編碼
				
				// 選擇資料格式 (三選一)
				.getFromJson(uri)
			    //.getFromHtml(uri)
			    //.getFromXml(uri)
			    
			    // 這兒開始是 Jsoup Document 物件操作
			    //.select(".css .selector ")
			    
		);
//
//		1. 自動代碼--------
//		常用的有fori/sout/psvm+Tab即可生成循環、System.out、main方法等boilerplate樣板代碼
//		例如要輸入for(User user : users)只需輸入user.for+Tab
//		再比如，要輸入Date birthday = user.getBirthday;只需輸入user.getBirthday.var+Tab即可。代碼標籤輸入完成後，按Tab，生成代碼。
//		Ctrl+Alt+O 優化導入的類和包
//		Alt+Insert 生成代碼(如get,set方法,構造函數等) 或者右鍵（Generate）
//		fori/sout/psvm + Tab
//		Ctrl+Alt+T 生成try catch 或者 Alt+enter
//		CTRL+ALT+T 把選中的代碼放在 TRY{} IF{} ELSE{} 里
//		Ctrl + O 重寫方法
//		Ctrl + I 實現方法
//		Ctr+shift+U 大小寫轉化
//		ALT+回車 導入包,自動修正
//		ALT+/ 代碼提示
//		CTRL+J 自動代碼
//		Ctrl+Shift+J，整合兩行為一行
//		CTRL+空格 代碼提示
//		CTRL+SHIFT+SPACE 自動補全代碼
//		CTRL+ALT+L 格式化代碼
//		CTRL+ALT+I 自動縮進
//		CTRL+ALT+O 優化導入的類和包
//		ALT+INSERT 生成代碼(如GET,SET方法,構造函數等)
//		CTRL+E 最近更改的代碼
//		CTRL+ALT+SPACE 類名或接口名提示
//		CTRL+P 方法參數提示
//		CTRL+Q，可以看到當前方法的聲明
//		Shift+F6 重構-重命名 (包、類、方法、變量、甚至注釋等)
//		Ctrl+Alt+V 提取變量
//		2. 查詢快捷鍵--------
//		Ctrl＋Shift＋Backspace可以跳轉到上次編輯的地
//		CTRL+ALT+ left/right 前後導航編輯過的地方
//		ALT+7 靠左窗口顯示當前文件的結構
//		Ctrl+F12 浮動顯示當前文件的結構
//		ALT+F7 找到你的函數或者變量或者類的所有引用到的地方
//
//		CTRL+ALT+F7 找到你的函數或者變量或者類的所有引用到的地方
//		Ctrl+Shift+Alt+N 查找類中的方法或變量
//		雙擊SHIFT 在項目的所有目錄查找文件
//		Ctrl+N 查找類
//		Ctrl+Shift+N 查找文件
//		CTRL+G 定位行
//		CTRL+F 在當前窗口查找文本
//		CTRL+SHIFT+F 在指定窗口查找文本
//		CTRL+R 在 當前窗口替換文本
//		CTRL+SHIFT+R 在指定窗口替換文本
//		ALT+SHIFT+C 查找修改的文件
//		CTRL+E 最近打開的文件
//		F3 向下查找關鍵字出現位置
//		SHIFT+F3 向上一個關鍵字出現位置
//		選中文本，按Alt+F3 ，高亮相同文本，F3逐個往下查找相同文本
//		F4 查找變量來源
//		CTRL+SHIFT+O 彈出顯示查找內容
//		Ctrl+W 選中代碼，連續按會有其他效果
//		F2 或Shift+F2 高亮錯誤或警告快速定位
//		Ctrl+Up/Down 光標跳轉到第一行或最後一行下
//		Ctrl+B 快速打開光標處的類或方法
//		CTRL+ALT+B 找所有的子類
//		CTRL+SHIFT+B 找變量的類
//		Ctrl+Shift+上下鍵 上下移動代碼
//		Ctrl+Alt+ left/right 返回至上次瀏覽的位置
//		Ctrl+X 刪除行
//		Ctrl+D 複製行
//		Ctrl+/ 或 Ctrl+Shift+/ 注釋（// 或者/*...*/ ）
//		Ctrl+H 顯示類結構圖
//		Ctrl+Q 顯示注釋文檔
//		Alt+F1 查找代碼所在位置
//		Alt+1 快速打開或隱藏工程面板
//		Alt+ left/right 切換代碼視圖
//		ALT+ ↑/↓ 在方法間快速移動定位
//		CTRL+ALT+ left/right 前後導航編輯過的地方
//		Ctrl＋Shift＋Backspace可以跳轉到上次編輯的地
//		Alt+6 查找TODO
//		3.其他快捷鍵
//		SHIFT+ENTER 另起一行
//		CTRL+Z 倒退(撤銷)
//		CTRL+SHIFT+Z 向前(取消撤銷)
//		CTRL+ALT+F12 資源管理器打開文件夾
//		ALT+F1 查找文件所在目錄位置
//		SHIFT+ALT+INSERT 豎編輯模式
//		CTRL+F4 關閉當前窗口
//		Ctrl+Alt+V，可以引入變量。例如：new String; 自動導入變量定義
//		Ctrl+~，快速切換方案（介面外觀、代碼風格、快捷鍵映射等菜單）
//		4.svn快捷鍵
//		ctrl+k 提交代碼到SVN
//		ctrl+t 更新代碼
//		5.調試快捷鍵
//		其實常用的 就是F8 F7 F9 最值得一提的 就是Drop Frame 可以讓運行過的代碼從頭再來
//		alt+F8 debug時選中查看值
//		Alt+Shift+F9，選擇 Debug
//		Alt+Shift+F10，選擇 Run
//		Ctrl+Shift+F9，編譯
//		Ctrl+Shift+F8，查看斷點
//		F7，步入
//		Shift+F7，智能步入
//		Alt+Shift+F7，強制步入
//		F8，步過
//		Shift+F8，步出
//		Alt+Shift+F8，強制步過
//		Alt+F9，運行至光標處
//		Ctrl+Alt+F9，強制運行至光標處
//		F9，恢復程序
//		Alt+F10，定位到斷點
//		6.重構
//		Ctrl+Alt+Shift+T，彈出重構菜單
//		Shift+F6，重命名
//		F6，移動
//		F5，複製
//		Alt+Delete，安全刪除
//		Ctrl+Alt+N，內聯
	}
}
