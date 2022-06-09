# Приложение для отправки тестовых услуг ПУ в kafka

## Основные методы
- Инициализация kafka producer
- Отправка сообщения списка услуг ПУ

## Запуска приложения
- Запустить [файл docker-compose](/documentation/docker/docker-compose.yml) командой **docker-compose up -d**
- Запустить [jar проекта](/documentation/jar/kafka-supu-dev-1.0-SNAPSHOT.jar) через командную строку/терминал командой **java -jar <путь до файла>**
- В браузере пройти по ссылке http://localhost:8888/open-api-ui и работать через интерфейс swagger

## Пример запроса для добавления услуг ПУ в kafka
- Расположение примера запроса: [файл](/documentation/request/add-provider-services-dto-rq.json)

### Для разработчиков
- Для создания нового jar использовать команду **gradle bootJar**