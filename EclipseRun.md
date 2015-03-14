# Eclipse configuration #

Run -> External Tool -> Add New Program

Name: Jetty Start | Jetty Stop
Location: $PATH\_MAVEN\bin\mvn.bat
Working Directory: ${workspace\_loc:/seismap}
Arguments: jetty:run  | jetty:stop


Debugg

Remote Java App

Project: seismap
Host: localhost
Port: 4000

