CC=javac
RUN=java
TARGET=StringFinder.class

all: ${TARGET}

run: ${TARGET}
	@${RUN} ${<:.class=}

%.class: %.java
	@${CC} $<

clean:
	@rm -f *.class