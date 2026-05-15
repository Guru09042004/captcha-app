# 🔐 CaptchaApp

A Spring Boot CAPTCHA generator with audio support.

## Features
- 6-character CAPTCHA generation (R always included)
- Audio CAPTCHA support (WAV file export)
- REST API for CAPTCHA generation and validation
- Session-based CAPTCHA storage

## Tech Stack
- Java 17+
- Spring Boot
- Maven

## Run the app
```bash
mvn spring-boot:run
```
App starts at `http://localhost:8080`

## API Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/captcha/generate` | Generate new CAPTCHA |
| POST | `/captcha/validate` | Validate user input |
| GET | `/captcha/audio` | Get audio WAV file |

## Author
**Guru09042004** — [GitHub](https://github.com/Guru09042004)
