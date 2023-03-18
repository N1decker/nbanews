# NbaNews application

[Проект](https://github.com/N1decker/nbanews) с регистрацией/авторизацией и правами доступа
на основе ролей(ADMIN, USER, EDITOR). Админ может удалять/банить пользователей,
пользователи - управлять своим профилем и данными через UI и по REST интерфейсу с базовой авторизацией,
а редакторы - добавлять/удалять новости. Возможно оценивать новости - лайк/дизлайк, можно писать
комментарии под каждой новостью. Проект покрыт unit-тестами.

Возможно, не работает регистрация, поэутоу, могут быть проблемы с рассылкой кода подтверждения

Стек технологий: 
- Spring Boot
- Spring Security
- Spring Security Test
- Spring Data JPA
- Hibernate ORM
- Hibernate Validator
- SLF4J 
- Thymeleaf 
- Apache Tomcat
- PostgreSQL
- JUnit 5
- Mockito
- AssertJ
- jQuery
- jQuery plugins
- Bootstrap
- Liquibase

Запуск приложения:
Открыть терминал в корне проекта -> mvn spring-boot:run.
Остановка приложения: ctrl + c

Также, можно запустить с докерхаб. Выполнять в этом же порядке:
- docker run --name postgresql -e POSTGRES_PASSWORD=password -e POSTGRES_USER=user -e POSTGRES_DB=nbanews n1decker/nbanews_postgres
- docker run --name app --link postgresql:n1decker/nbanews_postgresql -p 8080:8080 -d n1decker/nbanews_springboot_app
- можно посмотреть логи: docker logs $CONTAINER_ID 
