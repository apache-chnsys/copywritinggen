#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time : 2023/12/16 16:25
# @Author : jiangxintian
# @File : peom_evaluate.py
# 如有bug我吞粪自尽
# 该文件实现关键词直接生成诗词
import os
os.environ["CUDA_VISIBLE_DEVICES"] = "0,1"
from transformers import AutoTokenizer
from peft import PeftModel
from transformers import AutoModel
import sys

"""
解析命令行参数
:return:
"""
# 获取脚本名称
user_text = sys.argv[1]
print("user_text:", user_text)

type = sys.argv[2]
print("type:", type)


# ckpt_path = 'model/chatglm2_peom_qlora_final/' # model 3
# model_name_or_path = 'chatglm2-6b/chatglm_6b'
# model_old = AutoModel.from_pretrained(model_name_or_path,load_in_8bit=False,trust_remote_code = True)
# peft_loaded = PeftModel.from_pretrained(model_old,ckpt_path).cuda()
# model_new = peft_loaded.merge_and_unload()
# tokenizer = AutoTokenizer.from_pretrained(model_name_or_path, trust_remote_code=True) # model 3
#
# keywords = user_text
# query = f"问: 写一首以{user_text}为关键词的{type}"
# res,_ = model_new.chat(tokenizer,query=query)
query = f"问: 写一首以{user_text}为关键词的{type}"
print(query)
