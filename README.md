# 📚 Book Store API

A RESTful backend API for a virtual bookstore, developed as part of an academic project at the **University of Westminster**. The application is built using **Java** with the **JAX-RS (Jersey)** framework and supports full CRUD operations for managing books, authors, customers, shopping carts, and orders.

> ⚙️ The system operates fully in-memory using Java collections (`ArrayList`, `HashMap`), with no external database dependency.

---

## ✨ Features

### 📦 Resources
- **Books**: Add, view, update, and delete books.
- **Authors**: Manage author information and view associated books.
- **Customers**: Register and manage customer profiles.
- **Cart**: Add/remove books and calculate totals.
- **Orders**: Place and track orders with ISO-8601 timestamping.

### ✅ Functionalities
- RESTful design with resource-based URIs
- Full support for **CRUD operations**
- Custom exception handling using `ExceptionMapper` classes
- **JSON** for all request/response payloads
- Thoroughly tested using **Postman** (positive and negative test cases)

---

## 🛠️ Tech Stack

| Technology     | Description                          |
|----------------|--------------------------------------|
| Java           | Backend programming language         |
| JAX-RS (Jersey)| RESTful web services framework       |
| JSON           | Communication format                 |
| Postman        | API testing and validation           |

---

## 🚀 Getting Started

### Prerequisites

- Java 8 or higher
- Maven (optional, if you use it for building)
- Postman

### Run the Project

 Clone the repository:
   ```bash
   git clone https://github.com/aru-segar/BookStore.git
   cd BookStore
