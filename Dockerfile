FROM ubuntu:20.04

# Установка зависимостей
RUN apt-get update && apt-get install -y \
    tesseract-ocr \
    tesseract-ocr-eng \
    tesseract-ocr-rus \
    openjdk-11-jdk \
    maven \
    && rm -rf /var/lib/apt/lists/*

# Установка рабочей директории
WORKDIR /app

# Копирование исходного кода
COPY . /app

# Установка переменной TESSDATA_PREFIX
ENV TESSDATA_PREFIX=/usr/share/tesseract-ocr/4.00/tessdata

# Команда для запуска приложения
CMD ["mvn", "clean", "install", "exec:java"]