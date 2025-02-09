Данное API предназначено для получения и сохранения данных по торгам определенной акции(ее тикеру).    
Первый запуск осуществляется командами:
- ```shell
  mvn package
  ```
- ```shell
  docker compose up
  ```
Документацию после запуска приложения можно посмотреть по адресу <http://localhost:8080/swagger-ui/index.html>

> **Важно!**  
> Для корректной работы в корень проекта требуется добавить файл _.env_ и прописать в нем свои значения следующих свойств:
> - JWT_SECRET_KEY - для верификации JWT-токена. Сгенерировать можно [здесь](https://jwtsecret.com/generate)
> - POLYGON_API_KEY - для доступа к стороннему сервису [Polygon](https://polygon.io/)(требуется регистрация). Необходим при обращении к сервису для получения данных по акциям.
> - DATASOURCE_USERNAME - username для базы данных
> - DATASOURCE_PASSWORD - пароль для базы данных
>
> Пример файла _.env_ :
> ```properties
> JWT_SECRET_KEY=c53e09a3c3eb2ba2c145af61df04ddbf05efe7dd9210d29a7b896a28d0fff59e
> POLYGON_API_KEY=D5barFQEqXxp7xX8pQuQ5IW_WmMHnsk_
> DATASOURCE_USERNAME=postgres
> DATASOURCE_PASSWORD=root
> ```

 
