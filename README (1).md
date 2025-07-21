# Stego‑App

[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](LICENSE)

A simple console-based steganographic chat application in Java. Supports multiple encoding steps (zero‑width characters, Base64, emoji, AES encryption) to hide messages inside cover text. Provides user registration, authentication, and JSON‑based persistent storage.

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)  
  - [Prerequisites](#prerequisites)  
  - [Installation](#installation)  
  - [Compilation](#compilation)  
  - [Running the Application](#running-the-application)  
- [Usage](#usage)  
  - [Registration and Login](#registration-and-login)  
  - [Dashboard Menu](#dashboard-menu)  
- [Stego Steps](#stego-steps)  
- [Project Structure](#project-structure)  
- [Configuration and Data](#configuration-and-data)  
- [Contributing](#contributing)  
- [License](#license)  

## Features

- **User Authentication**:  
  - Secure registration and login with PBKDF2‑hashed passwords.  
- **Modular Stego Pipeline**:  
  - Chain multiple encoding/decoding steps via `CompositeStep`.  
  - Built‑in steps:  
    - Zero‑width character encoding  
    - Base64 encoding  
    - Emoji mapping  
    - AES‑256‑CBC encryption (PBKDF2 key derivation, random salt & IV)  
- **JSON Storage**:  
  - Persist users, contacts, inbox and sent messages in a JSON file.  
- **Console UI**:  
  - Simple menu‑driven interface (stubbed for future expansion).

## Getting Started

### Prerequisites

- **Java Development Kit (JDK) 8+**  
- **Google Gson 2.11.0** (included in `lib/com.google.gson_2.11.0.jar`)

### Installation

1. **Clone the repository**  
   ```bash
   git clone https://github.com/yourusername/Stego‑App.git
   cd Stego‑App
   ```
2. **Verify dependencies**  
   Ensure `lib/com.google.gson_2.11.0.jar` is present.

### Compilation

Compile all source files into an `out/` directory:

```bash
mkdir -p out
javac -cp lib/com.google.gson_2.11.0.jar -d out $(find src -name "*.java")
```

### Running the Application

```bash
java -cp "out:lib/com.google.gson_2.11.0.jar" com.stegochat.ui.ConsoleApp
```

> On **Windows**, replace `:` with `;` in the classpath.

## Usage

### Registration and Login

On startup, you’ll see:

```
1) Register
2) Login
3) Exit
> 
```

- **Register**: create a new account.  
- **Login**: sign in with existing credentials.  
- **Exit**: quit.

### Dashboard Menu

After login, the dashboard presents:

```
--- Dashboard for <username> ---
Your ID: <UUID>

1) View Contacts         [TODO]
2) Add Contact           [TODO]
3) Compose Message       [TODO]
4) Inbox                 [TODO]
5) Settings              [TODO]
6) Logout
> 
```

> **Note**: Items marked `[TODO]` are placeholders for upcoming features.

## Stego Steps

All stego logic implements the `StegoStep` interface:

```java
public interface StegoStep {
    String encode(String input);
    String decode(String input);
}
```

| Step Type   | Description                                                                                      |
|-------------|--------------------------------------------------------------------------------------------------|
| **ZeroWidth** | Hide data by mapping bits to zero‑width space (`​`) and non‑joiner (`‌`) characters. |
| **Base64**    | Standard Base64 encoding to ASCII.                                                             |
| **Emoji**     | Map each 4‑bit nibble to one of 16 emojis.                                                     |
| **Crypto**    | AES‑256‑CBC encryption with PBKDF2 (random salt & IV, Base64‐encoded).                         |

Combine them with `CompositeStep`:

```java
List<StegoStep> steps = Arrays.asList(
    new CryptoStep("password123"),
    new Base64Step(),
    new ZeroWidthStep()
);
CompositeStep pipeline = new CompositeStep(steps);

String coverText = pipeline.encode("Secret message");
String plaintext = pipeline.decode(coverText);
```

## Project Structure

```
.
├── LICENSE
├── README.md
├── .gitignore
├── lib/
│   └── com.google.gson_2.11.0.jar
├── data/
│   └── users.json         # JSON storage for users
├── src/
│   ├── com/stegochat/auth/
│   │   ├── AuthService.java
│   │   └── PasswordHasher.java
│   ├── com/stegochat/model/
│   │   ├── User.java
│   │   ├── Contact.java
│   │   ├── Message.java
│   │   ├── Step.java
│   │   └── StepType.java
│   ├── com/stegochat/stego/
│   │   ├── StegoStep.java
│   │   ├── Base64Step.java
│   │   ├── ZeroWidthStep.java
│   │   ├── EmojiStep.java
│   │   ├── CryptoStep.java
│   │   └── CompositeStep.java
│   ├── com/stegochat/storage/
│   │   ├── DataStore.java
│   │   └── JsonFileStore.java
│   └── com/stegochat/ui/
│       ├── ConsoleApp.java
│       └── Dashboard.java
└── Stego‑App.iml
```

## Configuration and Data

- **User data file**: `data/users.json` (auto‑created if missing).  
- **Add dependencies**: drop additional JARs into `lib/` and include on classpath.

## Contributing

Contributions are welcome! Feel free to open issues or submit pull requests for new features or bug fixes.

## License

This project is licensed under the **GNU General Public License v3.0**. See [LICENSE](LICENSE) for full details.
