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

<table style="border-collapse: collapse; width: 100%;">
  <thead>
    <tr>
      <th style="width: 350px;">기능</th>
      <th>Method</th>
      <th>URL</th>
      <th>Request</th>
      <th>Response</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>메모 생성</td>
      <td>POST</td>
      <td>/api/memos</td>
      <td>
        <pre>{
  "title": "string",
  "content": "string"
}</pre>
      </td>
      <td>
        ✅ <b>201 Created</b>
        <pre>{
  "id": 1,
  "title": "string",
  "content": "string"
}</pre>
      </td>
    </tr>
    <tr>
      <td>메모 전체<br>조회</td>
      <td>GET</td>
      <td>/api/memos</td>
      <td>(없음)</td>
      <td>
        ✅ <b>200 OK</b>
        <pre>[
  {
    "id": 1,
    "title": "string",
    "content": "string"
  },
  {
    "id": 2,
    "title": "string",
    "content": "string"
  }
]</pre>
        🕳️ <i>없으면 빈 배열 []</i>
      </td>
    </tr>
    <tr>
      <td>메모 단건<br>조회</td>
      <td>GET</td>
      <td>/api/memos/{id}</td>
      <td>(없음)</td>
      <td>
        ✅ <b>200 OK</b>
        <pre>{
  "id": 1,
  "title": "string",
  "content": "string"
}</pre>
        ❌ <b>404 Not Found</b><br>해당 식별자의 메모가 없음
      </td>
    </tr>
    <tr>
      <td>메모 수정<br>(덮어쓰기)</td>
      <td>PUT</td>
      <td>/api/memos/{id}</td>
      <td>
        <pre>{
  "title": "string",
  "content": "string"
}</pre>
      </td>
      <td>
        ✅ <b>200 OK</b>
        <pre>{
  "id": 1,
  "title": "string",
  "content": "string"
}</pre>
        ❌ <b>404 Not Found</b><br>해당 메모가 존재하지 않음<br>
        ⚠️ <b>400 Bad Request</b><br>필수값 누락
      </td>
    </tr>
    <tr>
      <td>메모 제목<br>수정</td>
      <td>PATCH</td>
      <td>/api/memos/{id}</td>
      <td>
        <pre>{
  "title": "string"
}</pre>
      </td>
      <td>
        ✅ <b>200 OK</b>
        <pre>{
  "id": 1,
  "title": "string",
  "content": "string"
}</pre>
        ❌ <b>404 Not Found</b><br>해당 메모가 존재하지 않음<br>
        ⚠️ <b>400 Bad Request</b><br>필수값 누락
      </td>
    </tr>
    <tr>
      <td>메모 삭제</td>
      <td>DELETE</td>
      <td>/api/memos/{id}</td>
      <td>(없음)</td>
      <td>
        ✅ <b>200 OK</b><br>
        ❌ <b>404 Not Found</b><br>해당 메모가 존재하지 않음
      </td>
    </tr>
  </tbody>
</table>
</details>

## 2. 버전별 문제점
### ver1 
MVC 패턴 적용
- 문제점
  - 실제 문제의 원인을 파악하기 어렵거나 잘못된 정보 전달 및 처리가 될 수 있다.
    1. 응답 코드가 세분화 되어있지 않다.(성공 시 모두 200OK)
    2. 적절한 예외가 발생하지 않는다.(실패 시 모두 500 Error 발생)
  - 서버가 종료된 후 다시 켜지면 데이터가 모두 초기화 된다.

### ver2
MVC 패턴 + 프론트 컨트롤러, 어댑터 패턴 적용
- 해결한 문제점
   - 실제 문제의 원인을 파악하기 어렵거나 잘못된 정보 전달 및 처리가 될 수 있다.
      1. 응답 코드가 세분화 되어있지 않다.(성공 시 모두 200OK)
      2. 적절한 예외가 발생하지 않는다.(실패 시 모두 500 Error 발생)

- 문제점
   - Controller에 책임이 너무 많다.(요청, 비지니스 로직, 응답, 예외 처리 등)
   - 서버가 종료된 후 다시 켜지면 데이터가 모두 초기화 된다.

### ver3 
레이어드 아키텍처 적용
- 해결한 문제점
    - Controller의 책임을 Layer별로 분리하였다.
- 문제점 
  1. 데이터베이스에 영구적으로 데이터가 저장되지 않는다. (Database 접근 기술)
  2. 예외 발생시 공통적으로 처리가 불가능하다.
     - 각각의 모든 예외를 try-catch 하여 처리해야 한다.
  3. RequestDto, ResponseDto를 공유하여 null값이 들어오기도 한다.
     - 필요없는 필드에 추가적인 null 검사를 해야한다.
  4. Spring Bean, 생성자 주입 등 Spring의 동작 원리에 대해 이해하지 못했다.
  5. 왜 Interface로 만들어서 구현하여 사용하는지 모른다.