MVNW := mvn
DC_DIR := $(CURDIR)
DOCKERHUB_USERNAME := andresmromerol
DOCKER_IMAGE_VERSION := v1
INFRASTRUCTURE_PATH := docker-compose

USER_CONTEXT := user-context
CONFIGURATION_SERVER := configuration-server
DISCOVERY_SERVER := discovery-server
GATEWAY_SERVER := gateway
AUTH_SERVICE_JAVA := auth-service-java
COMMON_JAVA_CONTEXT := common-java-context

.PHONY: f

f: format
db: docker-build
is: infrastructure-start
c: clean
i: install

run: c f i

f: format
c: clean

run: c f

clean:
	cd $(CONFIGURATION_SERVER) && mvn clean
	cd $(DISCOVERY_SERVER) && mvn clean
	cd $(GATEWAY_SERVER) && mvn clean
	cd $(USER_CONTEXT) && mvn clean
	cd $(AUTH_SERVICE_JAVA) && mvn clean
	cd $(COMMON_JAVA_CONTEXT) && mvn clean


format:
	cd $(CONFIGURATION_SERVER) && mvn spotless:apply
	cd $(DISCOVERY_SERVER) && mvn spotless:apply
	cd $(GATEWAY_SERVER) && mvn spotless:apply
	cd $(USER_CONTEXT) && mvn spotless:apply
	cd $(AUTH_SERVICE_JAVA) && mvn spotless:apply
	cd $(COMMON_JAVA_CONTEXT) && mvn spotless:apply

install:
	cd $(CONFIGURATION_SERVER) && $(MVNW) clean install -DskipTests
	cd $(DISCOVERY_SERVER) && $(MVNW) clean install -DskipTests
	cd $(GATEWAY_SERVER) && $(MVNW) clean install -DskipTests
	cd $(USER_CONTEXT) && $(MVNW) clean install -DskipTests
	cd $(AUTH_SERVICE_JAVA) && $(MVNW) clean install -DskipTests

docker-build:
	cd $(USER_CONTEXT) && docker build . -t $(DOCKERHUB_USERNAME)/$(USER_CONTEXT):$(DOCKER_IMAGE_VERSION)
	cd $(CONFIGURATION_SERVER) && docker build . -t $(DOCKERHUB_USERNAME)/$(CONFIGURATION_SERVER):$(DOCKER_IMAGE_VERSION)
	cd $(DISCOVERY_SERVER) && docker build . -t $(DOCKERHUB_USERNAME)/$(DISCOVERY_SERVER):$(DOCKER_IMAGE_VERSION)
	cd $(GATEWAY_SERVER) && docker build . -t $(DOCKERHUB_USERNAME)/$(GATEWAY_SERVER):$(DOCKER_IMAGE_VERSION)
	cd $(AUTH_SERVICE_JAVA) && docker build . -t $(DOCKERHUB_USERNAME)/$(AUTH_SERVICE_JAVA):$(DOCKER_IMAGE_VERSION)

infrastructure-start:
	cd $(INFRASTRUCTURE_PATH) && docker-compose up -d