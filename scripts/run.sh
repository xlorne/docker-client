docker build -f tensorflow.Dockerfile . -t tf:latest
docker run --gpus 'device=0' -it --rm -v ${PWD}:/app tf:latest python3 /app/train.py
