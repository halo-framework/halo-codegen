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
    target_dir = os.path.join(parent_dir,"app")
    if not os.path.exists(target_dir):
            os.mkdir(target_dir)
    target_dir = os.path.join(target_dir,"services")
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
    file1.write("json:"+file_path+"\n")
    target_dir = os.path.join(parent_dir,"ui")
    if not os.path.exists(target_dir):
            os.mkdir(target_dir)
    target_dir = os.path.join(target_dir,"config")
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
if file_path.find("service_factory") > 0:
    parent_dir = os.path.dirname(parent_dir)
    source_dir = os.path.join(parent_dir,"controllers")
    target_dir = os.path.join(parent_dir,"ui","rest","controllers")
    try:
      #os.rename(file_path, new_path)
      shutil.move(source_dir, target_dir)
    except Exception as e:
        file1.write("error:"+str(e)+"\n")
        pass
    #swagger
    source_dir = os.path.join(parent_dir,"openapi")
    target_dir = os.path.join(parent_dir,"ui","rest","openapi")
    try:
      #os.rename(file_path, new_path)
      shutil.move(source_dir, target_dir)
    except Exception as e:
        file1.write("error:"+str(e)+"\n")
        pass
    source_file = os.path.join(target_dir,"openapi.yaml")
    with open(source_file, 'r') as fi:
        txt = fi.read()
    txt1 = txt.replace(".controllers.",".ui.rest.controllers.")
    with open(source_file, 'w') as fi:
            fi.write(txt1)


file1.close()