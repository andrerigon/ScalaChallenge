build:
	./bin/sbt assembly
test:
	./bin/sbt cov
run:
	cat ${input} | java -jar target/scala-2.11/fyber.jar

