Мои лабораторные работы для BSUIR/БГУИР (белорусский государственный университет информатики и радиоэлектроники).

Предмет - OOTPiSP/ООТПиСП (объектно-ориентированные технологии программирования и стандарты проектирования).

## Общая информация

Каждая папка в этом репозитории - проект Gradle, который должен быть открыт через IntelliJ IDEA. Проконтролируйте, чтобы версии Gradle JVM и JDK соответствовали указанным ниже.

| Технология | Версия  | Пояснение                                    | Примечание                                                       |
|------------|---------|----------------------------------------------|------------------------------------------------------------------|
| Gradle     | 8.4-bin | Версия системы автоматической сборки         | -                                                                |
| Gradle JVM | 17.0.9  | Версия Java, используемая для запуска Gradle | [Настраивается в переменных средах ОС (JAVA_HOME и Path)][Link1] |
| Kotlin     | 1.9.20  | Версия Kotlin, используемая в проекте        | -                                                                |
| JDK        | 17.0.9  | Версия SDK, используемая в проекте           | [Настраивается в IntelliJ IDEA (Project Structure)][Link2]       |

[Link1]: https://java-lessons.ru/first-steps/java-home#:~:text=Теперь%20щёлкните%20правой%20кнопкой
[Link2]: https://www.jetbrains.com/help/idea/sdk.html#change-module-sdk

## Условия работ

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

## Использование

Запустить скомпилированные jar-файлы двойным нажатием ЛКМ, либо открыть консоль Windows в папке с jar-файлом и выполнить команду `java -jar JarFileName.jar`. Откроется консольное приложение с командами.
