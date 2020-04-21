## Sonarqube

docker run -d --name sonarqube -p 9000:9000 sonarqube:latest
./gradlew sonarqube -Dsonar.projectKey=REST -Dsonar.host.url=http://localhost:9000 -Dsonar.login=