## 📊 Hot-Deal Project

> 👔 Hotdeal은 의류 쇼핑몰로 상품 조회, 주문, 예약 구매 등을 소비자에게 제공하고, 상품 및 매출 관리를 관리자에게 제공하는 서비스입니다.

## 🌱 Hot Deal 서비스 간략 설명

###### 🥽 가상 시나리오입니다.

- 서비스 대상자 : 20 ~ 30대 고객
    - 앱을 다루는데 능숙한 연령대의 특수성으로 앱 내에 접속 및 수정 사항의 변동이 큼


- 서비스 규모
    - DAU 2,000명 정도의 타 도메인에서 새롭게 확장하는 서비스입니다.
    - 초기에는 매출액보다 서비스를 이용하는 고객 확보에 집중합니다.


- 예상 트래픽
    - 활성 유저 기준 : 로그인
    - 목표 DAU : 출시 후 6개월 안에 2,000 -> 10,000명
    - 목표 월간 재방문율 : 40% 이상 확보
        - 약 DAU 2,000명 기준 800명은 재방문 의사가 있는 고객


- 핵심 기능
    - 공통
        - 회원가입 및 로그인
        - 개인정보 변경 (회원 정보, 비밀번호)

    - 관리자
        - 관리자 상품 등록 및 수정
        - 관리자 매출 관리 기능
            - 일자별 매출액, 판매량 상품 순위, 상품 매출 순위, 사용자 결제액 순위

    - 사용자
        - 제품 조회 (카테고리 별 필터링, 검색)
        - 제품 일반 구매 / 예약 구매 기능
        - 사용자 주소 불러오기
        - 제품 문의, 리뷰 글 작성 및 수정
        - 장바구니 기능

---

### 🌱 사용된 기술

- for Server : Java 17, Spring Boot 3.3.2
- for Database : MySQL, Redis, JPA, QueryDSL
- for Cloud :  Raspberry Pi 5, Docker, Jenkins

---

### 🌱 Architecture

<img src="https://i.ibb.co/NZqvGfw/developer.jpg" alt="developer" border="0">

---

### 🌱 ERD

<img src="https://i.ibb.co/Sw5BrvP/Screenshot-2024-09-02-at-21-13-42.png" alt="Screenshot-2024-09-02-at-21-13-42" border="0" />

---

### 🌱 Hot Deal 문서

- [Hot Deal 기획서](https://florentine-porch-8b4.notion.site/Hot-Deal-fdcbc0df2fdd4c18b2d2b1fbf98430ae)
- [API 문서](http://221.163.118.12:30000/docs/index.html)

---

### 🌱 문제 및 트러블슈팅

- [각 레이어 간 사용되는 도메인 객체를 DTO와 VO 개념을 활용하여 설계한 사례](https://syeon2.github.io/devlog/hotdeal-domain.html)
- 예약 구매 상품 주문 시 재고 차감 동시성 이슈를 Pessimistic Lock으로 해결
- 장바구니 조회 및 수정 속도 개선
- 일반 상품 구매시 여러 상품 정보 조회 속도 개선
- cascade와 orphanRemoval를 활용한 주문 세부 정보 관리
