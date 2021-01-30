# Docker Compose
```bash
./build.sh
docker-compose build
docker-compose up
```

```bash
for i in {1..100}; do; http http://localhost/customer forwarded:for='docker.me;host=docker.me' ; done
```

```bash
http http://localhost/customer forwarded:for='docker.me;host=docker.me' --print=HhBb

http http://localhost/customer/addresses forwarded:for='docker.me;host=docker.me' --print=HhBb

```








# Build
```bash
./pack-images.sh
```

# Start
```bash
docker-compose up
```

# Test 
Open Browser [http://localhost/registry](http://localhost/registry)

```bash
http localhost/get --print=HhBb
http localhost/customer --print=HhBb
```
## POST customer 

```bash
echo '{
  "firstName": "Foo",
  "lastName": "Bar"
}' | http -v post http://localhost/customer/customers
```

## Check H2 Database
[http://localhost/customer/h2-console/](http://localhost/customer/h2-console/)
This is allowed because of the following configuration.
```yaml
  h2:
    console:
      settings:
        web-allow-others: true
```
## Call Customer API
```bash
http localhost/customer/customers --print=HhBb

GET /customer/customers HTTP/1.1
Accept: */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Host: localhost
User-Agent: HTTPie/2.3.0



HTTP/1.1 200 OK
Content-Type: application/hal+json
Date: Sun, 24 Jan 2021 16:23:23 GMT
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




# Gateway
```bash
gradle gateway-service:bootRun

http localhost:8080/get --print=HhBb


HTTP/1.1 200 OK
Access-Control-Allow-Credentials: true
Access-Control-Allow-Origin: *
Content-Length: 496
Content-Type: application/json
Date: Sat, 23 Jan 2021 18:13:11 GMT
Server: gunicorn/19.9.0

{
    "args": {},
    "headers": {
        "Accept": "*/*",
        "Accept-Encoding": "gzip, deflate",
        "Content-Length": "0",
        "Forwarded": "proto=http;host=\"localhost:8080\";for=\"0:0:0:0:0:0:0:1:54335\"",
        "Hello": "World",
        "Host": "httpbin.org",
        "User-Agent": "HTTPie/2.3.0",
        "X-Amzn-Trace-Id": "Root=1-600c6737-6f620a7e436615ba044eb4dc",
        "X-Forwarded-Host": "localhost:8080"
    },
    "origin": "0:0:0:0:0:0:0:1, 188.62.60.127",
    "url": "http://localhost:8080/get"
}

```
# Registry
```bash
gradle registry-service:bootRun
```


# Customer
```bash
gradle customer-service:bootRun
```
