# Builds and runs the Ktor backend (:server module) of this monorepo.
# --configure-on-demand keeps Gradle from evaluating the Android app modules,
# which aren't needed for the server build and would require an Android SDK.

FROM eclipse-temurin:21-jdk AS build
WORKDIR /workspace

COPY . .
RUN ./gradlew :server:installDist --configure-on-demand -x test --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /workspace/server/build/install/server ./

EXPOSE 8080
ENTRYPOINT ["./bin/server"]
