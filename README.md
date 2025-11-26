# 수강 신청 API 서버

프론트엔드 과제 수행을 위한 백엔드 Mock 서버입니다.

## 📋 목차
- [빠른 시작](#-빠른-시작)
- [API 명세](#-api-명세)
- [사용 예시 (curl)](#-사용-예시-curl)
- [샘플 데이터](#-샘플-데이터)
- [개발 도구](#-개발-도구) ← **Swagger UI 포함**
- [트러블슈팅](#-트러블슈팅)

---

## 🚀 빠른 시작

### 사전 요구사항
- **Docker Desktop** 설치 필요 ([다운로드](https://www.docker.com/products/docker-desktop/))

### Docker로 실행 (권장)

```bash
# 1. 프로젝트 디렉토리로 이동
cd course-enrollment-api

# 2. 서버 실행 (첫 실행 시 빌드에 약 1-2분 소요)
docker-compose up --build -d

# 3. 서버 상태 확인 (약 30초 후 healthy 상태 확인)
docker-compose ps

# 4. 서버 로그 확인 (선택사항)
docker-compose logs -f
```

서버가 `http://localhost:8080`에서 실행됩니다.

### 서버 종료
```bash
docker-compose down
```

### 로컬 실행 (Java 17 필요)

```bash
./gradlew bootRun
```

---

## 📚 API 명세

### Base URL
```
http://localhost:8080/api
```

### 인증
일부 API는 JWT 토큰 인증이 필요합니다. 로그인 후 받은 토큰을 헤더에 포함해주세요.

```
Authorization: Bearer {accessToken}
```

---

## 📝 API 상세

### 1. 회원가입
새로운 사용자를 등록합니다.

**Request**
```http
POST /api/users/signup
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123",
  "name": "홍길동"
}
```

**Response** `201 Created`
```json
{
  "id": 1,
  "email": "user@example.com",
  "name": "홍길동",
  "message": "회원가입이 완료되었습니다"
}
```

**Errors**
| Status | Code | Message |
|--------|------|---------|
| 409 | U001 | 이미 사용 중인 이메일입니다 |

---

### 2. 로그인
사용자 인증 후 JWT 토큰을 발급받습니다.

**Request**
```http
POST /api/users/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response** `200 OK`
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "user": {
    "id": 1,
    "email": "user@example.com",
    "name": "홍길동"
  }
}
```

**Errors**
| Status | Code | Message |
|--------|------|---------|
| 404 | U002 | 사용자를 찾을 수 없습니다 |
| 401 | U003 | 비밀번호가 일치하지 않습니다 |

---

### 3. 강의 등록 🔒
새로운 강의를 등록합니다. (인증 필요)

**Request**
```http
POST /api/courses
Authorization: Bearer {accessToken}
Content-Type: application/json

{
  "title": "React 기초",
  "description": "React의 기본 개념을 배웁니다",
  "instructorName": "김강사",
  "maxStudents": 30
}
```

**Response** `201 Created`
```json
{
  "id": 6,
  "title": "React 기초",
  "description": "React의 기본 개념을 배웁니다",
  "instructorName": "김강사",
  "maxStudents": 30,
  "currentStudents": 0,
  "availableSeats": 30,
  "isFull": false,
  "createdAt": "2024-01-15T10:30:00"
}
```

---

### 4. 강의 목록 조회
등록된 모든 강의를 조회합니다.

**Request**
```http
GET /api/courses
```

**Response** `200 OK`
```json
[
  {
    "id": 1,
    "title": "부동산 투자 기초",
    "instructorName": "김투자",
    "maxStudents": 30,
    "currentStudents": 5,
    "availableSeats": 25,
    "isFull": false
  },
  {
    "id": 2,
    "title": "주식 투자 입문",
    "instructorName": "이주식",
    "maxStudents": 50,
    "currentStudents": 50,
    "availableSeats": 0,
    "isFull": true
  }
]
```

---

### 5. 강의 상세 조회
특정 강의의 상세 정보를 조회합니다.

**Request**
```http
GET /api/courses/{courseId}
```

**Response** `200 OK`
```json
{
  "id": 1,
  "title": "부동산 투자 기초",
  "description": "부동산 투자의 기본 개념과 전략을 배웁니다.",
  "instructorName": "김투자",
  "maxStudents": 30,
  "currentStudents": 5,
  "availableSeats": 25,
  "isFull": false,
  "createdAt": "2024-01-15T09:00:00"
}
```

**Errors**
| Status | Code | Message |
|--------|------|---------|
| 404 | C001 | 강의를 찾을 수 없습니다 |

---

### 6. 수강 신청 🔒
강의에 수강 신청합니다. (인증 필요)

**Request**
```http
POST /api/courses/{courseId}/enroll
Authorization: Bearer {accessToken}
```

**Response** `201 Created`
```json
{
  "enrollmentId": 1,
  "courseId": 1,
  "courseTitle": "부동산 투자 기초",
  "instructorName": "김투자",
  "userId": 1,
  "userName": "홍길동",
  "enrolledAt": "2024-01-15T14:30:00",
  "message": "수강 신청이 완료되었습니다"
}
```

**Errors**
| Status | Code | Message |
|--------|------|---------|
| 404 | C001 | 강의를 찾을 수 없습니다 |
| 400 | C002 | 수강 정원이 초과되었습니다 |
| 409 | E001 | 이미 수강 신청한 강의입니다 |
| 401 | A003 | 인증이 필요합니다 |

---

## 🧪 사용 예시 (curl)

서버가 실행 중일 때 아래 명령어로 API를 테스트할 수 있습니다.

### 1. 서버 동작 확인
```bash
curl http://localhost:8080/api/courses
```

### 2. 회원가입
```bash
curl -X POST http://localhost:8080/api/users/signup \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"test1234","name":"테스트"}'
```

### 3. 로그인 (토큰 발급)
```bash
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"test1234"}'
```

### 4. 수강 신청 (토큰 필요)
```bash
# 로그인 응답에서 받은 accessToken을 사용
curl -X POST http://localhost:8080/api/courses/1/enroll \
  -H "Authorization: Bearer {accessToken}"
```

### 5. 전체 플로우 테스트 (복사해서 사용)
```bash
# 회원가입
curl -s -X POST http://localhost:8080/api/users/signup \
  -H "Content-Type: application/json" \
  -d '{"email":"demo@test.com","password":"demo1234","name":"데모유저"}'

echo ""

# 로그인 & 토큰 저장
TOKEN=$(curl -s -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{"email":"demo@test.com","password":"demo1234"}' | grep -o '"accessToken":"[^"]*' | cut -d'"' -f4)

echo "Token: ${TOKEN:0:50}..."

# 수강 신청
curl -s -X POST http://localhost:8080/api/courses/1/enroll \
  -H "Authorization: Bearer $TOKEN"
```

---

## 🎯 샘플 데이터

서버 시작 시 다음 샘플 강의가 자동으로 등록됩니다:

| ID | 강의명 | 강사 | 정원 |
|----|--------|------|------|
| 1 | 부동산 투자 기초 | 김투자 | 30 |
| 2 | 주식 투자 입문 | 이주식 | 50 |
| 3 | 재테크 마스터 클래스 | 박재테크 | 20 |
| 4 | 부동산 경매 실전 | 최경매 | 25 |
| 5 | ETF 투자 전략 | 정ETF | 40 |

---

## 🛠 개발 도구

### Swagger UI (API 문서)
브라우저에서 API 명세를 확인하고 직접 테스트할 수 있습니다.

- **URL: http://localhost:8080/swagger-ui/index.html**

> 인증이 필요한 API 테스트 시, 우측 상단 **Authorize** 버튼을 클릭하고 로그인에서 받은 JWT 토큰을 입력하세요.

### H2 Console
인메모리 데이터베이스 조회가 가능합니다.

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:coursedb`
- Username: `sa`
- Password: (빈 값)

---

## 🔧 트러블슈팅

### Docker 빌드 실패
```bash
# 캐시 삭제 후 재빌드
docker-compose build --no-cache
```

### 포트 충돌
```bash
# 8080 포트 사용 중인 프로세스 확인
lsof -i :8080

# docker-compose.yml에서 포트 변경
ports:
  - "3000:8080"  # 호스트:컨테이너
```

### 서버 로그 확인
```bash
docker-compose logs -f
```

---

## ⚠️ 주의사항

1. **서버 재시작 시 데이터 초기화**: 인메모리 DB(H2)를 사용하므로 서버 재시작 시 모든 데이터가 초기화됩니다.
2. **동시 수강신청**: 동일 강의에 여러 사용자가 동시에 수강신청할 경우 정원 초과가 발생할 수 있습니다 (테스트 환경에서는 문제 없음).
3. **토큰 만료**: JWT 토큰은 24시간 후 만료됩니다. 만료 시 다시 로그인하세요.

---

## 🔗 CORS 설정

프론트엔드 개발 시 CORS 문제 없이 API 호출이 가능하도록 모든 Origin을 허용하고 있습니다.

- Allowed Origins: `*`
- Allowed Methods: `GET`, `POST`, `PUT`, `DELETE`, `OPTIONS`
- Allowed Headers: `*`

---

## 📞 문의

과제 수행 중 API 관련 문의사항이 있으시면 담당자에게 연락해주세요.
