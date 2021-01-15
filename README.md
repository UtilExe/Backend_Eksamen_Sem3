[![Build Status](https://travis-ci.com/UtilExe/Backend_Eksamen_Sem3.svg?branch=main)](https://travis-ci.com/UtilExe/Backend_Eksamen_Sem3)

# Eksamensprojekt, 3 semester januar

## How to use:

Our project consists of a structure where we have our REST classes, and our DTO's where we have request/response fields. 

- Regarding our DTO naming, we have made sure to specify whether we use the DTO for a request, or for a response, as the input name need to match with the API. 

- We have tested all our REST endpoints, where we test the method, and check for a statusCode(200) to make sure the request went well. 

- Remove/Outcomment the if statement in security.SharedSecret:
 if(true){
      return "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA".getBytes();
}
The code is there for protection, to set a secret base64 encoding for the signature of the token.
This ensures that our token does not change signature upon development, meaning we don't have to switch the token every X time we deploy/run the project, but should not be used for production.

- The Java class, SetupTestUsers is specified as .gitignore, to makes ure we don't push the users and passwords up to Github. 
Therefore, the class needs to be created manually, if not already done. 