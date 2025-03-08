# microservices-architectures-patterns-testing-ddd-events-eda-cqrs-saga-cicd-docker-kubernetes

# 🔷  Project view
<div align="center">
<img src="./diagrams/2-feat-initialize-auth-service-java.gif" alt="Architecture Diagram" style="max-width: 800px; max-height: 400px; width: 100%; height: auto;">
</div>

# 🔷 Branches


##  🌵 9-feat/add-create-administrator-customer-user

<div align="center">

### [View user-create-admin-customer-endpoints-diagram.plantuml](./uml/9-user-create-admin-customer-endpoints-diagram.plantuml)

<img src="./diagrams/9-user-create-admin-customer-endpoints-diagram-g.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<br>
<br>

<img src="./diagrams/9-user-create-admin-customer-endpoints-diagram-01.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<img src="./diagrams/9-user-create-admin-customer-endpoints-diagram-02.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<img src="./diagrams/9-user-create-admin-customer-endpoints-diagram-03.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<img src="./diagrams/9-user-create-admin-customer-endpoints-diagram-04.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<img src="./diagrams/9-user-create-admin-customer-endpoints-diagram-05.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<img src="./diagrams/9-user-create-admin-customer-endpoints-diagram-06.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<img src="./diagrams/9-user-create-admin-customer-endpoints-diagram-07.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<img src="./diagrams/9-user-create-admin-customer-endpoints-diagram-08.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
</div>

<div align="left">
⚡️Add implementation of command and query bus.<br>
⚡️Add user domain.<br>
⚡️Implement Visitor, Abstract Factory, and Null Object patterns.<br>
⚡️Add create_user command.<br>
⚡️Add SearchById query.<br>
⚡️Add exceptions: UserAlreadyExistsException and UserNotFoundException.<br>
⚡️Add endpoints usr_create_administrator and usr_create_customer in Postman collection.<br>
</div>




##  🌵 8-feat/gateway-authorization-routes-and-header-config

<div align="center">

### [View gateway-module-diagram.plantuml](./uml/8-gateway-module-diagram.plantuml)

<img src="./diagrams/8-feat-gateway-authorization-routes-and-header-config-g1.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<img src="./diagrams/8-feat-gateway-authorization-routes-and-header-config-g2.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<br>
<br>

<img src="./diagrams/8-feat-gateway-authorization-routes-and-header-config-01.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<img src="./diagrams/8-feat-gateway-authorization-routes-and-header-config-02.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<img src="./diagrams/8-feat-gateway-authorization-routes-and-header-config-03.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
</div>

<div align="left">
⚡️Add exceptions: GatewayException, GatewayForbiddenAccessException, GatewayTokenExpiredException, GatewayTokenInvalidException, and GatewayTokenMissingException.<br>
⚡️Configure authentication and authorization for the routes in the microservices system.<br>
</div>


##  🌵 7-feat/add-refresh-token-auth

<div align="center">

### [View auth-refresh-token-endpoint-diagram.plantuml](./uml/7-auth-refresh-token-endpoint-diagram.plantuml)

<img src="./diagrams/7-feat-add-refresh-token-auth-g.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<br>
<br>

<img src="./diagrams/7-feat-add-refresh-token-auth-01.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<img src="./diagrams/7-feat-add-refresh-token-auth-02.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<img src="./diagrams/7-feat-add-refresh-token-auth-03.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
</div>

<div align="left">
⚡️Add command: ValidateToken.<br>
⚡️Add POST athj_refresh_token to postman_collection.<br>
⚡️Add exception: UserAuthTokenExpiredException.<br>
</div>

##  🌵 6-feat/add-logout-auth

<div align="center">

### [View auth-logout-diagram.plantuml](./uml/6-auth-logout-diagram.plantuml)

<img src="./diagrams/6-feat-add-logout-auth-g.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<br>
<br>

<img src="./diagrams/6-feat-add-logout-auth-01.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<img src="./diagrams/6-feat-add-logout-auth-02.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
</div>

<div align="left">
⚡️Add commands: ChangePassword and UserUpdate.<br>
⚡️Add custom Logout handler extending LogoutHandler.<br>
⚡️Add POST athj_logout endpoint to postman_collection.<br>
⚡️Update SecurityConfiguration.<br>
⚡️Update TokenJpaRepository.<br>
⚡️Add UML class diagram: auth-logout-diagram.<br>
</div>

