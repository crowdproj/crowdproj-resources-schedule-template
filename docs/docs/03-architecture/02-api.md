# Resource Schedule Template API

## Entities

#### DTO Example

```kotlin
data class ScheduleTemplateDto(
    val id: String,
    val name: String,
    val description: String?,
    val type: TemplateType,
    val workingHours: WorkingHoursDto,
    val timeSlotDuration: Int,
    val weekends: Set<DayOfWeek>,
    val holidays: Set<LocalDate>,
    val breaks: List<BreakDto>,
    val version: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val status: TemplateStatus
)

enum class TemplateType {
    STANDARD,      
    EXTENDED,      
    TWENTY_FOUR_SEVEN, 
    CUSTOM       
}

enum class TemplateStatus {
    DRAFT,      
    ACTIVE,     
    INACTIVE,  
    ARCHIVED 
}
```

- id - уникальный идентификатор шаблона
- name - имя шаблона
- descriptions - описание шаблона
- type - тип шаблона (пока, что их всего 4)
- workingHours - рабочие часы 
- timeSlotDuration - длительность одного таймслота в минутах
- weekends - выходные дни
- holidays - праздничные дни
- breaks - перерывы
- version - версия шаблона
- createdAt - дата создания шаблона
- updatedAt - дата последнего обновления шаблона
- status - статус шаблона

## API
1. CRUDS(Create, Read, Update, Delete, Search)
