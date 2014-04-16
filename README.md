Licensed under the Apache License 2.0.

Service for 10

To run the service you can do: mvn spring-boot:run

The service listens on 9000 and the management port is at 9001

Examples: 
Create user: 
curl -X POST --data "socialId=j121123&x=102.8324334524412&y=1.104232321447769" http://localhost:9000/user

Get the user: 
curl -i "http://localhost:9000/user/-6489553358112581005"

List users nearby (ten users):
curl -i "http://localhost:9000/user/-6489553358112581005/ten"

List the magic user (farthest user for now): 
curl -i "http://localhost:9000/user/-6489553358112581005/eleven"
