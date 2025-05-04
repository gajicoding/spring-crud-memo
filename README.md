# 메모장 프로젝트

메모를 CRUD 할 수 있는 Web Application 만들기

## 1. 요구사항 분석 및 설계
<details>
<summary><strong>메모장 API 요구사항</strong></summary>

1. 통신 데이터 형태는 JSON이다.
2. 각각의 메모는 식별자(id), 제목(title), 내용(contents)으로 구성되어 있다.
3. 응답을 각각의 API에 알맞게 해야 한다.
4. 메모를 생성할 수 있다. (CREATE)
    - 메모 생성 시 제목, 내용이 필요하다.
    - 생성된 데이터(식별자, 제목, 내용)가 응답된다.
5. 메모 전체 목록을 조회할 수 있다. (READ)
    - 여러 개의 데이터를 배열 형태로 한번에 응답한다.
    - 데이터가 없는 경우 비어있는 배열 형태로 응답한다.
6. 메모 하나를 조회할 수 있다. (READ)
    - 조회할 memo에 대한 식별자 id값이 필요하다.
    - 조회된 데이터가 응답된다.
    - 조회될 데이터가 없는 경우 Exception이 발생한다.
7. 메모 하나를 전체 수정(덮어쓰기)할 수 있다. (UPDATE)
    - 수정할 memo에 대한 식별자 id값이 필요하다.
    - 수정할 요청 데이터(제목, 내용)가 꼭 필요하다.
    - 수정된 데이터가 응답된다.
    - 수정될 데이터가 없는 경우 Exception이 발생한다.
8. 메모 하나의 제목을 수정(일부 수정)할 수 있다. (UPDATE)
    - 수정할 memo에 대한 식별자 id값이 필요하다.
    - 수정할 요청 데이터(제목)가 꼭 필요하다.
    - 수정된 데이터가 응답된다.
    - 수정될 데이터가 없는 경우 Exception이 발생한다.
9. 메모를 삭제할 수 있다. (DELETE)
    - 삭제할 memo에 대한 식별자 id값이 필요하다.
    - 삭제될 데이터가 없는 경우 Exception이 발생한다.
</details>

<details>
<summary><strong>HTTP API 설계</strong></summary>

| 기능                      | Method   | URL               | Request                                                   | Response                                                                                                                                                                           |
|-------------------------| -------- | ----------------- |-----------------------------------------------------------| ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 메모<br/>생성<br/>하기        | `POST`   | `/api/memos`      | `{"title": "string", "content": "string"}`          | ✔️ **201 Created**<br/>`{"id": 1, "title": "string", "content": "string"}`                                                                              |
| 메모<br/>전체<br/>조회<br/>하기 | `GET`    | `/api/memos`      | (없음)                                                      | ✔️ **200 OK**<br>`[{ "id": 1, "title": "string", "content": "string" }, { "id": 2, "title": "string", "content": "string" }]`<br>데이터가 없을 경우:<br>`[]` |
| 메모<br/>단건<br/>조회<br/>하기 | `GET`    | `/api/memos/{id}` | (없음)                                                      | ✔️ **200 OK**<br>`{"id": 1, "title": "string", "content": "string"}`<br>❌ **404 Not Found**: 해당 식별자의 메모가 존재하지 않는 경우                                    |
| 메모<br/>수정               | `PUT`    | `/api/memos/{id}` | `{"title": "string", "content": "string"}` | ✔️ **200 OK**<br>`{"id": 1, "title": "string", "content": "string"}`<br>❌ **404 Not Found**: 존재하지 않는 메모<br>❌ **400 Bad Request**: 필수값 누락               |
| 메모<br/>제목<br/>수정        | `PATCH`  | `/api/memos/{id}` | `{"title": "string"}`                   | ✔️ **200 OK**<br>`{"id": 1, "title": "string", "content": "string"}`<br>❌ **404 Not Found**: 존재하지 않는 메모<br>❌ **400 Bad Request**: 필수값 누락               |
| 메모<br/>삭제<br/>하기        | `DELETE` | `/api/memos/{id}` | (없음)                                                      | ✔️ **200 OK**<br>❌ **404 Not Found**: 해당 식별자의 메모가 존재하지 않는 경우                                                                                                                       |
</details>

    