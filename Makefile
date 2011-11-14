CC=javac
RUN=java
TARGET=NumberFinderRunner.class

all: ${TARGET}

run: ${TARGET}
	@${RUN} ${<:.class=}

%.class: %.java
	@echo "*** building $<"
	@${CC} -Xlint:unchecked $<

clean:
	@rm -f *.class