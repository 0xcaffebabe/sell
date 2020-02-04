# sell

一个简单的订餐系统

## 日志

### 日志框架

- 定制输出目标
- 定制输出格式
- 携带上下文信息（时间戳、；类路劲...）
- 选择性输出
- 灵活的配置

### 常见的日志框架

- log4j
- log4j2
- logback

#### 日志门面

- slf4j

## 微信登录

测试号申请:

<http://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index>

配置接口：

填写外网URL,该URL必须返回微信传过来的随机字符串

关注测试公众号

在网页授权选项填写授权回调页面域名

当进行微信登录时，跳转到:<https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect>

编写一个回调controller，微信网页授权回调的地址，微信回调会传递code与state两个参数

根据code、appid、secret获取access_token、openid:

<https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code>

根据access_token、openid 拉取用户信息

<https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN>

## 支付宝支付

登录支付宝沙箱：

<https://openhome.alipay.com/platform/appDaily.htm?tab=account>

设置相关密钥

创建订单

暴露接口给支付宝调用

- 验证金额、状态等
- 根据传递过来的交易号找到订单，设置支付状态

返回success，避免支付宝多次回调

## 分布式系统

- 多节点
- 消息通信
- 不共享内存

### 分布式session

![](./doc/asset/批注%202020-02-04%111035.png)