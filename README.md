

## ProFL: Program Repair for Fault Localization

ProFL is a unified debugging tool utilizing automated program repair to refine fault localization and currently it has been 
implemented as an one-click and fully automated Maven plugin built on the latest byte code program repair tool PraPR.
To apply ProFL on your project, you only need to 
(1) install ProFL to the local maven repository; 
(2) configure ProFL in the pom.xml of your target subject; 
(3) execute ProFL via maven command.



### 1 Install ProFL
 Before installation please check the following environment requirements. 
```text
OS: Ubuntu Linux or Mac OS X.
Build System: Maven 3+.
JDK: 1.7 (recommended for applying ProFL on Defects4J).
```

Install the ProFL into your local maven repository.
```sh
$ ./install.sh
```

If you encounter compilation error (e.g., saying signature of a certain method does not match the supplied arguments), probably your local repository contains some of old JAR files downloaded from Maven Central Repo. Please remove them via `rm -rf ~/.m2/repository/org/pitest` before installation.


### 2 Configure ProFL
Insert the following code into the `pom.xml` of the target project (already inserted for the three example projects).

```xml
<plugin>
    	<groupId>org.mudebug</groupId>
	<artifactId>prapr-plugin</artifactId>
	<version>2.0.3-SNAPSHOT</version>
</plugin>
```


### 3 Execute ProFL
Three examples(i.e., Lang-26, Chart-20, and Time-19) are provided.
Passing the example name to the python script `run-example.py` can execute ProFL for the target example subject.

For example, you can execute ProFL on Lang-26 with the following instructions.

```sh
$ cd ./examples
$ python3 run-example.py Lang-26
```

It would first execute maven compile and test tasks on the project
and then execute ProFL as a maven plugin.

### 4 ProFL Reports
For each subject, the final reports of ProFL are in the subject directory of the `target/prapr-reports/*YYMMDD*/`.
`profl.log` is the fault localization rank list generated by ProFL;
`sbfl.log` is the fault localization rank list generated by spectrum-based fault localization.
`fix-report.log` is the summary of potential fixes.

We summarize the expected execution information of the three examples as the follows.

Example Name | Bug Method | Ranked by Spectrum | Ranked by ProFL |  Execution Time|
:-: | :-: | :-: | :-: | :-:
Lang-17 | format | 17 | 1 | < 2 min|
Chart-20|ValueMarker.init | 5 | 1 | < 2 min|
Time-19| getOffsetFromLocal | 77| 1 | < 10 min|



If you use ProFL for academic purposes, please cite it as:
```text
@inproceedings{louIssta2020,
  author    = {Yiling Lou and
             Ali Ghanbari and 
             Xia Li and 
             Lingming Zhang and
             Haotian Zhang and
             Dan Hao and
             Lu Zhang},
  title     = {Can automatd program repair refine fault localization? A unified debugging approach},
  booktitle = {Proceedings of the 29th {ACM} {SIGSOFT} International Symposium on
               Software Testing and Analysis, {ISSTA} 2020, Los Angeles, California, United States, July 18-22 , 2020. },
  pages     = {12 pages},
  year      = {2020}
}
```

