# otus_java_2021_12

Курс Java Developer. Professional

Группа 2021_12

Павлов Михаил

hw18-gradle - Реактивное программирование. Spring Webflux. ДЗ

    Домашнее задание
        Реактивное Веб-приложение на Spring Boot
    
    Цель:
        Нучиться создавать реактивное CRUD-приложения на Spring Boot
    
    Описание/Пошаговая инструкция выполнения домашнего задания:
        Взять за основу ДЗ "Веб-приложение на Spring Boot".
        Приложение разделить на два сервиса:
        
        сервис для работы с базой данных (1)
        сервис для работы с web-клиентом (2) Для взаимодействия сервисов надо использовать webflux. Реактивный клиент на стороне сервиса (2) и реактивный контроллер на стороне сервиса (1). В сервисе (1) сделайте обращение к базе данных на отдельном пуле потоков.