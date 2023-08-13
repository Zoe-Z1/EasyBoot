#FROM openjdk:8u265-jre
#
#ENV JAVA_OPT="-Dfile.encoding=UTF-8 -Duser.timezone=GMT+08 -Xms512m -Xmx512m"
#
#RUN mkdir /app
#
#COPY app.jar /app
#
#CMD java ${JAVA_OPT} -jar /app/app.jar
#
#EXPOSE 9000


#stage('git pull 拉取代码') {
#    steps {
#        // Get some code from a GitHub repository
#        git branch: 'master', credentialsId: '117af67f-3976-4b81-a718-ce31c685170f', url: 'https://gitee.com/thisZhuMy_admin/fast-start.git'
#    }
#}


#stage('清除旧镜像及容器') {
#    steps {
#        //定义镜像名称
#        // 构建之前，需要先删除原有的镜像
#        //sh "docker rm -f fast-start"
#        //sh "docker rmi -f fast-start:v1.0.0"
#    }
#}
#stage('构建与发布镜像') {
#    steps {
#        //编译，构建本地镜像
#        sh "mvn clean install -Dmaven.test.skip=true"
#        //sh "docker build -t fast-start"
#        // 登录阿里云镜像
#        //sh "docker login --username=111 --password=111 registry-vpc.cn-heyuan.aliyuncs.com"
#        //sh "docker tag hellospringboot:v1.0.0 registry-vpc.cn-heyuan.aliyuncs.com/ningzaichun/jenkins:v0.0.1"
#        //发布镜像
#        //sh "docker push registry-vpc.cn-heyuan.aliyuncs.com/ningzaichun/jenkins:v0.0.1"
#    }
#}
#stage('运行容器') {
#    steps {
#        //sh "docker rmi -f registry-vpc.cn-heyuan.aliyuncs.com/ningzaichun/jenkins:v0.0.1"
#        //sh " docker pull registry-vpc.cn-heyuan.aliyuncs.com/ningzaichun/jenkins:v0.0.1"
#        //sh "docker run -d -p 8080:8080 --name hellonzc registry-vpc.cn-heyuan.aliyuncs.com/ningzaichun/jenkins:v0.0.1"
#    }
#}