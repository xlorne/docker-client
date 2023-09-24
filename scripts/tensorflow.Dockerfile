FROM  tensorflow/tensorflow:2.13.0-gpu
WORKDIR /app
COPY requirements.txt .
RUN pip install -r requirements.txt --index https://mirrors.ustc.edu.cn/pypi/web/simple/