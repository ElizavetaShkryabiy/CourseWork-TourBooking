
<h1>Приложение для покупки тура</h1>

Нам необходимо протестировать приложение для покупки тура, предоставляющее на выбор 2 варианта совершения 
сделки: покупка обычным способом и выдача кредита. Приложение передает данные банковским сервисам и имеет 
доступ к собственной СУБД, хранящей данные о совершенных платежах.

<h2>Начало работы:</h2>

* Необходимо иметь предустановленную на устройстве IntelliJ IDEA Ultimate, Docker, 
Docker Desktop, Git, браузер Google Chrome, приложение для извлечения файлов из ZIP-архива
* Открыть браузер Google Chrome, открыть ссылку на репозиторий
* На странице репозитория кликнуть Code->Download ZIP
* Извлечь файлы из архива
* Запустить файл build.gradle

<h2>Запуск авто-тестов:</h2>

1. Открыть терминал и ввести команду docker-compose up -d
2. Открыть боковое меню Database и кликнуть на +
3. Выбрать Data Source -> MySql
4. В открывшемся окне заполнить поля User - user, Password - pass, Database - booking
5. Кликнуть на Test connection. Установить драйвера для MySQL при необходимости
6. Кликнуть на ОК
7. Запустить приложение в терминале командой java -jar aqa-shop.jar
8. Открыть файл [BookingTest](src/test/java/ru/netology/booking/Test/BookingTest.java)
9. Запустить тесты сочетанием горячих клавишей Crt+Shift+F10
10. После прохождения всех тестов кликнуть на кнопку со слоником и подписью Open Gradle test report
для того, чтобы увидеть отчетность

<h3>Документы для отчетности:</h3>

* [План автоматизации](documentation/Plan.md)

* [Отчет о тестировании](documentation/Report.md)
* [Test Results - BookingTest.html](http://localhost:63342/courseWork/documentation/Test%20Results%20-%20BookingTest.html?_ijt=5vettlsbq44grglkame9k0lgec&_ij_reload=RELOAD_ON_SAVE)
* [Отчет по итогам автоматизации](documentation/Summary.md)