# Resource Schedule Template API

## Data Models

### Template
```json
{
  "id": "string",
  "name": "string",
  "type": "WORKSPACE|MEETING_ROOM|EQUIPMENT",
  "attributes": {
    "hasMonitor": "boolean",
    "deskType": "string",
    "capacity": "number",
    "equipment": "string[]"
  },
  "bookingRules": {
    "minDuration": "string", // e.g. "1h"
    "maxDuration": "string",
    "advanceBooking": "string",
    "cancellationPolicy": "string"
  },
  "pricing": {
    "baseRate": "number",
    "currency": "string",
    "interval": "HOURLY|DAILY|MONTHLY"
  }
}
```

### Resource
```json
{
  "id": "string",
  "templateId": "string",
  "location": {
    "building": "string",
    "floor": "number",
    "room": "string"
  },
  "status": "AVAILABLE|BOOKED|MAINTENANCE",
  "customAttributes": {
    "key": "value"
  }
}
```

### Booking
```json
{
  "id": "string",
  "resourceId": "string",
  "userId": "string",
  "startTime": "ISO8601",
  "endTime": "ISO8601",
  "status": "PENDING|CONFIRMED|CANCELLED",
  "totalPrice": "number",
  "additionalServices": "string[]"
}
```

## API Endpoints

### Templates

```
GET /api/v1/templates
POST /api/v1/templates
GET /api/v1/templates/{id}
PUT /api/v1/templates/{id}
DELETE /api/v1/templates/{id}
```

### Resources

```
GET /api/v1/resources
POST /api/v1/resources
GET /api/v1/resources/{id}
PUT /api/v1/resources/{id}
DELETE /api/v1/resources/{id}
GET /api/v1/resources/{id}/availability
```

### Bookings

```
GET /api/v1/bookings
POST /api/v1/bookings
GET /api/v1/bookings/{id}
PUT /api/v1/bookings/{id}
DELETE /api/v1/bookings/{id}
POST /api/v1/bookings/{id}/confirm
POST /api/v1/bookings/{id}/cancel
```

### Analytics

```
GET /api/v1/analytics/usage
GET /api/v1/analytics/revenue
GET /api/v1/analytics/availability
```

## Error Handling

All endpoints return standard HTTP status codes:
- 200: Success
- 201: Created
- 400: Bad Request
- 401: Unauthorized
- 403: Forbidden
- 404: Not Found
- 500: Internal Server Error

Error response format:
```json
{
  "code": "string",
  "message": "string",
  "details": "object"
}
```