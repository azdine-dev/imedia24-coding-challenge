# iMedia24 Coding challenge

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Official Kotlin documentation](https://kotlinlang.org/docs/home.html)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.3/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.3/gradle-plugin/reference/html/#build-image)
* [Flyway database migration tool](https://flywaydb.org/documentation/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

# Project Name

Briefly describe your project and what it does.

## Table of Contents

- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Build and Run the Docker Image](#build-and-run-the-docker-image)
- [Usage](#usage)
- [Endpoints](#endpoints)
- [Configuration](#configuration)
- [Dockerfile](#dockerfile)
- [Contributing](#contributing)
- [License](#license)

## Getting Started

This section provides instructions on how to get the application up and running using Docker.

### Prerequisites

- Docker: [Install Docker](https://docs.docker.com/get-docker/)

### Build and Run the Docker Image

To build the Docker image and run the application as a container:

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd <project-directory>
   
2. Build the docker image
   docker build -t <image-name> .

3. Run the container:
   docker run -p <host-port>:<container-port> <image-name>
   
   Replace <host-port> with the port you want to use on the host machine (e.g., 8080) 
   and <container-port> with the port your Spring Boot application
   listens on inside the container (e.g., 8080).

4. Access the application:
   Open your web browser and navigate to http://localhost:<host-port>
   to access the running application.