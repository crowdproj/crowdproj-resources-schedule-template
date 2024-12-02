# Тестовая стратегия: Resource Schedule Template Service

## 1. Функциональное тестирование

### 1.1 Тестирование создания шаблонов расписания
**Критерий приемки**: Администратор может успешно создавать шаблоны расписания

1. **Позитивное тестирование создания базового шаблона**
   ```json
   {
     "templateName": "Стандартная рабочая неделя",
     "workingHours": {
       "monday": {"start": "09:00", "end": "18:00"},
       "tuesday": {"start": "09:00", "end": "18:00"}
     },
     "timeSlotDuration": 30,
     "weekends": ["SATURDAY", "SUNDAY"]
   }
   ```
    - *Ожидаемый результат*: Успешное создание шаблона
    - *Метрики*:
        - Время создания < 1с
        - Корректность всех временных интервалов
        - Валидность JSON-схемы

2. **Негативное тестирование создания шаблона**
    - *Входные параметры*: некорректные временные интервалы, пересекающиеся слоты
    - *Ожидаемый результат*: информативные сообщения об ошибках
    - *Метрики*:
        - Полнота валидации
        - Понятность сообщений об ошибках

### 1.2 Тестирование управления временными слотами
**Критерий приемки**: Система корректно управляет временными слотами

1. **Тестирование создания слотов**
   ```json
   {
     "templateId": "standard-week",
     "date": "2024-02-20",
     "slots": [
       {"start": "09:00", "end": "09:30"},
       {"start": "09:30", "end": "10:00"}
     ]
   }
   ```
    - *Метрики*:
        - Корректность генерации слотов
        - Отсутствие пересечений
        - Соблюдение правил шаблона

2. **Тестирование правил доступности**
    - *Проверка*: праздники, выходные, технические перерывы
    - *Метрики*:
        - Корректность блокировки слотов
        - Обработка исключений

### 1.3 Тестирование API
**Критерий приемки**: API предоставляет корректный доступ к функциональности

```java
@Test
public void testGetAvailableSlots() {
    String templateId = "standard-week";
    LocalDate date = LocalDate.now();
    
    List<TimeSlot> slots = templateService.getAvailableSlots(templateId, date);
    
    assertNotNull(slots);
    assertTrue(slots.size() > 0);
    slots.forEach(slot -> {
        assertTrue(slot.getStart().isAfter(LocalTime.of(9, 0)));
        assertTrue(slot.getEnd().isBefore(LocalTime.of(18, 0)));
    });
}
```

## 2. Нефункциональное тестирование

### 2.1 Производительность
1. **Нагрузочное тестирование API**
   ```kotlin
   @Test
   fun loadTest() {
       val requests = 1000
       val template = "standard-week"
       
       measureTimeMillis {
           coroutineScope {
               repeat(requests) {
                   launch {
                       templateService.getAvailableSlots(template, LocalDate.now())
                   }
               }
           }
       }.let { time ->
           assertTrue(time < 5000) // 5 секунд на 1000 запросов
       }
   }
   ```

2. **Стресс-тестирование**
    - Проверка работы с большим количеством шаблонов
    - Тестирование долгосрочных периодов
    - Обработка конкурентных запросов

### 2.2 Интеграционное тестирование
```java
@Test
public void testCalendarIntegration() {
    // Тест интеграции с внешним календарем
    CalendarSync calendarSync = new CalendarSync();
    Template template = templateService.getTemplate("standard-week");
    
    List<Holiday> holidays = calendarSync.syncHolidays(template);
    
    assertNotNull(holidays);
    assertTrue(holidays.size() > 0);
    // Проверка корректности обработки праздников
}
```

## 3. Автоматизация тестирования

### 3.1 Unit-тесты
```java
public class TemplateValidatorTest {
    @Test
    public void testTimeSlotValidation() {
        TimeSlot slot = new TimeSlot("09:00", "10:00");
        Template template = new Template();
        template.addSlot(slot);
        
        ValidationResult result = validator.validate(template);
        assertTrue(result.isValid());
    }
}
```

### 3.2 Интеграционные тесты
```kotlin
@Test
fun testHolidayHandling() {
    val template = Template(
        name = "standard-week",
        workingHours = WorkingHours(...)
    )
    
    template.addHoliday(LocalDate.now())
    
    val slots = template.getAvailableSlots(LocalDate.now())
    assertTrue(slots.isEmpty())
}
```

## 4. Мониторинг и логирование

### 4.1 Метрики для отслеживания
- Время генерации слотов
- Количество конфликтов в расписании
- Успешность интеграции с календарями
- Использование популярных шаблонов

### 4.2 Логирование
```json
{
  "template_operation": {
    "type": "CREATE",
    "templateId": "standard-week",
    "timestamp": "2024-02-20T10:00:00",
    "status": "SUCCESS",
    "executionTime": 150
  }
}
```

## 5. Инструменты

### 5.1 Основные инструменты
- JUnit 5 / Kotest для модульных тестов
- TestContainers для интеграционных тестов
- Gatling для нагрузочного тестирования
- OpenSearch + Fluent Bit для логирования
- Grafana для мониторинга

### 5.2 CI/CD интеграция
```yaml
test:
  stage: test
  script:
    - ./gradlew test
    - ./gradlew integrationTest
    - ./gradlew performanceTest
  artifacts:
    reports:
      junit: build/test-results/test/TEST-*.xml
```

## 6. Критерии качества

### 6.1 Покрытие кода
- Общее покрытие > 80%
- Покрытие бизнес-логики > 90%
- Покрытие API endpoints 100%

### 6.2 Производительность
- Время ответа API < 100ms
- Успешность запросов > 99.9%
- Время генерации слотов < 1с