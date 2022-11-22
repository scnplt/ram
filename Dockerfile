FROM ubuntu:22.04
COPY . /usr/share/ram

RUN apt-get update && apt-get install -y default-jdk dos2unix android-sdk curl unzip
RUN curl https://dl.google.com/android/repository/commandlinetools-linux-9123335_latest.zip -o cmdline-tools.zip && \
    unzip cmdline-tools.zip && rm cmdline-tools.zip && mv cmdline-tools /usr/lib/android-sdk

ENV ANDROID_HOME /usr/lib/android-sdk
RUN export PATH=$PATH:$ANDROID_HOME/cmdline-tools/bin && yes | sdkmanager --licenses --sdk_root=$ANDROID_HOME

WORKDIR /usr/share/ram
RUN dos2unix ./gradlew 

