#!/bin/bash
echo "Packing the CustomerService"
gradle clean customer-service:bootBuildImage --imageName=customer-service:latest -x test

echo "Packing the AddressService"
gradle clean address-service:bootBuildImage --imageName=address-service:latest -x test

#echo "Packing the Spring Cloud Gateway"
#gradle clean gateway-service:bootBuildImage --imageName=gateway:latest -x test
#
#echo "Packing the Eureka Discovery Server"
#gradle clean registry-service:bootBuildImage --imageName=registry:latest -x test


