# Shortener API

#### This API makes the unique short URLs.

##### There are 3 Endpoints:

 - Makes short URL from original and adds to DB.
 - Returns original URL by shortened URL.
 - Redirects user by clicking on short URL to original one.
 
To edit server and port go to `application.properties` file.

All shortened URL are unique and have TTL for 10 minutes. Data exchange is in JSON format, for example:

### PostMapping: 
```sh
{ 
    "original" : "http://foo.com"
}
```

### GetMapping:
```sh
{ 
    "shortened" : "http://hostname.com/n" 
}
```

### Response Example
```sh
{
    "id": 1,
    "original": "http://foo.bar",
    "shortened": "http://hostname.com:8080/n",
    "timestamp": "2020-07-03T13:33:53.363+00:00"
}
```
Also, see swagger-file `api-documentation` in the root.

P.S: This API only for my personal CV.