# Функциональные требования: Resource Schedule Template Service

## 1. Управление временными интервалами

### 1.1. Базовая настройка расписания
- Определение рабочих часов для каждого дня недели
- Установка выходных дней
- Управление праздничными днями
- Настройка длительности стандартного временного слота
- Определение технических перерывов

### 1.2. Гибкое расписание
- Создание нестандартных графиков работы
- Настройка круглосуточного режима
- Управление сезонными изменениями
- Создание специальных режимов работы

### 1.3. Временные слоты
```json
{
  "timeSlotConfig": {
    "minDuration": "number", // в минутах
    "maxDuration": "number",
    "defaultDuration": "number",
    "allowedStartTimes": ["time"],
    "restrictions": {
      "advanceBooking": "duration",
      "maxContinuousBooking": "duration"
    }
  }
}
```

## 2. Управление шаблонами

### 2.1. Создание шаблонов
- Базовые шаблоны рабочих дней
- Шаблоны выходных дней
- Праздничные шаблоны
- Сезонные шаблоны

### 2.2. Настройка параметров шаблона
```json
{
  "templateConfig": {
    "id": "string",
    "name": "string",
    "type": "enum(STANDARD|EXTENDED|24x7|CUSTOM)",
    "schedule": {
      "workingDays": [{
        "dayOfWeek": "string",
        "slots": [{
          "start": "time",
          "end": "time"
        }]
      }],
      "breaks": [{
        "type": "enum(TECHNICAL|LUNCH|CLEANING)",
        "duration": "number",
        "startTime": "time"
      }]
    }
  }
}
```

## 3. Календарное планирование

### 3.1. Управление календарем
- Ведение календаря праздников
- Управление переносами рабочих дней
- Учет региональных особенностей
- Синхронизация с внешними календарями

### 3.2. Правила планирования
```json
{
  "planningRules": {
    "minAdvanceBooking": "duration",
    "maxAdvanceBooking": "duration",
    "cancellationPolicy": {
      "deadline": "duration",
      "penalty": "number"
    },
    "overbookingPolicy": {
      "allowed": "boolean",
      "maxOverbook": "number"
    }
  }
}
```

## 4. REST API

### 4.1. Основные эндпоинты
- `/templates` - управление шаблонами
- `/slots` - работа с временными слотами
- `/calendar` - управление календарем
- `/availability` - проверка доступности

### 4.2. Методы API
```typescript
interface TemplateAPI {
  createTemplate(template: TemplateConfig): Promise<Template>;
  updateTemplate(id: string, template: TemplateConfig): Promise<Template>;
  getTemplate(id: string): Promise<Template>;
  deleteTemplate(id: string): Promise<void>;
  listTemplates(filters: FilterConfig): Promise<Template[]>;
}

interface SlotAPI {
  getAvailableSlots(templateId: string, date: Date): Promise<TimeSlot[]>;
  checkAvailability(templateId: string, start: Date, end: Date): Promise<boolean>;
  reserveSlot(templateId: string, slotId: string): Promise<Reservation>;
}
```

## 5. Валидация и проверки

### 5.1. Валидация шаблонов
- Проверка корректности временных интервалов
- Валидация пересечений слотов
- Проверка логических ограничений
- Валидация бизнес-правил

### 5.2. Проверка доступности
```json
{
  "availabilityCheck": {
    "templateId": "string",
    "date": "date",
    "duration": "number",
    "constraints": {
      "mustStart": "time",
      "mustEnd": "time",
      "preferredTime": "time"
    }
  }
}
```

## 6. Интеграции

### 6.1. Внешние календари
- Интеграция с Google Calendar
- Поддержка iCal формата
- Синхронизация с Office 365
- Обработка конфликтов

### 6.2. События и уведомления
```json
{
  "notifications": {
    "templateUpdated": {
      "type": "event",
      "payload": {
        "templateId": "string",
        "changes": ["string"]
      }
    },
    "slotAvailabilityChanged": {
      "type": "event",
      "payload": {
        "slotId": "string",
        "status": "enum(AVAILABLE|RESERVED|BLOCKED)"
      }
    }
  }
}
```
