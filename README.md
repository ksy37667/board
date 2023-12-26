## 개발환경
* Java 8 + Spring boot 2.4.5
* Spring Security + JWT
* Spring Data JPA
* Mysql 8.0
* Gradle

<br/>


## 구현 API 목록
### Board API
* ```GET /api/board/search```
* ```GET /api/board/read/{id}```
* ```DELETE /api/board/delete/{id}```
* ```POST /api/board/create```
* ```PUT /api/board/update/{id}```
---
### User API
* ```POST /api/user/login```
* ```POST /api/user/signup```
* ```POST /api/user/logout```
* ```POST /api/user/refresh```
---
### Comment API
* ```GET /api/comment/wirte```
* ```GET /api/comment/read/{id}```
* ```DELETE /api/comment/delete/{id}```
* ```POST /api/comment/update/{id}```

<br/><br/><br/> 

- **게시글 목록 조회 : ```GET /api/board/search```**
    - Request :
    ```
    Content-Type: application/json
    {
      "data": [
            {
              "content": "string",
              "createdAt": "yyyy-MM-dd'T'HH:mm:ssZZZZZ",
              "modifiedAt": "yyyy-MM-dd'T'HH:mm:ssZZZZZ",
              "status": "NORMAL",
              "title": "string",
              "view": 0,
              "writer": "string"
            }
          ],
          "page": 0,
          "size": 2,
          "sort": {
            "empty": false,
            "sorted": true,
            "unsorted": false
          },
          "totalCount": 0,
          "totalPage": 0
    }
    ```
    - Response :
    ```
    "data": [
        {
          "title": "1234",
          "content": "wqerqewr",
          "writer": "1234",
          "view": 0,
          "status": "NORMAL",
          "createdAt": "2022-05-25T19:40:20+09:00",
          "modifiedAt": "2022-05-25T19:40:20+09:00"
        },
        {
          "title": "123",
          "content": "qpwoei qwpeoi",
          "writer": "123",
          "view": 0,
          "status": "NORMAL",
          "createdAt": "2022-05-23T16:04:28+09:00",
          "modifiedAt": "2022-05-23T16:04:28+09:00"
        }
      ],
      "page": 0,
      "size": 3,
      "totalPage": 1,
      "totalCount": 2,
      "sort": {
        "sorted": true,
        "unsorted": false,
        "empty": false
    }
    ```
  <br/><br/>
- 게시글 작성 : ```POST /api/board/create```
- Request
    ```
    curl -X POST http://localhost:8080/api/board/create \
        -H "Content-Type: application/json" \
        -Authorization: Bearer {accessToken} \
        -d '{ \
            "title": "1234", \
            "content": "wqerqewr", \
            "status": "NORMAL" \
        }'
    ```
- Response
    ```
    {
        "data": {
            "id": 1,
            "title": "1234",
            "content": "wqerqewr",
            "writer": "1234",
            "view": 0,
            "userId": 1,
            "status": "NORMAL",
            "createdAt": "2022-05-25T19:40:20+09:00",
            "modifiedAt": "2022-05-25T19:40:20+09:00"
        }
    }
    ```
<br/><br/>
  
- 로그인 : ```POST /api/user/login```
  - Request :
  ```
    curl -X POST http://localhost:8080/api/user/login \
      -H "Content-Type: application/json" \
      -d '{ \
            "email": "", \
            "password": "" \
      }'
  ```
  - Response :
  ```
  {
      "data": {
          "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxcHdvZWkxOTQ1NkBnbWFpbC5jb20iLCJyb2xlcyI6IlJPTEVfVVNFUiIsInVzZXJJZCI6MSwibmFtZSI6ImxlZWhoaCIsImlhdCI6MTcwMzU4MDU2OCwiZXhwIjoxNzAzNTkyNTY4fQ.QXNhhhJxgz46-wqFP3XCpekZigySxWxGC5niArtP4RA",
          "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxcHdvZWkxOTQ1NkBnbWFpbC5jb20iLCJyb2xlcyI6IlJPTEVfVVNFUiIsInVzZXJJZCI6MSwibmFtZSI6ImxlZWhoaCIsImlhdCI6MTcwMzU4MDU2OCwiZXhwIjoxNzA0MTg1MzY4fQ.F_SJUN6wcJeeTFFF1MotfXBqLKo6ehH2ffQ5YWdmLxo",
          "userId": 1,
          "username": "leehhh"
      }
  }
    
