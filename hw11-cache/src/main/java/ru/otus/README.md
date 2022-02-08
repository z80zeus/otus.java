Консольный вывод программы.

Сравнительное тестирование производится на двух таблицах:
* clients - без кэширования,
* managers - с кэшированием.

Каждая таблица сначала заполняется в одном цикле, затем читается в другом.

1. Запись и чтение таблицы клиентов:  


    Creating 10000 clients without caching... well done for 16691 ms
    Reading created 10000 clients from DB... well done for 18175 ms  


2. Повторное чтение таблицы клиентов показало десятикратный рост, что видимо объясняется кэшированием на стороне БД:  


    Reading created 10000 clients from DB again... well done for 1714 ms  
  

3. Запись таблицы менеджеров занимает примерно столько же времени, как и запись клиентов: 


    Creating 10000 managers with caching... well done for 18227 ms  
    Cache max size: 9964  

4. Чтение менеджеров происходит в 700 раз быстрее чтения клиентов из-за крайне малого количества промахов кэша:


    Reading created 10000 managers from the cache... well done for 25 ms  
    Cache missing: 36  

5. Вызов сборщика мусора, который очищает WeakHashMap.


    Call for garbage collector... well done for 10 ms  
    Cache size is 0  

6. Чтение менеджеров при 100% промахе кэша занимает примерно столько же времени, сколько читаются клиенты,
кэшированные базой данных (см.п.2):


    Reading created 10000 managers from the cache again... well done for 1511 ms  
    Cache missing: 10000

