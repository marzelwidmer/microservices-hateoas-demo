#!/bin/bash
echo "Packing the CustomerService"
gradle clean customer-service:bootBuildImage --imageName=customer-service:latest -x test

echo "Packing the AddressService"
gradle clean address-service:bootBuildImage --imageName=address-service:latest -x test

echo "Packing the Spring Cloud Gateway"
gradle clean gateway-service:bootBuildImage --imageName=gateway-service:latest -x test

echo "Packing the Eureka Discovery Server"
gradle clean eureka-service:bootBuildImage --imageName=eureka-service:latest -x test


