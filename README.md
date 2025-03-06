# microservices-architectures-patterns-testing-ddd-events-eda-cqrs-saga-cicd-docker-kubernetes

# 🔷  Project view
<div align="center">
<img src="./diagrams/2-feat-initialize-auth-service-java.gif" alt="Architecture Diagram" style="max-width: 800px; max-height: 400px; width: 100%; height: auto;">
</div>

# 🔷 Branches

##  🌵 3-feat/add-user-registration-auth

<div align="center">

### [View auth-register-endpoint-diagram.plantuml](./uml/auth-register-endpoint-diagram.plantuml)
<img src="./diagrams/3-auth-register-endpoint-diagram.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 500px; width: 100%; height: auto;">
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

### [View microservices-component-diagram](./uml/microservices-component-diagram.puml)
<img src="./diagrams/2-feat-initialize-auth-service-java.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 400px; width: 100%; height: auto;">
</div>

##  🌵 1-feat/add-configuration-discovery-gateway-observability-monitoring
<div align="center">

### [View microservices-component-diagram](./uml/microservices-component-diagram.puml)
<img src="./diagrams/1-feat-add-configuration-discovery-gateway-observability-monitoring.png" alt="Architecture Diagram" style="max-width: 800px; max-height: 400px; width: 100%; height: auto;">
</div>