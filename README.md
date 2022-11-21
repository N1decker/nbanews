# NbaNews application

[Проект](https://github.com/N1decker/nbanews) с регистрацией/авторизацией и правами доступа
на основе ролей(ADMIN, USER, EDITOR). Админ может удалять/банить пользователей,
пользователи - управлять своим профилем и данными через UI и по REST интерфейсу с базовой авторизацией,
а редакторы - добавлять/удалять новости. Возможно оценивать новости - лайк/дизлайк, можно писать
комментарии под каждой новостью.
Проект покрыт unit-тестами

Стек технологий: 
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
Открыть терминал в корне проекта -> mvn spring-boot:run

Остановка приложения: ctrl + c