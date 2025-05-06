# Название проекта

CardManagement

## Описание проекта

CardManagement — Система управления банковскими картами пользователей.

## Технологии

- Java 17
- Spring Boot 3
- Spring Security
- Spring Data JPA
- PostgreSQL
- Liquibase
- Docker
- Swagger/OpenAPI
## Методы контроллеров

### CardController

#### POST /api/v1/authentication/registration - регистрация нового пользователя  
#### RequestBody:  
{  
    "email":"...",  
    "password":"..."  
}  
Пароль обязан состоять только из букв и цифр. Так же, он должен содержать минимум 5 букв и 5 цифр.  
#### POST /api/v1/authentication/authentication - аутентификация  
#### RequestBody:  
{  
    "email":"...",  
    "password":"..."  
}  
Возвращает JWT токен.  
#### POST /api/v1/card/add/{cardTypeId} - создание новой карты для пользователя.  
cardTypeId - идентификатор типа карт (1 - VISA, 2 - MASTERCARD).  
Пользователь может иметь только по 5 карт от разного типа.
#### GET /api/v1/card?typeId&mask&pageNumber - получить карты текущего пользователя.  
typeId - тип пользовательской карты.  
mask - маскировка номера карты (**** **** **** 1234).  
pageNumber - номер отображаемой страницы списка карт.  
#### POST /api/v1/card/request - отправка запроса, связанного с картой (блокировка/удаление).  
#### RequestBody:  
{  
    "cardNumber":"1234567812345678",  
    "message":"some message",  
    "requestId":"1"  
}  
Сообщение об описании причины запроса должно иметь длину минимум 100 символов и максимум 300.  
#### PUT /api/v1/transaction/insert - внести деньги на карту пользователя.
#### RequestBody:  
{  
    "cardNumberTo":"1234567812345678",  
    "amount":"100"  
}  
Минимальная сумма пополнения - 10, максимальная - 1_000_000.  
#### PUT /api/v1/transaction/transfer - перевести деньги с одной карты на другую.  
#### RequestBody:  
{  
    "cardNumberFrom":"1234567812345678",  
    "cardNumberTo":"8765432187654321",  
    "amount":"100"  
}  
### Методы контроллеров для администрированных пользователей  
#### GET /api/v1/admin/card?mask&pageNumber - получить постраничный список пользовательских карт.  
#### GET /api/v1/admin/card/request&pageNumber - получить постраничный список пользовательских запросов о картах.  
#### GET /api/v1/admin/card/request/{email} - получить запросы о картах конкретного пользователя по email.  
#### PUT /api/v1/admin/card/block?number - заблокировать карту пользователя по номеру карты.  
#### PUT /api/v1/admin/card/activate?number - активировать карту пользователя по номеру карты.  
### Инструкция по запуску скачанного приложения
Всё что нужно сделать - это запустить docker-compose файл, находящийся в корне проекта.
