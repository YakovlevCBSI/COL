import os
import re

pid=""
p=os.popen('lsof -i :5555', 'r')
while 1:
    line = p.readline()
    if not line: break
    if 'java' in line:
        pids=line.split()
        print(pids)
        pid=pids[1]
    print('pid: ' + pid)

if pid:
    p=os.popen('kill -9 ' + pid, 'r')
    print('killed ' + pid)


