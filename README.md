
## NoteBook Manager

NoteBook Manager is a tiny application that performs CRUD jobs on Stock Entity via Rest Webservices.
It exposes 5 endpoint as following list :

- GET /api/notebooks (get a list of stocks)
- POST /api/notebooks (create a notebook)
- GET /api/notebooks/1 (get one notebook from the list)
- PATCH /api/notebooks/1 (update the price of a single notebook)
- DELETE /api/notebooks/1 (delete a single notebook)

## How to setup
It is docker based application. Running below command in project's directory builds 2 containers, NotebookService and Mysql containers with initial records:

```bash
docker-compose up
```


## How to use
This application is Restful and it follows OpenAPI specification in API documentation. Thanks to SwaggerUI, you can see endpoints documentation in a graphical user interface and try their functionality and see the response. After running the containers, you can access the application links as :

- Application Rest services : http://localhost:8090/api/notebooks
- SwaggerUI : http://localhost:8090/swagger-ui.html



## Test

Tests are written with H2 in-memory database with initial records.