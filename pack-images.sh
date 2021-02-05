#!/bin/bash
echo "Packing the CustomerService"
gradle customer-service:bootBuildImage --imageName=customer-service:latest -x test

echo "Packing the AddressService"
gradle address-service:bootBuildImage --imageName=address-service:latest -x test

echo "Packing the Spring Cloud Gateway"
gradle gateway-service:bootBuildImage --imageName=gateway-service:latest -x test

echo "Packing the Eureka Discovery Server"
gradle eureka-service:bootBuildImage --imageName=eureka-service:latest -x test


