## Session Test
- HttpSession을 이용해 상태정보 유지
- 서버측에서 저장
- 사용자 상태 유지
- 동일 세션 요청
- 세션만료
- 간단한 구현 연습을 위해 validation 사용하지 않았음
- ---
### signup
````
POST /api/members/signup HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Cookie: JSESSIONID=3729E1F69B224143EC86EF2C5511E4F7
Content-Length: 95

{
    "username": "helloSession",
    "email" : "session@test.com",
    "password" : "123456"
}
````
#### 성공
````
{
    "success": true,
    "data": {
        "username": "helloSession",
        "email": "session@test.com"
    },
    "message": null
}
````
### login
```
POST /api/members/login HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Cookie: JSESSIONID=6D040A54201DC485941D918C581DD839
Content-Length: 63

{
    "email" : "session@test.com",
    "password" : "123456"
}
```
#### 성공
````
{
    "success": true,
    "data": {
        "username": "helloSession",
        "email": "session@test.com"
    },
    "message": null
}
````
### logout
```
POST /api/members/logout HTTP/1.1
Host: localhost:8080
Cookie: JSESSIONID=6D040A54201DC485941D918C581DD839
```
#### 성공
````
{
    "success": true,
    "data": null,
    "message": null
}
````
### myinfo
```
GET /api/members/myinfo HTTP/1.1
Host: localhost:8080
Cookie: JSESSIONID=6D040A54201DC485941D918C581DD839
```
#### 성공
````
{
    "success": true,
    "data": {
        "username": "helloSession",
        "email": "session@test.com"
    },
    "message": null
}
````
