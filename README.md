<h1>Инструкция по запуску авто-тестов</h1>

<h2>Предусловие:</h2>

* Необходимо иметь предустановленную на устройстве IntelliJ IDEA Ultimate, Docker, 
Docker Desktop
* Скачать репозиторий и открыть его на устройстве, запустив файл build.gradle

<h2>Шаги:</h2>
1. Открыть терминал и ввести команду docker-compose up -d
2. Открыть боковое меню Database и кликнуть на +
3. Выбрать Data Source -> MySql
4. В открывшемся окне заполнить поля User - user, Password - pass, Database - booking
5. Кликнуть на Test connection. Установить драйвера для MySQL при необходимости
6. Кликнуть на ОК
7. Запустить приложение в терминале командой java -jar aqa-shop.jar
8. Открыть файл BookingTest, расположенный в каталоге Test.
Путь к каталогу: src->test->ru.netology.booking
9. Запустить тесты сочетанием горячих клавишей Crt+Shift+F10