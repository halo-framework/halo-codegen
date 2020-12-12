#!/usr/bin/python

import sys
import os
import shutil

file_path=sys.argv[1]
#file2 = open(r"c:\dev\MyFile2.txt", "a")
#if os.path.exists(file_path):
#    file2.write(file_path+"\n")
#else:
#    file2.write("missing:"+file_path+"\n")
if file_path.find("controller_service") > 0:
    dir = os.path.dirname(file_path)
    #file2.write("dir:"+dir+"\n")
    file_name = os.path.basename(file_path)
    #file2.write("file_name:"+file_name+"\n")
    parent_dir = os.path.dirname(dir)
    #file2.write("parent_dir:"+parent_dir+"\n")
    target_dir = os.path.join(parent_dir,"services")
    if not os.path.exists(target_dir):
        os.mkdir(target_dir)
    #file2.write("target:"+target_dir+"\n")
    new_name = file_name.replace("_controller_service.py","_service.py")
    new_path = os.path.join(target_dir,new_name)
    #file2.write("new_path:"+new_path+"\n")
    try:
        #os.rename(file_path, new_path)
        shutil.move(file_path, new_path)
    except Exception as e:
        #file2.write("error:"+str(e)+"\n")
        pass
    #file2.write("fixed:"+new_path+"\n")
#file2.close()
if file_path.find("_controller.json") > 0:
    dir = os.path.dirname(file_path)
    #file2.write("dir:"+dir+"\n")
    file_name = os.path.basename(file_path)
    #file2.write("file_name:"+file_name+"\n")
    parent_dir = os.path.dirname(dir)
    #file2.write("parent_dir:"+parent_dir+"\n")
    target_dir = os.path.join(parent_dir,"config")
    if not os.path.exists(target_dir):
        os.mkdir(target_dir)
    #file2.write("target:"+target_dir+"\n")
    new_name = file_name
    new_path = os.path.join(target_dir,new_name)
    #file2.write("new_path:"+new_path+"\n")
    try:
        #os.rename(file_path, new_path)
        shutil.move(file_path, new_path)
    except Exception as e:
        #file2.write("error:"+str(e)+"\n")
        pass
    #file2.write("fixed:"+new_path+"\n")