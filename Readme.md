面試專案說明

1. \src\main\resources\db\schema.sql 為建立資料表的schema,但實際上是用Spring Boot自動去建表的

2.如果未來coindeskAPI的幣別有增加的話,要去\src\main\resources\currencyList.txt,去新增幣別及對應的幣別代碼

3.在進行單元測試的時候,其中有幾隻API會需要手動新增參數(會跳出視窗)
