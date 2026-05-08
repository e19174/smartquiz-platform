# Smart Quiz Platform

A real-time quiz management system built using Spring Boot, React, WebSocket, and JWT authentication.

## Features

- Real-time quiz system
- Live leaderboard
- Answer statistics
- JWT authentication for admin
- Quiz PIN-based student joining
- CSV quiz upload
- WebSocket communication
- Timer-based quiz flow

## Tech Stack

### Frontend
- React
- Vite
- STOMP WebSocket
- SockJS

### Backend
- Spring Boot
- Spring Security
- JWT Authentication
- Spring WebSocket
- MySQL
- JPA / Hibernate

## Project Workflow

1. Admin logs in
2. Admin uploads quiz CSV
3. System generates unique quiz PIN
4. Students join using PIN
5. Admin starts quiz
6. Questions are broadcast in real time
7. Live leaderboard and statistics update instantly

## How to Run

### Backend

```bash
cd Backend
