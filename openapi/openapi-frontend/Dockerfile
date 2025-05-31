FROM openjdk:17-alpine

ARG OPENAPI_GENERATOR_VERSION=7.5.0

RUN apk add --no-cache curl unzip && \
    curl -L https://repo1.maven.org/maven2/org/openapitools/openapi-generator-cli/${OPENAPI_GENERATOR_VERSION}/openapi-generator-cli-${OPENAPI_GENERATOR_VERSION}.jar -o /openapi-generator-cli.jar && \
    echo -e '#!/bin/sh\njava -jar /openapi-generator-cli.jar "$@"' > /usr/local/bin/openapi-generator && \
    chmod +x /usr/local/bin/openapi-generator

WORKDIR /openapi
