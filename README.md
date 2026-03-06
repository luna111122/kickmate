# ⚽ Kickmate

> K-리그 경기 데이터를 기반으로  
> 실시간 AI 해설을 생성하고 음성으로 전달하는 스트리밍 서비스

영상 링크
https://youtu.be/7slHPzmRAUw

---

## 📌 프로젝트 소개

Kickmate는 K-리그 경기 이벤트 데이터를 분석하여  
AI가 실시간으로 해설을 생성하고 TTS로 음성 변환 후 스트리밍하는 서비스입니다.

단순 텍스트 생성 프로젝트가 아니라,

- 대용량 CSV 경기 로그 처리
- 실시간 이벤트 기반 해설 생성
- SSE 기반 스트리밍
- AI 해설 → 음성 변환 (TTS)
- AWS 인프라 배포 및 자동화

까지 포함한 **엔드투엔드 시스템**입니다.

---

## 🎯 프로젝트 목표

- 스포츠 데이터 기반 AI 해설 생성 시스템 구현
- 실시간 스트리밍 구조 설계
- AI + TTS + Web 서비스 통합
- 대용량 데이터 적재 및 조회 최적화

---

## 🏗 시스템 아키텍처

---

## 🛠 기술 스택

### Backend
- Spring Boot
- FastAPI
- JPA 
- MySQL
- SSE (Server-Sent Events)

### AI / Data
- OpenAI API
- Google Cloud TTS

### Infrastructure
- AWS EC2
- AWS RDS
- AWS S3
- Nginx


---

## 🔎 주요 기능

### 1. 경기 이벤트 기반 AI 해설 생성
- 스코어, 시간, 팀 상태 관리
- 이벤트 발생 시 문맥 기반 해설 생성
- 상태 기반 해설 로직 설계

### 2. SSE 기반 실시간 스트리밍
- WebSocket 대신 SSE 선택
- 서버 → 클라이언트 단방향 스트리밍
- Nginx proxy_buffering off 설정으로 지연 최소화

### 3. AI 해설 음성 변환
- OpenAI 응답 텍스트 → Google TTS 변환
- 음성 파일 AWS S3 저장
- URL 기반 스트리밍 제공

### 4. 대용량 경기 로그 처리
- CSV 대량 적재
- 인덱스 설계 최적화
- B-Tree 기반 조회 구조 개선

---

## 🏗 시스템 아키텍처
<img width="2488" height="1351" alt="image" src="https://github.com/user-attachments/assets/3db1988b-cc74-45bf-982a-601f3c1312f2" />

