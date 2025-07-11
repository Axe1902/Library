# Используем базовый образ с JDK (например, OpenJDK 11)
FROM openjdk:21-jdk

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем ваш скомпилированный JAR-файл в контейнер
COPY target/library-0.0.1-SNAPSHOT.jar /app/myapp.jar

# Указываем команду запуска приложения
CMD ["java", "-jar", "myapp.jar"]