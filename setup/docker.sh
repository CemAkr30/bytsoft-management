#!/bin/bash

echo "Veritabanı kurulumu yapılmaya başlatılıyor"

# PostgreSQL Docker konteynerini başlatma komutu
docker run -d --name postgres-container -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=1 -e POSTGRES_DB=enivermont -p 6000:5432 -v postgres-data:/var/lib/postgresql/data postgres

echo "Veritabanı başarıyla kuruldu."


echo "Redis kurulumu yapılmaya başlatılıyor"

docker run -d -p 6379:6379 --name my-redis redis


echo "Redis başarıyla kuruldu."

