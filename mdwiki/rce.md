# RCE

CVE-2014-1999
---

```
FuelPHP 1.1 から 1.7.1 まで
Request_Curl クラス
```
```
#1: C:\xampp\htdocs\main\fuelphp-1.7.1\fuel\index.php:3
    $a = new Fuel\Core\Request_Curl($_GET['']);
#2: C:\xampp\htdocs\main\fuelphp-1.7.1\fuel\core\classes\request\curl.php:38
            parent::__construct($resource, $options, $method);
#3: C:\xampp\htdocs\main\fuelphp-1.7.1\fuel\core\classes\request\curl.php:56
            return curl_init($this->resource);
#4: C:\xampp\htdocs\main\fuelphp-1.7.1\fuel\core\classes\request\curl.php:153
            $body = curl_exec($connection);
#5: C:\xampp\htdocs\main\fuelphp-1.7.1\fuel\core\classes\request\curl.php:177
            $this->set_response($body, $this->response_info('http_code', 200), $mime, $headers);
#6: C:\xampp\htdocs\main\fuelphp-1.7.1\fuel\core\classes\request\driver.php:301
                $body = \Format::forge($body, static::$auto_detect_formats[$mime])->to_array();
#7: C:\xampp\htdocs\main\fuelphp-1.7.1\fuel\core\classes\format.php:42
            return new static($data, $from_type);
#8: C:\xampp\htdocs\main\fuelphp-1.7.1\fuel\core\classes\format.php:75
                    $data = call_user_func(array($this, '_from_' . $from_type), $data);
#9: C:\xampp\htdocs\main\fuelphp-1.7.1\fuel\core\classes\format.php:485
            return unserialize(trim($string));
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-000082.html)


CVE-2015-7527
---

```
WordPress 用 Cool Video Gallery プラグインの lib/core.php
Cool Video Gallery 1.9
```
```
wordpressの関数を使用しているのでテストにはwordpressも必要
```
```
				$video_meta_data = unserialize($result['meta_data']);
```
[JVN](http://jvndb.jvn.jp/ja/contents/2015/JVNDB-2015-006467.html)


CVE-2015-6967
---

```
Nibbleblog 4.0.5 未満
Nibbleblog の My Image プラグイン
リモートの管理者により、実行可能な拡張子を持つファイルをアップロードされ、その後、content/private/plugins/my_image/image.php 配下の当該ファイルへ直接のリクエストを介して
```
```
#1: C:\xampp\htdocs\main\nibbleblog-v4.0.4\nibbleblog-v404\admin\ajax\uploader.php:40
        $content = file_get_contents("php://input");
#2: C:\xampp\htdocs\main\nibbleblog-v4.0.4\nibbleblog-v404\admin\ajax\uploader.php:60
        if( file_put_contents(PATH_UPLOAD.$filename.'_'.$number.'_o.'.$ext, $content) )
```
[JVN](http://jvndb.jvn.jp/ja/contents/2015/JVNDB-2015-004719.html)


CVE-2015-5681
---

```
WordPress 用 Powerplay Gallery プラグインの upload.php には、ファイルを無制限にアップロードされることにより
Powerplay Gallery 3.3
第三者により、実行可能な拡張子を持つファイルをアップロードされ、その後、*_uploadfolder/big/ 配下の当該ファイルへ直接のリクエストを介して
```
```
モジュールが手に入らない。
```
[JVN](http://jvndb.jvn.jp/ja/contents/2015/JVNDB-2015-004311.html)


CVE-2015-5457
---

```
PivotX 2.3.11 未満
PivotX には、複数の拡張子を持つファイルをリネームする際、新しいファイル拡張子を検証しないため
```
```
以下か？？？
#1: C:\xampp\htdocs\main\pivotx_2.3.10\pivotx\fileupload.php:75
        $fileName = $_FILES['file']['name'];
#2: C:\xampp\htdocs\main\pivotx_2.3.10\pivotx\fileupload.php:81
    $fileName = safeString($fileName,true,'.');
