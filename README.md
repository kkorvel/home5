# README #

See on viienda kodutöö failide hoidla, mida saab kasutada ainult algseks lugemiseks.
Töötamiseks looge endale isiklik repositoorium, näiteks privaatne 'fork' bitbucket serverisse, millest saate luua klooni oma arvutisse.

## Näidete kasutamine käsurealt ##
#### Kompileerimine: ####

```
#!bash

javac -cp src src/Node.java
```

#### Käivitamine: ####

```
#!bash

java -cp src Node
```


### Testide kasutamine ###
#### Testi kompileerimine: ####

```
#!bash

javac -encoding utf8 -cp 'src:test:test/junit-4.12.jar:test/hamcrest-core-1.3.jar' test/NodeTest.java

```
Sama Windows aknas (koolonite asemel peavad olema semikoolonid):

```
#!bash

javac -encoding utf8 -cp 'src;test;test/junit-4.12.jar;test/hamcrest-core-1.3.jar' test/NodeTest.java


```

#### Testi käivitamine: ####

```
#!bash

java -cp 'src:test:test/junit-4.12.jar:test/hamcrest-core-1.3.jar' org.junit.runner.JUnitCore NodeTest
```

Sama Windows aknas (koolonite asemel semikoolonid):

```
#!bash

java -cp 'src;test;test/junit-4.12.jar;test/hamcrest-core-1.3.jar' org.junit.runner.JUnitCore NodeTest
```
