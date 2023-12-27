## 개발환경
* Java 8 + Spring boot 2.4.5
* Spring Security + JWT
* Spring Data JPA
* Mysql 8.0
* Gradle
## 개발 기간
* 2023.12.15 ~ 2023.12.22
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
* ```POST /api/comment/write```
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
            "createdAt": "2023-12-20T19:40:20+09:00",
            "modifiedAt": "2023-12-20T19:40:20+09:00"
          },
          {
            "title": "123",
            "content": "qpwoei qwpeoi",
            "writer": "123",
            "view": 0,
            "status": "NORMAL",
            "createdAt": "2023-12-20T16:04:28+09:00",
            "modifiedAt": "2023-12-20T16:04:28+09:00"
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
- 게시글 작성 : ```POST /api/board```
  - Request
      ```
      curl -X POST http://localhost:8080/api/board \
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

- 게시글 삭제 : ```DELETE /api/board/{id}```
  - Request
      ```
      curl -X DELETE http://localhost:8080/api/board/{id} \
          -H "Content-Type: application/json" \
          -Authorization: Bearer {accessToken} \
      ```
  - Response
      ```
      {
        "data": "success"
      }
    ```
<br/><br/>

- 게시글 조회 : ```GET /api/board/{id}```
  - Request
      ```
      curl -X GET http://localhost:8080/api/board/{id} \
          -H "Content-Type: application/json" \
      ```
  - Response
      ```
        {
          "data": {
          "id": 1,
          "title": "123",
          "content": "qpwoei qwpeoi",
          "view": 13,
          "status": "NORMAL",
          "userId": 1,
          "createdAt": "2022-05-23T16:04:28+09:00",
          "modifiedAt": "2022-05-23T16:04:28+09:00"
          }
        }
      ```
  <br/><br/>

- 게시글 수정 : ```PUT /api/board/{id}```
  - Request
      ```
      curl -X PUT http://localhost:8080/api/board/{id} \
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
          "title": "123",
          "content": "qpwoei qwpeoi",
          "view": 13,
          "status": "NORMAL",
          "userId": 1,
          "createdAt": "2022-05-23T16:04:28+09:00",
          "modifiedAt": "2022-05-23T16:04:28+09:00"
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
    ```
- 회원가입 : ```POST /api/user/login```
  - Request :
    ```
    curl -X POST http://localhost:8080/api/user/sign-up \
      -H "Content-Type: application/json" \
      -d '{ \
        "email": "mmii33@naver.com", \
        "username": "33qjs", \
        "password": "gud13546946#", \
        "nickName": "0010101", \
        "socialType": "NORMAL" \
      }'
    ```
  - Response :
    ```
    {
      "data": {
        "id": 6,
        "username": "33qjs",
        "nickname": "0010101",
        "email": "mmii33@naver.com"
      }
    }
    ```

- 토큰 재발급 : ```POST /api/user/login```
  - Request :
    ```
    curl -X POST http://localhost:8080/api/user/refresh \
      -H "Content-Type: application/json" \
      -d '{ \
        "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxcHdvZWkxOTQ1NkBnbWFpbC5jb20iLCJyb2xlcyI6IlJPTEVfVVNFUiIsInVzZXJJZCI6MSwibmFtZSI6ImxlZWhoaCIsImlhdCI6MTcwMzY1MDM3NiwiZXhwIjoxNzA0MjU1MTc2fQ.mlxcplv6tQEVcyfXbhFKDvkUQUN1ihcGZEn6GmhrrI4" \
      }'
    ```
  - Response :
    ```
    {
      "data": {
        "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxcHdvZWkxOTQ1NkBnbWFpbC5jb20iLCJyb2xlcyI6Im51bGwiLCJ1c2VySWQiOjEsIm5hbWUiOiJsZWVoaGgiLCJpYXQiOjE3MDM2NTQwMzQsImV4cCI6MTcwMzY2NjAzNH0.6KPHnNB9H7zUkCO1PQrCE7f0nzuZUgmnkIWBKUU2W4w",
        "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxcHdvZWkxOTQ1NkBnbWFpbC5jb20iLCJyb2xlcyI6IlJPTEVfVVNFUiIsInVzZXJJZCI6MSwibmFtZSI6ImxlZWhoaCIsImlhdCI6MTcwMzY1MDM3NiwiZXhwIjoxNzA0MjU1MTc2fQ.mlxcplv6tQEVcyfXbhFKDvkUQUN1ihcGZEn6GmhrrI4",
        "userId": 1,
        "username": "leehhh"
      }
    }
    ```
- 댓글 작성 : ```POST /api/comment```
  - Request :
    ```
    curl -X POST http://localhost:8080/api/comment \
      -H "Content-Type: application/json" \
      -Authorization: Bearer {accessToken} \
      -d '{ \
        "postId": 1, \
        "content": "string" \
      }'
    ```
  - Response :
    ```
    {
      {
        "data": {
          "id": 4,
          "postId": 1,
          "userId": 1,
          "content": "string",
          "nickname": "mmmooommm",
          "createdAt": "2023-12-27T14:20:38+09:00",
          "modifiedAt": "2023-12-27T14:20:38+09:00"
        }
      }
    }
    ```

- 댓글 수정 : ```PUT /api/comment/{id}```
  - Request :
    ```
    curl -X PUT http://localhost:8080/api/comment/{id} \
      -H "Content-Type: application/json" \
      -Authorization: Bearer {accessToken} \
      -d '{ \
        "postId": 1, \
        "content": "string" \
      }'
    ```
  - Response :
    ```
    {
      {
        "data": {
          "id": 4,
          "postId": 1,
          "userId": 1,
          "content": "string",
          "nickname": "mmmooommm",
          "createdAt": "2023-12-27T14:20:38+09:00",
          "modifiedAt": "2023-12-27T14:20:38+09:00"
        }
      }
    }
    ```

- 댓글 조회 : ```GET /api/comment/{postId}```
  - Request :
    ```
    curl -X GET http://localhost:8080/api/comment/{postId} \
      -H "Content-Type: application/json" \
    ```
  - Response :
    ```
    {
      "data": [
        {
          "id": 3,
          "postId": 1,
          "userId": 1,
          "content": "새로씀",
          "nickname": "mmmooommm",
          "createdAt": "2023-12-12T12:25:17+09:00",
          "modifiedAt": "2023-12-12T12:25:17+09:00"
        },
        {
          "id": 4,
          "postId": 1,
          "userId": 1,
          "content": "string",
          "nickname": "mmmooommm",
          "createdAt": "2023-12-27T14:20:38+09:00",
          "modifiedAt": "2023-12-27T14:20:38+09:00"
        }
      ]
    }
    ```

- 댓글 삭제 : ```DELETE /api/comment/{id}```
  - Request :
    ```
    curl -X DELETE http://localhost:8080/api/comment/{id} \
      -H "Content-Type: application/json" \
      -Authorization: Bearer {accessToken} \
    ```
  - Response :
    ```
    {
      "data": {
        "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxcHdvZWkxOTQ1NkBnbWFpbC5jb20iLCJyb2xlcyI6Im51bGwiLCJ1c2VySWQiOjEsIm5hbWUiOiJsZWVoaGgiLCJpYXQiOjE3MDM2NTQwMzQsImV4cCI6MTcwMzY2NjAzNH0.6KPHnNB9H7zUkCO1PQrCE7f0nzuZUgmnkIWBKUU2W4w",
        "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxcHdvZWkxOTQ1NkBnbWFpbC5jb20iLCJyb2xlcyI6IlJPTEVfVVNFUiIsInVzZXJJZCI6MSwibmFtZSI6ImxlZWhoaCIsImlhdCI6MTcwMzY1MDM3NiwiZXhwIjoxNzA0MjU1MTc2fQ.mlxcplv6tQEVcyfXbhFKDvkUQUN1ihcGZEn6GmhrrI4",
        "userId": 1,
        "username": "leehhh"
      }
    }
    ```
