# Các bài Lap trong Lập trình mạng

* **Console quản lí Server-Client**
    * `$ java -jar <Tên file jar sau khi gradle build>.jar`
* **TCP Chat**
    * **TCP Client** : Dùng chung cho 3 server bên dưới
    * **TCP Server Single Client** : Server chỉ có thể phục vụ 1 client, không re-start sau khi client ngắt kết nối
    * **TCP Server Reopen Single Client** : Server chỉ có thể phục vụ 1 client, re-start sau khi client ngắt kết nối
    * **TCP Server Multi Client** : Server phục vụ nhiều client cùng 1 lúc, reponse mess:OK về client, có thể re-start sau khi client ngắt kết nối
 
 * **UDP Chat**
   *  **UDP Server** : Nhắn tin console
   *  **UDP Client** : Nhắn tin Console
   
*  **TCP Send File**
   *  **TCP Server Send File Simple**: Truyền file bằng Object(ObjectInputStream)
   *  **TCP Client Send File Simple**:
   *  **TCP Server Send Large File**: Cắt nhỏ file để truyền theo mảng bytes
   *  **TCP Client Send Large File**:
   
## JavaDocs:

[view javadocs](https://tranphuquy19.github.io/JavaNetworkProgramming/)

## DEMO:

![demo](/res/img/demo1.gif "Optional Title")
