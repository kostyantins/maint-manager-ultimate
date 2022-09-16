# Maint manager application
## The purpose of the project is to create ORM system to collaborate and manage maint tickets.

## Contents:
**[1. Presentation layer](#presentation_layer)**

**[2. Service layer](#service_layer)**

**[3. Data access layer](#data_access_layer)**

## Presentation layer
//TODO

## Service layer
//TODO

## Data access layer
//TODO

## Examples of Calls
```
POST http://localhost:8080/maint/create
Content-Type: application/json

{
"maintIdentifier": "MAINT-1",
"capabilityId": "1",
"createdData": "2022-09-14",
"dueData": "2022-09-15",
"solvePriorityId": "1",
"estimate": "5",
"fixVersion": "2022.08.01",
"client": "MCB"
}
```
or with comments
```
{
  "maintIdentifier": "MAINT-1",
  "capabilityId": "1",
  "createdData": "2022-09-14",
  "dueData": "2022-09-15",
  "solvePriorityId": "1",
  "estimate": "5",
  "fixVersion": "2022.08.01",
  "client": "MCB",
  "comments": [
    {
      "commentText": "This is MCB maint"
    }
  ]
}
```
###
```
GET http://localhost:8080/maint/get/all
```

###
```
GET http://localhost:8080/maint/get?maintId={maintId}
```

###
```
GET http://localhost:8080/maint/get/identifier?maintIdentifier={maintIdentifier}
```