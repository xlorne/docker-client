docker run -it --gpus all --rm pytorch/pytorch:latest python3 -c 'import torch; print(torch.cuda.is_available())'