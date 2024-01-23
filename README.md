[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=Hummel009_OOP-Technologies-and-Design-Standards&metric=code_smells)](https://sonarcloud.io/summary/overall?id=Hummel009_OOP-Technologies-and-Design-Standards)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Hummel009_OOP-Technologies-and-Design-Standards&metric=sqale_rating)](https://sonarcloud.io/summary/overall?id=Hummel009_OOP-Technologies-and-Design-Standards)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Hummel009_OOP-Technologies-and-Design-Standards&metric=security_rating)](https://sonarcloud.io/summary/overall?id=Hummel009_OOP-Technologies-and-Design-Standards)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Hummel009_OOP-Technologies-and-Design-Standards&metric=bugs)](https://sonarcloud.io/summary/overall?id=Hummel009_OOP-Technologies-and-Design-Standards)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Hummel009_OOP-Technologies-and-Design-Standards&metric=vulnerabilities)](https://sonarcloud.io/summary/overall?id=Hummel009_OOP-Technologies-and-Design-Standards)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=Hummel009_OOP-Technologies-and-Design-Standards&metric=duplicated_lines_density)](https://sonarcloud.io/summary/overall?id=Hummel009_OOP-Technologies-and-Design-Standards)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Hummel009_OOP-Technologies-and-Design-Standards&metric=reliability_rating)](https://sonarcloud.io/summary/overall?id=Hummel009_OOP-Technologies-and-Design-Standards)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Hummel009_OOP-Technologies-and-Design-Standards&metric=alert_status)](https://sonarcloud.io/summary/overall?id=Hummel009_OOP-Technologies-and-Design-Standards)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=Hummel009_OOP-Technologies-and-Design-Standards&metric=sqale_index)](https://sonarcloud.io/summary/overall?id=Hummel009_OOP-Technologies-and-Design-Standards)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=Hummel009_OOP-Technologies-and-Design-Standards&metric=ncloc)](https://sonarcloud.io/summary/overall?id=Hummel009_OOP-Technologies-and-Design-Standards)

Мои лабораторные работы для BSUIR/БГУИР (белорусский государственный университет информатики и радиоэлектроники).

Предмет - OOTPiSP/ООТПиСП (объектно-ориентированные технологии программирования и стандарты проектирования).

## Общая информация

Этот репозиторий - проект Gradle, который должен быть открыт через IntelliJ IDEA. 

| Технология                             | Версия |
|----------------------------------------|--------|
| Система автоматической сборки Gradle   | 8.5    |
| Java, используемая для запуска Gradle  | 8+     |
| Java, используемая для сборки проекта  | 8+     |
| Java, используемая для запуска проекта | 8+     |
| Kotlin                                 | 1.9.22 |

## Установка

Установка моих проектов Gradle и основы работы с ними примерно одинаковы, так что
смотрите [общую инструкцию](https://github.com/Hummel009/The-Rings-of-Power#readme).

## Условия

Все работы являются консольными программами на тему ООП. Каждая следующая работа является развитием предыдущей. Ретроспективно работы были улучшены и пофикшены, но даже в неисправленном виде были успешно сданы.

Суть всех лабораторных: список объектов, с которым можно взаимодействовать. Поиск по нему, вывод информации, добавление, редактирование. Программа - бесконечный цикл, который запрашивает команды. exit = выход из цикла.

### Лабораторная работа 1

Основы ООП. По условию требовалось реализовать объекты, иерархию классов с наследованием, второстепенные конструкторы, перезапись функций.

В программе есть "магазин транспорта". Транспорт делится на велосипеды и автомобили, велосипеды - на аисты и стелсы, автомобили - на жигули и фольксвагены. Собственно, есть иерархия, конструкторы, наследование, полиморфизм. Программу можно заполнить списком объектов и выводить его. Есть функции, например поиск по цвету или цене.

### Лабораторная работа 2

По условию требовалось добавить к предыдущей лабораторной работе функционал создания объекта прямо во время работы пользователя.

Собственно, добавлена функция добавки своего транспорта. Пользователь вводит тип, цену, цвет. Автомобиль/велосипед добавляется в список и функционирует, как и все остальные итемы.

### Лабораторная работа 3

По условию требовалось добавить к предыдущей лабораторной работе интерфейсы (не графические, а в джавишном смысле implements), а также несколько видов сериализации: xml, json, bin.

Интерфейс добавлен - это автомобиль с улучшением. Методы интерфейса банальные: сеттер и геттер. Интерфейс реально полезен при серилазации json, ведь, если объект имплементит интерфейс "улучшение", то мы сохраняем лишнее поле, а потом так же и десериализуем. Сериализация реализована всякими библиотеками и происходит на каждом шаге. Через try-catch предусмотрена загрузка листа по умолчанию, если файлы данных устарели или не существуют.

### Лабораторная работа 4

По условию требовалось добавить к предыдущей лабораторной работе динамическую загрузку плагина. Плагин либо с функциями, либо с объектами.

Были удалены две из трёх сериализаций из-за их практического неудобства и сложностей при работе с плагином. Очевидно: плагин - это скомпилированный джарник, который нельзя импортировать (по условию). Как тогда делать объекты из класса внутри? Через newInstance(). Используя полный путь к классу. Собственно, это ещё одна переменная, которая и до этого применялась в сериализации json и теперь очень выручает.

Таким образом, плагин (в моём случае - один класс для нового автомобиля, CarRenault) подгружается в том смысле, что мы сохраняем его имя и потом по этому имени ищем автомобиль внутри плагина, если он не был найден внутри нашей программы. Эта логика через try-catch встроена во все места, где создаётся объект.

Этот плагин - плагин с объектами, вовлекающимися в логику программы. Нового функционала в нём нет. Но джарники с функционалом также будут работать, так как в лабораторной работе есть фрагмент кода, осуществляющий поиск класса Loader с функцией load() внутри плагина. Если таковое есть, то код оттуда будет выполнен. В пятой лабораторной работе показано, как это работает.

### Лабораторная работа 5

По условию требовалось добавить к предыдущей лабораторной работе шифрование и дешифрование сериализованных файлов, а также реализовать конвертатор xml -> json и обратно.

Конвертатор реализован, тут ничего интересного рассказать не получится. Что касается шифрации и дешифрации - это лабораторная работа из другого предмета, которую я оформил в виде плагина с новым функционалом. 

Ещё в 4 работе в лабораторной работе был невостребованный фрагмент кода "попытка загрузить новые операции из плагина, если они там есть" - Loader.load(). Теперь это пригодилось: плагин шифрования добавляет две новые функции, которые можно использовать после загрузки плагина - они добавляются в хеш-карту функционала программы.

### Лабораторная работа 6

По условию требовалось применить паттерны. Паттерны в основном присобачены криво, так как работа слишком уж простая. Но они есть.
