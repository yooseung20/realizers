# 쿠버네티스 로컬 환경 구축

#### 1. Docker 설치
#### 2. minikube 설치

로컬 환경에서 kubernetes를 실행하기 위해 minikube를 설치합니다.

```shell
brew install minikube
```

#### 3. kubectx + kubens 설치

kubectx는 kubectl에서 컨텍스트(클러스터) 간에 빠르게 전환할 수 있는 도구입니다. kubens는 Kubernetes 네임스페이스 간에 쉽게 전환하고 이를 kubectl에 설정할 수 있는 도구입니다.

```shell
brew install kubectx
```

#### 4. fzf 설치

fzf는 kubectx, kebens 사용 시 interactively select 할 수 있게 도와주는 도구입니다.

```shell
brew install fzf
```

#### 5. kube-ps1 설치 - 보충 필요

터미널에서 현재 쿠버네티스 context와 namespace를 표시해주는 도구입니다.

```shell
brew install kube-ps1

### .zshrc에 아래 내용 추
source "/opt/homebrew/opt/kube-ps1/share/kube-ps1.sh"
PS1='$(kube_ps1)'$PS1
```

