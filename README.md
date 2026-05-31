# Simple Banking App

A Java Swing desktop application demonstrating core software development concepts.

## Features
- Create bank accounts
- Deposit and withdraw funds
- Transfer between accounts
- Input validation and error handling

## Concepts Demonstrated
- **Event Handling** – Button clicks trigger account operations
- **Exception Handling** – Invalid input and insufficient funds are caught gracefully
- **Code Refactoring** – Modular 3-layer design (Model, Service, UI)
- **Unit Testing** – 8 JUnit 5 test cases covering all business logic
- **Git & GitHub** – Full version history with meaningful commits

## Tech Stack
- Java 17
- Java Swing (GUI)
- JUnit 5 (Testing)
- IntelliJ IDEA

## Setup Instructions
1. Install JDK 17+ and IntelliJ IDEA Community Edition
2. Clone this repository:
   git clone https://github.com/Hassan-zubair-ch/Banking.git
3. Open as existing Java project in IntelliJ
4. Add JUnit 5 to classpath (Alt+Enter on any @Test annotation)
5. Run BankingAppUI.java as Java Application

## Project Structure
src/
├── banking/
│   ├── model/
│   │   └── BankAccount.java
│   ├── service/
│   │   └── BankService.java
│   ├── ui/
│   │   └── BankingAppUI.java
│   └── test/
│       └── BankServiceTest.java