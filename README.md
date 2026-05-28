# groom-spring-boot-starter-msa

Spring Cloud Gateway와 2개의 Downstream 서버로 이루어진 초보자용 MSA 학습 프로젝트 틀이다.

## 주제

온라인 서점의 아주 단순한 주문 시스템이다.

- `catalog-service`
  - 책 정보를 관리한다.
  - 책 목록 조회, 책 단건 조회를 담당한다.
- `order-service`
  - 주문을 관리한다.
  - 주문 생성 시 `OpenFeign`으로 `catalog-service`를 호출해서 책 정보와 가격을 확인한다.
- `gateway`
  - 외부 클라이언트의 단일 진입점이다.
  - 요청을 각 서비스로 라우팅한다.

구성:

- `gateway`
  - 외부 요청 진입점
  - `/api/catalog/**`, `/api/orders/**` 라우팅
- `catalog-service`
  - 책 목록/상세 제공
- `order-service`
  - 주문 생성/조회
  - `OpenFeign`으로 `catalog-service` 호출

도메인 주제:

- 책 주문 시스템
- `catalog-service`가 책 정보를 제공하고
- `order-service`가 책 존재 여부와 가격을 조회한 뒤 주문을 만든다.

## 폴더 구조

```text
msa-book-store-starter
├── build.gradle
├── settings.gradle
├── gateway
├── catalog-service
└── order-service
```

## 기술 스택

- Java 21
- Spring Boot 3.5.0
- Spring Cloud 2025.0.0
- Spring Cloud Gateway
- Spring Cloud OpenFeign

## 서비스 책임

### 1. Gateway

- 모든 외부 요청의 진입점
- URI 기반 라우팅
- 예시:
  - `/api/catalog/**` -> `catalog-service`
  - `/api/orders/**` -> `order-service`

### 2. Catalog Service

- 책 카탈로그 조회
- 이후 확장 시:
  - 책 등록
  - 책 수정
  - 책 삭제

### 3. Order Service

- 주문 생성
- 주문 목록 조회
- 주문 상세 조회
- 주문 생성 시 카탈로그 서비스와 통신

## 구현해야 하는 API

처음 배우는 개발자 기준으로, 아래 API를 구현 목표로 보면 된다.

### Catalog Service API

| 기능 | Method | Path | 설명 |
|---|---|---|---|
| 책 목록 조회 | `GET` | `/api/catalog/books` | 전체 책 목록 조회 |
| 책 단건 조회 | `GET` | `/api/catalog/books/{bookId}` | 특정 책 상세 조회 |
| 책 등록 | `POST` | `/api/catalog/books` | 새 책 등록 |
| 책 수정 | `PUT` | `/api/catalog/books/{bookId}` | 기존 책 정보 수정 |
| 책 삭제 | `DELETE` | `/api/catalog/books/{bookId}` | 책 삭제 |

현재 skeleton에는 `GET` 2개만 들어 있고, 나머지는 학습용 확장 항목이다.

### Order Service API

| 기능 | Method | Path | 설명 |
|---|---|---|---|
| 주문 생성 | `POST` | `/api/orders` | 주문 생성 |
| 주문 목록 조회 | `GET` | `/api/orders` | 전체 주문 조회 |
| 주문 단건 조회 | `GET` | `/api/orders/{orderId}` | 주문 상세 조회 |
| 주문 취소 | `PATCH` | `/api/orders/{orderId}/cancel` | 주문 취소 |

현재 skeleton에는 `POST /api/orders`, `GET /api/orders`만 들어 있고, 나머지는 학습용 확장 항목이다.

## Request / Response DTO 설계 예시

아래 DTO는 README 기준 계약 예시다. 처음에는 이 구조대로 구현하고, 이후 공통 응답 포맷으로 감싸도 된다.

### Catalog Service DTO

#### 1. 책 등록 요청

```json
{
  "title": "Spring Microservices",
  "author": "Tom",
  "price": 32000
}
```

```java
public record CreateBookRequest(
    String title,
    String author,
    BigDecimal price
) {}
```

#### 2. 책 수정 요청

```json
{
  "title": "Spring Microservices 2nd",
  "author": "Tom",
  "price": 35000
}
```

```java
public record UpdateBookRequest(
    String title,
    String author,
    BigDecimal price
) {}
```

#### 3. 책 응답

```json
{
  "id": 1,
  "title": "Spring Boot Start",
  "author": "Alice",
  "price": 25000
}
```

