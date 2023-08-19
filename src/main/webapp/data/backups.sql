-- MySQL dump 10.13  Distrib 5.7.24, for Win64 (x86_64)
--
-- Host: localhost    Database: zefraweb
-- ------------------------------------------------------
-- Server version	5.7.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `email` varchar(25) NOT NULL,
  `name` varchar(10) DEFAULT NULL,
  `password` varchar(300) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES ('479310608@qq.com','小shu','de7bfdce14174fa7b7d025e41b092c3fa4761ae8413491aca608e92cde44a54c');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ccontext`
--

DROP TABLE IF EXISTS `ccontext`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ccontext` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `context` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ccontext`
--

LOCK TABLES `ccontext` WRITE;
/*!40000 ALTER TABLE `ccontext` DISABLE KEYS */;
INSERT INTO `ccontext` VALUES (3,'<h2 style=\"text-align: center;\"><span style=\"font-weight: normal;\">C++指针对象模型</span></h2><p><br></p><div class=\"container0\"><htmlcode><textarea id=\"code0\" style=\"display: none;\"></textarea><div class=\"CodeMirror cm-s-base16-dark\" translate=\"no\"><div style=\"overflow: hidden; position: relative; width: 3px; height: 0px; top: 290px; left: 34px;\"><textarea autocorrect=\"off\" autocapitalize=\"off\" spellcheck=\"false\" tabindex=\"0\" style=\"position: absolute; bottom: -1em; padding: 0px; width: 1000px; height: 1em; min-height: 1em; outline: none;\"></textarea></div><div class=\"CodeMirror-vscrollbar\" tabindex=\"-1\" cm-not-content=\"true\" style=\"display: block; bottom: 0px;\"><div style=\"min-width: 1px; height: 354px;\"></div></div><div class=\"CodeMirror-hscrollbar\" tabindex=\"-1\" cm-not-content=\"true\"><div style=\"height: 100%; min-height: 1px; width: 0px;\"></div></div><div class=\"CodeMirror-scrollbar-filler\" cm-not-content=\"true\"></div><div class=\"CodeMirror-gutter-filler\" cm-not-content=\"true\"></div><div class=\"CodeMirror-scroll\" tabindex=\"-1\"><div class=\"CodeMirror-sizer\" style=\"margin-left: 30px; min-width: 477.906px; margin-bottom: -15px; border-right-width: 35px; min-height: 369px; padding-right: 15px; padding-bottom: 0px;\"><div style=\"position: relative; top: 0px;\"><div class=\"CodeMirror-lines\" role=\"presentation\"><div role=\"presentation\" style=\"position: relative; outline: none;\"><div class=\"CodeMirror-measure\"><span><span>​</span>x</span></div><div class=\"CodeMirror-measure\"></div><div style=\"position: relative; z-index: 1;\"></div><div class=\"CodeMirror-cursors\" style=\"visibility: hidden;\"><div class=\"CodeMirror-cursor\" style=\"left: 4px; top: 342px; height: 19px;\">&nbsp;</div></div><div class=\"CodeMirror-code\" role=\"presentation\" style=\"\"><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">1</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\"><span class=\"cm-keyword\">class</span> <span class=\"cm-def\">user</span> {</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">2</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\"><span class=\"cm-keyword\">public</span>:</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">3</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">    <span class=\"cm-type\">int</span> <span class=\"cm-variable\">userId</span>;</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">4</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">    <span class=\"cm-variable\">user</span> <span class=\"cm-variable\">usera</span>;</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">5</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">    <span class=\"cm-variable\">user</span><span class=\"cm-operator\">*</span> <span class=\"cm-variable\">userp</span>;</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">6</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\"><span cm-text=\"\">​</span></span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">7</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">    <span class=\"cm-variable\">user</span>(<span class=\"cm-type\">int</span> <span class=\"cm-variable\">id</span>) : <span class=\"cm-variable\">userId</span>(<span class=\"cm-variable\">id</span>), <span class=\"cm-variable\">usera</span>(<span class=\"cm-variable\">user</span>(<span class=\"cm-variable\">id</span> <span class=\"cm-operator\">+</span> <span class=\"cm-number\">1</span>)), <span class=\"cm-variable\">userp</span>(<span class=\"cm-atom\">nullptr</span>) {}</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">8</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">    <span class=\"cm-comment\">// 初始化 user1，user1.usera，user1.user.p 均位于栈空间</span></span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">9</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">};</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">10</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\"><span class=\"cm-keyword\">class</span> <span class=\"cm-def\">user</span> {</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">11</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\"><span class=\"cm-keyword\">public</span>:</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">12</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">    <span class=\"cm-type\">int</span> <span class=\"cm-variable\">userId</span>;</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">13</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">    <span class=\"cm-variable\">user</span> <span class=\"cm-variable\">usera</span>;</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">14</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">    <span class=\"cm-variable\">user</span><span class=\"cm-operator\">*</span> <span class=\"cm-variable\">userp</span>;</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">15</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\"><span cm-text=\"\">​</span></span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">16</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">    <span class=\"cm-variable\">user</span>(<span class=\"cm-type\">int</span> <span class=\"cm-variable\">id</span>) : <span class=\"cm-variable\">userId</span>(<span class=\"cm-variable\">id</span>), <span class=\"cm-variable\">usera</span>(<span class=\"cm-variable\">user</span>(<span class=\"cm-variable\">id</span> <span class=\"cm-operator\">+</span> <span class=\"cm-number\">1</span>)), <span class=\"cm-variable\">userp</span>(<span class=\"cm-atom\">nullptr</span>) {}</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">17</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">    <span class=\"cm-comment\">// 初始化 user1，user1.usera，user1.user.p 均位于栈空间</span></span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">18</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">};</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">19</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\"><span cm-text=\"\">​</span></span></pre></div></div></div></div></div></div><div style=\"position: absolute; height: 35px; width: 1px; border-bottom: 0px solid transparent; top: 369px;\"></div><div class=\"CodeMirror-gutters\" style=\"height: 404px;\"><div class=\"CodeMirror-gutter CodeMirror-linenumbers\" style=\"width: 30px;\"></div></div></div></div></htmlcode></div><p>  已知有如上类，</p><p>  1.我执行以下代码：<br></p><p>   user * temp = new user();&nbsp;</p><p>   user * tempp = new user();&nbsp;</p><p> &nbsp; &nbsp; user temp2;&nbsp;</p><p>   temp-&gt;usera = temp2;&nbsp;</p><p> &nbsp; &nbsp; temp-&gt;userp&nbsp;= tempp;</p><p>  <font color=\"#ea2e2e\">①.temp中的usera变量是存储在栈上还是堆上？temp中的userp对象是存储在栈上还是堆上？</font></p><p>  <font color=\"#2eea67\">答案：存储在堆上，temp-&gt;usera = temp2是将temp2拷贝一份赋值到堆空间</font><font color=\"#ea2e2e\"><br></font></p><p>  <font color=\"#ea2e2e\">②.如果调用temp的析构函数，tempp所指向内存会被释放吗？</font><br></p><p>  <font color=\"#2eea67\">答案：不会释放，只会释放调用temp所指向内存的内容，即只会释放调用user * userpp指针的内存，必须手动调用temp-&gt;userp才能被释放掉</font><br></p><p><br></p><p>  2.我执行以下代码：&nbsp;</p><p>  &nbsp;user temp;&nbsp;</p><p>  &nbsp;user * tempp = new user();&nbsp;</p><p>  &nbsp;user temp2;&nbsp;</p><p>  &nbsp;temp.usera = temp2;&nbsp;</p><p>  &nbsp;temp.userp&nbsp;= tempp;<br></p><p>  <font color=\"#ea2e2e\">①.如上temp中的usera变量是存储在栈上还是堆上？temp中的userp对象是存储在栈上还是堆上？</font><br></p><p>  <font color=\"#2eea67\">答案：都是存储在栈上，因为temp本身就是在栈上存储</font><br></p><p><br></p>');
/*!40000 ALTER TABLE `ccontext` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctags`
--

