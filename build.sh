#!/bin/bash
echo "Build the CustomerService"
gradle customer-service:clean customer-service:test customer-service:build

echo "Build the AddressService"
gradle address-service:clean address-service:test address-service:build

echo "Build the Spring Cloud Gateway"
gradle gateway-service:clean gateway-service:test gateway-service:build

echo "Build the Eureka Discovery Server"
gradle eureka-service:clean eureka-service:build
