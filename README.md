# Тестовое задание
Выполнил Голованов Сергей
## Запуск
Запуск контейнрера с базой данных (опционально)
````
docker-compose -f src/main/docker/postgresql.yml up -d
````
Остановка контейнера с базой данных
````
docker-compose -f src/main/docker/postgresql.yml down
````
При первом запуске приложения создаются таблицы в базе данных.\
Адрес для запросов
````
http://localhost:8080
````
### Endpoints
POST "/api/createTask" - Создать задачу и поместить её в очередь\
Пример тела запроса
````
{
"title": "Task 1",
"description": "Description for task 1"
}
````
PUT "/api/updateTask" - Изменить задачу в БД (кроме исполнителя)\
Пример тела запроса
````
    {
        "id": 1,
        "title": "Task 1",
        "description": "Description for task 1",
        "taskStatus": "PAUSED",
        "time": "2023-04-16T23:04:04.176398"
    }
````
GET "/api/task" - Получить список всех задач

GET "/api/task/{id}" - Получить данные задачи по id

GET "/api/taskBrief" - Получить список всех задач с краткой информацией

POST "/api/task/setPerformer/{id}" - Назначить исполнителя с кодом id на задачу\
Пример тела запроса с кодом задачи
````
{
    "id": "1"
}
````
DELETE "/api/task/{id}" - Удалить задачу из БД по id

GET "/api/task/worker/{id}" - Полчить список всех задач исполнителя по его id

GET "/api/task/save" - Инициировать запись всех задач из очереди в БД

POST "/api/createWorker" - Создать исполнителя\
Пример тела запроса
````
{
    "name": "Sergei Golovanov"
}
````
PUT "/api/updateWorker" - Изменить данные исполнителя\
Пример тела запроса
````
    {
        "id": 1,
        "name": "Sergei S Golovanov",
        "position": "DEVELOPMENT"
    }
````
GET "/api/worker" - Получить список всех исполнителей

GET "/api/worker/{id}" - Получить данные исполнителя по id

DELETE "/api/worker/{id}" - Удалить исполнителя из БД по id

## Задача
Организовать простую структуру БД при запуске приложения: связанные таблицы Tasks и Workers.

Tasks: id, title, description, time, status, performer(исполнитель задачи).

Workers: id, name, position, avatar (img).

Написать сервис, реализующий следующие REST методы для работы с задачами (к примеру поля id, title, description, time, status, performer) и исполнителями (id, name, position, avatar (img)):
1) принимающий задачу и складывающий в очередь, реализованную в сервисе, инструментами java.(без внешних MQ и т.д.)
2) считывающий 3 задачи из реализованной очереди и складывающий их в БД несколькими потоками(PG или Oracle).
3) выдающий все задачи из базы в списке с сокращенными данными (id, title, status).
4) выдающий задачу по id с полным описанием.
5) меняющий задачу по id (все кроме id и performer).
6) назначить на задачу исполнителя.
7) CRUD операции с сущностями Workers. Не забыть показывать краткую информацию по задачам, назначенным на исполнителя.

Применяемые технологии: многопоточность, jdbcTemplate (без ORM), Spring Boot, SQL