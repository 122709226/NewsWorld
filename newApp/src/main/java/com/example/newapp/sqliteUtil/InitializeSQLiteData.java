package com.example.newapp.sqliteUtil;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.newapp.util.MyApplicationContext;

/**
 * Created by Administrator on 2017/5/25.
 */

public class InitializeSQLiteData {
    private static SQLiteDatabase writeableDB, readableDB;

    static {
        FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(MyApplicationContext.getMyApplicationContext());
        writeableDB = feedReaderDbHelper.getWritableDatabase();
        readableDB = feedReaderDbHelper.getReadableDatabase();
    }

    public static void initializationData() {
        writeableDB.beginTransaction();
        //创建"category"表
        try {
            writeableDB.execSQL("CREATE TABLE `category` (\n" +
                    "  `Id` int(11) NOT NULL,\n" +
                    "  `category` int(11) DEFAULT NULL,\n" +
                    "  `kinds` varchar(50) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`Id`)\n" +
                    ")");
            //插入ategory数据
            writeableDB.execSQL("INSERT INTO `category` VALUES (1,1,'焦点'),(2,2,'社会'),(3,3,'财经'),(4,4,'体育'),(5,5,'科技'),(6,6,'国际'),(7,7,'娱乐');");
            //创建评论表
            writeableDB.execSQL("CREATE TABLE `comment` (\n" +
                    "  `Id` int(11) NOT NULL,\n" +
                    "  `usercount` varchar(200) DEFAULT NULL,\n" +
                    "  `username` varchar(50) DEFAULT NULL,\n" +
                    "  `usergender` varchar(25) NOT NULL DEFAULT '男',\n" +
                    "  `userhead` varchar(200) DEFAULT '空',\n" +
                    "  `newid` int(11) DEFAULT NULL,\n" +
                    "  `comments` text,\n" +
                    "  `praise` int(11) DEFAULT '0',\n" +
                    "  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                    "  PRIMARY KEY (`Id`)\n" +
                    ")");
            writeableDB.execSQL("INSERT INTO `comment` VALUES (1,'1','发送','男','header/image1.png',8,'士大夫的撒飞洒的',0,'2017-05-26 01:16:55'),(2,'2','色东方','男','header/image1.png',8,'asdf',0,'2017-05-26 01:16:53'),(3,'3','爱迪生','女','header/image1.png',7,'看好了',2,'2017-05-26 01:16:50'),(12,'123456','刀锋','男','header/penguin.png',14,'八里街',10,'2016-01-07 22:23:38'),(13,'123456','刀锋','男','header/penguin.png',14,'八里街',2,'2016-01-07 22:23:33'),(14,'123456','刀锋','男','header/penguin.png',14,'八里街啦咯啦咯啦咯啦咯啦图兔兔啦咯啦咯啦咯兔兔咯啦咯啦咯啦咯啦吐了咯啦咯拒绝图兔兔拒绝旅途兔兔距离',2,'2016-01-07 22:23:36'),(15,'123456','刀锋','男','header/penguin.png',16,'哈喽咯墨迹空军建军节',0,'2015-12-25 18:07:14'),(16,'123456','刀锋','男','header/penguin.png',16,'哈喽咯墨迹空军建军节',0,'2015-12-25 18:07:15'),(17,'123456','刀锋','男','header/penguin.png',16,'哈喽咯墨迹空军建军节',0,'2015-12-25 18:07:16'),(18,'123456','刀锋','男','header/penguin.png',16,'阿萨德佛挡杀佛看到就发生',1003,'2016-01-05 20:58:47'),(19,'123456','刀锋','男','header/penguin.png',16,'计算机是世界上计算机上计算机上计算机',92,'2016-01-05 20:58:45'),(20,'123456','刀锋','男','header/penguin.png',16,'哈喽咯墨迹空军建军节',10,'2016-01-05 17:07:38'),(21,'123456','刀锋','男','header/penguin.png',16,'哈喽咯墨迹空军建军节',0,'2015-12-25 18:07:17'),(22,'123456','刀锋','男','header/penguin.png',16,'哈喽咯墨迹空军建军节',72,'2016-01-05 18:12:30'),(23,'123456','刀锋','男','header/penguin.png',16,'哈喽咯墨迹空军建军节',11,'2016-01-05 17:02:42'),(24,'123456','刀锋','男','header/penguin.png',16,'克隆啦咯了来咯哦哦',0,'2015-12-25 18:08:45'),(25,'123456','刀锋','男','header/penguin.png',16,'他咯啦咯啦咯啦咯哦哦',0,'2015-12-25 18:09:54'),(26,'123456','刀锋','男','header/penguin.png',16,'gghhjjjjjjj',0,'2015-12-26 14:10:18'),(27,'123456','刀锋','男','header/penguin.png',16,'ghjJava',0,'2017-05-26 01:19:24'),(28,'123456','刀锋','男','header/penguin.png',12,'咯弄啦啦啦',0,'2015-12-28 22:11:25'),(29,'123456','刀锋','男','header/penguin.png',12,'咯弄Java',-2,'2016-01-05 18:08:48'),(30,'123456','刀锋','男','header/penguin.png',9,'巨龙',1,'2016-01-05 18:16:28'),(31,'123456','刀锋','男','header/penguin.png',9,'巨龙咯哦哦',1,'2016-01-05 18:15:24'),(32,'111','好人','女','header/penguin.png',16,'gloom',0,'2017-05-26 01:18:50'),(33,'111','好人','女','header/image1.png',16,'ill哦OK',0,'2017-05-26 01:18:47'),(34,'13635302288','咯弄','女','header/image1.png',16,'涂抹巨龙沦陷',0,'2017-05-26 01:18:40'),(35,'13635302288','咯弄','女','header/image1.png',16,'涂抹巨龙沦陷讨论组',0,'2017-05-26 01:18:39'),(36,'13635302233','例如','女','header/image1.png',10,'哦了啦咯了',6,'2017-05-26 01:18:37'),(37,'13635302233','例如','女','header/image1.png',10,'真kick纪录看',6,'2017-05-26 01:18:38'),(38,'13635302233','例如','女','header/image1.png',16,'哈喽',0,'2017-05-26 01:18:35'),(39,'13635302233','例如','女','header/image1.png',16,'哈喽阿拉啦啦',0,'2017-05-26 01:18:35'),(40,'13635302233','例如','女','header/image1.png',16,'考虑考虑',0,'2017-05-26 01:18:34'),(41,'13635302233','例如','女','header/image1.png',9,'考虑考虑',1,'2017-05-26 01:18:33'),(42,'13635302233','例如','女','header/image1.png',16,'km',0,'2017-05-26 01:18:31'),(43,'13635302233','例如','女','header/image1.png',16,'JJ',0,'2017-05-26 01:18:30'),(44,'13635302233','例如','女','header/image1.png',9,'哦哦',0,'2017-05-26 01:18:32'),(45,'13635302233','例如','女','header/image1.png',16,'公会',0,'2017-05-26 01:18:29'),(46,'13635302233','例如','女','header/image1.png',16,'空间了',0,'2017-05-26 01:18:28'),(47,'13635302233','例如','女','header/image1.png',12,'寂寞了',-2,'2017-05-26 01:18:27'),(48,'13635302233','例如','女','header/image1.png',16,'叫路口',0,'2017-05-26 01:18:26'),(49,'13635302233','例如','女','header/image1.png',16,'卢丽丽',2,'2017-05-26 01:18:25'),(50,'13635302233','例如','女','header/image1.png',16,'来咯弄',7,'2017-05-26 01:18:24'),(51,'111','好人','女','header/image1.png',12,'来咯哪啊',0,'2017-05-26 01:14:34'),(52,'111','好人','女','header/image1.png',12,'HK惭愧halo五十咯母体11阿莫统计哈波推G12嗯OK啦1博努奇1嗷呜加了句till继续死1咯五哈鲁体积',0,'2017-05-26 01:14:32'),(53,'111','好人','女','header/image1.png',12,'阿巴巴爸爸吧all就咯啦咯啦LOL吉吉来PK了啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦咯5就咯啦咯啦咯啦咯啦咯啦',3,'2017-05-26 01:14:31'),(54,'111','好人','女','header/image1.png',16,'咯努力来咯哦哦',11,'2017-05-26 01:14:30'),(55,'111','好人','女','header/image1.png',12,'哈哈哈哈哈哈哈哈哈1哎哎哎彼此彼此',2,'2017-05-26 01:14:29'),(56,'111','好人','女','header/image1.png',12,'啦咯啦咯考虑旅途兔兔',-1,'2017-05-26 01:14:28'),(57,'111','好人','女','header/image1.png',10,'ggvxv',3,'2017-05-26 01:14:26'),(58,'111','好人','女','header/image1.png',10,'gfhdgycg',1,'2017-05-26 01:14:26'),(59,'111','好人','女','header/image1.png',10,'ggjvjcucjxjcjxjxjxblzjfzj',6,'2017-05-26 01:14:24'),(60,'111','好人','女','header/image1.png',11,'风雨交加快捷键',1,'2017-05-26 01:14:23'),(61,'111','好人','女','header/image1.png',11,'豆浆好方法',1,'2017-05-26 01:14:23'),(62,'111','好人','女','header/image1.png',13,'如图还回家',0,'2017-05-26 01:14:21'),(63,'111','好人','女','header/image1.png',10,'带来了',1,'2017-05-26 01:14:20'),(64,'111','好人','女','header/image1.png',6,'空军建军节考虑吐了咯',0,'2017-05-26 01:14:19'),(65,'111','好人','女','header/image1.png',6,'啦咯啦咯啦咯啦咯',0,'2017-05-26 01:14:05'),(66,'111','好人','女','header/image1.png',17,'啦咯啦咯啦咯啦咯啦咯',0,'2017-05-26 01:14:14'),(67,'111','好人','女','header/image1.png',17,'习大大威武',0,'2017-05-26 01:14:16'),(68,'111','好人','女','header/image1.png',17,'哦路兔兔wwwYY',0,'2017-05-26 01:14:18');");


            //创建新闻表
            writeableDB.execSQL("CREATE TABLE `news` (\n" +
                    "  `Id` int(11) NOT NULL,\n" +
                    "  `category` int(11) NOT NULL DEFAULT '0',\n" +
                    "  `title` varchar(50) DEFAULT '',\n" +
                    "  `part` text,\n" +
                    "  `content` text,\n" +
                    "  `picture` varchar(255) DEFAULT NULL,\n" +
                    "  `author` varchar(255) DEFAULT NULL,\n" +
                    "  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,\n" +
                    "  PRIMARY KEY (`Id`)\n" +
                    ")");
            writeableDB.execSQL("\n" +
                    "INSERT INTO `news` VALUES (3,5,'第三方','做生意的林先生需要现金，就去中国银行取了20张百元钞票，又在建设银行取了5张百元钞票，全部是今年的新版钞票。看到网上关于错币的新闻，好奇的拿起自己的钞票研究。还真发现有一张不同的。“别的钞票水印上毛主席下颌上的痣清晰可见，但是编号为XE83061110这一张钞票相同位置却没有痣。”林先生','<img src=\\\"image/a82ad.jpg\\\">\\n</br>\\n<p>NBA历史上，还从没有在季后赛东西部两支球队以不败战绩会师总决赛，随着骑士在主场被绿军绝杀，这样的奇迹再次成为泡影。但是勇士和骑士即将上演总决赛Round 3的对决，却依然是以一种近乎于无敌的姿态会师，可这无敌的背后，却是如今联盟格局失衡的写照，造成这种局面的根本原因，就是因为除了勇士和骑士队，实际上联盟根本就找不出第三支拥有三巨头或者四巨头的球队，或许在未来至少两年内，骑士和勇士依然会持续这样的局面，两个球队可能会至少连续五年会师总决赛。\\n三巨头和三人组，实质上有着天差地别的差距，在过去的10年中能够称得上是三巨头的球队实际上寥寥可数，2008年波士顿凯尔特人的皮尔斯，加内特以及雷阿伦算是真正意义上的三巨头，严格说后来的湖人队科比+奥多姆+加索尔都不算是三巨头。巨星，很明显是至少能够在自己所在位置有统治力的球星，甚至能够影响联盟。这之后热火队的韦德，詹姆斯以及波什也算是真正意义上的三巨头。\\n2015年夺冠之后的勇士，水花+格林已经算是三巨头级别，而在东部只有詹姆斯、欧文以及乐福的骑士算是三巨头球队，也就是从那个时候开始，从双方的总决赛第一次相遇开始，联盟中已经出现了两家独大垄断东西部的情况。就东部的格局来说且不说三巨头，就是三人组的球队都非常少。骑士淘汰单核乔治的步行者，然后横扫了没有洛瑞的只有伊巴卡和德罗赞的猛龙，从球星成色上对比东部只有一个球队还能算是三巨头，那就是韦德、朗多和巴特勒的公牛。</p>','image/a82ad.jpg','澎湃新闻网','2017-05-29 10:42:27'),(4,4,'色东方','俺的沙发','俺的沙发“我并不曾想将我的痛苦加诸于这位副警长，但是他刚好就在那儿（所以我杀了他）。” Godbolt说，当他在路边等待救护车前来接送他的时候，他和那些居民正在谈论他正打算将孩子接回家，紧接着有人叫来了警察。“是他们所做的一切干预了事情的发展，其代价就是该名警长的生命，我很抱歉。”他说道。\\n当被问及杀害被害人之后他的下一步计划是什么，Godbolt表示，他本打算自杀，无论他是否杀害了人，他都不值得生存下来。然而，事实是，当Godbolt在Brookhaven作案之后，警察试图将他逮捕后，他自称被室内持枪者所伤，并顺其自然地被当作案发目击者准备送往一间当地的医院。\\n密西西比调查局发言人Strain表示，目前正在从各个案发场所搜集和汇总案件的相关证据，但是调查当局对于案件的进一步细节持非常谨慎的态度，尚没有透露其他更多信息。目前，被害的副警长以及其他受害者的身份信息也尚未公布。\\n截至发稿时间，澎湃新闻多次致电发言人Strain，电话也均处于无人接听的状态。\\n据福克斯新闻28日报道，时年35岁的作案嫌疑人Godbolt，曾有过多次犯罪记录。2005年，Godbolt涉嫌用手枪袭击一名男子后，抢劫了被袭害人身上的现金和首饰;2013年，林肯县警长办公室以实施简单攻击为由起诉了他；2015年，Godbolt因未遵守警长要求而被捕;同年，他以非法持有驾照并超速行驶；2016年，他再次以轻度攻击和无序行为被记录在案。\\nThe Clarion-Ledger报道称，林肯郡约有人口34000人，其中，Brookhaven约有人口14000人，Bogue\\nChitto作为一个非法人镇，距离Brookhaven南部只有10米左右，人口只','image/a1keji1.jpg','第三方','2017-05-29 11:30:37'),(6,7,'范冰冰摘“视后”桂冠','19日晚，一年一度的“国剧盛典”在北京盛大举办。当晚，范冰冰身着刺绣花朵长裙，佩戴璀璨珠宝优雅亮相，她凭借电视剧《武媚娘传奇》摘得“视后”桂冠，并获得“2015年度风云人物”。范冰冰身穿透视美裙披着外套御寒，获李晨小心搀扶','19日晚，一年一度的“国剧盛典”在北京盛大举办。当晚，范冰冰身着刺绣花朵长裙，佩戴璀璨珠宝优雅亮相，她凭借电视剧《武媚娘传奇》摘得“视后”桂冠，并获得“2015年度风云人物”。范冰冰身穿透视美裙披着外套御寒，获李晨小心搀扶','image/a7yule1.jpg','澎湃新闻网','2017-05-29 11:30:45'),(7,7,'曝华人星二代带日本女星嗑药','网易娱乐12月19日报道本网站内凡注明“来源：中国江西网”的所有文字、图片和音','网易娱乐12月19日报道视频稿件均属本网站原创内容，版权均属“中国江西网”所有，任何媒体、网站或个人未经本网站协 议授权不得转载、链接、转贴或以其他方式复制发表。本网站原创内容版权归本网站所有，内容为作者个人观点，本网站只提供参考并不构成任何商业目的及应用建议。 已经由本网站协议授权的媒体、网站，在下载使用时必须注明稿件来源：“中国江西网”，违者本网将依法追究法律责任。','image/a7yule2.jpg','网易娱乐','2017-05-29 11:30:47'),(8,1,'习近平:全力组织搜救','新华网北京12月20日电','<img src=\\\"image/a3news17.jpg\\\">灾害发生后，党中央、国务院高度重视。中共中央总书记、国家主席、中央军委主席习近平立即作出重要指示，要求广东省、深圳市迅速组织力量开展抢险救援，第一时间抢救被困人员，尽最大努力减少人员伤亡，做好伤员救治、伤亡人员家属安抚等善后工作。注意科学施救，防止发生次生灾害。中央有关部门指导地方加强各类灾害和安全生产隐患排查，制定预案，加强预警及应急处置等工作，确保人民群众生命财产安全。','image/a3news17.jpg','央视','2017-05-29 10:55:29'),(9,1,'深圳一工业园山体滑坡','公安部消防局指挥中心消息，20日11时42分，光明新区长圳红坳村凤凰社区宝泰园附近山坡垮塌。截至目前，事故已造成约20栋建筑被埋压，波及面积1万余平方米，生命探测仪探测到倒塌建筑物内有生命迹象。此外，周围煤气站已紧急关闭，暂无煤气泄漏危险。现场下起小雨，给救援工作带来困难','<img src=\\\"image/a1gongyeyuan.jpg\\\">#柳溪工业园山体滑坡#【据目击者：共两个工业园被淹埋】据目击者曾先生介绍，事故中总共两个工业园被淹，其中柳溪工业园全部破淹，德吉程工业园约百分之八十被淹，曾先生住在德吉程工业园，其称他们工业园至少两人被埋。新华社深圳12月20日电','image/a1gongyeyuan.jpg','新华网','2017-05-29 11:34:13'),(10,1,'母亲让女儿用父亲医保卡买药','患有高血压的邹某，让女儿用丈夫老周的社保卡买药，合计报销11376.64元，结果母女俩双双获刑。前天，诸暨市人民法院判决的首例冒用社保卡案，给有些人敲响了警钟。士大夫的所得税法','患有高血压的邹某，让女儿用丈夫老周的社保卡买药，合计报销11376.64元，结果母女俩双双获刑。前天，诸暨市人民法院判决的首例冒用社保卡案，给有些人敲响了警钟。\\n','image/a702d.jpg','绍兴网','2017-05-29 10:38:25'),(11,1,'曝王岐山一个半月未公开露面','自11月2日会见了基辛格后，王岐山就没有独自在公开报道中现身。前几次他“隐身”之后，都出现了大老虎落马的震撼事件。如今，他一个多月未动，中纪委又在“憋大招”么？','新修订的《中国共产党廉洁自律准则》和《中国共产党纪律处分条例》明年1月1日正式开始实行。哪些是执纪重点，如何掌握执纪尺度，如何真正实现“纪法分开”，这些关键问题都需要参与法规起草者的权威发声。此次中纪委大员们密集出京，正是亲自对两项法规作专题辅导。对于他们的到来，各省市和部委极为重视。长安街知事（微信ID:Capitalnews）查阅了相关党报的新闻报道，专题辅导的消息都上了头版甚至头条的位置。','image/wangqisan.jpg','澎湃新闻网','2017-05-29 09:48:05'),(12,1,'市民取出土豪金百元错币','做生意的林先生需要现金，就去中国银行取了20张百元钞票，又在建设银行取了5张百元钞票，全部是今年的新版钞票。看到网上关于错币的新闻，好奇的拿起自己的钞票研究。还真发现有一张不同的。“别的钞票水印上毛主席下颌上的痣清晰可见，但是编号为XE83061110这一张钞票相同位置却没有痣。”林先生说。getHistoricalAxisValue(int, int, int)\\n\\n','<img src=\\\"image/tuhaofenh101.jpg\\\">做生意的林先生需要现金，就去中国银行取了20张百元钞票，又在建设银行取了5张百元钞票，全部是今年的新版钞票。看到网上关于错币的新闻，好奇的拿起自己的钞票研究。还真发现有一张不同的。“别的钞票水印上毛主席下颌上的痣清晰可见，但是编号为XE83061110这一张钞票相同位置却没有痣。”林先生说。做生意的林先生需要现金，就去中国银行取了20张百元钞票，又在建设银行取了5张百元钞票，全部是今年的新版钞票。看到网上关于错币的新闻，好奇的拿起自己的钞票研究。还真发现有一张不同的。“别的钞票水印上毛主席下颌上的痣清晰可见，但是编号为XE83061110这一张钞票相同位置却没有痣。”林先生说。做生意的林先生需要现金，就去中国银行取了20张百元钞票，又在建设银行取了5张百元钞票，全部是今年的新版钞票。看到网上关于错币的新闻，好奇的拿起自己的钞票研究。还真发现有一张不同的。“别的钞票水印上毛主席下颌上的痣清晰可见，但是编号为XE83061110这一张钞票相同位置却没有痣。”林先生说。做生意的林先生需要现金，就去中国银行取了20张百元钞票，又在建设银行取了5张百元钞票，全部是今年的新版钞票。看到网上关于错币的新闻，好奇的拿起自己的钞票研究。还真发现有一张不同的。“别的钞票水印上毛主席下颌上的痣清晰可见，但是编号为XE83061110这一张钞票相同位置却没有痣。”林先生说。','image/tuhaofenh.jpg','澎湃新闻网','2017-05-29 09:50:35'),(13,1,'清华大学遇难博士后的32年人生轨迹(图)','32年前，安徽萧县农村，作为家中长子的孟祥见出生。像众多农家子弟一样，他相信，知识可以改变命运。','32年前，安徽萧县农村，作为家中长子的孟祥见出生。像众多农家子弟一样，他相信，知识可以改变命运。','image/a2news17.jpg','北青网-北京青年报','2017-05-26 00:58:12'),(14,1,'北京电子科技职业学院与江苏汇博机器人公司签署合作意向书','日前，王海平校长带领北京电子科技职业学院汽车工程学院、自动化工程学院、电信工程学院及教务处、国际合作与交流处等部门负责人赴江苏汇博机器人公司开展调研。汇博公司孔繁河董事长和王振华总经理参与了调研活动。','<img src=\\\"image/a5news17.jpg\\\">\\n江苏汇博机器人技术有限公司是一家专门从事机器人技术研发与产业化的国家级高新技术企业，在企业的发展过程中，汇博公司得到了国家863计划、科技支撑计划以及江苏省各级政府、哈尔滨工业大学、苏州大学等有关部门的大力支持，获得国家创新人才推进计划“先进机器人技术”重点领域创新团队（科技部颁发、全国唯一）、江苏省创新团队、江苏省双创人才、苏州市姑苏人才、苏州工业园区科技领军人才等荣誉称号，是江苏省先进机器人技术重点实验室共建单位。','image/a5news17.jpg','中国高校之窗','2017-05-26 01:03:44'),(15,6,'英国记者强闯南海称合法','美国《华尔街日报》周五报道，美国国防部证实美军一架B-52战略轰炸机在南海执行任务时飞进了中国在南海华阳礁上空2海里范围内，五角大楼目前正调查军机会如此接近岛礁的原因。中国外交部在事发后向美国驻华大使馆提出抗议，而国防部则表示，中国军队对美军机活动保持了严密监视，守礁部队高度戒备，对美军机予以警告驱离。','美国《华尔街日报》周五报道，美国国防部证实美军一架B-52战略轰炸机在南海执行任务时飞进了中国在南海华阳礁上空2海里范围内，五角大楼目前正调查军机会如此接近岛礁的原因。中国外交部在事发后向美国驻华大使馆提出抗议，而国防部则表示，中国军队对美军机活动保持了严密监视，守礁部队高度戒备，对美军机予以警告驱离。','image/a00_01.jpg','观察者网','2017-05-29 09:51:27'),(16,1,'河北保定“土豪村”年底分红上千万','2015年12月19日，在河北保定市望都县恒业合作社举行的2015年股金分红大会上，股民分红共计1000多万元，不少农民拿到分红后高兴地合不拢嘴。','<p>2015年12月19日，河北保定市望都县恒业合作社举办股金分红大会，以入股该社小辛庄村千亩莲藕基地投入额的18%向股民分红，共计1000多万元。据了解，该合作社于2007年10月成立，共有484户社员得到了股金分红据了解，该合作社于2007年10月成立，共有484户社员得到了股金分红。</p>说。<p>getHistoricalAxisValue(int, int, int)</p>\\ngetAxisValue(int)\\ngetMotionRange(int)\\nConstant Value: 17 (0x00000011)\\npublic static final int AXIS_ORIENTATION<br>\\n<IMG src = \\\"image/a45g.jpg\\\">\\n<p>Added in API level 12</p>\\nAxis constant: Orientation axis of a motion event.\\n\\nFor a touch screen or touch pad, reports the orientation of the finger or tool in radians relative to the vertical plane of the device. An angle of 0 radians indicates that the major axis of contact is oriented upwards, is perfectly circular or is of unknown orientation. A positive angle indicates that the major axis of contact is oriented to the right. A negative angle indicates that the major axis of contact is oriented to the left. The full range is from -PI/2 radians (finger pointing fully left) to PI/2 radians (finger pointing fully right).\\nFor a stylus, the orientation indicates the direction in which the stylus is pointing in relation to the vertical axis of the current orientation of the screen. The range is from -PI radians to PI radians, where 0 is pointing up, -PI/2 radians is pointing left, -PI or PI radians is pointing down, and PI/2 radians is pointing right. See also AXIS_TILT.\\n2015年12月19日，河北保定市望都县恒业合作社举办股金分红大会，以入股该社小辛庄村千亩莲藕基地投入额的18%向股民分红，共计1000多万元。据了解，该合作社于2007年10月成立，共有484户社员得到了股金分红据了解，该合作社于2007年10月成立，共有484户社员得到了股金分红。说。getHistoricalAxisValue(int, int, int)\\ngetAxisValue(int)\\ngetMotionRange(int)\\nConstant Value: 17 (0x00000011)\\npublic static final int AXIS_ORIENTATION\\n\\nAdded in API level 12\\nAxis constant: Orientation axis of a motion event.\\n\\nFor a touch screen or touch pad, reports the orientation of the finger or tool in radians relative to the vertical plane of the device. An angle of 0 radians indicates that the major axis of contact is oriented upwards, is perfectly circular or is of unknown orientation. A positive angle indicates that the major axis of contact is oriented to the right. A negative angle indicates that the major axis of contact is oriented to the left. The full range is from -PI/2 radians (finger pointing fully left) to PI/2 radians (finger pointing fully right).\\nFor a stylus, the orientation indicates the direction in which the stylus is pointing in relation to the vertical axis of the current orientation of the screen. The range is from -PI radians to PI radians, where 0 is pointing up, -PI/2 radians is pointing left, -PI or PI radians is pointing down, and PI/2 radians is pointing right. See also AXIS_TILT.安居房阿达就流口水的减肥 深刻的理解法\\n2015年12月19日，河北保定市望都县恒业合作社举办股金分红大会，以入股该社小辛庄村千亩莲藕基地投入额的18%向股民分红，共计1000多万元。据了解，该合作社于2007年10月成立，共有484户社员得到了股金分红据了解，该合作社于2007年10月成立，共有484户社员得到了股金分红。说。getHistoricalAxisValue(int, int, int)\\ngetAxisValue(int)\\ngetMotionRange(int)\\nConstant Value: 17 (0x00000011)\\npublic static final int AXIS_ORIENTATION\\n\\nAdded in API level 12\\nAxis constant: Orientation axis of a motion event.\\n\\nFor a touch screen or touch pad, reports the orientation of the finger or tool in radians relative to the vertical plane of the device. An angle of 0 radians indicates that the major axis of contact is oriented upwards, is perfectly circular or is of unknown orientation. A positive angle indicates that the major axis of contact is oriented to the right. A negative angle indicates that the major axis of contact is oriented to the left. The full range is from -PI/2 radians (finger pointing fully left) to PI/2 radians (finger pointing fully right).\\nFor a stylus, the orientation indicates the direction in which the stylus is pointing in relation to the vertical axis of the current orientation of the screen. The range is from -PI radians to PI radians, where 0 is pointing up, -PI/2 radians is pointing left, -PI or PI radians is pointing down, and PI/2 radians is pointing right. See also AXIS_TILT.安居房阿达就流口水的减肥 深刻的理解法\\n2015年12月19日，河北保定市望都县恒业合作社举办股金分红大会，以入股该社小辛庄村千亩莲藕基地投入额的18%向股民分红，共计1000多万元。据了解，该合作社于2007年10月成立，共有484户社员得到了股金分红据了解，该合作社于2007年10月成立，共有484户社员得到了股金分红。说。getHistoricalAxisValue(int, int, int)\\ngetAxisValue(int)\\ngetMotionRange(int)\\nConstant Value: 17 (0x00000011)\\npublic static final int AXIS_ORIENTATION\\n\\nAdded in API level 12\\nAxis constant: Orientation axis of a motion event.\\n\\nFor a touch screen or touch pad, reports the orientation of the finger or tool in radians relative to the vertical plane of the device. An angle of 0 radians indicates that the major axis of contact is oriented upwards, is perfectly circular or is of unknown orientation. A positive angle indicates that the major axis of contact is oriented to the right. A negative angle indicates that the major axis of contact is oriented to the left. The full range is from -PI/2 radians (finger pointing fully left) to PI/2 radians (finger pointing fully right).\\nFor a stylus, the orientation indicates the direction in which the stylus is pointing in relation to the vertical axis of the current orientation of the screen. The range is from -PI radians to PI radians, where 0 is pointing up, -PI/2 radians is pointing left, -PI or PI radians is pointing down, and PI/2 radians is pointing right. See also AXIS_TILT.安居房阿达就流口水的减肥 深刻的理解法\\n2015年12月19日，河北保定市望都县恒业合作社举办股金分红大会，以入股该社小辛庄村千亩莲藕基地投入额的18%向股民分红，共计1000多万元。据了解，该合作社于2007年10月成立，共有484户社员得到了股金分红据了解，该合作社于2007年10月成立，共有484户社员得到了股金分红。说。getHistoricalAxisValue(int, int, int)\\ngetAxisValue(int)\\ngetMotionRange(int)\\nConstant Value: 17 (0x00000011)\\npublic static final int AXIS_ORIENTATION\\n\\nAdded in API level 12\\nAxis constant: Orientation axis of a motion event.\\n\\nFor a touch screen or touch pad, reports the orientation of the finger or tool in radians relative to the vertical plane of the device. An angle of 0 radians indicates that the major axis of contact is oriented upwards, is perfectly circular or is of unknown orientation. A positive angle indicates that the major axis of contact is oriented to the right. A negative angle indicates that the major axis of contact is oriented to the left. The full range is from -PI/2 radians (finger pointing fully left) to PI/2 radians (finger pointing fully right).<br>\\n<IMG src = \\\"image/tuhaofenh101.jpg \\\">\\nFor a stylus, the orientation indicates the direction in which the stylus is pointing in relation to the vertical axis of the current orientation of the screen. The range is from -PI radians to PI radians, where 0 is pointing up, -PI/2 radians is pointing left, -PI or PI radians is pointing down, and PI/2 radians is pointing right. See also AXIS_TILT.安居房阿达就流口水的减肥 深刻的理解法','image/tuhaofenh101.jpg','新华网','2017-05-29 11:33:42'),(17,1,'习近平2016年首次国内考察赴重庆','新华网北京1月5日电  习近平总书记4日下午来到重庆果园港考察，听取长江上游航运中心建设、铁路公路水路联运等情况介绍，察看正在作业的集装箱船。听说渝新欧国际铁路沿线国家实现一次报关查验、全线放行，他赞赏“挺好”。看到港口设施齐备，已初具规模，他说：“这里大有希望。”','<center>\\n<img src=\\\"image/a1news17.jpg\\\" height=\\\"300\\\" width=\\\"600\\\">\\n</center>\\n<p>　新华网北京1月5日电  习近平总书记4日下午来到重庆果园港考察，听取长江上游航运中心建设、铁路公路水路联运等情况介绍，察看正在作业的集装箱船。听说渝新欧国际铁路沿线国家实现一次报关查验、全线放行，他赞赏“挺好”。看到港口设施齐备，已初具规模，他说：“这里大有希望。”</p>\\n<center>\\n<img src=\\\"image/a2news17.jpg\\\"  height=\\\"300\\\" width=\\\"600\\\">\\n</center>\\n<h3>背景：重庆</h3>\\n<p>重庆市位于我国内陆西南部、长江上游地区。党的十八大以来，重庆的汽车、电子信息、装备制造等支柱产业不断发展壮大，战略性新兴产业蓬勃发展，经济发展质量和效益明显提高，２０１４年全市生产总值比上年增长１０．９％，预计２０１５年全市生产总值比上年增长１１％。</p>\\n<center>\\n<img src=\\\"image/a3news17.jpg\\\"  height=\\\"300\\\" width=\\\"600\\\">\\n</center>\\n<h3>背景：果园港</h3>\\n<p>近年来，重庆加快水运发展，长江上游航运中心雏形已初步显现。果园港是国家级铁路、公路、水路多式联运综合交通枢纽，是重庆规划布局的现代化港口群中的主枢纽港、重庆建设长江上游航运中心的重要载体，设计年通过能力３０００万吨，目前已建成１６个５０００吨级泊位。</p>\\n<center>\\n<img src=\\\"image/a4news17.jpg\\\"  height=\\\"300\\\" width=\\\"600\\\">\\n</center>\\n<h3>习近平考察京东方：“把创新搞上去”</h3>\\n<p>习近平来到重庆京东方光电科技有限公司考察，在柔性屏、超高清显示屏等产品前观看演示，了解８．５代液晶面板生产工艺流程，用放大镜贴着玻璃面板观看里面的电路。他说，五大发展理念，“创新”摆在第一位，一定要牢牢把创新抓在手里，把创新搞上去。</p>\\n<center>\\n<img src=\\\"image/a5news17.jpg\\\"  height=\\\"300\\\" width=\\\"600\\\">\\n</center>\\n<h3>背景：重庆京东方光电科技有限公司</h3>\\n<p>京东方重庆第８．５代薄膜晶体管液晶显示屏生产线项目，总投资３２８亿元，设计产能每月１２万片，主要产品包括笔记本电脑和电视的显示模组，是京东方集团全国７个半导体显示器件生产基地之一。去年３月投产，预计今年３月达产，年销售收入将达到１５０亿元</p>\\n\\n','image/a1news17.jpg','新华视点”微博报道','2017-05-26 01:01:46'),(18,1,'重庆火车站：动花“快闪”迎端午','今天上午，一场以“浓情端午、渝快出行”为主题的快闪活动，在重庆北站北广场候车大厅举行。精彩的快闪表演，为出行的旅客带来了假日的清凉与浓浓的端午祝福。','旅客卢先生说，这种形式很新颖，旅客们感受到传统节日的氛围，也让大家出行的心情更加愉快。\\n<img src=\\\"image/a1jiaodian901.jpg\\\"  height=\\\"300\\\" width=\\\"600\\\">上午9点，重庆北站北广场的候车厅里，突然响起了悠扬的《兔子舞》舞曲。20多名车站“动花”及青年职工们，突然出现在重庆北站北广场的候车厅。她们伴着动感的音乐，以整齐的舞步“快闪”起来。整齐划一的舞姿、青春靓丽的形象，引起过往旅客的驻足观赏。不仅如此，“动花”们还邀请部分旅客，一起加入快闪的行列，共舞贺佳节。当天，“动花”们还邀请旅客参与趣味竞答、并发放了小礼物，为旅客送上了节日的祝福。\\n重庆网络广播电视台记者 唐春琳','image/a1jiaodian9.jpg','重庆网络广播电视台','2017-05-29 12:20:49'),(19,2,'江苏做了一件什么事，总理要求各地“都要这样做”?','“我们在全球竞争的大背景下要增强紧迫感，进一步深化‘放管服’改革，持续优化我国的营商环境!”这是李克强总理在今年4月一次常务会议上对各地方、各部门提出的明确要求。','事实上，在这之前有一个省份早已动了起来。他们通过对全省各市县开展“放管服”改革和创业创新环境评价，对标世界银行发布的“企业营商环境指标”，进一步改善和优化全省的营商投资环境。\\n　　今年2月起，江苏省借鉴世界银行《企业营商环境评价》标准，在去年对8市20县(市、区)开展试评价的基础上，对全省13个设区市、96个县(市、区)开展2016年度“放管服”改革和创业创新环境评价。\\n　　对于江苏省的此项改革探索，李克强总理近日作出重要批示：“有比较就能看出存在的不足。今年‘放管服’改革要瞄准问题所在着力攻坚，各地各部门都要这样做。”\\n　　“我们要以全球营商环境最好的地区为标杆，具体、量化地呈现各地的改革成效。”江苏省一位负责人说。\\n　　该负责人介绍，在参照世界银行对全球各地的营商环境评价体系基础上，江苏省围绕改革要求、立足省情实际，以“企业申请开办时间压缩了多少”、“投资项目审批提速了多少”、“群众办事方便了多少”等量化指标为重点，制定了20项内容、45项评价指标的“江苏版”评价体系。\\n　　他说，本次评价委托第三方机构采集数据，把与国际经济体“对标评分”和省内各地“比较评分”相结合。“这意味着‘江苏版’评价体系向世界银行看齐，不降低评价标准。”\\n　　从天津市“封存109枚公章”，到广东省缩短“万里审批图”，可以说，本届中央政府扭住不放、持续深化的“放管服”改革，已经在全国各地掀起一场优化营商环境、为市场“清障搭台”的新浪潮。\\n　　在世界银行发布的《全球营商环境报告》中，中国排名逐年前移，2017年比2014年累计上升了18位。与此相对应的另一组数据是，2016年全国新设企业552.8万户，同比增长24.5%，日均新设企业1.51万户，新增市场主体继续呈“井喷式”增长。\\n　　而江苏省正在大力推进的“对标世界银行”改革举措，正是这场变革的一个最新缩影。当地有关负责人透露，评价结束后，全省将针对发现的差距和问题作出部署，推动更多市县简化审批、优化市场环境，从而通过评价形成改革“倒逼机制”。\\n　　正如李克强总理所说：“我们最大的资源就是人力和人才资源。要不断通过深化‘放管服’改革，处理好政府和市场的关系，为企业解开‘枷锁’，释放市场活力和社会创造力，解放和发展生产力。”\\n　　新京报特约记者 储思琮','image/a2shehui.jpg','新京报','2017-05-29 18:24:25'),(20,2,'端午出行高速路车流量“前紧后松” 预计日均超过200万辆','今年端午节假期期间，市民短途周边出行需求旺盛，虽然高速公路不免费通行，但预计高速公路车流量将继续呈上涨态势，日均超过200','新京报讯 （记者郭超）记者从市交通委获悉，今年端午节假期期间，市民短途周边出行需求旺盛，虽然高速公路不免费通行，但预计高速公路车流量将继续呈上涨态势，日均超过200万辆。预计假期第一天（28日），上午10点至12点路网压力较大，交通指数峰值达到中度拥堵。\\n　　根据以往交通运行数据研判，预计假期第一天（28日），上午10点至12点路网压力较大，交通指数峰值预计接近8，为中度拥堵，故宫、后海、香山等景区是出行热点地区，将面临更大的交通压力，建议市民错峰出行、避开拥堵。假期首日其余时间和第二、第三天交通运行状况维持在“畅通”到“轻度拥堵”状态。\\n　　预计今年端午假期高速公路日均车辆数将继续增长，日均超过200万辆，同比增幅10%。交通需求量排名前五位的高速公路分别为京藏高速、京承高速、京开高速、京港澳高速和机场高速。\\n　　假期首日5月28日上午9至12点是高速公路出行高峰，出京方向车流量集中，城市快速路与高速公路连接处的拥堵情况将较为突出，健翔桥、上清桥、望和桥、来广营桥、马家楼桥、西红门南桥、岳各庄桥、宛平桥、四元桥、五元桥等这些桥区易出现车多拥堵情况。\\n　　市交通部门将通过“北京交通”APP、微博等及时告知实时路况信息和服务信息，为市民朋友规划自己的出行时间及路线提供参考。\\n　　■ 铁路\\n　　铁路增开36.5对列车\\n　　从北京铁路局获悉，“端午”小长假运输期限自5月27日至30日止，共计4天，北京铁路局计划增开旅客列车37.5对，预计全局累计发送旅客440万人（日均110万人），同比增加12.8万人，增长3.0%。其中，5月28日为客流高峰日，当天预计发送旅客131.7万人。\\n　　为满足“端午”小长假旅客出行需求，北京铁路局采取加挂车、动车组列车重联及高铁按高峰运行图开行等方式，计划增开列车37.5对，主要开往上海、深圳、烟台、日照、青岛、武汉、哈尔滨、西安等，以及京津冀的秦皇岛、唐山、邯郸、清河城等，方便当地百姓出行。\\n　　■ 公交\\n　　开通4条节假日专线\\n　　●八达岭专线\\n　　由德胜门专座直达往返八达岭长城景区，全程高速；5月28日、29日开行，发车时间为德胜门8:30-10:30、八达岭14:30-16:30，单程票价30元，购买定制公交往返电子票可享优惠票价50元。\\n　　●慕田峪专线\\n　　由东直门外专座直达往返慕田峪长城景区，全程高速；10月31日前逢节假日开行，发车时间为东直门外8:00、慕田峪16:00，单程票价30元。\\n　　●野三坡百里峡专线\\n　　由天桥汽车站专座直达往返野三坡百里峡景区；10月8日前逢节假日开行，发车时间为天桥汽车站7:30-8:30、百里峡17:00-17:30，单程票价40元。\\n　　●古北水镇专线\\n　　由东直门外专座直达古北水镇景区；东直门外发车时间——工作日8:00、9:00、12:00、15:30，节假日8:00、9:00、12:00、14:00、15:30，古北水镇发车时间——工作日11:00、12:00、16:00、21:00，节假日11:00、12:00、16:00、19:00、21:00，单程票价48元。','image/a2shehui2.jpg','新京报','2017-05-29 18:24:32'),(21,1,'路虎越野车高速路上爆胎失控 后排女子被甩出车外','据了解，该事故车辆为一辆桂A牌照路虎揽胜越野车，在行至该高速路段时突然爆胎失控，撞上道路中间的防护栏。当时车内共有4人','\\n<img src=\\\"image/a1jiaodian1001.jpg\\\"  height=\\\"300\\\" width=\\\"600\\\">广西新闻网河池5月28日讯 5月27日15时13分，河池市消防支队指挥中心接到报警:河池市水任往金城江方向高速路段发生一起交通事故，1人失踪。指挥中心立即调派金城江消防中队官兵前往救援。\\n据了解，该事故车辆为一辆桂A牌照路虎揽胜越野车，在行至该高速路段时突然爆胎失控，撞上道路中间的防护栏。当时车内共有4人，坐在车后排的一名女子在强大的撞击中被甩出车外，失去踪迹，其余几人在安全气囊的保护下均无大碍\\n消防官兵到达现场时，现场交警已在道路对面隔离带的草丛里找到了失踪人员，由于隔离带布置了铁丝网，在医护人员为该女子做伤口简易处理期间，消防官兵利用破拆工具组对隔离带铁丝网进行破拆。16时00分，消防官兵成功将被困人员救出交由现场医护人员，此次事故造成1人受伤。\\n目前，事故具体原因及财产损失还在进一步调查中','image/a1jiaodian10.jpg','广西新闻网 ','2017-05-29 11:32:53'),(22,1,'可笑！中超丢人一幕引20国60大媒体疯狂报道','中超第11轮上海申花0-0战平广州富力的比赛，这场比赛海外也进行了同步的转播，结果比赛中一次关键的判罚，引起了巨大的争议','<img src=\\\"image/a1jiaodian12.jpg\\\"  height=\\\"300\\\" width=\\\"600\\\">中超第11轮上海申花0-0战平广州富力的比赛，这场比赛海外也进行了同步的转播，结果比赛中一次关键的判罚，引起了巨大的争议。下半时特维斯突入禁区后连过三人完成劲射破门，却被裁判马力判为手球在线进球无效，特维斯完全不能接受，双手抱头表示遗憾。\\n赛后，阿根廷媒体率先对此进行了铺天盖地的报道，以\\\"特维斯40天后伤愈回归，绝杀却被判罚无效，特维斯很生气\\\"为题。阿根廷几大报纸《号角报》、《奥莱报》和《民族报》当然都不会放过报道这一大新闻的机会。很快，消息传遍世界各地，美国的《福克斯体育》、英国的《进球网》、西班牙的《阿斯报》、意大利的《米兰体育报》等，用不等的篇幅对此消息进行了转载报道。粗略统计，短短24小时内，至少有20个国家接近60家的知名媒体，对此事进行了报道。特维斯毕竟是转会费和收入高达5.6亿人民币的第一天价外援，媒体都爱在他身上炒作新闻。这是继秦升踩踏维特塞尔遭遇半年重罚后，又一焦点事件被外国媒体抓了现行，当然体现的还是中超联赛形象不佳的一面。球场暴力、裁判执法水平太低，中超的问题，咋就这么多呢?','image/a1jiaodian12.jpg','体育综合  ','2017-05-29 11:31:57'),(23,3,'2003年的这一版《天龙八部》','据悉，这是蒋欣的荧幕初吻，当时的蒋欣爱玩爱闹，开玩笑对林志颖说，想不到我的荧幕初吻竟然给了你这样的老男人。','据悉，这是蒋欣的荧幕初吻，当时的蒋欣爱玩爱闹，开玩笑对林志颖说，想不到我的荧幕初吻竟然给了你这样的老男人。\\n2003年的这一版《天龙八部》由张纪中担任总制片人，周晓文、于敏等联合执导的古装武侠爱情剧，由胡军、林志颖、高虎、刘亦菲、陈好、刘涛等联袂主演。\\n有了刘亦菲、陈好、刘涛这么多的女星，蒋欣在其中的戏份肯定不会太突出。\\n对此，网友们是这么认为的：\\n小草儿啊：当时看木婉清时觉得这姑娘很漂亮怎么没红\\n我是来观光的-：不知道有没有人看过早年蒋欣演的黑蜘蛛…那时候还不知道她是谁，把我吓坏了\\n爱张绵羊我就是我：而且蒋欣当时没有谈过恋爱，不知道怎么演。导演对她说，你就把自己当作狮子，把他当作一盘肉[笑cry][笑cry][笑cry]所以这是小鲜肉的来历吗\\n小黑粉123：因为此剧还有刘涛，刘亦菲，陈好。\\n杜乔丹：兄妹系列 段誉大战木婉清','image/a45g.jpg','娱乐圈','2017-05-29 10:26:44'),(24,1,'美国枪击案8人死亡 嫌犯：想被警方击毙才杀人','据美国媒体The Clarion-Ledger28日报道称，27日发生在密西西比林肯郡郊区的枪击案嫌疑人目前已经被捕，嫌疑犯Godbolt对该媒体记者陈述了自己的作案原因','<img src=\\\"image/a1jiaodian15.jpg\\\"  height=\\\"300\\\" width=\\\"600\\\">\\n\\t据美国媒体The Clarion-Ledger28日报道称，27日发生在密西西比林肯郡郊区的枪击案嫌疑人目前已经被捕，嫌疑犯Godbolt对该媒体记者陈述了自己的作案原因。\\nGodbolt表示，他曾在林肯郡对三个被害人实施了犯罪：两个在Brookhaven，一个在Bogue Chitto。\\n他表示，他之所以做了这件事，是因为他爱他的妻子和孩子，他也喜欢Bogue Chitto。\\nGodbolt自称，在Bogue Chitto作案之前，他曾经试图与当地街道上的一住户谈论他的孩子。就是在那个时候，周围邻居报警了，副警长就是在前来试图调解纠纷后被杀。\\n“我并不曾想将我的痛苦加诸于这位副警长，但是他刚好就在那儿（所以我杀了他）。” Godbolt说，当他在路边等待救护车前来接送他的时候，他和那些居民正在谈论他正打算将孩子接回家，紧接着有人叫来了警察。“是他们所做的一切干预了事情的发展，其代价就是该名警长的生命，我很抱歉。”他说道。\\n当被问及杀害被害人之后他的下一步计划是什么，Godbolt表示，他本打算自杀，无论他是否杀害了人，他都不值得生存下来。然而，事实是，当Godbolt在Brookhaven作案之后，警察试图将他逮捕后，他自称被室内持枪者所伤，并顺其自然地被当作案发目击者准备送往一间当地的医院。\\n密西西比调查局发言人Strain表示，目前正在从各个案发场所搜集和汇总案件的相关证据，但是调查当局对于案件的进一步细节持非常谨慎的态度，尚没有透露其他更多信息。目前，被害的副警长以及其他受害者的身份信息也尚未公布。\\n截至发稿时间，澎湃新闻多次致电发言人Strain，电话也均处于无人接听的状态。\\n<img src=\\\"image/a1jiaodian15.jpg\\\"  height=\\\"300\\\" width=\\\"600\\\">\\n<p>据福克斯新闻28日报道，时年35岁的作案嫌疑人Godbolt，曾有过多次犯罪记录。2005年，Godbolt涉嫌用手枪袭击一名男子后，抢劫了被袭害人身上的现金和首饰;2013年，林肯县警长办公室以实施简单攻击为由起诉了他；2015年，Godbolt因未遵守警长要求而被捕;同年，他以非法持有驾照并超速行驶；2016年，他再次以轻度攻击和无序行为被记录在案。\\nThe Clarion-Ledger报道称，林肯郡约有人口34000人，其中，Brookhaven约有人口14000人，Bogue\\nChitto作为一个非法人镇，距离Brookhaven南部只有10米左右，人口只有533人。\\n案发后，当地政府官员发起了对该起悲剧进行祈祷的相关说明。说明称，这是有记录以来，林肯郡在历史上遭遇的最严重的犯罪事件。每天都有大量的警务人员为了保护和服务社区而作出了自己的牺牲。甚至，我们在这个过程中失去了最优秀的警察，感谢执法机构所做的努力。\\n据美国密西西比州当地媒体“实时新闻网”28日报道称，密西西比州调查局当地时间28日早间证实，27日（周六）当晚在该州林肯郡郊区发生的枪击案造成8人死亡，其中包括当地的一位副警长。案件发 </p>','image/a1jiaodian15.jpg','环球网  ','2017-05-29 18:51:40');");

            //创建用户表
            writeableDB.execSQL("CREATE TABLE `user` (\n" +
                    "  `Id` int(11) NOT NULL,\n" +
                    "  `count` varchar(100) NOT NULL DEFAULT '',\n" +
                    "  `gender` varchar(50) DEFAULT NULL,\n" +
                    "  `username` varchar(50) NOT NULL DEFAULT '',\n" +
                    "  `password` varchar(50) NOT NULL DEFAULT '',\n" +
                    "  `head` varchar(100) NOT NULL DEFAULT 'header/user_icon_pi.png',\n" +
                    "  PRIMARY KEY (`Id`)\n" +
                    ")");
            writeableDB.execSQL("INSERT INTO `user` VALUES (1,'123456','男','刀锋','123456','header/user_icon_pi.png'),(2,'1234567','女','艾尔','123456','header/phppg002.png'),(7,'123','男','如果','123','header/phppg002.png'),(8,'111','女','好人','111','header/image1.png'),(9,'13635302288','女','咯弄','qq123556789','header/penguin.jpg'),(10,'13635302211','女','啦啦啦','qq123456','header/penguin.jpg'),(11,'13635302233','女','例如','qq123456','header/image1.png');");


            //该方法标准事物的成功，否则endTransaction()将回滚
            writeableDB.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //由事物决定是否回滚
            writeableDB.endTransaction();
        }


    }
}
