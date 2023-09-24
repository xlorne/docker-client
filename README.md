# Nvidia-Docker

  
[docker java](https://github.com/docker-java/docker-java/blob/main/docs/getting_started.md)  
[ubuntu docker install](https://docs.docker.com/engine/install/ubuntu/)  
[docker nvidia install](https://docs.nvidia.com/datacenter/cloud-native/container-toolkit/latest/install-guide.html)  
[docker cn registry](https://www.runoob.com/docker/docker-mirror-acceleration.html)

[pytorch docker](https://hub.docker.com/r/pytorch/pytorch)   
[tensorflow docker](https://hub.docker.com/r/tensorflow/tensorflow)  


## Tensorflow

```dockerfile
FROM  tensorflow/tensorflow:2.13.0-gpu
WORKDIR /app
COPY requirements.txt .
RUN pip install -r requirements.txt --index https://mirrors.ustc.edu.cn/pypi/web/simple/
```

run
```shell
docker build -f tensorflow.Dockerfile . -t tf:latest
docker run --gpus 'device=0' -it --rm -v ${PWD}:/app tf:latest python3 /app/train.py

```

--gpus 'device=0' 指定gpu 0  
--gpus 'device=0,1' 指定gpu 0,1  
--gpus all 指定所有gpu


## Pytorch

run
```shell
docker run -it --gpus all --rm pytorch/pytorch:latest python3 -c 'import torch; print(torch.cuda.is_available())'
```


## Nvidia-Docker

```shell
docker run --rm --gpus all ubuntu nvidia-smi
```
