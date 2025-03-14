Spring Boot Project with JWT Authentication

This project implements a Login and Authentication system using JWT (JSON Web Token) to protect endpoints and ensure that only authenticated users have access to protected resources.

Project Structure

1. JwtAuthenticationEntryPoint

Function: Manages authentication errors. When an unauthenticated request is made, it returns a 401 error.

Location: com.example.springprojeto3.JWT

Key Methods:

commence(...) - Sets the HTTP 401 response with an appropriate error message.

2. JwtAutorizationFilter

Function: Filters HTTP requests to check and validate JWT tokens.

Location: com.example.springprojeto3.JWT

Key Methods:

doFilterInternal(...) - Filters and validates JWT tokens in requests.

toAuthentication(...) - Creates an authentication object based on token information.

3. JWTToken

Function: Represents the JWT token as a Java object.

Location: com.example.springprojeto3.JWT

Key Attributes:

token - String representing the JWT token.

4. JwtUserDetails

Function: Implements UserDetails to store authenticated user information.

Location: com.example.springprojeto3.JWT

Key Attributes:

id - ID of the authenticated user.

usuario - Object containing user data.

5. JwtUserDetailsService

Function: Implements UserDetailsService to fetch user information based on the username.

Location: com.example.springprojeto3.JWT

Key Methods:

loadUserByUsername(...) - Loads user details based on the username.

getTokenAuthenticated(...) - Creates and returns a JWT token for the authenticated user.

6. JWTUtils

Function: Utility for handling JWT tokens.

Location: com.example.springprojeto3.JWT

Key Methods:

createToken(...) - Creates a new JWT token with user information.

getClaimsFromToken(...) - Extracts claims from a JWT token.

getUsernameFromToken(...) - Retrieves the username from a JWT token.

isValidToken(...) - Verifies if a JWT token is valid.

7. AutenticacaoController

Function: Manages the authentication endpoint.

Location: com.example.springprojeto3.Web.Controller

Key Methods:

autenticar(...) - Performs user authentication and generates a JWT token.

8. UsuarioController

Function: Manages user-related operations (CRUD and password updates).

Location: com.example.springprojeto3.Web.Controller

Key Methods:

create(...) - Creates a new user.

getById(...) - Retrieves user information by ID.

updatePassword(...) - Changes a user's password.

getAll(...) - Retrieves the list of all users.

How to Run the Project

Configure the Database: Ensure you have a database configured in application.properties.

Build and Run: Use the following command to build and run the project:

./mvnw spring-boot:run

Test the Endpoints:

Authentication: POST /api/v1/auth

Create User: POST /api/v1/usuarios

Get User by ID: GET /api/v1/usuarios/{id}

Update Password: PATCH /api/v1/usuarios/{id}

List Users: GET /api/v1/usuarios

Requirements

Java 17+

Maven

Spring Boot

JWT (io.jsonwebtoken)

