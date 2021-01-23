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
