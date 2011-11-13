CC=javac
RUN=java
TARGET=NumberFinder.class

all: ${TARGET}

run: ${TARGET}
	@${RUN} ${<:.class=}

%.class: %.java
	@echo "*** building $<"
	@${CC} -Xlint:unchecked $<

clean:
	@rm -f *.class