# Grid07 Backend Assignment

## 🚀 Overview

This project implements a scalable backend system using **Spring Boot**, **PostgreSQL**, and **Redis**.

It simulates a social platform with:

* Posts
* Comments (Human & Bot)
* Likes
* Real-time virality scoring
* Bot interaction guardrails
* Notification batching system

---

## 🧱 Tech Stack

* Spring Boot
* PostgreSQL
* Redis
* Docker

---

## ⚙️ Setup Instructions

### 1. Start services

```bash
docker-compose up -d
```

### 2. Run Spring Boot app

```bash
mvn spring-boot:run
```

---

## 📌 Features

### 1. Virality Score

* Like → +20
* Comment → +50
* Bot Reply → +1

Stored in Redis:

```
post:{id}:virality_score
```

---

### 2. Atomic Locks (Concurrency Protection)

#### Horizontal Cap

* Max 100 bot replies per post

```
post:{id}:bot_count
```

#### Vertical Cap

* Max depth = 20

#### Cooldown Cap

* Bot cannot interact with same user within 10 minutes

```
cooldown:bot_{id}:user_{id}
```

---

## 🔒 Thread Safety

Thread safety is ensured using Redis atomic operations:

* `INCR` → for counting bot replies
* Redis is single-threaded → no race conditions

Example:

```
INCR post:1:bot_count
```

If limit exceeds:

* Request rejected
* Counter rolled back

---

## 🔔 Notification System

* Instant notification if no cooldown
* Otherwise stored in:

```
user:{id}:pending_notifs
```

* Scheduler runs every 5 minutes:

    * Aggregates notifications
    * Sends summary

---

## 🧪 Testing

* Postman Collection included (`.json` file)
* JMeter used for concurrency testing

Expected result:

* Only 100 bot comments allowed even under heavy load

---

## 📦 API Endpoints

### Create Post

```
POST /api/posts
```

### Add Comment

```
POST /api/posts/{id}/comments
```

### Like Post

```
POST /api/posts/{id}/like
```

---

## 📂 Postman Collection

A Postman collection JSON file is included in this repository to easily test all APIs.

You can import it into Postman:

* Open Postman
* Click **Import**
* Select the `.json` file

---

## 🧠 Design Philosophy

* Redis = fast control layer (gatekeeper)
* PostgreSQL = source of truth
* Stateless backend
* Scalable and concurrency-safe design

---

## ✅ Conclusion

This system ensures:

* High performance
* Strong concurrency control
* Clean architecture
* Real-world scalability
