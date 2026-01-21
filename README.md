# 🔐 Spring Security JWT RBAC Project

A **production-style Spring Boot application** implementing **JWT Authentication**, **Refresh Tokens**, and **Role-Based Access Control (RBAC)** using **Spring Security** and **MySQL**.

This project demonstrates how modern backend systems handle **stateless authentication**, **secure API access**, and **token lifecycle management**.

---

## 🚀 Features

* ✅ JWT-based authentication (Access Token)
* 🔄 Refresh Token with database persistence
* 🔐 Role-Based Access Control (ADMIN / USER)
* 🚪 Secure logout with refresh token revocation
* 🧑‍💻 Default admin auto-initialization
* 🗄️ MySQL + Spring Data JPA
* 🧪 Postman collections included (GitHub versioned)
* ⚙️ Stateless REST APIs (No server sessions)

---

## 🛠️ Tech Stack

* **Java 17**
* **Spring Boot 3.x**
* **Spring Security 6**
* **JWT (jjwt – HS512)**
* **MySQL**
* **Spring Data JPA (Hibernate)**
* **Maven**
* **Postman**
* **Git & GitHub**

---

## 🏗️ Project Architecture

```
Client (Postman / Angular)
        ↓
AuthController (Login / Refresh / Logout)
        ↓
JWT Filter (OncePerRequestFilter)
        ↓
Spring Security Filter Chain
        ↓
Controllers (User / Admin / Role)
        ↓
Service Layer
        ↓
Repository Layer
        ↓
MySQL Database
```

---

## 🔐 Authentication Flow

1. User logs in with username & password
2. Server generates:

   * Access Token (short-lived)
   * Refresh Token (stored in DB)
3. Client sends Access Token in `Authorization` header
4. JWT Filter validates token on every request
5. When Access Token expires:

   * Refresh Token generates new Access Token
6. Logout revokes Refresh Token in database

---

## 👥 Roles & Access

| Role  | Access                                  |
| ----- | --------------------------------------- |
| USER  | User dashboard, profile                 |
| ADMIN | Admin dashboard, user & role management |

---

## 🔑 Default Admin Credentials

```text
Username: admin
Password: admin123
```

(Automatically created at application startup)

---

## 📦 API Endpoints

### 🔓 Public APIs

| Method | Endpoint               |
| ------ | ---------------------- |
| POST   | /api/auth/login        |
| POST   | /api/auth/refreshToken |
| POST   | /api/auth/logout       |
| POST   | /api/users/register    |

### 🔒 User APIs (USER / ADMIN)

| Method | Endpoint             |
| ------ | -------------------- |
| GET    | /api/users/profile   |
| GET    | /api/users/dashboard |

### 👑 Admin APIs (ADMIN only)

| Method | Endpoint                    |
| ------ | --------------------------- |
| GET    | /api/admin/dashboard        |
| GET    | /api/admin/users            |
| GET    | /api/admin/users/{username} |
| PUT    | /api/admin/users/{username} |
| DELETE | /api/admin/users/{username} |
| GET    | /api/roles                  |
| POST   | /api/roles                  |

---

## 🧪 Postman Integration (Recommended)

This repository includes **Postman collections and environments** for easy testing.

### 📁 Location

```
postman/
├── collections/
│   └── SpringJwtRBAC.postman_collection.json
└── environments/
    └── SpringJwtRBAC-Local.postman_environment.json
```

### ▶ How to Use

1. Clone the repository
2. Open Postman
3. Import both JSON files
4. Select environment: `SpringJwtRBAC-Local`
5. Run **Login API** → tokens auto-saved
6. Test secured APIs

---

## ⚙️ Configuration

### application.properties

```properties
server.port=8181
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.expiration=12000000
jwt.refreshExpiration=604800000
```

---

## ▶️ How to Run

```bash
git clone https://github.com/<your-username>/SpringSecurity-JWT-RBAC.git
cd SpringSecurity-JWT-RBAC
mvn spring-boot:run
```

Application will start at:

```
http://localhost:8181
```

---

## 🧠 Interview Highlights

* Stateless authentication using JWT
* Refresh token persistence & revocation
* Custom JWT filter (OncePerRequestFilter)
* Role-based authorization using `@PreAuthorize`
* Secure logout implementation
* Postman + GitHub versioned API testing

---

## 👨‍💻 Author

**Bharath Kumar Racharla**
Java Full Stack Developer
📧 [bharathkitty9009@gmail.com](mailto:bharathkitty9009@gmail.com)
🔗 GitHub: [https://github.com/Bharathkiity](https://github.com/Bharathkiity)

---

⭐ If you find this project useful, give it a star!