DROP TABLE IF EXISTS `ctags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ctags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag` bigint(20) NOT NULL,
  `time` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctags`
--

LOCK TABLES `ctags` WRITE;
/*!40000 ALTER TABLE `ctags` DISABLE KEYS */;
INSERT INTO `ctags` VALUES (3,2,0);
/*!40000 ALTER TABLE `ctags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctitle`
--

DROP TABLE IF EXISTS `ctitle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ctitle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctitle`
--

LOCK TABLES `ctitle` WRITE;
/*!40000 ALTER TABLE `ctitle` DISABLE KEYS */;
INSERT INTO `ctitle` VALUES (3,'C++指针对象模型');
/*!40000 ALTER TABLE `ctitle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dcontext`
--

DROP TABLE IF EXISTS `dcontext`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dcontext` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `context` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dcontext`
--

LOCK TABLES `dcontext` WRITE;
/*!40000 ALTER TABLE `dcontext` DISABLE KEYS */;
INSERT INTO `dcontext` VALUES (5,'<h2 style=\"text-align: center;\"><span style=\"font-weight: normal;\">【ICSharpCode.SharpZipLib.dll】zip解压缩处理动态库</span></h2><p>  作用：<font color=\"#2eea67\">1.处理C#语言程序的文件解压缩问题</font></p><p>     <font color=\"#2eea67\">2.读取压缩包中的文件流</font></p><p><br></p><p>  使用教程:</p><div class=\"container0\"><htmlcode><textarea id=\"code0\" style=\"display: none;\"></textarea><div class=\"CodeMirror cm-s-base16-dark\" translate=\"no\"><div style=\"overflow: hidden; position: relative; width: 3px; height: 0px; top: 254px; left: 197.422px;\"><textarea autocorrect=\"off\" autocapitalize=\"off\" spellcheck=\"false\" tabindex=\"0\" style=\"position: absolute; bottom: -1em; padding: 0px; width: 1000px; height: 1em; min-height: 1em; outline: none;\"></textarea></div><div class=\"CodeMirror-vscrollbar\" tabindex=\"-1\" cm-not-content=\"true\" style=\"display: block; bottom: 0px;\"><div style=\"min-width: 1px; height: 468px;\"></div></div><div class=\"CodeMirror-hscrollbar\" tabindex=\"-1\" cm-not-content=\"true\"><div style=\"height: 100%; min-height: 1px; width: 0px;\"></div></div><div class=\"CodeMirror-scrollbar-filler\" cm-not-content=\"true\"></div><div class=\"CodeMirror-gutter-filler\" cm-not-content=\"true\"></div><div class=\"CodeMirror-scroll\" tabindex=\"-1\" draggable=\"false\"><div class=\"CodeMirror-sizer\" style=\"margin-left: 30px; min-width: 570.141px; margin-bottom: -15px; border-right-width: 35px; min-height: 483px; padding-right: 15px; padding-bottom: 0px;\"><div style=\"position: relative; top: 0px;\"><div class=\"CodeMirror-lines\" role=\"presentation\"><div role=\"presentation\" style=\"position: relative; outline: none;\"><div class=\"CodeMirror-measure\"><pre class=\"CodeMirror-line-like\">x</pre></div><div class=\"CodeMirror-measure\"></div><div style=\"position: relative; z-index: 1;\"></div><div class=\"CodeMirror-cursors\" style=\"\"><div class=\"CodeMirror-cursor\" style=\"left: 167.422px; top: 361px; height: 19px;\">&nbsp;</div></div><div class=\"CodeMirror-code\" role=\"presentation\" style=\"\"><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">1</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\"><span class=\"cm-keyword\">using</span> <span class=\"cm-variable\">ICSharpCode</span>.<span class=\"cm-variable\">SharpZipLib</span>.<span class=\"cm-variable\">Zip</span>;</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">2</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\"><span class=\"cm-keyword\">public</span> <span class=\"cm-keyword\">class</span> <span class=\"cm-def\">temp</span> </span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">3</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">{</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">4</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">  <span class=\"cm-comment\">//我们要读取的zip压缩包路径</span></span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">5</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">  <span class=\"cm-type\">string</span> <span class=\"cm-variable\">path</span> <span class=\"cm-operator\">=</span> <span class=\"cm-meta\">@</span><span class=\"cm-string\">\'.\\lua\\lua.zip\'</span>;</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">6</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">  <span class=\"cm-comment\">//根据路径创建我们的zip处理对象</span></span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">7</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">  <span class=\"cm-variable\">ZipFile</span> <span class=\"cm-variable\">zip</span> <span class=\"cm-operator\">=</span> <span class=\"cm-keyword\">new</span> <span class=\"cm-variable\">ZipFile</span>(<span class=\"cm-variable\">path</span>);</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">8</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">  <span class=\"cm-comment\">//如果压缩包带有密码，输入参数</span></span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">9</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">  <span class=\"cm-variable\">zip</span>.<span class=\"cm-variable\">Password</span> <span class=\"cm-operator\">=</span> <span class=\"cm-string\">\"123456\"</span>;</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">10</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">  <span class=\"cm-comment\">//获取指定文件的信息</span></span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">11</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">  <span class=\"cm-type\">string</span> <span class=\"cm-variable\">filename</span> <span class=\"cm-operator\">=</span> <span class=\"cm-string\">\"c145782.lua\"</span>:</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">12</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">  <span class=\"cm-variable\">ZipEntry</span> <span class=\"cm-variable\">ze</span> <span class=\"cm-operator\">=</span> <span class=\"cm-variable\">zip</span>.<span class=\"cm-variable\">GetEntry</span>(<span class=\"cm-variable\">filename</span>);</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">13</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">  <span class=\"cm-comment\">//将指定文本转为流  </span></span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">14</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">  <span class=\"cm-variable\">Stream</span> <span class=\"cm-variable\">zipStream</span> <span class=\"cm-operator\">=</span> <span class=\"cm-variable\">zip</span>.<span class=\"cm-variable\">GetInputStream</span>(<span class=\"cm-variable\">ze</span>);</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">15</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">  <span class=\"cm-comment\">//把流转为字符串返回</span></span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">16</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">  <span class=\"cm-keyword\">using</span>(<span class=\"cm-variable\">StreamReader</span> <span class=\"cm-variable\">sr</span> <span class=\"cm-operator\">=</span> <span class=\"cm-keyword\">new</span> <span class=\"cm-variable\">StreamReader</span>(<span class=\"cm-variable\">zipStream</span>,<span class=\"cm-variable\">Encoding</span>.<span class=\"cm-variable\">UTF8</span>))</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">17</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">  {</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">18</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\"><span class=\"cm-tab\" role=\"presentation\" cm-text=\"	\">    </span><span class=\"cm-tab\" role=\"presentation\" cm-text=\"	\">    </span><span class=\"cm-comment\">//获取目标字符串</span></span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">19</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">    <span class=\"cm-tab\" role=\"presentation\" cm-text=\"	\">    </span><span class=\"cm-type\">String</span> <span class=\"cm-variable\">res</span> <span class=\"cm-operator\">=</span> <span class=\"cm-variable\">sr</span>.<span class=\"cm-variable\">ReadToEnd</span>();</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">20</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">    <span class=\"cm-tab\" role=\"presentation\" cm-text=\"	\">    </span><span class=\"cm-comment\">//字符串操作.......</span></span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">21</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">    <span class=\"cm-tab\" role=\"presentation\" cm-text=\"	\">    </span><span class=\"cm-comment\">//关闭流</span></span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">22</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">    <span class=\"cm-tab\" role=\"presentation\" cm-text=\"	\">    </span><span class=\"cm-variable\">sr</span>.<span class=\"cm-variable\">Colose</span>();</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">23</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">    </span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">24</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">  }</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: -30px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 22px;\">25</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">}</span></pre></div></div></div></div></div></div><div style=\"position: absolute; height: 35px; width: 1px; border-bottom: 0px solid transparent; top: 483px;\"></div><div class=\"CodeMirror-gutters\" style=\"height: 518px;\"><div class=\"CodeMirror-gutter CodeMirror-linenumbers\" style=\"width: 30px;\"></div></div></div></div></htmlcode></div><p>  官方地址：https://github.com/icsharpcode/SharpZipLib</p>');
/*!40000 ALTER TABLE `dcontext` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dtags`
--

