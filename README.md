
For apps using a Hazelcast lock use arguments

java -jar JARNAME-SANPSHOT.jar --spring.profiles.active=hazelcast


For apps using a DB lock 

Run DbServer.java to run server h2 instance

And use arguments

java -jar JARNAME-SANPSHOT.jar --spring.profiles.active=db
