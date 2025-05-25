# kakao_campus_scheduler
## 일정 관리 API 명세서

| 기능       | Method | URL                  | 요청 형식      | 응답 형식      | 상태 코드 |
|------------|--------|----------------------|----------------|----------------|------------|
| 일정 등록   | POST   | /api/schedules        | JSON body      | 일정 객체         | 200 OK     |
| 단건 조회   | GET    | /api/schedules/{id}   | PathVariable   | 일정 객체         | 200 OK<br>404 Not Found |
| 전체 조회   | GET    | /api/schedules        | Query Param (author, date, page, size) | 일정 목록 배열  | 200 OK     |
| 일정 수정   | PUT    | /api/schedules/{id}   | JSON body (task, author, password) | 수정된 일정 객체 | 200 OK<br>401 Unauthorized<br>404 Not Found |
| 일정 삭제   | DELETE | /api/schedules/{id}   | JSON body (password) | 메시지         | 200 OK<br>401 Unauthorized<br>404 Not Found |

