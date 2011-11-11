CC=javac
RUN=java
TARGET=StringFinder.class

all: ${TARGET}

run: ${TARGET}
	@${RUN} ${<:.class=}

%.class: %.java
	@${CC} -Xlint:unchecked $<

clean:
	@rm -f *.class