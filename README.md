# Short URL generator
##### This Project deployed in aws,mainly used for devops demonstrations

#短网址生成器

##1.介绍

短网址就是把普通网址，转换成比较短的网址。比如：http://t.cn/RlB2PdD 这种，在某些限制字数的应用里。好处不言而喻。短、字符少、美观、便于发布、传播。

##2.CI/CD Pipeline

修改代码后可以一键发布到线上,刷新页面直接看到最新效果。

###Pipeline的数据流图

 ![aws codepipeline](https://github.com/sunwayjiao/urlshorten/blob/master/Pipeline.png)

##3.线上环境

###1)演示环境

http://ec2-18-205-158-123.compute-1.amazonaws.com/main/index

###2)Swagger 环境

http://ec2-18-205-158-123.compute-1.amazonaws.com/swagger/swagger-ui.html

###3)在线Logger

http://ec2-18-205-158-123.compute-1.amazonaws.com/actuator/loggers/

#####动态修改Logger Level命令

curl -X POST http://ec2-18-205-158-123.compute-1.amazonaws.com/actuator/loggers/aws.urlshortener \
-H "Content-Type: application/vnd.spring-boot.actuator.v2+json;charset=UTF-8" \
--data '{"configuredLevel":"DEBUG"}'

###4)AWS CodePipeline

https://console.aws.amazon.com/codesuite/codepipeline/pipelines?region=us-east-1,选择第一个 shorturl_aws_myFirstPipeline


