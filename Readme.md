匯入專案說明:

建議使用eclipse IDE 版本越新越好

電腦必須裝Maven

IDE開啟之後,新增一個workspace(名字隨意),接著照著步驟選

import -> git -> project from Git ->  Clone URI  
把信中附上的URI貼上去 URI的框框裡 接著IDE會自動帶出其他框框的資料,接著一直按next按到可以按finish,至此專案就匯入完成

接著找com.cathayunited.interview.Application; 啟動Spring boot
底下console 應該會看到Tomcat started on port(s): 8080 (http) with context path ' 等等字樣 代表spring boot 啟動完成

接著可以開始測試了  尋找com.cathayunitedinterview.test底下的TestApi  

裡面有個別的單元測試


附註:
1. \src\main\resources\db\schema.sql 為建立資料表的schema,但實際上是用Spring Boot自動去建表的

2.如果未來coindeskAPI的幣別有增加的話,要去\src\main\resources\currencyList.txt,去新增幣別及對應的幣別代碼

3.在進行單元測試的時候,其中有幾隻API會需要手動新增參數(會跳出視窗)
