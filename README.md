Markdown

# 💬 Ephernal Chat Backend

> **Temporary vibes, permanent connections.**
> A lightweight, Spring Boot-based WebSocket backend for real-time, ephemeral messaging.

Created by **Harsh Parmar(ryu)** 🎸

## 🚀 About The Project

Ephernal Chat is a backend service designed for instant, temporary communication. No history saved, no strings attached. Just hop in, chat, and vanish. Built with the latest tech stack to ensure speed and reliability.

### 🛠 Tech Stack
* **Language:** Java 24 (Bleeding Edge! 🔥)
* **Framework:** Spring Boot 3.5.5
* **Communication:** WebSockets (Real-time duplex)
* **Tools:** Maven, Lombok

---

## ⚙️ Features

* **Real-time Messaging:** Zero latency chat experience.
* **Private DM Support:** Whisper logic included (`private_chat`).
* **Duplicate Username Bouncer:** Prevents identity theft by rejecting duplicate usernames at entry.
* **Live User List:** Real-time updates on who joined or left.
* **CORS Configured:** Securely connected to `chat.ryuverse.fun`.

---

## 🔌 API & WebSocket Events

**Base URL:** `ws://localhost:8080/chat`

### 1. Join the Chat
Send this JSON immediately after connecting:
```json
{
  "type": "join",
  "username": "Ryu",
  "age": 19,
  "gender": "Male"
}

###2. Send Public Message (Broadcast)
To send a message to everyone:

JSON

{
  "type": "chat",
  "username": "Ryu",
  "message": "Hello World!"
}

### 3. Send Private Message (DM)
To send a secret message to a specific user:

JSON

{
  "type": "private_chat",
  "from": "Ryu",
  "to": "ChillGuy",
  "message": "Psst, check this out."
}
