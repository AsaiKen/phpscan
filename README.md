# PHPSCAN

### 概要

PHPの静的解析ツールです。  
PHPのコードを擬似的に実行して脆弱性を検出します。  

以下の特徴があります。  

* オブジェクト指向のPHPに対応
* リフレクションに対応
* エスケープ処理の有無を考慮して脆弱性を判別

以下の脆弱性を検査することができます。  

* Remote Code Execution
* Local File Inclusion
* SQL Injection
* Path Manipulation
* Server Side Request Forgery
* Cross Site Scripting
* Object Injection
* PHP File Manipulation
* XML External Entity

検査結果はHTML形式のレポートとして出力されます。  
検査レポートには、ユーザ入力値が初めて代入された地点から危険な関数で使用される地点までの呼び出しの経路が記載されます。  

### 動作環境

以下の環境で動作確認しました。  

* OS: Ubuntu 18.04
* ミドルウェア: jdk1.8.0_242

### ビルド方法

`gradlew build`を実行すると、phpscan-1.0-SNAPSHOTディレクトリが作成されます。  

### 実行方法

CMSのプラグインを検査する場合を一例として説明します。  

1. setting.propertiesをテキストエディタで開きます。
2. ENTRY_POINT_PATHにプラグインのディレクトリのパスを記入します。
3. PROJECT_PATHにCMS本体のディレクトリのパスを記入します。
4. PHP_INI_PATHにphp.iniファイルのパスを記入します。
5. `phpscan-1.0-SNAPSHOT/bin/phpscan`を実行すると、プラグインのディレクトリ内のPHPファイル群に対して検査を実行します。
6. 脆弱性があれば、resultディレクトリ内に検査レポートが作成されます。

### 動作設定

動作設定はsetting.propertiesで行います。  

#### ENTRY_POINT_PATH

検査対象のPHPファイル群が配置されたディレクトリのパスを指定します。  
ENTRY_POINT_PATHとENTRY_POINT_PARENT_PATHは、いずれかを必ず入力する必要があります。  

#### ENTRY_POINT_PARENT_PATH

ENTRY_POINT_PATHが複数存在する場合に使用します。  
ENTRY_POINT_PATH群を1つのディレクトリ内に配置し、この項目にそのディレクトリのパスを指定することで、ENTRY_POINT_PATH群を検査することができます。  
例えば、同時に複数のプラグインを検査したい場合に、プラグインのディレクトリ群を配置したディレクトリのパスを指定します。  
ENTRY_POINT_PATHとENTRY_POINT_PARENT_PATHは、いずれかを必ず入力する必要があります。  

#### PROJECT_PATH

検査対象のPHPファイルを実行するために必要なPHPファイル群が配置されているディレクトリのパスを指定します。  
このディレクトリ内にあるPHPファイル群は検査対象になりません。  
例えば、CMSのプラグインを検査する場合は、この項目にCMS本体のディレクトリのパスを指定します。  
未指定の場合、ENTRY_POINT_PATHとENTRY_POINT_PARENT_PATHのいずれかの値が使用されます。  

#### PHP_INI_PATH

php.iniファイルのパスを指定します。  
入力必須の項目です。  

#### IGNORE_TXT_PATH

検査から除外するPHPファイルのパスを記載したテキストファイルのパスを指定します。  

#### IGNORE_REGEXPS

検査から除外するパスを正規表現で指定します。  
コンマ区切りで複数指定することができます。  

#### SQL_ESCAPE_FUNCTIONS

アプリケーションが独自にSQL文の文字列をエスケープする関数を実装している場合に、その関数名を指定します。  
コンマ区切りで複数指定することができます。  

#### HTML_ESCAPE_FUNCTIONS

アプリケーションが独自にHTMLの文字列をエスケープする関数を実装している場合に、その関数名を指定します。  
コンマ区切りで複数指定することができます。  

#### DISABLED_VULNERABILITY_CATEGORIES

検査から除外する脆弱性のカテゴリを指定します。  
指定したカテゴリは検査レポートに表示されません。  

指定できる値は以下の通りです。  

* Remote Code Execution
* Local File Inclusion
* SQL Injection
* Path Manipulation
* Server Side Request Forgery
* Cross Site Scripting
* Object Injection
* PHP File Manipulation

コンマ区切りで複数指定することができます。  

#### PHP_FILE_EXTENSIONS

PHPファイルとして認識する拡張子を記載します。  
コンマ区切りで複数指定することができます。  
未指定の場合、phpとincの2つの拡張子がPHPファイルとして認識されます。  

#### USED_FRAMEWORKS

アプリケーションの使用しているフレームワーク、CMSを指定します。  
指定することで検査の網羅性を上げることができます。  

指定できる値は以下の通りです。  

* WordPress

コンマ区切りで複数指定することができます。  

### 発見した脆弱性

以下のサイトに掲載されている脆弱性のうち、PHP関連のものはすべてPHPSCANで発見しています。  

[http://jvndb.jvn.jp/search/index.php?mode=_vulnerability_search_IA_VulnSearch&lang=ja&useSynonym=1&keyword=%90%F3%88%E4%81%40%8C%92](http://jvndb.jvn.jp/search/index.php?mode=_vulnerability_search_IA_VulnSearch&lang=ja&useSynonym=1&keyword=%90%F3%88%E4%81%40%8C%92)

### その他

PHP7の機能には未対応です。  

### ライセンス

This software is released under the MIT License, see LICENSE.txt.  