```java
public record BookResponse(
    Long id,
    String title,
    String author,
    BigDecimal price
) {}
```

### Order Service DTO

#### 1. 주문 생성 요청

```json
{
  "bookId": 1,
  "quantity": 2,
  "customerName": "Kim"
}
```

```java
public record CreateOrderRequest(
    Long bookId,
    int quantity,
    String customerName
) {}
```

#### 2. 주문 응답

```json
{
  "orderId": 1,
  "bookId": 1,
  "bookTitle": "Spring Boot Start",
  "quantity": 2,
  "customerName": "Kim",
  "totalPrice": 50000,
  "status": "CREATED"
}
```

```java
public record OrderResponse(
    Long orderId,
    Long bookId,
    String bookTitle,
    int quantity,
    String customerName,
    BigDecimal totalPrice,
    String status
) {}
```

#### 3. 주문 목록 응답

```json
[
  {
    "orderId": 1,
    "bookId": 1,
    "bookTitle": "Spring Boot Start",
    "quantity": 2,
    "customerName": "Kim",
    "totalPrice": 50000,
    "status": "CREATED"
  }
]
```

### Service-to-Service DTO

`order-service`가 `catalog-service`를 호출할 때는 아래 DTO를 쓰면 된다.

```json
{
  "id": 1,
  "title": "Spring Boot Start",
  "author": "Alice",
  "price": 25000
}
```

```java
public record BookSummary(
    Long id,
    String title,
    String author,
    BigDecimal price
) {}
```

## 추천 구현 순서

초보자라면 아래 순서로 진행하는 게 가장 안전하다.

1. `catalog-service`의 조회 API 완성
2. `order-service`에서 OpenFeign으로 `catalog-service` 호출
3. `gateway`를 통해 외부에서만 접근하도록 확인
4. 인메모리 리스트 대신 DB로 교체
5. 예외 처리와 공통 응답 포맷 추가

## DB / JPA까지 포함해서 확장할 내용

네, 이 프로젝트를 실제 학습용으로 완성시키려면 DB와 JPA 설정까지 포함하는 게 좋다.

권장 확장 방향:

- `catalog-service`
  - `Book` 엔티티 추가
  - `BookRepository` 생성
  - H2 또는 MySQL/PostgreSQL 연결
  - JPA로 책 등록/수정/삭제 구현
- `order-service`
  - `Order` 엔티티 추가
  - `OrderRepository` 생성
  - 주문 상태(`CREATED`, `CANCELED`) 관리
  - 주문 이력 DB 저장

추천 엔티티 예시:

```java
@Entity
public class Book {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String author;
    private BigDecimal price;
}
```

```java
@Entity
public class BookOrder {
    @Id
    @GeneratedValue
    private Long id;
    private Long bookId;
    private String bookTitle;
    private Integer quantity;
    private String customerName;
    private BigDecimal totalPrice;
    private String status;
}
```

## 실행 순서

프로젝트 루트가 아니라 현재 저장소 루트의 Gradle Wrapper를 사용한다.

```bash
./gradlew -p msa-book-store-starter :catalog-service:bootRun
./gradlew -p msa-book-store-starter :order-service:bootRun
./gradlew -p msa-book-store-starter :gateway:bootRun
```

포트:

- Gateway: `8000`
- Catalog Service: `8081`
- Order Service: `8082`

## 호출 예시

책 목록 조회:

```bash
curl http://localhost:8000/api/catalog/books
```

주문 생성:

```bash
curl -X POST http://localhost:8000/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "bookId": 1,
    "quantity": 2,
    "customerName": "Kim"
  }'
```

주문 목록 조회:

```bash
curl http://localhost:8000/api/orders
```

## 학습 포인트

1. API Gateway가 여러 서비스를 하나의 진입점으로 묶는 방식
2. Downstream 서비스끼리 직접 통신할 때 OpenFeign을 사용하는 방식
3. 서비스별로 Gradle 모듈을 나누는 기본 구조
4. 도메인별 책임 분리
5. 이후 JPA와 DB를 붙였을 때 서비스별 데이터 저장소를 어떻게 나누는지 이해하는 것

## 다음 확장 아이디어

- Service Discovery(Eureka) 추가
- Config Server 추가
- 공통 라이브러리 모듈 분리
- Docker Compose 구성
- DB 연동 및 JPA 추가
- Resilience4j, Circuit Breaker 추가
