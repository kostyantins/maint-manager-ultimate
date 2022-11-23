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

## Examples of '.http' Calls
```
POST http://localhost:8080/maints
Content-Type: application/json

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

###
GET http://localhost:8080/maints/all

###
GET http://localhost:8080/maints/1

###
GET http://localhost:8080/maints/identifier?maintIdentifier=MAINT-1

###
DELETE http://localhost:8080/maints/1

###
PUT http://localhost:8080/maints/fixversion/1.1.1/id/1

###
POST http://localhost:8080/comments
Content-Type: application/json

{
  "commentText": "This is MCB maint additional comment",
  "createdData": "2022-10-14",
  "maint": {
    "id": 1,
    "maintIdentifier": "MAINT-1",
    "capabilityId": "1",
    "createdData": "2022-09-14",
    "dueData": "2022-09-15",
    "solvePriorityId": "1",
    "estimate": "5",
    "fixVersion": "2022.08.01",
    "client": "MCB"
  }
}

###
GET http://localhost:8080/comments?maintCommentId=1

###
GET http://localhost:8080/comments/all

###
GET http://localhost:8080/comments/all/identified

###
GET http://localhost:8080/priorities/all

###
POST http://localhost:8080/capabilities
Content-Type: application/json

{
  "id": "4",
  "capabilityName": 2
}

###
GET http://localhost:8080/capabilities?capabilityId=4

###
GET http://localhost:8080/capabilities/all

###
POST http://localhost:8080/priorities
Content-Type: application/json

{
  "id": "4",
  "priorityName": 2
}

###
GET http://localhost:8080/priorities?priorityId=4

###
GET http://localhost:8080/priorities/all
```

## Build docker image:
```mvn spring-boot:build-image```

## Swagger:
```http://localhost:8080/swagger-ui/index.html```