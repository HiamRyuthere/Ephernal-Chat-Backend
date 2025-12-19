#  Ephernal Chat Backend

> **"Temporary vibes, permanent connections."** > A lightweight, Spring Boot-based WebSocket backend for real-time, ephemeral messaging.

**Created by:** `Harsh Parmar (Ryu)` 🕶️

---

## Project Overview

**Ephernal Chat** is a specialized backend service built for instantaneous and temporary communication. The architecture is strictly designed to ensure that no message history is persisted, providing a true **"vanish"** experience. 

### 🛠 Tech Stack

| Component | Technology |
| :--- | :--- |
| **Language** | Java 24  |
| **Framework** | Spring Boot 3.5.5 🍃 |
| **Communication** | WebSockets (Full-duplex) |
| **Build Tool** | Maven  |
| **Utilities** | Lombok  |

---

## ✨ Key Features

* **Real-time Messaging:** Low-latency bi-directional communication using WebSockets.
* **Private Messaging:** Integrated logic for targeted peer-to-peer (P2P) communication.
* **Identity Management:** Automatic rejection of duplicate usernames to maintain session integrity.
* **Active Session Tracking:** Real-time updates for user joins and departures.
* **Security:** Configured CORS policies for authorized domains.

---

##  API & WebSocket Documentation

**Base URL:** `ws://localhost:8080/chat`

### 1. Connection & Authentication
Immediately after establishing a connection, the client must send a **"join"** payload:

```json
{
  "type": "join",
  "username": "Ryu",
  "age": 19,
  "gender": "Male"
}
```
2. Broadcast Messaging
To transmit a message to all connected users:
```json
{
  "type": "chat",
  "username": "Ryu",
  "message": "Hello World! 🌍"
}
```
3. Direct Messaging (DM)
To send a message to a specific recipient:
```json
{
  "type": "private_chat",
  "from": "Ryu",
  "to": "RecipientUsername",
  "message": "Direct message content goes here. 🤫"
}
```
#  Installation and Execution

## Prerequisites
**Java Development Kit** (JDK) 24

**Maven** (Bundled with IntelliJ IDEA)

**IDE:** IntelliJ IDEA (Recommended)

## Setup Steps
Clone the Repository:
```bash
https://github.com/HiamRyuthere/Ephernal-Chat-Backend
```
Open Project: Open the folder in your IDE. Wait for Maven dependency synchronization to complete.

Verify Configuration: Ensure the Project SDK is set to Java 24 in your IDE settings.

Run Application: Execute the main application class annotated with @SpringBootApplication. The server starts on port 8080 by default.

## 🧪 Testing
### Postman:
Use the WebSocket request feature to connect to ws://localhost:8080/chat and choose body then raw and paste testing json there. 
Do it for every endpoint. 

Alternatively, you can create your own frontend for that. (Contact me if you want an already built frontend)
<br><br><br><br>
<p align="center">
  Made with ❤️</p>
