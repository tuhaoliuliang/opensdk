# 关于我们
欢迎使用土豪流量SDK, 如果您还没有注册，请前往[这里注册](https://open.tuhaoliuliang.cn/register), 当前我们提供了PHP/JAVA/C#三种语言的支持，每种语言均有Demo提供，其余语言也可在这三类语言的基础上进行编写，如遇问题可以联系我们，也欢迎大家把自己写好的SDK贡献出来！


## AppId / AppSecret 签名通讯协议
此方式开发快速简单，我们已经为每个账户创建了一份AppId和AppSecret，可以直接从网站后台获得（[点击这里](https://open.tuhaoliuliang.cn/shop/company/developer)），配合 SDK 即可进行对接；

####定义：

######系统参数

![系统参数](https://img.alicdn.com/imgextra/i3/20248220/TB2ZMy2XKTyQeBjSspaXXcjjFXa-20248220.png)


######应用参数
	
	参见各个 API 内的参数说明
	
######签名方法
	
```
	调用 API 时需要对请求参数进行签名验证，服务器会对该请求参数进行验证是否合法的。方法如下：

根据参数名称（除签名和图片文件）将所有请求参数按照字母先后顺序排序:key + value .... key + value
例如：将foo=1,bar=2,baz=3 排序为bar=2,baz=3,foo=1，参数名和参数值链接后，得到拼装字符串bar2baz3foo1

系统暂时只支持MD5加密方式：
md5：将 secret 拼接到参数字符串头、尾进行md5加密，格式是：md5(secretkey1value1key2value2...secret)

注：我们需要的是32位的字符串，字母全部小写（如果是md5出来的是大写字母，请转为小写）。
	
```


######调用示例
	
```
调用API：tuhao.account.balance，应用参数mobile=13888888888(这里先简化参数，实际该函数有多个参数，具体见函数说明)，使用MD5加密，因为各开发语言语法不一致，以下实例只体现逻辑。
为便于说明，假设 app_key、secret 值均为 test。

1）输入参数为：
    method=tuhao.data.charge
    timestamp=2016-08-06 13:52:03
    format=json
    app_id=test
    v=1.0
    sign_method=md5
    mobile=13888888888

2）按照参数名称升序排列：
    app_id=test
    format=json
    method=tuhao.data.charge
    mobile=13888888888
    sign_method=md5
    timestamp=2016-08-06 13:52:03
    v=1.0

3）连接字符串
    连接参数名与参数值,并在首尾加上secret，如下：
testapp_idtestformatjsonmethodtuhao.data.chargemobile13888888888sign_methodmd5timestamp2016-08-06 13:52:03v1.0test

4）生成签名：
    32位MD5值 -> 40dcfe5add4028f1b8f31cd497a28eb3

5）拼装HTTP请求
    将所有参数值转换为UTF-8编码，然后拼装，通过浏览器访问该地址，即成功调用一次接口，如下：
    https://open.tuhaoliuliang.cn/api/entry?sign=40dcfe5add4028f1b8f31cd497a28eb3&timestamp=2016-08-06%2013:52:03&v=1.0&app_id=test&method=tuhao.data.charge&sign_method=md5&format=json&mobile=13888888888
	
```




## 函数列表
1. 流量充值 tuhao.data.charge
2. 查询余额 tuhao.account.balance
3. 流量充值异步回调  


### 流量充值
#### 提交参数

参数名称  	| 类型 		|   是否必须 	 | 示例	| 描述
------- 	| --- 		| ----------- | ----| ----- 
timestamp | Date 	| 是		| 2016-8-29 14:03 | 格式化时间戳  
app_id 	| String	| 是		| JH6Ws3pL7Y9wCFQ  | 后台查看
method		| String	| 固定值 	| tuhao.data.charge| 调用的方法名称
sign 		| String 	| 是   	| bde20de7191e259a50c643a9313495d5 | 传入生成的签名
v 			| Number 	| 固定值	| 1.0 | 版本号
amount 	| Number 	| 是 		| 1024 | 流量包（amount < 1000 ? amount : amount * 1024）
area		| Boolean | 是		| 0| 全国流量(0) \|\| 地方流量(1)
roam 		| Boolean	| 否		| 1 | 不可漫游(0) \|\| 可漫游(1) - 默认值为1

#### 流量充值提交返回参数

参数名称  	| 类型 | 状态值	| 描述	 
------- 	| --- |  -----	| ----- 
status 	| String 	| success \|\| failed | 提交成功或者失败 
info 		| String	| 订单提交成功 \|\| ...  | 订单提交成功或者失败返回的原因
task_id	| String	| 20160829141509881606 | 订单提交成功后的订单号


### 余额查询
#### 提交参数
参数名称  	| 类型 | 是否必须 | 示例	| 描述	 
------- 	| --- | ------- | -----	| ----- 
timestamp | Date 	| 是	| 2016-8-29 14:03 | 格式化时间戳  
app_id 	| String	| 是	| JH6Ws3pL7Y9wCFQ  | 后台查看
method		| String	| 固定值 | tuhao.account.balance| 调用的方法名称
sign 		| String 	| 是   | bde20de7191e259a50c643a9313495d5| 传入生成的签名
v 			| String 	| 固定值	| 1.0 | 版本号|

#### 余额查询提交返回参数

参数名称  	| 类型 | 状态值	| 描述	 
------- 	| --- |  -----	| ----- 
balance 	| Number 	| 100.00 | 返回余额


### 充值回调推送
#### 返回参数
参数名称  	| 类型 | 状态值	| 描述	 
------- 	| --- |  -----	| ----- 
status 	| String 	| success \|\| error | 充值是否完成
info 		| String	| 充值成功 \|\| 充值失败  | 充值信息
task_id 	| String 	|  20160829141509881606 | tuhao.data.charge 传递的订单ID






