# Spring Quora - A Reactive Q&A Platform

This project is a **reactive, event-driven, Quora-like application** built with **Spring Boot** and the **Project Reactor framework**.  
It is designed to be a scalable and resilient platform for asking questions, providing answers, and generating personalized user feeds in real-time.

---

## 🚀 Features

- **User Management**: Create and manage user profiles.  
- **Q&A System**: Users can post questions and provide answers.  
- **Topic-Based Content**: Organize questions under specific topics.  
- **Follow System**: Users can follow other users and topics of interest.  
- **Personalized Feed**: A Redis-cached, real-time feed of questions from followed users and topics, built on a fan-out architecture.  
- **Asynchronous Event Handling**: Utilizes Kafka for view count tracking and RabbitMQ for real-time notifications.  
- **Full-Text Search**: Powered by Elasticsearch for efficient and powerful search across all questions.  

---

## 🏗️ Architecture

The application is built on a **modern, reactive microservices-oriented architecture**:

- **Web Layer**: Spring WebFlux for non-blocking, reactive REST APIs.  
- **Primary Database**: MongoDB for storing core application data (Users, Questions, Answers).  
- **Search Index**: Elasticsearch for fast and flexible full-text search.  
- **Cache**: Redis for caching pre-generated user feeds to ensure low-latency reads.  
- **Messaging Queues**:  
  - **RabbitMQ** → Manages real-time notifications (e.g., new questions posted).  
  - **Apache Kafka** → Handles high-throughput event streams like question view counts.  

⚡ The feed system uses a **fan-out-on-write strategy**: when a user posts a question, it pushes the question’s ID into all followers’ Redis feeds.

---

## ✅ Prerequisites

Make sure you have installed:

- **Java 21+**  
- **Gradle 8.0+**  
- **Docker & Docker Compose**  

---

## 🛠️ Local Setup Guide

### Step 1: Start External Dependencies with Docker

#### MongoDB

docker run -d --name mongodb -p 27017:27017 mongo

#### Elasticsearch

docker run -d --name elasticsearch -p 9200:9200 -p 9300:9300 \
  -e "discovery.type=single-node" \
  -e "xpack.security.enabled=false" \
  -e "xpack.security.http.ssl.enabled=false" \
  docker.elastic.co/elasticsearch/elasticsearch:8.13.4

RabbitMQ (with management UI on port 15672)

docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management

Redis

docker run -d --name redis -p 6379:6379 redis

#### Step 2: Start Apache Kafka (Windows)

### Zookeeper

zookeeper-server-start.bat ..\..\config\zookeeper.properties

#### Kafka Broker
kafka-server-start.bat ..\..\config\server.properties

#### Verify
netstat -ano | findstr 9092

Step 3: Run the Application

#### Build & run:

./gradlew build
./gradlew bootRun


The app will be available at:
👉 http://localhost:8080

#### 📡 API Endpoints
User Controller (/api/users)

POST / → Create new user

{ "name": "John", "email": "john@example.com" }

Topic Controller (/api/topics)

POST / → Create new topic

{ "name": "Spring", "description": "Reactive programming" }

Follow Controller (/api/follows)

POST /follow → Follow a user/topic

POST /unfollow → Unfollow a user/topic

Question Controller (/api/questions)

POST / → Create question

GET /{id} → Get question by ID

GET / → Paginated list of questions (cursor-based)

GET /search → Full-text search

GET /elasticsearch → Search via Elasticsearch

Answer Controller (/api/answers)

POST / → Create answer

GET /{id} → Get answer by ID

PUT /{id} → Update answer

DELETE /{id} → Delete answer

GET /question/{questionId} → Answers for a question

Feed Controller (/api/feed)

GET /{userId} → Get personalized feed

#### 🧪 Testing with Postman

Use Postman or any API client to test endpoints.
Refer to the included Feed Generation Testing Guide for step-by-step instructions.

#### 📂 Project Structure
com.example.demo
 ┣ controllers    # REST controllers
 ┣ services       # Business logic
 ┣ repositories   # Spring Data repositories
 ┣ models         # Entities (User, Question, Answer)
 ┣ dto            # DTOs for API requests/responses
 ┣ config         # Redis, Kafka, RabbitMQ configs
 ┣ consumers      # Kafka consumers
 ┣ producers      # Kafka producers

#### 🧹 Environment Cleanup

Stop & remove all Docker containers:

FOR /F "tokens=*" %i IN ('docker ps -aq') DO docker stop %i && docker rm %i

#### 📜 License

This project is licensed
