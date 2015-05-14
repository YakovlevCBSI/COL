# -*- coding: utf-8 -*-
import sys
import os

testToExclude=''
lines=''

def read_file(pathToPom=""):
    global lines
    with open("pom.xml") as f:
        lines=f.readlines()

def add_string():
    global lines 
    global testToExclude
    new_lines=''
    endOfIncludes = False
    startOfIncludes = False
    for line in lines:
        if '<excludes>' in line:
            startOfIncludes=True
            
        elif '</excludes>' in line:
            endOfIncludes=True
            line=line.replace('</excludes>','')
            line=line+'<exclude>' + testToExclude +'</exclude>\n</excludes>'
        
        new_lines += line
            
    lines= new_lines
    
def write_file(pathToPom=""):
    global lines
    file=open(pathToPom + "pom.xml",'w')
    file.write(lines)
    file.close()

def main(pathToPom=""):
    read_file(pathToPom)
    add_string()
    write_file(pathToPom)

for arg in sys.argv:
    if arg != __file__:
        testToExclude = arg

        print('modifying ' + arg)
        print('textToExclude:'+testToExclude)
        main()