#3: C:\xampp\htdocs\main\pivotx_2.3.10\pivotx\lib.php:4485
        $str = strtr($str, array(
#4: C:\xampp\htdocs\main\pivotx_2.3.10\pivotx\lib.php:4535
        $str = trim(utf8_decode($str));
#5: C:\xampp\htdocs\main\pivotx_2.3.10\pivotx\lib.php:4537
        $str = strtr($str, array("\xC4"=>"Ae", "\xC6"=>"AE", "\xD6"=>"Oe", "\xDC"=>"Ue", "\xDE"=>"TH",
#6: C:\xampp\htdocs\main\pivotx_2.3.10\pivotx\lib.php:4540
        $str=str_replace("&amp;", "", $str);
#7: C:\xampp\htdocs\main\pivotx_2.3.10\pivotx\lib.php:4553
        $str = preg_replace("$delim$regex$delim", "", $str);
#8: C:\xampp\htdocs\main\pivotx_2.3.10\pivotx\lib.php:4555
        return $str;
#9: C:\xampp\htdocs\main\pivotx_2.3.10\pivotx\fileupload.php:81
    $fileName = safeString($fileName,true,'.');
#10: C:\xampp\htdocs\main\pivotx_2.3.10\pivotx\fileupload.php:209
        $out = fopen($targetDir . DIRECTORY_SEPARATOR . $fileName, $chunk == 0 ? "wb" : "ab");
```
[JVN](http://jvndb.jvn.jp/ja/contents/2015/JVNDB-2015-003530.html)


CVE-2015-2842
---

```
GoAdmin CE 3.3-1421902800 未満の 3.x
GoAutoDial GoAdmin CE の audiostore (Voice Files) アップロード機能の go_audiostore.php には、ファイルを無制限にアップロードされることにより
第三者により、実行可能な拡張子を持つファイルをアップロードされ、その後、sounds/ 配下のファイルに直接のリクエストを介して
```
```
http://goautodial.org/
GOautodial is an open source web based call center system based on CentOS.
```
```
モジュールが手に入らない。
```
[JVN](http://jvndb.jvn.jp/ja/contents/2015/JVNDB-2015-002681.html)


CVE-2015-2825
---

```
Simple Ads Manager 2.5.96 未満
WordPress 用 Simple Ads Manager プラグインの sam-ajax-admin.php には、ファイルを無制限にアップロードされることにより
第三者により、実行可能な拡張子を持つファイルをアップロードされ、その後、path パラメータで指定されたディレクトリ内の当該ファイルへの直接のリクエストを介して
```
```
#1: C:\xampp\htdocs\main\simple-ads-manager.2.5.94\simple-ads-manager\sam-ajax-admin.php:306
            $file = $uploadDir . basename($_FILES['uploadfile']['name']);
#2: C:\xampp\htdocs\main\simple-ads-manager.2.5.94\simple-ads-manager\sam-ajax-admin.php:308
            if ( move_uploaded_file( $_FILES['uploadfile']['tmp_name'], $file )) {
```
[JVN](http://jvndb.jvn.jp/ja/contents/2015/JVNDB-2015-002476.html)


CVE-2015-2194
---

```
Fusion 3.1
Wordpress 用 Fusion テーマの functions.php の fusion_options 関数には、ファイルを無制限にアップロードされることにより
リモート認証されたユーザにより、fusion_save アクションの実行可能な拡張子を持つファイルをアップロードされ、そのファイルにアクセスされることで
```
```
モジュールが手に入らない。
```
[JVN](http://jvndb.jvn.jp/ja/contents/2015/JVNDB-2015-001649.html)


CVE-2015-1604
---

```
Adminsystems CMS 4.0.2 未満
Adminsystems CMS の asys/site/files.php には、ファイルを無制限にアップロードされることにより
リモート認証されたユーザにより、実行形式の拡張子を持つファイルをアップロードされ、その後、upload/files/ 配下のファイルに直接のリクエストを介してアクセスされることで
```
```
#1: C:\xampp\htdocs\main\adminsystems-4.0.0\upload\connectors\php\filemanager.class.php:260
        move_uploaded_file($_FILES['newfile']['tmp_name'], $this->doc_root . $this->post['currentpath'] . $_FILES['newfile']['name']);
```
[JVN](http://jvndb.jvn.jp/ja/contents/2015/JVNDB-2015-001566.html)


CVE-2014-8603
---

```
XCloner 3.1.1 (WordPress)
XCloner 3.5.1 (Joomla!)
WordPress および Joomla! 用 XCloner プラグインの cloner.functions.php
リモートの管理者により、(1) バックアップ作成時のファイル名内のシェルのメタ文字、または以下の変数に関する処理を介して、任意のコードを実行される可能性があります。

(2) $_CONFIG[tarpath]
(3) $exclude
(4) $_CONFIG['tarcompress']
(5) $_CONFIG['filename']
(6) $_CONFIG['exfile_tar']
(7) $_CONFIG[sqldump]
(8) $_CONFIG['mysql_host']
(9) $_CONFIG['mysql_pass']
(10) $_CONFIG['mysql_user']
(11) $database_name
(12) $sqlfile
```
```
攻撃が１リクエストで完結しないっぽい。
exec($_CONFIG[tarpath] . " $excluded_cmd -" . $_CONFIG['tarcompress'] . "vf $backup_file --update $file");
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-008079.html)


CVE-2014-9308
---

```
WP EasyCart 3.0.9 未満
WordPress 用 WP EasyCart (別名 WordPress Shopping Cart) プラグインの inc/amfphp/administration/banneruploaderscript.php には、ファイルをアップロードされることにより
リモート認証されたユーザにより、実行形式の拡張子を持つファイルをアップロードされ、その後、products/banners/ 配下のファイルへ直接のリクエストを介してアクセスされることで
```
```
#1: C:\xampp\htdocs\main\wp-easycart.3.0.8\wp-easycart\inc\amfphp\administration\banneruploaderscript.php:36
        $filename = $_FILES['Filedata']['name'];
#2: C:\xampp\htdocs\main\wp-easycart.3.0.8\wp-easycart\inc\amfphp\administration\banneruploaderscript.php:41
        $explodedfilename = pathinfo($filename);
#3: C:\xampp\htdocs\main\wp-easycart.3.0.8\wp-easycart\inc\amfphp\administration\banneruploaderscript.php:42
        $nameoffile = $explodedfilename['filename'];
#4: C:\xampp\htdocs\main\wp-easycart.3.0.8\wp-easycart\inc\amfphp\administration\banneruploaderscript.php:46
        move_uploaded_file($_FILES['Filedata']['tmp_name'], "../../../products/banners/".$nameoffile."_".$date.".".$fileextension);
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-007692.html)


CVE-2014-10021
---

```
WP Symposium 14.11
WordPress 用 WP Symposium プラグインの UploadHandler.php は、ファイルをアップロードされることにより
第三者により、実行形式の拡張子を持つファイルをアップロードされ、その後、server/php/ 配下のファイルの直接のリクエストを介してアクセスされること
```
```
#1: C:\xampp\htdocs\main\wp-symposium-14.11\server\php\UploadHandler.php:738
                    $files[] = $this->handle_file_upload(
#2: C:\xampp\htdocs\main\wp-symposium-14.11\server\php\UploadHandler.php:520
            $file->name = $this->get_file_name($name, $type, $index, $content_range);
#3: C:\xampp\htdocs\main\wp-symposium-14.11\server\php\UploadHandler.php:473
            return $this->get_unique_filename(
#4: C:\xampp\htdocs\main\wp-symposium-14.11\server\php\UploadHandler.php:459
            $name = trim(basename(stripslashes($name)), ".\x00..\x20");
#5: C:\xampp\htdocs\main\wp-symposium-14.11\server\php\UploadHandler.php:469
            return $name;
#6: C:\xampp\htdocs\main\wp-symposium-14.11\server\php\UploadHandler.php:473
            return $this->get_unique_filename(
#7: C:\xampp\htdocs\main\wp-symposium-14.11\server\php\UploadHandler.php:441
                $name = $this->upcount_name($name);
#8: C:\xampp\htdocs\main\wp-symposium-14.11\server\php\UploadHandler.php:431
            return preg_replace_callback(
#9: C:\xampp\htdocs\main\wp-symposium-14.11\server\php\UploadHandler.php:441
                $name = $this->upcount_name($name);
#10: C:\xampp\htdocs\main\wp-symposium-14.11\server\php\UploadHandler.php:450
                $name = $this->upcount_name($name);
#11: C:\xampp\htdocs\main\wp-symposium-14.11\server\php\UploadHandler.php:431
            return preg_replace_callback(
#12: C:\xampp\htdocs\main\wp-symposium-14.11\server\php\UploadHandler.php:450
                $name = $this->upcount_name($name);
#13: C:\xampp\htdocs\main\wp-symposium-14.11\server\php\UploadHandler.php:452
            return $name;
#14: C:\xampp\htdocs\main\wp-symposium-14.11\server\php\UploadHandler.php:473
            return $this->get_unique_filename(
#15: C:\xampp\htdocs\main\wp-symposium-14.11\server\php\UploadHandler.php:520
            $file->name = $this->get_file_name($name, $type, $index, $content_range);
#16: C:\xampp\htdocs\main\wp-symposium-14.11\server\php\UploadHandler.php:532
                $file_path = $this->get_upload_path($file->name);
#17: C:\xampp\htdocs\main\wp-symposium-14.11\server\php\UploadHandler.php:174
            $file_name = $file_name ? $file_name : '';
#18: C:\xampp\htdocs\main\wp-symposium-14.11\server\php\UploadHandler.php:176
            return $this->options['upload_dir'].$this->get_user_path()
#19: C:\xampp\htdocs\main\wp-symposium-14.11\server\php\UploadHandler.php:532
                $file_path = $this->get_upload_path($file->name);
#20: C:\xampp\htdocs\main\wp-symposium-14.11\server\php\UploadHandler.php:545
                        move_uploaded_file($uploaded_file, $file_path);
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-007613.html)


CVE-2014-9473
---

```
cforms II 14.7 およびそれ以前
WordPress 用 CformsII プラグインの lib_nonajax.php には、ファイルをアップロードされることにより
第三者により、cf_uploadfile2[] パラメータを介して、実行可能な拡張子を持つファイルをアップロードされ、その後、デフォルトのアップロードディレクトリにある当該ファイルへの直接のリクエストを介して
```
```
#1: C:\xampp\htdocs\main\cforms2.14.7\cforms2\lib_nonajax.php:237
                $value = ($user->user_url<>'')?$user->user_url:$_POST[$field_type];
#2: C:\xampp\htdocs\main\cforms2.14.7\cforms2\lib_nonajax.php:388
                        rename($_SESSION['cforms']['upload'][$n]['files'][$m],str_replace('xx',$subID,$_SESSION['cforms']['upload'][$n]['files'][$m]));
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-007546.html)


CVE-2014-9521
---

```
InfiniteWP admin panel 2.4.4 未満
InfiniteWP Admin Panel の uploadScript.php には、allWPFiles query パラメータが設定されている場合、ファイルをアップロードされることにより
第三者により、二重拡張子を持つファイルをアップロードされ、その後、アップロードディレクトリにある当該ファイルへ直接のリクエストを介してアクセスされることで
```
```
モジュールが手に入らない。
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-007517.html)


CVE-2014-8770
---

```
Magmi 0.7.17a およびそれ以前
Magento Community Edition (CE) 用 MAGMI (別名 Magento Mass Importer) プラグインの magmi/web/magmi.php には、ファイルをアップロードされることにより
リモート認証されたユーザにより、PHP ファイルを含む ZIP ファイルをアップロードされ、その後、magmi/plugins/ 配下の PHP ファイルに直接のリクエストを介してアクセスされることで
```
```
バグ位置が分からない。
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-005452.html)


CVE-2014-5460
---

```
Tribulant Slideshow Gallery 1.4.7 未満
WordPress 用 Tribulant Slideshow Gallery プラグインには、ファイルをアップロードされることにより
リモート認証されたユーザにより、PHP ファイルをアップロードされ、その後、wp-content/uploads/slideshow-gallery/ 配下のファイルへ直接のリクエストを介してアクセスされることで
```
```
#1: C:\xampp\htdocs\main\slideshow-gallery.1.4.6\slideshow-gallery\models\slide.php:115
                            $imagename = $_FILES['image_file']['name'];
#2: C:\xampp\htdocs\main\slideshow-gallery.1.4.6\slideshow-gallery\models\slide.php:117
                            $image_ext = GalleryHtmlHelper::strip_ext($imagename, "ext");
#3: C:\xampp\htdocs\main\slideshow-gallery.1.4.6\slideshow-gallery\helpers\html.php:252
                $extArray = preg_split("/[\.]/", $filename);
#4: C:\xampp\htdocs\main\slideshow-gallery.1.4.6\slideshow-gallery\helpers\html.php:260
                    $filename = $extArray[$p];
#5: C:\xampp\htdocs\main\slideshow-gallery.1.4.6\slideshow-gallery\helpers\html.php:261
                    return $filename;
#6: C:\xampp\htdocs\main\slideshow-gallery.1.4.6\slideshow-gallery\helpers\html.php:265
            return false;
#7: C:\xampp\htdocs\main\slideshow-gallery.1.4.6\slideshow-gallery\models\slide.php:117
                            $image_ext = GalleryHtmlHelper::strip_ext($imagename, "ext");
#8: C:\xampp\htdocs\main\slideshow-gallery.1.4.6\slideshow-gallery\models\slide.php:118
                            $imagename = GalleryHtmlHelper::sanitize($image_name) . '.' . $image_ext;
#9: C:\xampp\htdocs\main\slideshow-gallery.1.4.6\slideshow-gallery\models\slide.php:121
                            $imagefull = $imagepath . $imagename;
#10: C:\xampp\htdocs\main\slideshow-gallery.1.4.6\slideshow-gallery\models\slide.php:124
                            elseif (!move_uploaded_file($_FILES['image_file']['tmp_name'], $imagefull)) { $this -> errors['image_file'] = __('Image could not be moved from TMP to "wp-content/uploads/", please check permissions', $this -> plugin_name); }
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-004187.html)


CVE-2014-5519
---

```
PhpWiki 1.5.0
PhpWiki の Ploticus モジュール
第三者により、index.php/HeIp に対する edit[content] パラメータのデバイスオプションのシェルのメタ文字を介して
```
```
        $errstr = exec($cmd); //, $outarr, $returnval); // normally 127
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-004163.html)


CVE-2014-2223
---

```
Plogger 1.0 RC1 およびそれ以前
Plogger の plog-admin/plog-upload.php には、ファイルをアップロードされることにより
リモート認証されたユーザにより、PHP ファイル、およびゼロ以外の長さの PNG ファイルを含む ZIP ファイルをアップロードされ、plog-content/uploads/archive/ の ZIP ファイルに対する直接のリクエストを介して、PHP ファイルにアクセスされることで
```
```
#1: C:\xampp\htdocs\main\plogger-1.0RC1\plog-admin\plog-upload.php:143
                    $result = add_picture($album_id, $_FILES['userfile']['tmp_name'], $_FILES['userfile']['name'], $_REQUEST['caption'], $_REQUEST['description']);
#2: C:\xampp\htdocs\main\plogger-1.0RC1\plog-admin\plog-admin-functions.php:215
        $filename_parts = explode('.', strrev($filename), 2);
#3: C:\xampp\htdocs\main\plogger-1.0RC1\plog-admin\plog-admin-functions.php:217
        $filename_ext = strtolower(strrev($filename_parts[0]));
#4: C:\xampp\htdocs\main\plogger-1.0RC1\plog-admin\plog-admin-functions.php:257
        $final_filename = sanitize_filename($unique_filename_base).'.'.$filename_ext;
#5: C:\xampp\htdocs\main\plogger-1.0RC1\plog-admin\plog-admin-functions.php:260
        $final_fqfn = $config['basedir'].'plog-content/images/'.$create_path.'/'.$final_filename;
#6: C:\xampp\htdocs\main\plogger-1.0RC1\plog-admin\plog-admin-functions.php:273
            if (!move_uploaded_file($tmpname, $final_fqfn)) {
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-004161.html)


CVE-2014-5521
---

```
XRMS CRM
上記製品のバージョン 1.99.2
XRMS CRM の plugins/useradmin/fingeruser.php
リモート認証されたユーザにより、username パラメータのシェルのメタ文字を介して
```
```
<?php if ($username) {system("/usr/bin/ssh $ssh_user /usr/bin/finger $username");} ?>
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-004022.html)


CVE-2015-8761
---

```
Values 7.x-1.2 未満の 7.x-1.x
Drupal 用 Values モジュールは、パーミッションを適切にチェックしないため
"Import value sets" パーミッションを持つリモートの管理者により、ctools インポート内のエクスポートされた Values のリストを介して
```
```
バグの箇所が不明
```
[JVN](http://jvndb.jvn.jp/ja/contents/2015/JVNDB-2015-006744.html)


CVE-2015-7381
---

```
refbase 0.9.6 まで
Web Reference Database (別名 refbase) の install.php には、PHP リモートファイルインクルージョンの脆弱性
第三者により、(1) pathToMYSQL または (2) databaseStructureFile パラメータを介して
```
```
#1: C:\xampp\htdocs\main\refbase-0.9.5\install.php:52
            $adminUserName = $_POST['adminUserName'];
#2: C:\xampp\htdocs\main\refbase-0.9.5\install.php:439
            exec($pathToMYSQL . " -h " . $hostName . " -u " . $adminUserName . " -p" . $adminPassword . " --database=" . $databaseName . " < " . $databaseStructureFile . " 2>&1", $resultArray);
```
[JVN](http://jvndb.jvn.jp/ja/contents/2015/JVNDB-2015-006744.html)


CVE-2015-4726
---

```
AudioShare 2.0.2
AudioShare の ajax/myajaxphp.php には、PHP リモートファイルインクルージョンの脆弱性
第三者により、config['basedir'] パラメータの URL を介して
```
```
モジュールが手に入らない。
```
[JVN](http://jvndb.jvn.jp/ja/contents/2015/JVNDB-2015-003233.html)


CVE-2015-4338
---

```
XCloner 3.1.2
WordPress 用 XCloner プラグイン
リモート認証されたユーザにより、言語の Translation LM_FRONT_* フィールドを介して
```
```
バグの箇所が不明
```
[JVN](http://jvndb.jvn.jp/ja/contents/2015/JVNDB-2015-003200.html)


CVE-2015-1059
---

```
adaptcms 3.0.3
AdaptCMS の admin/files/add には、ファイルをアップロードされることにより、任意の PHP コードを実行される脆弱性が存在します。
リモート認証されたユーザにより、PHP 拡張子を持つファイルをアップロードされ、その後、/app/webroot/uploads 配下のファイルへ直接のリクエストを介して
```
```
CakePHPを使用している
バグの箇所が不明
```
[JVN](http://jvndb.jvn.jp/ja/contents/2015/JVNDB-2015-001059.html)


CVE-2015-2945
---

```
mt-phpincgi
mt-phpincgi は、Movable Type のテンプレートを PHP として動作させるスクリプトです。
```
```
#1: C:\xampp\htdocs\main\mt-phpincgi.php:3
        $cgiurl = $_GET["requrl"];
#2: C:\xampp\htdocs\main\mt-phpincgi.php:8
    $url = parse_url($cgiurl);
#3: C:\xampp\htdocs\main\mt-phpincgi.php:66
    $fp = fsockopen($url['host'], $url['port']) or exit("communication error");
#4: C:\xampp\htdocs\main\mt-phpincgi.php:70
        $response .= fgets($fp, 4096);
#5: C:\xampp\htdocs\main\mt-phpincgi.php:76
    $data = split("\r\n\r\n", $response, 2);
#6: C:\xampp\htdocs\main\mt-phpincgi.php:78
    $pagedata = $data[1];
#7: C:\xampp\htdocs\main\mt-phpincgi.php:96
    fwrite($fp, $pagedata) or exit("temporary file save error");
#8: C:\xampp\htdocs\main\mt-phpincgi.php:113
    include("./cgitmp/" . $tmp_filename);
```
[JVN](http://jvndb.jvn.jp/ja/contents/2015/JVNDB-2015-000067.html)


CVE-2014-9567
---

```
ProjectSend r100 から r561
ProjectSend (旧 cFTP) の process-upload.php
第三者により、PHP 拡張子のファイルをアップロードされ、その後、upload/files/ または upload/temp/ ディレクトリにある当該ファイルへ直接のリクエストを介して
```
```
モジュールが手に入らない
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-007536.html)


CVE-2014-9185
---

```
Morfy 1.05
Morfy CMS の install.php には、静的コードの挿入により、config.php に任意の PHP コードを挿入
リモート認証されたユーザにより、site_url パラメータを介して、config.php に任意の PHP コードを挿入
```
```
#1: C:\xampp\htdocs\main\morfy-cms-1.0.5\install.php:59
            $post_site_title = isset($_POST['site_title']) ? $_POST['site_title'] : '';
#2: C:\xampp\htdocs\main\morfy-cms-1.0.5\install.php:64
            file_put_contents('config.php', "<?php
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-007335.html)


CVE-2014-9280
---

```
MantisBT 1.2.18 未満
MantisBT の core/current_user_api.php の current_user_get_bug_filter 関数
第三者により、filter パラメータを介して
```
```
#1: C:\xampp\htdocs\main\mantisbt-release-1.2.17\core\gpc_api.php:61
            $t_result = gpc_strip_slashes( $_POST[$p_var_name] );
#2: C:\xampp\htdocs\main\mantisbt-release-1.2.17\core\gpc_api.php:481
            return $p_var;
#3: C:\xampp\htdocs\main\mantisbt-release-1.2.17\core\gpc_api.php:61
            $t_result = gpc_strip_slashes( $_POST[$p_var_name] );
#4: C:\xampp\htdocs\main\mantisbt-release-1.2.17\core\gpc_api.php:74
        return $t_result;
#5: C:\xampp\htdocs\main\mantisbt-release-1.2.17\core\gpc_api.php:106
        $t_result = call_user_func_array( 'gpc_get', $args );
#6: C:\xampp\htdocs\main\mantisbt-release-1.2.17\core\gpc_api.php:113
        return $t_result;
#7: C:\xampp\htdocs\main\mantisbt-release-1.2.17\core\current_user_api.php:194
        $f_filter_string = gpc_get_string( 'filter', '' );
#8: C:\xampp\htdocs\main\mantisbt-release-1.2.17\core\current_user_api.php:209
                $t_filter = unserialize( $f_filter_string );
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-005832.html)


CVE-2014-9001
---

```
Incredible PBX 11 2.0.6.5.0
Incredible PBX の reminders/index.php
リモート認証されたユーザにより、以下のパラメータのシェルのメタ文字を介して、任意のコマンドを実行される脆弱性が存在します。

(1) APPTMIN パラメータ
(2) APPTHR パラメータ
(3) APPTDA パラメータ
(4) APPTMO パラメータ
(5) APPTYR パラメータ
(6) APPTPHONE パラメータ
```
```
モジュールが手に入らない。
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-005567.html)


CVE-2014-8998
---

```
X7 Chat 2.0.0 から 2.0.5.1
X7 Chat の lib/message.php
リモート認証されたユーザにより、巧妙に細工された index.php の HTTP ヘッダを介して、eval switch を伴う preg_replace 関数によって処理
```
```
			$message = preg_replace("/(http:\/\/(.+?)\.[^ \[\"<]*)(.)/ie","autoparse_url(\"2\",\"$1\",\"$3\");",$message);
			$message = preg_replace("/(www\.(.+?)\.[^ \[\"<]*)(.)/ie","autoparse_url(\"1\",\"$1\",\"$3\");",$message);
			$message = preg_replace("/([^@\]\s]*)@(.+?)\.(.+?)([\s\[])/i","<a href=\"mailto: $1@$2.$3\">$1@$2.$3</a>$4",$message);
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-005553.html)


CVE-2014-3829
---

```
Centreon 2.5.1
Centreon Enterprise Server 2.2
Centreon および Centreon Enterprise Server の displayServiceStatus.php には、command_line 変数に関する処理に不備がある
第三者により、(1) session_id または (2) template_id パラメータのシェルのメタ文字を介して
```
```
iso
モジュールが手に入らない。
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-005068.html)


CVE-2014-6389
---

```
PHPCompta/NOALYSS 6.7.2 未満
PHPCompta/NOALYSS の backup.php
第三者により、d パラメータのシェルのメタ文字を介して
```
```
モジュールが手に入らない。
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-004596.html)


CVE-2014-5194
---

```
Sphider 1.3.6
Sphider の admin/admin.php には、静的コードの挿入により、任意の PHP コードを settings/conf.php に挿入
リモート認証されたユーザにより、_word_upper_bound パラメータを介して、任意の PHP コードを settings/conf.php に挿入
```
```
#1: C:\xampp\htdocs\main\sphider-1.3.6\admin\db_main.php:5
    extract($_REQUEST);
#2: C:\xampp\htdocs\main\sphider-1.3.6\admin\db_main.php:6
    var_dump($_version_nr);
#3: C:\xampp\htdocs\main\sphider-1.3.6\admin\configset.php:117
            fwrite($fhandle,"$"."version_nr            = '".$_version_nr. "';");
#4: C:\xampp\htdocs\main\sphider-1.3.6\admin\configset.php:237
    include "../settings/conf.php";
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-003710.html)


CVE-2014-5090
---

```
Status2K
Status2k の admin/options/logs.php
第三者により、Admin Panel の Add Logs の Location フィールド内のシェルのメタ文字を介して
```
```
モジュールが手に入らない
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-003682.html)


CVE-2014-5112
---

```
trixbox
Fonality trixbox の maint/modules/home/index.php
第三者により、lang パラメータのシェルのメタ文字を介して
```
```
モジュールが手に入らない
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-003606.html)


CVE-2014-3453
---

```
Flag 7.x-3.0
Flag 7.x-3.5
Drupal 用 Flag モジュールの includes/flag.export.inc の flag_import_form_validate 関数には、Eval インジェクションにより
リモート認証された管理者により、admin/structure/flags/import の "Flag import code" のテキストエリアを介して
```
```
リフレクションで呼び出しているっぽい。
  eval($form_state['values']['import']);
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-002539.html)


CVE-2014-2558
---

```
File Gallery 1.7.9.2 未満
WordPress 用 File Gallery プラグインは、文字列を適切にエスケープしないため
リモートの管理者により、/wp-admin/options-media.php の設定フィールド内の \' (バックスラッシュクォート) を介して
```
```
wordpressの関数を経由しているっぽい
```
				create_function(
					'',
					$anon),
```
```
			add_settings_field("size_" . $size, $translated_size . $size_translated, create_function('', 'echo file_gallery_options_fields( array("name" => "' . $size . '", "type" => "intermediate_image_sizes", "disabled" => 0) );'), 'media', 'intermediate_image_sizes');
```
[JVN](http://jvndb.jvn.jp/ja/contents/2014/JVNDB-2014-002387.html)
