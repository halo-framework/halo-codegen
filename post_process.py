#!/usr/bin/python

import sys
import os
import shutil

file_path=sys.argv[1]
file2 = open(r"c:\dev\MyFile2.txt", "a")
if os.path.exists(file_path):
    file2.write(file_path+"\n")
else:
    file2.write("missing:"+file_path+"\n")
if file_path.find("controller_service") > 0:
    dir = os.path.dirname(file_path)
    file_name = os.path.basename(file_path)
    parent_dir = os.path.dirname(dir)
    target_dir = os.path.join(parent_dir,"mixin")
    new_name=file_name.replace("_controller_service.py","_service.py")
    new_path = os.path.join(target_dir,new_name)
    os.rename(file_path, new_path)
    #shutil.copyfile(file_name, file_name+".xyz")
    file2.write("fixed:"+new_path+"\n")
file2.close()
