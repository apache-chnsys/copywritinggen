#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time : 2023/12/16 16:25
# @Author : jiangxintian
# @File : peom_evaluate.py
# 如有bug我吞粪自尽
# 该文件实现图片生成诗词
import os
os.environ["CUDA_VISIBLE_DEVICES"] = "0,1"
from PIL import Image
from transformers import AutoTokenizer
from transformers import AutoModelForSeq2SeqLM
from peft import PeftModel
import jieba.posseg as pseg
from transformers import AutoModel
from diffusers import UniDiffuserPipeline
import sys

# 获取脚本名称
user_text = sys.argv[1]
print("user_text:", user_text)

type = sys.argv[2]
print("type:", type)

file_path = sys.argv[3]
print("file_path:", file_path)

# pipe = UniDiffuserPipeline.from_pretrained("../learning/unidiffuser-v1") # model 1:https://huggingface.co/thu-ml/unidiffuser-v1
# print('uni model loaded.')
# model_checkpoint = "../../NMT_attack/models/Helsinki-NLP-en-zh"# model 2 :https://huggingface.co/Helsinki-NLP/opus-mt-en-zh
# tokenizer_trs = AutoTokenizer.from_pretrained(model_checkpoint)
# model_trs = AutoModelForSeq2SeqLM.from_pretrained(model_checkpoint)
#
# model_checkpoint_ze = "../../NMT_attack/models/Helsinki-NLP-zh-en"# model 2 :https://huggingface.co/Helsinki-NLP/opus-mt-zh-en
# tokenizer_trs_ze = AutoTokenizer.from_pretrained(model_checkpoint_ze)
# model_trs_ze = AutoModelForSeq2SeqLM.from_pretrained(model_checkpoint_ze)
# print('translation model loaded.')
#
# ckpt_path = 'model/chatglm2_peom_qlora_final/' # model 3
# model_name_or_path = 'chatglm2-6b/chatglm_6b'
# model_old = AutoModel.from_pretrained(model_name_or_path,load_in_8bit=False,trust_remote_code = True)
# peft_loaded = PeftModel.from_pretrained(model_old,ckpt_path).cuda()
# model_new = peft_loaded.merge_and_unload()
# tokenizer = AutoTokenizer.from_pretrained(model_name_or_path, trust_remote_code=True) # model 3
#
#
# # 1、image to text
# init_image = Image.open(file_path).convert("RGB")
# init_image = init_image.resize((512, 512))
#
# sample = pipe(image=init_image, num_inference_steps=20, guidance_scale=8.0)
# i2t_text = sample.text[0]
# print(i2t_text)
#
# # 2、text to Chinese
# sentence = i2t_text
# input_ids = tokenizer_trs.encode(sentence, return_tensors="pt")
# sentence_generated_tokens = model_trs.generate(input_ids)
# sentence_decoded_pred = tokenizer_trs.decode(sentence_generated_tokens[0], skip_special_tokens=True)
# print(sentence_decoded_pred)
#
# # 3、keywords extract
# words = pseg.cut(sentence_decoded_pred)
# nouns = [word for word, flag in words if flag.startswith('n')]
#
# print("提取的名词:", "/ ".join(nouns))
#
# # 4、 peom generate
# keywords = ' '.join(nouns)
# query = f"问: 写一首以{keywords}为关键词的诗"
# res,_ = model_new.chat(tokenizer,query=query)
# print(res)
#
# # 5、keywords extract
# words = pseg.cut(' '.join(res.split('\n')[1:]))
# nouns = [word for word, flag in words if flag.startswith('n')]
#
# print("提取的名词:", "  ".join(nouns))
#
# # 6、text to English
# sentence_zh = "  ".join(nouns)
# input_ids_ze = tokenizer_trs_ze.encode(sentence_zh, return_tensors="pt")
# sentence_generated_tokens_ze = model_trs_ze.generate(input_ids_ze)
# sentence_decoded_pred_ze = tokenizer_trs_ze.decode(sentence_generated_tokens_ze[0], skip_special_tokens=True)
# print(sentence_decoded_pred_ze)
#
# # 7、generate picture
# prompt = sentence_decoded_pred_ze
#
# sample = pipe(prompt=prompt, num_inference_steps=20, guidance_scale=8.0)
# t2i_image = sample.images[0]
# t2i_image.save("peom_image.png")