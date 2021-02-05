#!/bin/bash
echo "Build the CustomerService"
gradle customer-service:clean customer-service:bootJar

echo "Build the AddressService"
gradle address-service:clean address-service:bootJar

echo "Build the Spring Cloud Gateway"
gradle gateway-service:clean gateway-service:bootJar

echo "Build the Eureka Discovery Server"
gradle eureka-service:clean eureka-service:bootJar
