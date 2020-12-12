#!/usr/bin/python

import sys
import os
import shutil

file_path=sys.argv[1]
dir = os.path.dirname(file_path)
parent_dir = os.path.dirname(dir)
file_name = os.path.basename(file_path)
log_path = os.path.join(parent_dir,"log_file.txt")
file1 = open(log_path, "a")
file1.write(file_path+"\n")
if file_path.find("controller_service") > 0:
    file1.write("service:"+file_path+"\n")
    target_dir = os.path.join(parent_dir,"services")
    if not os.path.exists(target_dir):
        os.mkdir(target_dir)
    new_name = file_name.replace("_controller_service.py","_service.py")
    new_path = os.path.join(target_dir,new_name)
    try:
        #os.rename(file_path, new_path)
        shutil.move(file_path, new_path)
    except Exception as e:
        file1.write("error:"+str(e)+"\n")
        pass
if file_path.find("_controller_json") > 0:
    file1.write("jaon:"+file_path+"\n")
    target_dir = os.path.join(parent_dir,"config")
    if not os.path.exists(target_dir):
        os.mkdir(target_dir)
    new_name = file_name.replace("_controller_json.py",".json")
    new_path = os.path.join(target_dir,new_name)
    try:
        #os.rename(file_path, new_path)
        shutil.move(file_path, new_path)
    except Exception as e:
        file1.write("error:"+str(e)+"\n")
        pass
file1.close()