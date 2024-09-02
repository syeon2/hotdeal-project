## 📊 Hot-Deal Project

> 👔 Hotdeal은 의류 쇼핑몰로 상품 조회, 주문, 예약 구매 등을 소비자에게 제공하고, 상품 및 매출 관리를 관리자에게 제공하는 서비스입니다.

---

### 🌱 사용된 기술

- for Server : Java 17, Spring Boot 3.2.5
- for Database : MySQL, Redis, JPA, QueryDSL

### 🌱 Hot Deal 문서

- [Hot Deal 기획서](https://florentine-porch-8b4.notion.site/Hot-Deal-fdcbc0df2fdd4c18b2d2b1fbf98430ae)
- [API 문서](http://221.163.118.12:30000/docs/index.html)

---

### 🌱 핵심 기능

- 회원가입, 로그인, 비밀번호 변경
- 회원 정보 불러오기, 주문 정보 조회
- 관리자 상품 등록 및 수정
- 관리자 매출 관리 기능 (매출 조회, 일자별 매출액, 판매량 상품 순위, 상품 매출 순위, 사용자 결제액 순위)
- 사용자 제품 리스트 조회 (검색, 카테고리 필터링)
- 제품 일반 구매 및 예약 구매
- 사용자 주소 불러오기
- 제품 문의, 리뷰 글 작성
- 장바구니 기능

---

### 🌱 Architecture

<img src="https://i.ibb.co/R2XFpWc/architecture.jpg" alt="architecture" border="0">

---

### 🌱 ERD

<img src="https://i.ibb.co/2qVLfRj/hotdeal-erd.png" alt="hotdeal-erd" border="0">

---

### 🌱 문제 및 트러블슈팅

- 예약 구매 상품 주문 시 재고 차감 동시성 이슈를 Pessimistic Lock으로 해결
- 장바구니 조회 및 수정 속도 개선
- 일반 상품 구매시 여러 상품 정보 조회 속도 개선
- cascade와 orphanRemoval를 활용한 주문 세부 정보 관리
