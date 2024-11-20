# JWT 
> ### JWT 토큰 기반 인증의 동작 방식
- 사용자가 로그인 요청
  - LoginForm에 입력 
- 서버의 인증 확인 
  - 서버에서 DataBase를 통해 먼저 인증수행 
- JWT 생성
  - 인증이 성공하면, 사용자의 정보를 JWT로 암호화하여 토큰 생성
- JWT 응답
  - Server에서 Client에게 JWT 토큰을 포함해 응답을 전송.
- 토큰 저장 및 전송
  - Client는 해당 전송받은 토큰을 로컬 저장소에 저장, 
  - 요청시 이 JWT를 HTTP 헤더에 담아 서버에 전송
- 토큰 검증
  - Client의 요청이 오면 서버에서 JWT 추출하고 토큰을 검증함.
- 인증된 응답 처리 
  - 토큰이 유효하면 OK

> ### 장점
> - 서버 부하가 낮다.
> - 클라이언트와 서버가 다른 도메인일 때 유용
> ### 단점
> - 구현 및 복잡도 증가 ( 확실히 복잡하다고 느꼈음 )
> - 정보를 많이 담을 수 있지만, 비용 증가
> - JWT 토큰 갱신시 일부분만 만료,갱신이 어렵다.
> - 비밀 키 유출시 덴져뤄스

> ### 관련
- `Header` : HS256과 타입 JWT
````
{
  "alg": "HS256",
  "typ": "JWT"
}
````
- `Payload` : Claim 인증 관련 데이터 ID,권한을 넣음.
````
{
  "sub": "user@example.com",
  "role": "USER",
  "iat": 1683600000,
  "exp": 1683686400
}
````
- `Signature`
  - Header와 Payload를 비밀 키로 서명하여 생성. 토큰 무결성
````
HMACSHA256(
  base64UrlEncode(header) + "." + base64UrlEncode(payload),
  secret
)
````
- `Secret Key`
  - application.properties 파일에서 Base64로 인코딩 된 Secret Key를 읽어옴.
  - Base64로 인코딩 된 Secret Key를 디코딩하여 HMAC 키로 변환
- `Signature Algorithm` 
  - JWT 서명에 사용할 알고리즘을 정의(예: HS256).
- `Base64로 Decoding`
  - 인코딩 된 Secret Key를 디코딩하여 바이트 배열로 변환.
- `Key 변환 `
  - Keys.hmacShaKeyFor() 메서드를 사용하여 JWT 서명 및 검증에 사용할 Key 객체를 생성
- `@PostConstruct` 
  - 이 과정은 Bean 초기화 후 한 번만 실행되므로, Key 객체를 효율적으로 설정할 수 있음.
- `@Value 어노테이션 `
  - 설정 파일에 정의된 값을 필드에 주입해줌. 
  - application.properties에 있는 Secret Key를 가져와준다.

> **_회원가입_** 
````
POST /api/signup HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Content-Length: 95

{
    "email" : "zhalr3424@naver.com",
    "username" : "허정현",
    "password" : "123456"
    
}
```` 
결과 
```
{
    "id": 1,
    "username": "허정현",
    "email": "zhalr3424@naver.com",
    "role": "USER"
}
```
> **_로그인 토큰 발급_** 
```
POST /api/login HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Authorization: ••••••
Content-Length: 66

{
    "email" : "zhalr3424@naver.com",
    "password" : "123456"
}
```
**_결과_**

![image](https://github.com/user-attachments/assets/d02b3cb9-2e8c-4553-8c47-55bf72689ebd)

> **_관리자 로그인_**
- 서버에서 단 하나의 계정만 생성
- 로그인시 토큰 발급
````
POST /api/login HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Content-Length: 61

{
  "email": "admin@admin.com",
  "password": "admin123!@#"
}
````
> **_관리자 권한 회원 조회_**
- Role_ADMIN만 가능하다.
````
GET /api/admin/members HTTP/1.1
Host: localhost:8080
Authorization: ••••••
````
**_결과_**

![image](https://github.com/user-attachments/assets/8b389445-b678-4c55-b574-a56b4e3042e2)



