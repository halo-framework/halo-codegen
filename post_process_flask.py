#!/usr/bin/python

import sys
import os
import shutil
import time
import datetime
import traceback
import json
import re

file_path=sys.argv[1]
block_path=os.environ["RTRV_BLOCK"]
log_dir=os.environ["POST_LOG"]
dir = os.path.dirname(file_path)
parent_dir = os.path.dirname(dir)
file_name = os.path.basename(file_path)
log_path = os.path.join(log_dir,"post_log_file.txt")
file1 = open(log_path, "a+")
ts = time.time()
sttime = datetime.datetime.fromtimestamp(ts).strftime('%Y%m%d_%H:%M:%S - ')
file1.write(sttime+"start:"+file_path+"\n")
block_file_path = os.path.join(block_path,"block.txt")
block_query_file_path = os.path.join(block_path,"block_query.txt")
block_command_file_path = os.path.join(block_path,"block_command.txt")

def flip(txt):
    i = txt.index( "[[" )
    start = i + len( "[[" )
    end = txt.index( "]]", start )
    z = txt[start:end]
    nza = z.split('_', 1)[1]
    nzb = nza.split('_')
    nz = ""
    for i in nzb:
        nz = nz+i.capitalize()
    txto = txt.replace("[["+z+"]]",nz)
    return txto

