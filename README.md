
## NoteBook Manager

NoteBook Manager is a sample tiny application that performs CRUD jobs on Notebook Entity via Restful Webservices.

It exposes 5 endpoint as following list :

- GET /api/notebooks (get a list of notebook)
- POST /api/notebooks (create a notebook)
- GET /api/notebooks/1 (get one notebook from the list)
- PATCH /api/notebooks/1 (update the price of a single notebook)
- DELETE /api/notebooks/1 (delete a single notebook)

## How to setup
It is a docker based application. Running below command in project's directory builds 2 running containers, NotebookService and Mysql containers with initial records:

```bash
docker-compose up
```


## How to use
This application is Restful and it follows OpenAPI specification in API documentation. Thanks to SwaggerUI, you can see endpoints documentation in a graphical user interface and try their functionality and see the response. After running the containers, you can access the application links as :

- Application Rest services : http://localhost:8090/api/notebooks
- SwaggerUI : http://localhost:8090/swagger-ui.html



## Test

Tests are written with H2 in-memory database with initial records.


## Link

[This repository belongs to Spring boot Rest Api+swagger+MySQL+Docker tutorial](https://m-shaeri.ir/blog/restful-spring-boot-application-swagger-mysql-docker-a-real-world-example/)
