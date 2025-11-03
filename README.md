# Product Management API
# Bartosz Mordarski

REST API do zarządzania produktami z walidacją, historią zmian i blokowaniem zabronionych fraz w nazwach.


- **CRUD produktów** - tworzenie, edycja, usuwanie i przeglądanie produktów
- **Walidacja danych** - automatyczna walidacja nazwy, ceny, ilości i kategorii
- **Zakresy cenowe** - każda kategoria (ELECTRONICS, BOOKS, CLOTHES) ma określony zakres cen
- **Zabronione frazy** - dynamiczna lista niedozwolonych słów w nazwach produktów
- **Historia zmian** - automatyczne śledzenie wszystkich operacji (CREATE, UPDATE, DELETE)

## Jak uruchomić

## 1. Sklonuj repozytorium
```bash
git clone https://github.com/BartoszMordarski/ztp-lab1.git
cd ztp-lab1
```

### 2. Uruchom bazę danych PostgreSQL
```bash
docker-compose up -d
```

### 3. Uruchom aplikację
```bash
./mvnw clean install
./mvnw spring-boot:run
```

Aplikacja dostępna na: **http://localhost:8080**

## Testy
```bash
# Uruchom testy
./mvnw -Dtest=CucumberTestRunner test

# raport
open target/cucumber-reports.html
```


## Technologie

- Java 17
- Spring Boot 3.x
- PostgreSQL 16
- Hibernate/JPA
- Cucumber + RestAssured
- Testcontainers

## Wymagania

- Java 17+
- Maven 3.6+
- Docker & Docker Compose
