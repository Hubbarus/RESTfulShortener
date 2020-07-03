This API makes short the unique short URLs.

There are 3 Endpoints:
1. Used for making short URL from original and adding to DB.
2. Returns original URL by shortened one.
3. Redirects user, when click on short URL to original one.

To edit server and port go to application.properties.

All shortened URL are unique and have TTL for 10 minutes.
Data exchange is in JSON format, for example:

1. PostMapping: 
 {
     "original" : "http://foo.com"
 }
 
 2. GetMapping: 
 {
     "shortened" : "http://hostname.com/n"
 }
 
Also, see swagger-file "api-documentation" in the root.

P.S: This API only for my personal CV.