# hello-ontology
This is an simple example that stores or deletes an employee entity into or from a RDF repository via REST API endpoints. It includes the following functionalities (note that an authentication needed: **admin / admin**):
*  Create an employee  
    POST - http://localhost:8080/employees  
    e.g. request body may look like this:
    ```
    {
      "firstname": "Will",
      "surname": "Smith",
      "department": {
          "name":"Marketing",
          "company": {
            "name":"Coop"
          }
      }
    }
    ```
*  Delete an employee  
    Specified by an ID which can be found in the headers/Location of the response when creating an employee
*  Get all the statements
*  Download the Ontology in turtle format  
    "http://localhost:8080/ontology/download" will start a download

Further details about the endpoints can be found in Swagger UI (username / password: `admin / admin`): *{server-address}/swagger-ui.html* (e.g.: http://localhost:8080/swagger-ui.html). 
