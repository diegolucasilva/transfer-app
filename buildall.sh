./customer-service/gradlew -p customer-service &
./account-service/gradlew -p account-service &
./money-transfer-service/gradlew -p money-transfer-service &
./notification-service/gradlew -p notification-service

docker-compose up --build
aws sqs --endpoint-url=http://localhost:4566 create-queue --queue-name notification-queue-sqs --region sa-east-1
