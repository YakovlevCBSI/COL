__author__ = 'alpark'
import ftplib
import os
import sys

def scan_folders_in_cur_dir(path, xml_only=False):
    files = []

    for f in os.listdir(path):
        if xml_only == True:
            if f.endswith('.xml'):
                files.append(f)
                print(f)

        else:
            files.append(f)

    return files

def connect_to_ftp():
    ftp = ftplib.FTP()
    ftp.connect('qeload-master', 21)
    print (ftp.getwelcome())

    try:
        print ("loggin in...")
        ftp.login('jenkins','fcat1234')
        print("sucess")

    except:
        "failed to login"

    return ftp

xml_path = os.path.join('target','surefire-reports')
##xml_path = '/Users/alpark/git/ccs-channelonline/ccs-channelonline/target/surefire-reports/'

xml_files = scan_folders_in_cur_dir(xml_path, True)

ftp = connect_to_ftp()
ftp.cwd('/var/lib/jenkins/Documents/colReports')

for file in xml_files:
    filename = os.path.join(xml_path, file)
    print(filename)
    myfile = open(filename, 'rb')
    ftp.storbinary('STOR ' + file, myfile)
    myfile.close

ftp.quit()

print("finished uploading")