##  🌵 5-feat/add-change-password-auth

<div align="center">

### [View auth-change-password-endpoint-diagram.plantuml](./uml/5-auth-change-password-endpoint-diagram.plantuml)
<img src="./diagrams/5-feat-add-change-password-auth-g.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<br>
<br>

<img src="./diagrams/5-feat-add-change-password-auth-01.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<img src="./diagrams/5-feat-add-change-password-auth-02.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<img src="./diagrams/5-feat-add-change-password-auth-03.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<img src="./diagrams/5-feat-add-change-password-auth-04.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
</div>

<div align="left">
⚡️Add commands: ChangePassword and UserUpdate.<br>
⚡️Add IClaimPort.<br>
⚡️Modify ports: IPasswordPort, IUserAuthPersistencePort.<br>
⚡️Add exceptions: UserAuthNewPasswordConfirmationInvalidException, UserAuthPasswordConfirmationInvalidException.<br>
⚡️Create endpoint: PUT athj_change_password.<br>
</div>

##  🌵 4-feat/add-user-authentication-endpoint

<div align="center">

### [View auth-authenticate-endpoint-diagram.plantuml](./uml/4-auth-authenticate-endpoint-diagram.plantuml)

<img src="./diagrams/4-feat-add-user-authentication-endpoint-g1.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<img src="./diagrams/4-feat-add-user-authentication-endpoint-g2.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<br>
<br>

<img src="./diagrams/4-feat-add-user-authentication-endpoint-01.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<img src="./diagrams/4-feat-add-user-authentication-endpoint-02.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<img src="./diagrams/4-feat-add-user-authentication-endpoint-03.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<img src="./diagrams/4-feat-add-user-authentication-endpoint-04.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<img src="./diagrams/4-feat-add-user-authentication-endpoint-05.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<img src="./diagrams/4-feat-add-user-authentication-endpoint-06.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
</div>

<div align="left">
⚡️Add commands: AuthenticationToken, TokenRevoke, and TokenSave.<br>
⚡️Add queries: UserSearchByEmail, TokenGenerate, and TokenRefresh.<br>
⚡️Add ports: ITokenPersistencePort, ITokenPort, and IAuthenticationPort.<br>
⚡️Add exceptions: TokenRevocationFailedException, TokenSaveFailedException, UserAuthUserNotFoundException, and ValidTokenNotFoundException.<br>
⚡️Create the Token domain.<br>
⚡️create POST endpoint athj_authenticate in postman_collection.<br>

</div>

##  🌵 3-feat/add-user-registration-auth

<div align="center">

### [View auth-register-endpoint-diagram.plantuml](./uml/3-auth-register-endpoint-diagram.plantuml)

<img src="./diagrams/3-auth-register-endpoint-diagram-g.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<br>
<br>

<img src="./diagrams/3-auth-register-endpoint-diagram-1.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
<img src="./diagrams/3-auth-register-endpoint-diagram-2.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
</div>

<div align="left">
⚡️Create shared classes in common-java-context.<br>  
⚡️ Implement Onion Architecture for better separation of concerns.<br>
⚡️Apply Domain Driven Design principles to structure the project.<br>
⚡️Add command and query bus for CQRS.<br>
⚡️Define the user and user_vw aggregates.<br>
⚡️Create the UserAuthRegister command and EncryptPassword query along with the ports: IPasswordPort and IUserAuthPersistencePort under the user domain.<br>
⚡️Add the UserSearchByEmail query and IUserViewPersistencePort port in the user_vw domain.<br>
⚡️Create a centralized exception controller.<br>
⚡️Integration to monitoring tools for better observability.<br>
⚡️create PUT endpoint athj_user_register in postman_collection.<br>
</div>

##  🌵 2-feat/initialize-auth-service-java
<div align="center">

### [View microservices-component-diagram](./uml/1-microservices-component-diagram.puml)
<img src="./diagrams/2-feat-initialize-auth-service-java.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 400px; width: 100%; height: auto;">
</div>

##  🌵 1-feat/add-configuration-discovery-gateway-observability-monitoring
<div align="center">

### [View microservices-component-diagram](./uml/1-microservices-component-diagram.puml)
<img src="./diagrams/1-feat-add-configuration-discovery-gateway-observability-monitoring.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 400px; width: 100%; height: auto;">
</div>