try:
    if file_path.find("retrieve_controller.py") > 0:
        file1.write(sttime+"retrieve_controller:"+file_path+"\n")
        with open(block_file_path, 'r') as blockfile:
            block = blockfile.read()
        with open(file_path, 'r') as fi:
            txt = fi.read()
            txt1 = txt.replace("#replace_block",block)
        with open(file_path, 'w') as fo:
            fo.write(txt1)
    if file_path.find("_controller_model.py") > 0 or file_path.find("_controller_repository.py") > 0:
        if file_path.find("_controller_model.py") > 0:
            fdir = "domain"
            sdir = "model"
        else:
            fdir = "infra"
            sdir = "repository"
        #if file_path.find("retrieve_controller_") < 0:
        if file_path.find("initiate_controller_") > 0:
            target_dir = os.path.join(parent_dir,fdir)
            if not os.path.exists(target_dir):
                os.mkdir(target_dir)
            target_dir = os.path.join(target_dir,sdir)
            if not os.path.exists(target_dir):
                os.mkdir(target_dir)
            with open(file_path, 'r') as fi:
                txtr = fi.read()
                txt = flip(txtr)
                while txt.find("[[") > 0:
                    txt = flip(txt)
            with open(file_path, 'w') as fo:
                fo.write(txt)
            if file_path.find("initiate_controller_model.py") > 0:
                new_name = file_name.replace("initiate_controller_model.py","model.py")
            else:
                new_name = file_name.replace("initiate_controller_repository.py","repository.py")
            new_path = os.path.join(target_dir,new_name)
            try:
                #os.rename(file_path, new_path)
                shutil.move(file_path, new_path)
            except Exception as e:
                file1.write(sttime+"error:"+str(e)+"\n")
                pass
        else:
            try:
                os.remove(file_path)
            except OSError as e:  ## if failed, report it back to the user ##
                print ("Error: %s - %s." % (e.filename, e.strerror))
    if file_path.find("_controller_sql.py") > 0:
        if file_path.find("retrieve_controller_sql.py") >= 0:
            target_dir = os.path.join(parent_dir,"infra")
            if not os.path.exists(target_dir):
                os.mkdir(target_dir)
            target_dir = os.path.join(target_dir,"sql")
            if not os.path.exists(target_dir):
                os.mkdir(target_dir)
            new_name = file_name.replace("_controller_sql.py","_sql.py")
            new_path = os.path.join(target_dir,new_name)
            try:
                #os.rename(file_path, new_path)
                shutil.move(file_path, new_path)
            except Exception as e:
                file1.write(sttime+"error:"+str(e)+"\n")
                pass
        else:
            try:
                os.remove(file_path)
            except OSError as e:  ## if failed, report it back to the user ##
                print ("Error: %s - %s." % (e.filename, e.strerror))
    if file_path.find("controller_handler") > 0:
        file1.write(sttime+"handler:"+file_path+"\n")
        target_dir = os.path.join(parent_dir,"app")
        if not os.path.exists(target_dir):
                os.mkdir(target_dir)
        target_dir = os.path.join(target_dir,"handlers")
        if not os.path.exists(target_dir):
                    os.mkdir(target_dir)
        new_name = file_name.replace("_controller_handler.py","_handler.py")
        new_path = os.path.join(target_dir,new_name)
        try:
            #os.rename(file_path, new_path)
            shutil.move(file_path, new_path)
        except Exception as e:
            file1.write(sttime+"error:"+str(e)+"\n")
            pass
        if new_name.startswith("retrieve_"):
            with open(new_path, 'r') as fi:
                txt = fi.read()
                txt1 = txt.replace("AbsBianCommandHandler","AbsBianQueryHandler")
                with open(block_query_file_path, 'r') as blockfile:
                    block = blockfile.read()
                    txt1 = txt1.replace("#replace_block",block)
            with open(new_path, 'w') as fo:
                fo.write(txt1)
        else:
            with open(new_path, 'r') as fi:
                txt = fi.read()
                with open(block_command_file_path, 'r') as blockfile:
                    block = blockfile.read()
                    txt1 = txt.replace("#replace_block",block)
            with open(new_path, 'w') as fo:
                fo.write(txt1)
    if file_path.find("service_factory") > 0:
        file1.write(sttime+"service_factory:"+file_path+"\n")
        parent_dir = os.path.dirname(parent_dir)
        # move handlers settings
        base_dir = os.path.dirname(parent_dir)
        src_dir = os.path.join(parent_dir,"controllers")
        target_dir = os.path.join(base_dir,'env','handler')
        for file in os.listdir(src_dir):
            if file.endswith("_settings.py"):
                new_name = file.replace("_controller_settings.py",".json")
                new_path = os.path.join(target_dir,new_name)
                old_path = os.path.join(src_dir,file)
                try:
                    #os.rename(file_path, new_path)
                    shutil.move(old_path, new_path)
                except Exception as e:
                    file1.write(sttime+"error:"+str(e)+"\n")
                    pass
                handler_name = new_name.replace(".json","_handler")
                with open(new_path, 'r') as fi:
                    txt = fi.read()
                    txt1 = txt.replace("xxx_handler",handler_name)
                    if handler_name.startswith("retrieve_"):
                        txt1 = txt1.replace('"command"','"query"')
                with open(new_path, 'w') as fo:
                    fo.write(txt1)
        #move controllers
        source_dir = os.path.join(parent_dir,"controllers")
        target_dir = os.path.join(parent_dir,"entrypoints","rest","controllers")
        try:
          #os.rename(file_path, new_path)
          shutil.move(source_dir, target_dir)
        except Exception as e:
            file1.write(sttime+"error:"+str(e)+"\n")
            pass
        #swagger
        source_dir = os.path.join(parent_dir,"openapi")
        target_dir = os.path.join(parent_dir,"entrypoints","rest","openapi")
        try:
          #os.rename(file_path, new_path)
          shutil.move(source_dir, target_dir)
        except Exception as e:
            file1.write(sttime+"error:"+str(e)+"\n")
            pass
        source_file = os.path.join(target_dir,"openapi.yaml")
        with open(source_file, 'r') as fi:
            txt = fi.read()
            txt1 = txt.replace(".controllers.",".entrypoints.rest.controllers.")
        #for matchedtext in re.findall(r'(requestBody)(.*)(ref)', txt1):
        #    txt1 = txt1.replace(matchedtext,"xxx")
        with open(source_file, 'w') as fi:
            fi.write(txt1)
        #new

except Exception as e:
    file1.write(sttime+"ex_error:"+str(e)+"\n")
    exc_type, exc_value, exc_traceback = sys.exc_info()
    traceback.print_tb(exc_traceback, limit=1, file=file1)
    file1.write("\n")
file1.write(sttime+"finish:"+file_path+"\n")
file1.close()