DROP TABLE IF EXISTS `dtags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dtags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag` bigint(20) NOT NULL,
  `time` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dtags`
--

LOCK TABLES `dtags` WRITE;
/*!40000 ALTER TABLE `dtags` DISABLE KEYS */;
INSERT INTO `dtags` VALUES (5,98308,0);
/*!40000 ALTER TABLE `dtags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dtitle`
--

DROP TABLE IF EXISTS `dtitle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dtitle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dtitle`
--

LOCK TABLES `dtitle` WRITE;
/*!40000 ALTER TABLE `dtitle` DISABLE KEYS */;
INSERT INTO `dtitle` VALUES (5,'【ICSharpCode.SharpZipLib.dll】C# Zip解压缩处理动态库');
/*!40000 ALTER TABLE `dtitle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `econtext`
--

DROP TABLE IF EXISTS `econtext`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `econtext` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `context` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `econtext`
--

LOCK TABLES `econtext` WRITE;
/*!40000 ALTER TABLE `econtext` DISABLE KEYS */;
INSERT INTO `econtext` VALUES (3,'<h2 style=\"text-align: center;\"><span style=\"font-weight: normal;\">Java Tomact服务器启动报错:A child container failed during start&nbsp;</span></h2><p>  <font color=\"#2eea67\">解决方案:检查filter筛选器类中的注是否写对</font></p><script src=\"js/dependency/vue.js\"></script><script src=\"element-ui/lib/index.js\"></script><script src=\"./js/tools.js\" charset=\"UTF-8\"></script><script src=\"./js/article.js\" charset=\"UTF-8\"></script><div class=\"container0\"><htmlcode><textarea id=\"code0\" style=\"display: none;\"></textarea><div class=\"CodeMirror cm-s-base16-dark\" translate=\"no\"><div style=\"overflow: hidden; position: relative; width: 3px; height: 0px; top: 80px; left: 39.7969px;\"><textarea autocorrect=\"off\" autocapitalize=\"off\" spellcheck=\"false\" tabindex=\"0\" style=\"position: absolute; bottom: -1em; padding: 0px; width: 1000px; height: 1em; min-height: 1em; outline: none;\"></textarea></div><div class=\"CodeMirror-vscrollbar\" tabindex=\"-1\" cm-not-content=\"true\"><div style=\"min-width: 1px; height: 0px;\"></div></div><div class=\"CodeMirror-hscrollbar\" tabindex=\"-1\" cm-not-content=\"true\"><div style=\"height: 100%; min-height: 1px; width: 0px;\"></div></div><div class=\"CodeMirror-scrollbar-filler\" cm-not-content=\"true\"></div><div class=\"CodeMirror-gutter-filler\" cm-not-content=\"true\"></div><div class=\"CodeMirror-scroll\" tabindex=\"-1\" draggable=\"false\"><div class=\"CodeMirror-sizer\" style=\"margin-left: 0px; min-width: 177.984px; margin-bottom: -15px; border-right-width: 35px; min-height: 103px; padding-right: 0px; padding-bottom: 0px;\"><div style=\"position: relative; top: 0px;\"><div class=\"CodeMirror-lines\" role=\"presentation\"><div role=\"presentation\" style=\"position: relative; outline: none;\"><div class=\"CodeMirror-measure\"><pre class=\"CodeMirror-line-like\">x</pre></div><div class=\"CodeMirror-measure\"></div><div style=\"position: relative; z-index: 1;\"></div><div class=\"CodeMirror-cursors\" style=\"visibility: hidden;\"><div class=\"CodeMirror-cursor\" style=\"left: 39.7969px; top: 76px; height: 19px;\">&nbsp;</div></div><div class=\"CodeMirror-code\" role=\"presentation\" style=\"\"><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: 0px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 1px;\">1</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">      @WebFilter(\"/*\")</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: 0px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 1px;\">2</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">      //错误写法:(\"*/\")</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: 0px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 1px;\">3</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">      public void filter() {</span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: 0px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 1px;\">4</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" class=\"cm-tab-wrap-hack\" style=\"padding-right: 0.1px;\"><span class=\"cm-tab\" role=\"presentation\" cm-text=\"	\">    </span><span class=\"cm-tab\" role=\"presentation\" cm-text=\"	\">    </span><span class=\"cm-tab\" role=\"presentation\" cm-text=\"	\">    </span></span></pre></div><div style=\"position: relative;\"><div class=\"CodeMirror-gutter-wrapper\" aria-hidden=\"true\" style=\"left: 0px;\"><div class=\"CodeMirror-linenumber CodeMirror-gutter-elt\" style=\"left: 0px; width: 1px;\">5</div></div><pre class=\" CodeMirror-line \" role=\"presentation\"><span role=\"presentation\" style=\"padding-right: 0.1px;\">      }</span></pre></div></div></div></div></div></div><div style=\"position: absolute; height: 35px; width: 1px; border-bottom: 0px solid transparent; top: 103px;\"></div><div class=\"CodeMirror-gutters\" style=\"height: 138px;\"><div class=\"CodeMirror-gutter CodeMirror-linenumbers\" style=\"width: 1px;\"></div></div></div></div></htmlcode></div>'),(4,'<h2 style=\"text-align: center;\"><span style=\"font-weight: normal;\">Java服务器端resp.write()返回的数据客户端无法接收</span></h2><p>  前端js请求报错：<font color=\"#ea2e2e\">status of 405 (Method Not Allowed)\n\nFailed to load resource: the server responded with a status of 405 (Method Not</font></p><p><font color=\"#ea2e2e\">Allowed)</font></p><p>  原因如下:​​</p><div class=\"img_wrapper\" style=\"height: 282px; width: 751px;\"><img src=\"..\\ZefraWeb\\harticle\\img\\0.jpg\" class=\"preview_Image\"></div><p>  <font color=\"#2eea67\">解决方案：删除父类的方法即可</font></p>'),(5,'<h2 style=\"text-align: center;\"><span style=\"font-weight: normal;\">Java执行Sql代码报错：Expected one result (or null) to be returned by selectOne(), but found: 2</span></h2><p>  错误位置代码:</p><p><br></p><div class=\"img_wrapper\" style=\"width: 667px; height: 355px;\"><img src=\"..\\ZefraWeb\\harticle\\img\\1.jpg\" class=\"preview_Image\"></div><p>  错误原因：要查找的参数，在数据库里找到了多条满足的情况数据。但返回的时候，只能接收一个数据，就导致了该问题。&nbsp;</p><p>  </p><div class=\"img_wrapper\"><img src=\"..\\ZefraWeb\\harticle\\img\\2.jpg\" class=\"preview_Image\"></div><p>  <font color=\"#2eea67\">解决方案：1.把对象转为集合存储</font></p><p><font color=\"#2eea67\">      &nbsp; &nbsp;2.sql中把重复的元素去除</font></p>'),(6,'<h2 style=\"text-align: center;\"><span style=\"font-weight: normal;\">JavaScript报错:Error in v-on handler: \"TypeError: Converting circular structure to JSON\"</span></h2><p>--&gt;starting at object with constructor \'Object\'<br></p><p>--&gt;roperty \'_renderProxy\' closes the circle\"</p><p><br></p><div class=\"img_wrapper\" style=\"height: 370px; width: 642px;\"><img src=\"..\\ZefraWeb\\harticle\\img\\3.jpg\" class=\"preview_Image\"></div><p>  如图，我们用Json转化webData的对象,webData的name和pass对象被错传为name和password而不是字符，转换不了时报错</p><p>  <font color=\"#2eea67\">解决方案：查看一下自己传入json中的对象是否可以转为字符串&nbsp;</font></p>'),(7,'<h2 style=\"text-align: center;\"><span style=\"font-weight: normal;\">C++编译报错：X已定义在X.obj</span></h2><p>  原因：在h文件里面定义了变量，多个cpp文件导入该h文件时会出现变量多次定义的情况</p><p>  <font color=\"#2eea67\">解决方案：把变量定义在cpp文件</font></p>'),(8,'<h2 style=\"text-align: center;\"><span style=\"font-weight: normal;\">CSS按钮元素在设置没有问题的情况下仍然无法被点击</span></h2><p>  原因：float-left 属性可以使元素浮动到左侧，并允许其他元素占据该元素原本的位置。这个属性在实现响应式布局和网站排版时非常实用，可以帮助开发者更加灵活地控制页面元素的位置。然而，当使用 float-left 后，被浮动的元素会脱离文档流，并且与其他元素重叠或间隙较小，可能会导致事件不能正常触发。由于多个button按钮元素设置了 float: left，它们堆叠在一起，形成一个摆放错乱的布局，可能会造成按钮之间无法点击的问题</p><p> <font color=\"#2eea67\"> 解决方案：清除元素的float属性即可解决</font></p>'),(9,'<h2 style=\"text-align: center;\"><span style=\"font-weight: normal;\">Java SpringMvc启动报错：[ERROR] Context initialization failed</span></h2><p>  错误原因：spring加载的配置文件格式写错</p><p><br></p><div class=\"img_wrapper\" style=\"height: 416px; width: 723px;\"><img src=\"..\\ZefraWeb\\harticle\\img\\4.jpg\" class=\"preview_Image\"></div><p> <font color=\"#2eea67\"> 解决方案：用正确写法-@PropertySource(\"classpath:jdbc.properties\")</font></p>'),(10,'<h2 style=\"text-align: center;\"><span style=\"font-weight: normal;\">Java SpringMvc接收客户端传入的Json数据解析成对象时报错</span></h2><p>  报错信息：<font color=\"#ea2e2e\">严重- Servlet.service() for servlet [dispatcher] in context with path [/SpringBotMvc] threw exception [Request processing failed; nested exception is org.springframework.http.converter.HttpMessageConversionException: Type definition error: [simple type, class com.zefra.domain.Account]; nested exception is com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance of `com.zefra.domain.Account` (no Creators, like default construct, exist): cannot deserialize from Object value (no delegate- or property-based Creato</font></p><p><br></p><div class=\"img_wrapper\" style=\"width: 962px; height: 295px;\"><img src=\"..\\ZefraWeb\\harticle\\img\\5.jpg\" class=\"preview_Image\"></div><p>  报错代码如上</p><p>  原因：没有给Account对象提供无参构造函数，Spring无法反序列化解析</p><p>  <font color=\"#2eea67\">解决方案：给对象加上无参构造函数即可</font></p><p><font color=\"#2eea67\"><br></font></p><p><br></p><div class=\"img_wrapper\" style=\"width: 867px; height: 220px;\"><img src=\"..\\ZefraWeb\\harticle\\img\\6.jpg\" class=\"preview_Image\"></div><p><br></p><p><br></p>'),(12,'<h2 style=\"text-align: center;\"><span style=\"font-weight: normal;\">Java服务器端返回请求信息报错：Error parsing HTTP request header</span></h2><p>  <font color=\"#ea2e2e\">--&gt;Note: further occurrences of HTTP header parsing errors will be logged at DEBUG level.</font></p><p>  错误原因：服务器返回的请求头信息超过了定义的最大数量限制</p><p>  <font color=\"#2eea67\">解决方案:把get请求转为post请求返回</font></p>'),(13,'<h2 style=\"text-align: center;\"><span style=\"font-weight: normal;\">Java服务器sql查询时报错：Type interface com.x.class is not known to the MapperRegistry</span></h2><p>  问题原因：在mybatis的配置文件中没有正确映射对应的mapper接口</p><p>  <font color=\"#2eea67\">解决方案：在mybatis的对应配置文件中映射正确的接口即可</font></p>');
/*!40000 ALTER TABLE `econtext` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `etags`
--

DROP TABLE IF EXISTS `etags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag` bigint(20) NOT NULL,
  `time` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `etags`
--

LOCK TABLES `etags` WRITE;
/*!40000 ALTER TABLE `etags` DISABLE KEYS */;
INSERT INTO `etags` VALUES (3,264,8.33333),(4,520,33.3333),(5,1032,0.115741),(6,2064,0.138889),(7,2,0.115741),(8,4608,0.694444),(9,8712,0.231481),(10,10760,0.694444),(12,520,0),(13,17928,0.0231481);
/*!40000 ALTER TABLE `etags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `etitle`
--

DROP TABLE IF EXISTS `etitle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etitle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `etitle`
--

LOCK TABLES `etitle` WRITE;
/*!40000 ALTER TABLE `etitle` DISABLE KEYS */;
INSERT INTO `etitle` VALUES (3,'Java Tomact服务器启动报错:A child container failed during start'),(4,'Java服务器端resp.write()返回的数据网页端无法接收'),(5,'Java执行Sql代码报错：Expected one result (or null) to be returned by selectOne(), but found: 2'),(6,'JavaScript报错:Error in v-on handler: \"TypeError: Converting circular structure to JSON\"'),(7,'C++编译报错：X已定义在X.obj'),(8,'CSS按钮元素在设置没有问题的情况下仍然无法被点击'),(9,'Java SpringMvc启动报错：[ERROR] Context initialization failed'),(10,'Java SpringMvc接收客户端传入的Json数据解析成对象时报错'),(12,'Java服务器端返回请求信息报错：Error parsing HTTP request header'),(13,'Java服务器sql查询时报错：Type interface com.x.class is not known to the MapperRegistry.');
/*!40000 ALTER TABLE `etitle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `temp`
--

DROP TABLE IF EXISTS `temp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `temp` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL DEFAULT '',
  `url` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `temp`
--

LOCK TABLES `temp` WRITE;
/*!40000 ALTER TABLE `temp` DISABLE KEYS */;
INSERT INTO `temp` VALUES (1,'jack','101.com'),(2,'tom','145.com'),(3,'sad','134'),(12,'sqd','asd'),(23,'sadad','asd');
/*!40000 ALTER TABLE `temp` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-19 22:34:18
