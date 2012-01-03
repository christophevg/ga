BUILD_DIR=build
CC=javac -d ${BUILD_DIR}
RUN=java -cp ${BUILD_DIR}
APP=NumberFinderRunner
TARGET=build/${APP}.class

all: ${TARGET}

run: ${TARGET}
	@${RUN} ${APP}

build/%.class: %.java
	@mkdir -p build
	@echo "*** building $<"
	@${CC} -Xlint:unchecked $<

clean:
	@rm -rf build
