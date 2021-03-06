<h1>План внедрения автоматизации</h1>

Нам необходимо тестировать покупку тура.

<h2>Перечень автоматизируемых сценариев</h2>

У нас имеется **3 части** приложения, которые необходимо протестировать разными способами и инструментами: 
* выбор способа оплаты 
* ввод банковских реквизитов
* проверка того, что приложение сохранило в СУБД информацию о способе и успешности платежа.

<h3>Выбор способа оплаты</h3>

Создадим объект страницы и проверим, что тесты успешно находят локаторы для выбранного способа оплаты 
и происходит переход на страницу банковского сервиса.

<h3>Ввод банковских реквизитов</h3>

Проверим успешность заполнения формы валидными и невалидными значениями 
с помощью **метода граничных значений** и **метода эквивалентных значений**.

**Валидными** считаются значения: APPROVED карта - 1111 2222 3333 4444, DECLINED карта - 5555 6666 7777 8888; 
CVC из 3х цифр; срок действия карты: нижняя граница - нынешний месяц и год, верхняя - +5 лет от сегодняшнего месяца и года.

**Невалидными** считаются: номер карты, состоящий меньше или больше чем из 16 цифр
(в случае тестирования нашей СУБД: все номера, отличающиеся от предоставленных); CVC, состоящий больше или меньше
чем из 3х цифр; срок действия - дата в прошлом и дата, превышающая 5 лет от сегодняшнего месяца, нули; пустое поле; буквы латиницы
и кириллицы, спец. символы.

1. **Ввод валидных значений**. Ввести в поля номер карты, срок действия, CVC-код валидные значения и кликнуть кнопку Отправить.
   * Ожидаемый результат: сообщение об успешности проведенной операции, запись в СУБД о типе и (не-)успешности операции.
2. **Ввод невалидных значений** в поле _Номер карты_. Заполнить поле невалидными значениями, а остальные поля - валидными и 
кликнуть кнопку Отправить.
   * Ожидаемый результат: сообщение о недопустимом формате номера или невозможность ввести значение. Отсутствие записей в СУБД
3. **Ввод невалидных значений** в поле _Срок действия_. Заполнить поле невалидными значениями, а остальные поля - валидными и
    кликнуть кнопку Отправить.
   * Ожидаемый результат: сообщение о недопустимой дате или невозможность ввести значение. Отсутствие записей в СУБД
4. **Ввод невалидных значений** в поле _CVC-код_. Заполнить поле невалидными значениями, а остальные поля - валидными и
   кликнуть кнопку Отправить.
   * Ожидаемый результат: сообщение о недопустимом формате кода или невозможность ввести значение. Отсутствие записей в СУБД
5. Оставить **пустым** поле номер карты/срок действия/CVC-код и в остальные поля ввести валидные значения и кликнуть кнопку
Отправить.
   * Ожидаемый результат: сообщение о том, что поле не может быть пустым. Отсутствие записей в СУБД

<h3>Проверка того, что приложение сохранило в СУБД информацию о способе и успешности платежа</h3>

До выполнения авто-тестов необходимо запустить БД на виртуальной машине. Проверка записей в СУБД должна выполняться после
прохождения каждого теста и информация должна соответствовать ожидаемому результату в случае каждого сценария.

<h2>Инструменты и окружение</h2>

Для автоматизации тестирования нам необходимо использовать такие инструменты как:

* **Java**  - язык написания авто-тестов
* **IntelliJ IDEA** - редактор написания кода
* **Gradle** - система сборки для построения проекта
* **Git** - необходим для контроля версий проекта и отката изменений при необходимости
* **Github** - публичный репозиторий для возможности работа в команде и доступа других членов команды к коду
* **JUnit5** - среда тестирования для приложений Java
* **Faker** необходим для создания невалидных данных
* **Selenide** и **Инструменты разработчика в браузере** необходимы для поиска элементов страницы авто-тестами
* Паттерн **Page Object** необходим для имитации действий пользователя и удобства чтения кода и сокращения его размеров
* **SQL** и **DbUtils** необходим для доступа к данным БД из авто-тестов для сценария с авторизованным пользователем
* **MySQL** для подключения к приложению БД
* **Lombok** - инструмент для облегчения написания тестов, создания объектов и работы с DbUtils

Необходимо подготовить **тестовое окружение**:

+ использовать Docker для доступа к БД
+ добавить Allure для отчетности

<h2>Риски</h2>

* риск не найти на странице локатор необходимого элемента
* риск неправильно настроить саму СУБД или подключение к ней
* риск найти ошибки в работе приложения
* риск не найти ответ для решения неизвестных факторов

<h2>Интервальная оценка с учётом рисков (в часах)</h2>

Написание авто-тестов займет около 5 часов. Подключение к СУБД около 3 часов. Написание отчетности около 2 часов.
С учетом рисков _суммарное время работы_ составит около **15 часов**.

<h2>План сдачи работ</h2>

Работы планируются сдать к 27.12.21


