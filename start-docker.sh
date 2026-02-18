#!/bin/bash

# Script para rodar o Yuni Service com Docker

echo "🔨 Compilando projeto..."
cd /Users/alessandranastassja/Desktop/yuni/yuni-srv
./mvnw clean package -DskipTests -q

if [ $? -eq 0 ]; then
    echo "✅ Build concluído com sucesso!"
    echo "🐳 Iniciando Docker Compose..."
    docker-compose down -v 2>/dev/null
    sleep 2
    docker-compose up
else
    echo "❌ Erro ao compilar. Verifique os logs."
    exit 1
fi

