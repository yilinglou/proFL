import os 
import sys

# which example to run Lang-26/Chart-20/Chart-26/Time-19
sub = sys.argv[1]


os.chdir(sub)
os.system('mvn clean test -Dhttps.protocols=TLSv1.2')
os.system('mvn org.mudebug:prapr-plugin:profl -Dhttps.protocols=TLSv1.2')

