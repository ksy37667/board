## 개발환경
* Java 8 + Spring boot 2.4.2
* Mysql
* Gradle

<br/>


## 구현 API


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