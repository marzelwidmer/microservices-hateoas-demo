# Docker Compose
## Build Package
```bash
./build.sh
``` 
## Docker-Compose - Build Docker And Start 
```bash
docker-compose build
docker-compose up
```

# Build with Buildpacks
```bash
./pack-images.sh
``` 
## Docker-Compose - Start
```bash
docker-compose up
```

# Test Applications
## Docker Setup
```bash
for i in {1..100}; do; http http://docker/myapp/services/customer forwarded:for='docker;host=docker' ; done
```

```bash
http http://docker/myapp/services/customer/customers forwarded:for='docker.me;host=docker.me' --print=HhBb
http http://docker/myapp/services/customer forwarded:for='docker;host=docker' --print=HhBb
http http://docker/myapp/services/customer/addresses forwarded:for='docker;host=docker' --print=HhBb

```


## POST customer 

```bash
echo '{
  "firstName": "Foo",
  "lastName": "Bar"
}' | http -v post http://docker/myapp/services/customer/customers
```

## Check H2 Database
[http://docker/myapp/services/customer/h2-console/](http://docker/myapp/services/customer/h2-console/)
This is allowed because of the following configuration.
```yaml
  h2:
    console:
      settings:
        web-allow-others: true
```
## Call Customer API
```bash
http http://docker/myapp/services/customer/customers --print=HhBb
Accept: */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Host: docker
User-Agent: HTTPie/2.3.0



HTTP/1.1 200 OK
Content-Type: application/hal+json
Date: Sun, 31 Jan 2021 15:37:47 GMT
transfer-encoding: chunked
{
    "_embedded": {
        "customers": [
            {
                "firstName": "Foo",
                "id": "b2f8f0ab-8fb1-4fa4-8214-964f82562065",
                "lastName": "Bar"
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost/customer/customers"
        }
    }
}
```



# Hal-Browser from Customer
- [/customer/explorer/index.html#uri=http://docker/myapp/services/customer/
](http://docker/myapp/services/customer/explorer/index.html#uri=http://docker/myapp/services/customer/)
- [/customer/browser/index.html#http://docker/myapp/services/customer/](http://docker/myapp/services/customer/browser/index.html#http://docker/myapp/services/customer/)









## Standalone Setup
```bash
http http://localhost:8080/myapp/services/customer/customers forwarded:for='docker;host=docker' --print=HhBb
```
