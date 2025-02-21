[![Code Smells][code_smells_badge]][code_smells_link]
[![Maintainability Rating][maintainability_rating_badge]][maintainability_rating_link]
[![Security Rating][security_rating_badge]][security_rating_link]
[![Bugs][bugs_badge]][bugs_link]
[![Vulnerabilities][vulnerabilities_badge]][vulnerabilities_link]
[![Duplicated Lines (%)][duplicated_lines_density_badge]][duplicated_lines_density_link]
[![Reliability Rating][reliability_rating_badge]][reliability_rating_link]
[![Quality Gate Status][quality_gate_status_badge]][quality_gate_status_link]
[![Technical Debt][technical_debt_badge]][technical_debt_link]
[![Lines of Code][lines_of_code_badge]][lines_of_code_link]

Мои лабораторные работы для BSUIR/БГУИР (белорусский государственный университет информатики и радиоэлектроники).

Предмет - OOTPiSP/ООТПиСП (объектно-ориентированные технологии программирования и стандарты проектирования).

## Условия

Все работы являются консольными программами на тему ООП. Каждая следующая работа является развитием предыдущей.
Ретроспективно работы были улучшены и пофикшены, но даже в неисправленном виде были успешно сданы.

Суть всех лабораторных: список объектов, с которым можно взаимодействовать. Поиск по нему, вывод информации, добавление,
редактирование. Программа - бесконечный цикл, который запрашивает команды. exit = выход из цикла.

### Лабораторная работа 1

Основы ООП. По условию требовалось реализовать объекты, иерархию классов с наследованием, второстепенные конструкторы,
перезапись функций.

В программе есть "магазин транспорта". Транспорт делится на велосипеды и автомобили, велосипеды - на аисты и стелсы,
автомобили - на жигули и фольксвагены. Собственно, есть иерархия, конструкторы, наследование, полиморфизм. Программу
можно заполнить списком объектов и выводить его. Есть функции, например поиск по цвету или цене.

### Лабораторная работа 2

По условию требовалось добавить к предыдущей лабораторной работе функционал создания объекта прямо во время работы
пользователя.

Собственно, добавлена функция добавки своего транспорта. Пользователь вводит тип, цену, цвет. Автомобиль/велосипед
добавляется в список и функционирует, как и все остальные итемы.

### Лабораторная работа 3

По условию требовалось добавить к предыдущей лабораторной работе интерфейсы (не графические, а в джавишном смысле
implements), а также несколько видов сериализации: xml, json, bin.

Интерфейс добавлен - это автомобиль с улучшением. Методы интерфейса банальные: сеттер и геттер. Интерфейс реально
полезен при серилазации json, ведь, если объект имплементит интерфейс "улучшение", то мы сохраняем лишнее поле, а потом
так же и десериализуем. Сериализация реализована всякими библиотеками и происходит на каждом шаге. Через try-catch
предусмотрена загрузка листа по умолчанию, если файлы данных устарели или не существуют.

### Лабораторная работа 4

По условию требовалось добавить к предыдущей лабораторной работе динамическую загрузку плагина. Плагин либо с функциями,
либо с объектами.

Были удалены две из трёх сериализаций из-за их практического неудобства и сложностей при работе с плагином. Очевидно:
плагин - это скомпилированный джарник, который нельзя импортировать (по условию). Как тогда делать объекты из класса
внутри? Через newInstance(). Используя полный путь к классу. Собственно, это ещё одна переменная, которая и до этого
применялась в сериализации json и теперь очень выручает.

Таким образом, плагин (в моём случае - один класс для нового автомобиля, CarRenault) подгружается в том смысле, что мы
сохраняем его имя и потом по этому имени ищем автомобиль внутри плагина, если он не был найден внутри нашей программы.
Эта логика через try-catch встроена во все места, где создаётся объект.

Этот плагин - плагин с объектами, вовлекающимися в логику программы. Нового функционала в нём нет. Но джарники с
функционалом также будут работать, так как в лабораторной работе есть фрагмент кода, осуществляющий поиск класса Loader
с функцией load() внутри плагина. Если таковое есть, то код оттуда будет выполнен. В пятой лабораторной работе показано,
как это работает.

### Лабораторная работа 5

По условию требовалось добавить к предыдущей лабораторной работе шифрование и дешифрование сериализованных файлов, а
также реализовать конвертатор xml -> json и обратно.

Конвертатор реализован, тут ничего интересного рассказать не получится. Что касается шифрации и дешифрации - это
лабораторная работа из другого предмета, которую я оформил в виде плагина с новым функционалом.

Ещё в 4 работе в лабораторной работе был невостребованный фрагмент кода "попытка загрузить новые операции из плагина,
если они там есть" - Loader.load(). Теперь это пригодилось: плагин шифрования добавляет две новые функции, которые можно
использовать после загрузки плагина - они добавляются в хеш-карту функционала программы.

### Лабораторная работа 6

По условию требовалось применить паттерны. Паттерны в основном присобачены криво, так как работа слишком уж простая. Но
они есть.

## Приложения

В папке docs лежит официальная презентация, которую показывали на лекциях.

<!----------------------------------------------------------------------------->

[code_smells_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_OOP-Technologies-and-Design-Standards&metric=code_smells

[code_smells_link]: https://sonarcloud.io/summary/overall?id=Hummel009_OOP-Technologies-and-Design-Standards

[maintainability_rating_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_OOP-Technologies-and-Design-Standards&metric=sqale_rating

[maintainability_rating_link]: https://sonarcloud.io/summary/overall?id=Hummel009_OOP-Technologies-and-Design-Standards

[security_rating_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_OOP-Technologies-and-Design-Standards&metric=security_rating

[security_rating_link]: https://sonarcloud.io/summary/overall?id=Hummel009_OOP-Technologies-and-Design-Standards

[bugs_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_OOP-Technologies-and-Design-Standards&metric=bugs

[bugs_link]: https://sonarcloud.io/summary/overall?id=Hummel009_OOP-Technologies-and-Design-Standards

[vulnerabilities_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_OOP-Technologies-and-Design-Standards&metric=vulnerabilities

[vulnerabilities_link]: https://sonarcloud.io/summary/overall?id=Hummel009_OOP-Technologies-and-Design-Standards

[duplicated_lines_density_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_OOP-Technologies-and-Design-Standards&metric=duplicated_lines_density

[duplicated_lines_density_link]: https://sonarcloud.io/summary/overall?id=Hummel009_OOP-Technologies-and-Design-Standards

[reliability_rating_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_OOP-Technologies-and-Design-Standards&metric=reliability_rating

[reliability_rating_link]: https://sonarcloud.io/summary/overall?id=Hummel009_OOP-Technologies-and-Design-Standards

[quality_gate_status_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_OOP-Technologies-and-Design-Standards&metric=alert_status

[quality_gate_status_link]: https://sonarcloud.io/summary/overall?id=Hummel009_OOP-Technologies-and-Design-Standards

[technical_debt_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_OOP-Technologies-and-Design-Standards&metric=sqale_index

[technical_debt_link]: https://sonarcloud.io/summary/overall?id=Hummel009_OOP-Technologies-and-Design-Standards

[lines_of_code_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_OOP-Technologies-and-Design-Standards&metric=ncloc

[lines_of_code_link]: https://sonarcloud.io/summary/overall?id=Hummel009_OOP-Technologies-and-Design-Standards
