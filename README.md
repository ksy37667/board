## 개발환경
* Java 8 + Spring boot 2.4.5
* Spring Security + JWT
* Spring Data JPA
* Mysql 8.0
* Gradle
---

## 개발 기간
* 2023.12.15 ~ 2023.12.20
---

## 문제 해결 전략
### 1. 로그인, 회원가입, 로그아웃
JWT는 `stateless` 한 장점을 가지고 있지만 100% `stateless` 한 장점 만을 가지고 사용하기에는 보안상 문제가 발생할 수 있다.<br>
특히 토큰이 탈취당한 경우에는 서버에서 토큰에 대한 제어 권한이 없기 때문에 토큰을 무효화 시킬 수 없다.<br>
그래서 `access token` 의 만료 기간을 비교적 짧게 잡고 만료기간이 긴 `refresh token`은 db에서 관리한다.<br>
만약 `access token` 을 탈취 당하더라도 만료기간이 짧기 때문에 비교적 안전하고 로그아웃시에 `refresh token`을 db에서 삭제하면 토큰을 사용할 수 없도록 한다.<br>

* `로그인 성공시`
  * access token 과 refresh token 을 발급한다. 이 과정에서 refresh token 은 DB 에 저장한다.
* `로그인이 되어있는 경우`
  * db를 조회 할 필요 없이 access token 만을 사용해서 토큰이 유효한지만 확인한다.
* `로그아웃` 
  * refresh token 을 DB 에서 삭제해서 더 이상 refresh token 을 사용할 수 없도록 한다.

### 2. spring security + jwt 를 사용한 인증
### AuthenticationManager
`AuthenticationManager`는 인증을 담당하는데, `authenticate` 메소드를 가지고 있다.<br>
`AuthenticationFilter`에 의해 인증이 완료되면` AuthenticationManager`를 통해 인증된 `Authentication` 객체를 `SecurityContextHolder`에 저장한다.<br>
나는 JWT 인증을 사용하기 때문에 `JwtAuthenticationFilter`를 따로 구현하고 이 필터 안에서` AuthenticationManager`를 사용한다.<br>

### AuthenticationProvider
`AuthenticationProvider`는 실제 인증을 처리하는 객체이다.<br>
나는 `JwtAuthenticationProvider` 를 만들고 JWT 인증을 처리한다.<br>

### JwtAuthenticationProvider
`JwtAuthenticationToken` 을 받아서 토큰의 유효성을 검사하고 유효하다면 `Authentication` 객체를 구현한다.
JWT token으로부터 Claim을 추출하고 email, 권한등을 확인해서 JwtAuthenticationToken 에 담아서 반환한다.
JWT token이 유효하지 않은 경우에는 `JwtAuthenticationException` 을 발생시킨다.

### AccessDeniedHandler
`AccessDeniedHandler`는 인증은 되었지만 권한이 없는 경우에 호출되는 핸들러이다.<br>
403 Forbidden 오류가 발생한다. 

### AuthenticationEntryPoint
`AuthenticationEntryPoint`는 인증이 되지 않은 경우에 호출되는 핸들러이다.<br>
401 Unauthorized 오류가 발생한다.


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
        "commentId": 2,
        "success": true
      }
    }
    ```
