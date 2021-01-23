#!/bin/bash

echo "Packing the Spring Cloud Gateway"
gradle gateway-service:bootBuildImage --imageName=gateway:latest

echo "Packing the Eureka Discovery Server"
gradle registry-service:bootBuildImage --imageName=registry:latest

echo "Packing the CustomerService"
gradle customer-service:bootBuildImage --imageName=customer:latest

