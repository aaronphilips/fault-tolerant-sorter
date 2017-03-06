all: DataGenerator.class DataSorter.class

DataGenerator.class:DataGenerator.java
	javac DataGenerator.java

DataSorter.class:DataSorter.java
	javac DataSorter.java
clean:
	rm -f *.class
	rm -f *.so
	rm -f *.h
