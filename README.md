# 💬 Real-Time Chat Application

A secure, real-time chat application built using **Spring Boot, WebSocket, RabbitMQ, and PostgreSQL**.  
Designed as an internal communication tool for organizations with scalable, event-driven architecture.

---

## 🚀 Live Demo
👉 https://chat-app-jdaj.onrender.com

---

## 🧠 Architecture Overview

This application follows an **event-driven architecture**:
Frontend (HTML + JS)
↓
WebSocket (STOMP)
↓
Spring Boot Backend
↓
RabbitMQ (Message Queue)
↓
Consumer Service
↓
PostgreSQL (Database)
↓
WebSocket → Real-time delivery to users


---

## 🔄 Application Flow

1. **User Login**
   - User logs in with username & password
   - Password is stored using BCrypt hashing
   - Backend returns a **JWT token**

2. **WebSocket Connection**
   - Client connects using WebSocket (`/ws`)
   - JWT token is passed and validated
   - User is authenticated and assigned a session

3. **Send Message**
   - Message sent via STOMP endpoint `/app/chat.sendMessage`
   - Sender is extracted securely from JWT (not from UI)

4. **Message Queue (RabbitMQ)**
   - Message is pushed to RabbitMQ queue
   - Ensures asynchronous and reliable processing

5. **Consumer Processing**
   - Consumer listens to queue
   - Saves message to database
   - Sends message to sender & receiver via WebSocket

6. **Real-Time Delivery**
   - Messages delivered instantly using:
     ```
     /user/queue/messages
     ```

---

## 🛠️ Tech Stack

### Backend
- Java 21
- Spring Boot
- Spring WebSocket (STOMP)
- Spring Security (JWT)
- Spring Data JPA

### Messaging
- RabbitMQ (CloudAMQP)

### Database
- PostgreSQL (Render)

### Frontend
- HTML, CSS, JavaScript
- SockJS + STOMP.js

### Deployment
- Render (Backend & DB)
- CloudAMQP (RabbitMQ)

---

## 🔐 Security Features

- JWT-based authentication
- Password hashing using BCrypt
- Secure WebSocket handshake
- Sender validation from token (not client)

---

## 📌 Key Features

- ✅ Real-time chat using WebSockets  
- ✅ Secure login with JWT  
- ✅ Message queue using RabbitMQ  
- ✅ Persistent chat history (PostgreSQL)  
- ✅ User-to-user private messaging  
- ✅ Cloud deployment  

---

## ⚠️ Challenges & Solutions

### 🔁 Duplicate Messages Issue
- **Problem:** Messages were delivered twice
- **Cause:** Both Controller and Consumer were sending messages
- **Solution:**  
  ✔ Made RabbitMQ Consumer the **single delivery point**

---

### 🔐 JWT Not Working in WebSocket
- **Problem:** WebSocket requests had `Auth header: null`
- **Solution:**  
  ✔ Passed token via query param  
  ✔ Implemented custom Handshake Interceptor  

---

### 🌐 Localhost Issues in Production
- **Problem:** Local DB and RabbitMQ not accessible
- **Solution:**  
  ✔ Switched to cloud services (Render + CloudAMQP)

---

## 📦 API Endpoints

### Auth
POST /auth/login

### Chat
GET /chat/history?user1={user1}&user2={user2}

### WebSocket

/ws
/app/chat.sendMessage
/user/queue/messages

## 🧪 How to Run Locally

1. Clone repo

git clone <repo-url>


2. Configure `application.properties`
- PostgreSQL
- RabbitMQ

3. Build and run

mvn clean install
java -jar target/app.jar

4. Open frontend

index.html


---

## 📈 Future Enhancements

- 🔵 Typing indicators  
- 🟢 Online/Offline status  
- ✔️ Message delivery status (seen/delivered)  
- 👥 Group chat support  
- 📱 Mobile-friendly UI  

---

## 👨‍💻 Author

**Navin Kalkur**

---

## ⭐ If you like this project

Give it a star ⭐ on GitHub!
