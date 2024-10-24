# Проектная работа Java Advanced 2024

## Описание
Набор микросервисов для мониторинга залогов

1. discovery
2. gateway
3. dictionary - сервис для хранения и предоставления справочной информации 
4. client - сервис для хранения и предоставления информации о клиентах 
5. contract - сервис для хранения и предоставления информации о договорах
6. pledge - сервис для хранения и предоставления информации об объектах залога
7. pledgeMonitoring - сервис для расчета периодичности и видов работ
8. task - сервис для выставления задач

## стек 
postgres  
kafka  
zookeeper  
openfeign    
grpc  
prometheus  
grafana  
loki  

## Для запуска 

```mvn compile```

```mvn package```

```docker-compose up```

1.  Создание пользователя 
curl POST http://localhost:9000/api/client
Content-Type: application/json
{
  "name": "name",
  "inn": "inn"
}

2. Создание договора
curl POST http://localhost:9000/api/contract
Content-Type: application/json
{
  "name" : "б/н",
  "clientId": "12ef3fd7-3f2e-43a7-87a9-e5eeb9d3fd6b",
  "contractType": "9e418a78-acc8-4664-8149-77b07a08a0f9",
  "amount": "100"
}

3.  Создание объекта залога
curl POST http://localhost:9000/api/pledge
Content-Type: application/json
{
    "name": "Залог",
    "contractId": "c7eaebf5-3355-4a15-959d-a88523b0eb8b",
    "pledgeCost": "10.00" ,
    "contractCost": "10.12",
    "estimatedCost" : "123123.123",
    "liquidityType" : "c0241a63-b189-42c5-880e-58ab5f73a08e",
    "qualityType" : "eeb01a6c-0d20-4146-a8db-76dfb86c70d9",
    "pledgeType" : "1e163bba-5e03-42d4-974d-87cbf2a6cddd"
}

4. Получение списка работ по договору
curl GET http://localhost:9000/api/examinations/520e625b-8ca2-4b2a-aa2c-335682cce431

5. Получение списка задач по договору
curl GET http://localhost:9000/api/task/520e625b-8ca2-4b2a-aa2c-335682cce431

6. Обновление статуса задачи
curl PATCH http://localhost:9000/api/examinations
Content-Type: application/json
{
    "examinationId": "1274240b-84f7-4a98-b6eb-9a2c4ebfe6ae",
    "statusId" : "810d2cc2-81ae-4c79-a741-5cfe0fe286f4"
}

7.  Закрытие задачи
curl PATCH http://localhost:9000/api/task
Content-Type: application/json
{
    "id": "e4e05261-6230-4bd7-83ca-b639da883d94",
    "taskStatus" : "810d2cc2-81ae-4c79-a741-5cfe0fe286f4"
}


# grafana
localhost:3000











--------




docker run -d -p 5001:5000 --restart=always --name registry registry:2

helm install prostgres-helm ./postgres-helm


docker build -t client_image .  
docker tag client_image:latest localhost:5001/client_image:latest  
docker push localhost:5001/client_image:latest

docker build -t contract_image .  
docker tag contract_image:latest localhost:5001/contract_image:latest  
docker push localhost:5001/contract_image:latest  

docker build -t dictionary_image .  
docker tag dictionary_image:latest localhost:5001/dictionary_image:latest  
docker push localhost:5001/dictionary_image:latest

docker build -t discovery_image .  
docker tag discovery_image:latest localhost:5001/discovery_image:latest  
docker push localhost:5001/discovery_image:latest  

docker build -t gateway_image .  
docker tag gateway_image:latest localhost:5001/gateway_image:latest  
docker push localhost:5001/gateway_image:latest

docker build -t pledge_image .  
docker tag pledge_image:latest localhost:5001/pledge_image:latest  
docker push localhost:5001/pledge_image:latest

docker build -t rootapp_image .  
docker tag rootapp_image:latest localhost:5001/rootapp_image:latest  
docker push localhost:5001/rootapp_image:latest

helm % kubectl apply -f rolebinding.yaml


helm install discovery-helm ./discovery-helm  
helm install gateway-helm ./gateway-helm  
helm install loki-helm ./loki-helm  
helm install client-helm ./client-helm  
helm install contract-helm ./contract-helm  
helm install grafana-helm ./grafana-helm  
helm install prometheus-helm ./prometheus-helm  
helm install dictionary-helm ./dictionary-helm
helm install pledge-helm ./pledge-helm  
helm install root-helm ./root-helm
helm install zookeeper-helm ./zookeeper-helm  
helm install kafka-helm ./kafka-helm


helm delete client-helm
helm delete gateway-helm
helm delete discovery-helm
helm delete contract-helm
helm delete grafana-helm
helm delete prometheus-helm
helm delete dictionary-helm
helm delete pledge-helm
helm delete root-helm
helm delete zookeeper-helm
helm delete kafka-helm

kubectl get pods -o wide

kubectl port-forward grafana-5d7f65bcf4-nwpsp 3000:3000
kubectl port-forward prometheus-847544cfbc-h29c9 9090:9090
kubectl port-forward discovery-service-685ddb6c87-k45hs 8080:8080

kubectl delete pods 

sudo lsof -i :8080
sudo kill -9 45393
