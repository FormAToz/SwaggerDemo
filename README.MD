Тестовое задание (Swagger API, JWT).

В файле настроек приложения есть поля с портом, главной страницей сваггера, роли юзера и секретном токене.

В рамках данного задания пароль задается автоматически по значению поля Email.

При запуске программы по адресу http://localhost:8010, будет произведена автоматическая переадресация
на страницу отображения Swagger API.

При регистрации пользователя по адресу http://localhost:8010/profiles/set пользователь будет создан и сохранен в бд.
В консоль выведется токен, с помощью которого можно получить доступ к адресам /profiles/* и /errors/*.
Доступ ко всем остальным адресам (включая /profiles/set) происходит без аутентификации.

Разрешен доступ по адресу /exit. В результате произойдет переадресация на адрес /exit-success и на экран будет выведено
сообщение о закрытии приложения.

==============================
endpoints:
==============================

POST /profiles/set
Создает запись профиля и присваивает ему id
Request:
принимает json следующей структурой:

{
	“name”: string
	“email”: string
	“age”: int
}

Responses:
в случае успеха возвращает id записи пользователя

status 200
{
	“idUser”: int
}

В случае некорректного email
status 400

{
	“msg”: string
}

В случае если email уже передавался (реализовать через фильтр)
status 403

{
	“msg”: string
}



GET /profiles/last
Возвращает последний созданный профиль

Responses:
status 200
{
	“id”: int
	“name”: string
	“email”: string
	“age”: int
“created”: timestamp
}



GET /profiles
Возвращает все созданные профили

Responses:
status 200
[{
	“id”: int
	“name”: string
	“email”: string
	“age”: int
“created”: timestamp
}...]




GET /profiles/{ID}
Возвращает профиль по его ID

Responses:
status 200
{
	“id”: int
	“name”: string
	“email”: string
	“age”: int
“created”: timestamp
}

status 404
в случае если запись не найдена
{
	“msg”: string
}




POST /profiles/get
Возвращает профиль по email

Request:
принимает json следующей структурой:

{
	“email”: string
}

Responses:
status 200
{
	“id”: int
	“name”: string
	“email”: string
	“age”: int
“created”: timestamp
}

status 404
в случае если запись не найдена

{
	“msg”: string
}




GET /error/last
Возвращает сообщение последней ошибки

Responses:
status 200
{
	“msg”: string
	“created”: timestamp
}




GET /exit
Производит закрытия приложение с редиректом на страницу /exit-success с надписью ‘приложение закрыто’