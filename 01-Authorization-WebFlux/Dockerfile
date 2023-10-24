FROM gradle:8.4-jdk17

WORKDIR /tmp
ADD . /tmp

RUN gradle build

CMD ["gradle", "clean", "bootRun"]
EXPOSE 3010
