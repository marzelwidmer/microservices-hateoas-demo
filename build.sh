#!/bin/bash
echo "Build the CustomerService"
gradle customer-service:clean customer-service:build 

echo "Build the AddressService"
gradle address-service:clean address-service:build

#echo "Build the Spring Cloud Gateway"
#gradle gateway-service:build
#
#echo "Build the Eureka Discovery Server"
#gradle registry-service:build