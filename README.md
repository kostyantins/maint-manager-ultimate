# Maint manager application
## The purpose of the project is to create ORM system to collaborate and manage maint tickets.

## Contents:
**[1. Presentation layer](#presentation_layer)**

**[2. Service layer](#service_layer)**

**[3. Data access layer](#data_access_layer)**

**[4. Examples of '.http' calls ](#examples_of_http_calls)**

**[5. Build docker image](#build_docker_image)**

**[6. Swagger](#swagger)**

**[7. Containers](#containers)**

## Presentation layer
   - controller
   - swagger

## Service layer
   - dto
   - exceptions
   - exception_handler
   - mappers
   - services

## Data access layer
    - entities
    - enums
    - repositories
    
## Examples of http calls
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

- @Operation - Describe operation or method HTTP for appropriate path.

- @Parameter - Describe one parameter for the operation in OpenAPI.

- @RequestBody - Describe an operation body

- @ApiResponse - Describe an answer on operation

- @Tag - Describe a tag for an operation or for the OpenAPI.

- @Server - Describe a server for the operation or for OpenAPI.

- @Callback - Describe amount or requests

- @Link - Describe development time reference for the response.

- @Schema - Describe input/output data.

- @ArraySchema - Describe input/output data for the type of arrays.

- @Content - Describe schema or an examples for the type of multimedia.

- @Hidden - Describe a resource, operation or property

## Containers:
1. Run DB through docker image - docker-compose.yaml needs to be run:

To start container with postgres
```docker-compose up -d``` 

To shut container with postgres down
```docker-compose down```

2. Start the application - mvn spring-boot:run or through Intellij Idea Run config